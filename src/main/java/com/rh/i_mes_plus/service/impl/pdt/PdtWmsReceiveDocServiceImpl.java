package com.rh.i_mes_plus.service.impl.pdt;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtWmsReceiveDocDTO;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsReceiveDocMapper;
import com.rh.i_mes_plus.model.other.WmsProjectBase;
import com.rh.i_mes_plus.model.pdt.*;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.other.IWmsProjectBaseService;
import com.rh.i_mes_plus.service.pdt.*;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 入库单表
 *
 * @author hbq
 * @date 2020-12-29 15:41:49
 */
@Slf4j
@Service
public class PdtWmsReceiveDocServiceImpl extends ServiceImpl<PdtWmsReceiveDocMapper, PdtWmsReceiveDoc> implements IPdtWmsReceiveDocService {
    @Resource
    private PdtWmsReceiveDocMapper pdtWmsReceiveDocMapper;
    @Autowired
    private IPdtWmsReceiveDetailService pdtWmsReceiveDetailService;
    @Autowired
    private IPdtWmsReceiveDetailSubService pdtWmsReceiveDetailSubService;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private IPdtWmsBoxService pdtWmsBoxService;
    @Autowired
    private IPdtWmsStockInfoService pdtWmsStockInfoService;
    @Autowired
    private IPdtWmsBoxBarcodeService pdtWmsBoxBarcodeService;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<Map> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return pdtWmsReceiveDocMapper.findList(pages, params);
    }

    @Override
    public Result getDocNo(Map<String, Object> map) {
        String depaCode = MapUtil.getStr(map, "depaCode");
        String dtCode = MapUtil.getStr(map, "dtCode");
        String prefix=depaCode + dtCode + DateUtil.format(new Date(), "yyyyMM");
        String maxDocNo=prefix+"0001";
        PdtWmsReceiveDoc one = getOne(new QueryWrapper<PdtWmsReceiveDoc>()
                .last("where doc_no like '" + prefix + "%' order by doc_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getDocNo(), prefix));
            maxDocNo=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxDocNo, "查询成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveAll(PdtWmsReceiveDocDTO pdtWmsReceiveDocDTO) {
        String finalDocNo="";
        try{
            PdtWmsReceiveDoc pdtWmsReceiveDoc=pdtWmsReceiveDocDTO;
            String docNo = pdtWmsReceiveDoc.getDocNo();
            finalDocNo=docNo;
            hasPda(docNo);
            saveOrUpdate(pdtWmsReceiveDoc);
            //删除原有docNo的详情
            pdtWmsReceiveDetailService.remove(new QueryWrapper<PdtWmsReceiveDetail>().eq("doc_no",docNo));
            List<PdtWmsReceiveDetail> pdtWmsReceiveDetails = pdtWmsReceiveDocDTO.getPdtWmsReceiveDetails();
            System.out.println(pdtWmsReceiveDetails);
            pdtWmsReceiveDetails.forEach(u->u.setDocNo(docNo));
            pdtWmsReceiveDetailService.saveBatch(pdtWmsReceiveDetails);
            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed(  "单号："+finalDocNo+"已扫码，无法修改");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result removeAll(Map<String, List<Long>> map) {
        String finalDocNo="";
        try{
            List<Long> ids = map.get("ids");
            for (Long id : ids) {
                PdtWmsReceiveDoc pdtWmsReceiveDoc = getById(id);
                String docNo = pdtWmsReceiveDoc.getDocNo();
                finalDocNo=docNo;
                hasPda(docNo);
                pdtWmsReceiveDetailService.remove(new QueryWrapper<PdtWmsReceiveDetail>().eq("doc_no",docNo));
            }
            removeByIds(ids);
            return Result.succeed("删除成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed("单号："+finalDocNo+"已扫码，无法删除");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result pdaPdtReceive(Map<String, Object> map) {
        String finalBarcode="";
        try{
            String boxNo = MapUtil.getStr(map, "boxNo");
            String empNo = MapUtil.getStr(map, "empNo");
            String docNum = MapUtil.getStr(map, "docNum");
            String dtCode = MapUtil.getStr(map, "dtCode");
            UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
            if (user == null) {
                return Result.failed("无此员工");
            }
            String empName=user.getUserName();
            PdtWmsBox box = pdtWmsBoxService.getOne(new QueryWrapper<PdtWmsBox>()
                    .eq("box_no", boxNo));
            if (box == null) {
                return Result.failed("无此箱号");
            }
            List<PdtWmsStockInfo> list = pdtWmsStockInfoService.list(new LambdaQueryWrapper<PdtWmsStockInfo>()
                    .eq(PdtWmsStockInfo::getBoxNo, boxNo)
            );
            if (list!=null&&list.size()>0) {
                return Result.failed("该箱号已在库中");
            }
            Long skAmount = box.getBoxQty();

            String modelCode = box.getModelCode();
            String modelName = box.getModelName();
            PdtWmsReceiveDoc receiveDoc = getOne(new QueryWrapper<PdtWmsReceiveDoc>()
                    .eq("doc_no", docNum));
            if (receiveDoc == null) {
                return Result.failed("无此单号");
            }
            if(!("1".equals(receiveDoc.getStatus())||"2".equals(receiveDoc.getStatus()))){
                return Result.failed("此单号不为录入状态");
            }
            if (!receiveDoc.getDtCode().equals(dtCode)){
                return Result.failed("扫码类型出错");
            }
            if (!modelCode.equals(receiveDoc.getModelCode())){
                return Result.failed("扫码料号不符");
            }
            PdtWmsReceiveDetail receiveDetail = pdtWmsReceiveDetailService.getOne(new QueryWrapper<PdtWmsReceiveDetail>()
                    .eq("doc_no", docNum)
                    .eq("model_code", modelCode)
            );
            if (receiveDetail != null){
                Long planTotal=receiveDetail.getPlanNum();
                Long receiveTotal=receiveDetail.getReceiveNum();
                Long remainTotal=planTotal-receiveTotal;
                if (remainTotal<skAmount){
                    return Result.failed("条码数量超出待收数量");
                }
                List<PdtWmsBoxBarcode> boxBarcodes = pdtWmsBoxBarcodeService.list(new QueryWrapper<PdtWmsBoxBarcode>().eq("box_no", boxNo));
                for (PdtWmsBoxBarcode boxBarcode : boxBarcodes) {
                    String barcode = boxBarcode.getBarcode();
                    PdtWmsStockInfo stockInfo = pdtWmsStockInfoService.getOne(new QueryWrapper<PdtWmsStockInfo>()
                            .eq("doc_no", docNum)
                            .eq("barcode", barcode)

                    );
                    if (stockInfo!=null){
                        finalBarcode=barcode;
                        int i=1/0;
                        return Result.failed("重复扫码");
                    }
                    /**
                     * 绑定入库条码明细
                     * */
                    PdtWmsReceiveDetailSub pdtWmsReceiveDetailSub = new PdtWmsReceiveDetailSub();
                    pdtWmsReceiveDetailSub.setDocNo(docNum);
                    pdtWmsReceiveDetailSub.setRdId(receiveDetail.getId());
                    pdtWmsReceiveDetailSub.setBoxNo(boxNo);
                    pdtWmsReceiveDetailSub.setModelCode(modelCode);
                    pdtWmsReceiveDetailSub.setModelName(box.getModelName());
                    pdtWmsReceiveDetailSub.setBatch(box.getBatch());
                    pdtWmsReceiveDetailSub.setBarcode(barcode);
                    pdtWmsReceiveDetailSub.setReceiveManName(user.getUserName());
                    PdtWmsReceiveDetailSub one = pdtWmsReceiveDetailSubService.getOne(new QueryWrapper<PdtWmsReceiveDetailSub>()
                            .eq("doc_no", docNum)
                            .eq("barcode", barcode)
                    );
                    if (one!=null){
                        pdtWmsReceiveDetailSubService.update(pdtWmsReceiveDetailSub,new UpdateWrapper<PdtWmsReceiveDetailSub>()
                                .eq("doc_no", docNum)
                                .eq("barcode", barcode)
                        );
                    }else{
                        pdtWmsReceiveDetailSubService.save(pdtWmsReceiveDetailSub);
                    }
                    /**
                     * 添加库存表
                     * */
                    PdtWmsStockInfo newStockInfo = new PdtWmsStockInfo();
                    newStockInfo.setDocNo(docNum);
                    newStockInfo.setModelCode(modelCode);
                    newStockInfo.setModelName(modelName);
                    newStockInfo.setCustCode(receiveDoc.getCustomer());
                    newStockInfo.setModelVer(box.getModelVer());
                    newStockInfo.setBarcode(boxBarcode.getBarcode());
                    newStockInfo.setBatch(box.getBatch());
                    newStockInfo.setBoxNo(box.getBoxNo());
                    newStockInfo.setAmount(1L);
                    newStockInfo.setWhCode(receiveDoc.getWhCode());
                    newStockInfo.setSupplierCode(receiveDoc.getSupperCode());
                    newStockInfo.setPdate(new Date());
                    newStockInfo.setInStockTime(receiveDoc.getInDate());
                    newStockInfo.setInStockMan(empName);
                    newStockInfo.setNumberType(1L);
                    newStockInfo.setStockFlag("1");
                    pdtWmsStockInfoService.save(newStockInfo);

                }
                /**
                 * 更新入库单详情
                 */
                receiveDetail.setReceiveNum(receiveDetail.getReceiveNum()+skAmount);
                pdtWmsReceiveDetailService.updateById(receiveDetail);
                /**
                 * 更新入库单状态
                 * */
                receiveDoc.setStatus("2");

                updateById(receiveDoc);
                //pda操作添加日志
                /*pdaMesLogService.save(PdaMesLog.builder()
                        .name("成品入库")
                        .context("入库单号:" + docNum + " 入库箱号:" + boxNo +" 操作人："+user.getUserName())
                        .build()
                );*/
            }
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed("条码:"+finalBarcode+"重复扫码");
        }
        return Result.succeed("扫描成功");
    }

    @Override
    public List<String>  getReceiveDocListByDtCode(Map<String, Object> map) {
        return pdtWmsReceiveDocMapper.getReceiveDocListByDtCode(map);
    }

    @Override
    public Result close(Map<String, Object> map) {
        String empNo = MapUtil.getStr(map, "empNo");
        String docNum = MapUtil.getStr(map, "docNum");
        UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
        if (user == null) {
            return Result.failed("无此员工");
        }
        PdtWmsReceiveDoc receiveDoc = getOne(new QueryWrapper<PdtWmsReceiveDoc>()
                .eq("doc_no", docNum));
        if (receiveDoc==null){
            return Result.failed("无此单号");
        }
        int receiveDetailTotal = pdtWmsReceiveDetailService.count(new QueryWrapper<PdtWmsReceiveDetail>().eq("doc_no", docNum));
        int receivedCount = pdtWmsReceiveDetailService.count(new QueryWrapper<PdtWmsReceiveDetail>().eq("doc_no", docNum).last("and plan_num=receive_num"));
        if (receiveDetailTotal!=receivedCount){
            return Result.failed("实收数量不符");
        }
        receiveDoc.setStatus("4");
        updateById(receiveDoc);
        return Result.succeed("关结成功");
    }

    /**判断该单号是否已经过pda扫码，如果是不允许删除*/
    private void hasPda(String docNo){
        List<PdtWmsReceiveDetailSub> pdtWmsReceiveDetailSubs = pdtWmsReceiveDetailSubService.list(new QueryWrapper<PdtWmsReceiveDetailSub>().eq("doc_no", docNo));
        if (pdtWmsReceiveDetailSubs!=null&&pdtWmsReceiveDetailSubs.size()>0){
            int i=1/0;
        }
    }
}
