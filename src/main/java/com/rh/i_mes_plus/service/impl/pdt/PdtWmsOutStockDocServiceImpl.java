package com.rh.i_mes_plus.service.impl.pdt;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtWmsOutStockDocDTO;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsOutStockDocMapper;
import com.rh.i_mes_plus.model.pdt.*;
import com.rh.i_mes_plus.model.ums.UmsUser;
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
 * 成品出库单主表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
@Slf4j
@Service
public class PdtWmsOutStockDocServiceImpl extends ServiceImpl<PdtWmsOutStockDocMapper, PdtWmsOutStockDoc> implements IPdtWmsOutStockDocService {
    @Resource
    private PdtWmsOutStockDocMapper pdtWmsOutStockDocMapper;
    @Autowired
    private IPdtWmsOutStockListService pdtWmsOutStockListService;
    @Autowired
    private IPdtWmsOutStockDetailService pdtWmsOutStockDetailService;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private IPdtWmsStockInfoService pdtWmsStockInfoService;
    @Autowired
    private IPdtWmsBoxService pdtWmsBoxService;
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
        return pdtWmsOutStockDocMapper.findList(pages, params);
    }

    @Override
    public Result getDocNo(Map<String, Object> map) {
        String depaCode = MapUtil.getStr(map, "depaCode");
        String dtCode = MapUtil.getStr(map, "dtCode");
        String prefix=depaCode + dtCode + DateUtil.format(new Date(), "yyyyMM");
        String maxDocNo=prefix+"0001";
        PdtWmsOutStockDoc one = getOne(new QueryWrapper<PdtWmsOutStockDoc>()
                .last("where doc_no like '" + prefix + "%' order by doc_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getDocNo(), prefix));
            maxDocNo=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxDocNo, "查询成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveAll(PdtWmsOutStockDocDTO pdtWmsOutStockDocDTO) {
        String finalDocNo="";
        try{
        PdtWmsOutStockDoc pdtWmsOutStockDoc=pdtWmsOutStockDocDTO;
        String docNo = pdtWmsOutStockDoc.getDocNo();
        finalDocNo=docNo;
        hasPda(docNo);
        saveOrUpdate(pdtWmsOutStockDoc);
        //删除原有docNo的详情
        pdtWmsOutStockDetailService.remove(new QueryWrapper<PdtWmsOutStockDetail>().eq("doc_no",docNo));

        List<PdtWmsOutStockDetail> pdtWmsOutStockDetails = pdtWmsOutStockDocDTO.getPdtWmsOutStockDetails();
        pdtWmsOutStockDetails.forEach(u->u.setDocNo(docNo));
        pdtWmsOutStockDetailService.saveBatch(pdtWmsOutStockDetails);
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
                PdtWmsOutStockDoc pdtWmsOutStockDoc = getById(id);
                String docNo = pdtWmsOutStockDoc.getDocNo();
                finalDocNo=docNo;
                hasPda(docNo);
                pdtWmsOutStockDetailService.remove(new QueryWrapper<PdtWmsOutStockDetail>().eq("doc_no",docNo));
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
    public Result pdaPdtOutStock(Map<String, Object> map) {
        try{
            //此处boxNo是箱码
            String boxNo = MapUtil.getStr(map, "boxNo");
            String empNo = MapUtil.getStr(map, "empNo");
            String docNum = MapUtil.getStr(map, "docNum");
            String dtCode = MapUtil.getStr(map, "dtCode");
            UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
            if (user == null) {
                return Result.failed("无此员工");
            }
            String empName=user.getUserName();
            PdtWmsOutStockDoc outStockDoc = getOne(new QueryWrapper<PdtWmsOutStockDoc>()
                    .eq("doc_no", docNum));
            if (outStockDoc == null) {
                return Result.failed("无此单号");
            }
            if (!outStockDoc.getDtCode().equals(dtCode)){
                return Result.failed("扫码类型出错");
            }
            PdtWmsBox box = pdtWmsBoxService.getOne(new QueryWrapper<PdtWmsBox>()
                    .eq("box_no", boxNo));
            if (box == null) {
                return Result.failed("无此箱号");
            }
            Long skAmount = box.getBoxQty();

            String modelCode = box.getModelCode();
            if(skAmount<=0){
                return Result.failed("剩余数量不足");
            }
            List<PdtWmsOutStockDetail> outStockDetails = pdtWmsOutStockDetailService.list(new QueryWrapper<PdtWmsOutStockDetail>()
                    .eq("model_code", modelCode)
                    .eq("doc_no", docNum)
                    .last("and plan_num!=receive_num")
            );
            if (outStockDetails==null||outStockDetails.size()<=0){
                return Result.failed("出库单与物料不匹配");
            }
            //需要判断所扫箱码是否小于剩余总数量
            Long planTotal=0l;
            Long receiveTotal=0l;
            for (PdtWmsOutStockDetail outStockDetail : outStockDetails) {
                planTotal+=outStockDetail.getPlanNum();
                receiveTotal+=outStockDetail.getReceiveNum();
            }
            Long remainTotal=planTotal-receiveTotal;
            if (remainTotal<skAmount){
                return Result.failed("条码数量超出待收数量");
            }
            List<PdtWmsStockInfo> stockInfos = pdtWmsStockInfoService.list(new QueryWrapper<PdtWmsStockInfo>().eq("box_no", boxNo));
            if (stockInfos == null||stockInfos.size()<=0) {
                return Result.failed("无此箱码");
            }
            if ("2".equals(stockInfos.get(0).getStockFlag())){
                return Result.failed("该箱号已备料");
            }
            int i=0;
            for (PdtWmsStockInfo stockInfo : stockInfos) {
                PdtWmsOutStockDetail outStockDetail = outStockDetails.get(i);
                if ("1".equals(stockInfo.getStockFlag())) {
                    //修改仓库信息
                    stockInfo.setStockFlag("2");
                    pdtWmsStockInfoService.updateById(stockInfo);

                    //添加list表
                    PdtWmsOutStockList pdtWmsOutStockList = new PdtWmsOutStockList();
                    pdtWmsOutStockList.setDocNo(docNum);
                    pdtWmsOutStockList.setOsdId(outStockDetail.getId());
                    pdtWmsOutStockList.setBoxNo(boxNo);
                    pdtWmsOutStockList.setBarcode(stockInfo.getBarcode());
                    pdtWmsOutStockList.setModelCode(stockInfo.getModelCode());
                    pdtWmsOutStockList.setModelName(stockInfo.getModelName());
                    pdtWmsOutStockList.setAmount(1L);
                    pdtWmsOutStockList.setOutStockManName(empName);
                    pdtWmsOutStockList.setOutStockTime(new Date());
                    pdtWmsOutStockList.setRemark("");
                    pdtWmsOutStockListService.save(pdtWmsOutStockList);
                    //修改DETAIL表数据
                    outStockDetail.setReceiveNum(outStockDetail.getReceiveNum()+1);
                    pdtWmsOutStockDetailService.updateById(outStockDetail);
                    if (outStockDetail.getReceiveNum()==outStockDetail.getPlanNum()){
                        i++;
                    }
                }
            }

            //修改出库单状态
            outStockDoc.setDocStatus("2");

            updateById(outStockDoc);

            if ("29".equals(dtCode)){
                //如果是返工出库，需要删除入库单信息
                /*List<PdtWmsReceiveDetailSub> subs = pdtWmsReceiveDetailSubService.list(new QueryWrapper<PdtWmsReceiveDetailSub>()
                        .eq("box_no", boxNo)
                );
                String receiveDocNo = subs.get(0).getDocNo();
                PdtWmsReceiveDetail receiveDetail = pdtWmsReceiveDetailService.getOne(new QueryWrapper<PdtWmsReceiveDetail>().eq("doc_no", receiveDocNo));
                if (subs == null||receiveDetail == null) {
                    return Result.failed("入库单信息错误");
                }*/

                /* 1.删除仓库信息 */
                pdtWmsStockInfoService.remove(new QueryWrapper<PdtWmsStockInfo>()
                        .eq("box_no",boxNo)
                );
                /* 2.删除入库单条码绑定 */
                /*pdtWmsReceiveDetailSubService.remove(new QueryWrapper<PdtWmsReceiveDetailSub>()
                        .eq("box_no",boxNo)
                );*/
                /* 3.修改入库详情数量 */
                /*receiveDetail.setPlanNum(receiveDetail.getPlanNum()-skAmount);
                receiveDetail.setReceiveNum(receiveDetail.getReceiveNum()-skAmount);
                pdtWmsReceiveDetailService.updateById(receiveDetail);*/

            }
            /*pdaMesLogService.save(PdaMesLog.builder()
                    .name("成品出库")
                    .context("出库单号:" + docNum + " 出库箱码:" + boxNo +" 操作人:"+user.getUserName())
                    .build()
            );*/
            return Result.succeed("出库成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "出库失败");
        }
    }

    @Override
    public List<String> getOutStockDocListByDtCode(Map<String, Object> map) {
        return pdtWmsOutStockDocMapper.getOutStockDocListByDtCode(map);
    }

    @Override
    public Result statement(Map<String, Object> map) {
        List<Map<String,Object>> result=pdtWmsOutStockDocMapper.statement(map);
        List<Map<String,Object>> smsWmsOutStockMap=pdtWmsOutStockDetailService.getListByDocNo(map);
        for (Map<String, Object> smsWmsOutStock : smsWmsOutStockMap) {
            Long planNum = MapUtil.getLong(smsWmsOutStock, "planNum");
            Long receiveNum = MapUtil.getLong(smsWmsOutStock, "receiveNum");
            if (planNum-receiveNum>0){
                List<Map<String,Object>> outStockDocS = pdtWmsOutStockDocMapper.statementStock(MapUtil.getStr(smsWmsOutStock,"modelCode"));
                for (Map<String, Object> outStockDoc : outStockDocS) {
                    outStockDoc.putAll(smsWmsOutStock);
                    result.add(outStockDoc);
                }
            }
        }
        return Result.succeed(result,"查询成功");
    }

    @Override
    public Result close(Map<String, Object> map) {
        String empNo = MapUtil.getStr(map, "empNo");
        String docNum = MapUtil.getStr(map, "docNum");
        UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
        if (user == null) {
            return Result.failed("无此员工");
        }
        PdtWmsOutStockDoc outStockDoc = getOne(new QueryWrapper<PdtWmsOutStockDoc>()
                .eq("doc_no", docNum));
        if (outStockDoc==null){
            return Result.failed("无此单号");
        }
        int totalCount = pdtWmsOutStockDetailService.count(new QueryWrapper<PdtWmsOutStockDetail>().eq("doc_no", docNum));
        int outedCount = pdtWmsOutStockDetailService.count(new QueryWrapper<PdtWmsOutStockDetail>().eq("doc_no", docNum).last("and plan_num=receive_num"));
        if (totalCount==outedCount) {
            return Result.failed("实收数量不符");
        }
        outStockDoc.setDocStatus("4");
        updateById(outStockDoc);
        return Result.succeed("关结成功");
    }


    /**判断该单号是否已经过pda扫码，如果是不允许删除*/
    private void hasPda(String docNo){
        List<PdtWmsOutStockList> pdtWmsOutStockLists = pdtWmsOutStockListService.list(new QueryWrapper<PdtWmsOutStockList>().eq("doc_no", docNo));
        if (pdtWmsOutStockLists!=null&&pdtWmsOutStockLists.size()>0){
            int i=1/0;
        }
    }
}
