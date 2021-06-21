package com.rh.i_mes_plus.service.impl.sms;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.dto.SmsWmsReceiveAllDTO;
import com.rh.i_mes_plus.mapper.sms.SmsWmsReceiveDocMapper;
import com.rh.i_mes_plus.model.other.PdaMesLog;
import com.rh.i_mes_plus.model.sms.*;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.iqc.IIqcOqaService;
import com.rh.i_mes_plus.service.other.IPdaMesLogService;
import com.rh.i_mes_plus.service.sms.*;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购入库单表
 *
 * @author hqb
 * @date 2020-10-07 15:25:51
 */
@Slf4j
@Service
public class SmsWmsReceiveDocServiceImpl extends ServiceImpl<SmsWmsReceiveDocMapper, SmsWmsReceiveDoc> implements ISmsWmsReceiveDocService {
    @Resource
    private SmsWmsReceiveDocMapper smsWmsReceiveDocMapper;
    @Resource
    private ISmsWmsBarcodeInfoService smsWmsBarcodeInfoService;
    @Resource
    private ISmsWmsReceiveDetailService smsWmsReceiveDetailService;
    @Autowired
    private ISmsWmsPoDetService smsWmsPoDetService;
    @Autowired
    private ISmsWmsReceiveDetailSubService smsWmsReceiveDetailSubService;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private ISmsWmsStockInfoService smsWmsStockInfoService;
    @Autowired
    @Lazy
    private IIqcOqaService iqcOqaService;
    @Autowired
    private ISmsWmsReloadDocDetailService smsWmsReloadDocDetailService;
    @Autowired
    private ISmsWmsReloadDocService smsWmsReloadDocService;
    @Autowired
    private IPdaMesLogService pdaMesLogService;
    @Autowired
    private ISmsWmsIoTypeService smsWmsIoTypeService;
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
        return smsWmsReceiveDocMapper.findList(pages, params);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveAll(SmsWmsReceiveAllDTO smsWmsReceiveAllDTO) {
        try{
            SmsWmsReceiveDoc smsWmsReceiveDoc = smsWmsReceiveAllDTO.getSmsWmsReceiveDoc();

            List<SmsWmsReceiveDetail> smsWmsReceiveDetails = smsWmsReceiveAllDTO.getSmsWmsReceiveDetails();
            List<SmsWmsBarcodeInfo> smsWmsBarcodeInfos = smsWmsReceiveAllDTO.getSmsWmsBarcodeInfos();
            String finalNewWrDocNum = smsWmsReceiveDoc.getWrDocNum();
            smsWmsReceiveDoc.setWrDocNum(finalNewWrDocNum);
            smsWmsBarcodeInfos.forEach(u->u.setTblDocNum(finalNewWrDocNum));
            smsWmsReceiveDetails.forEach(u->u.setWrDocNum(finalNewWrDocNum));
            smsWmsReceiveDocMapper.insert(smsWmsReceiveDoc);
            smsWmsBarcodeInfoService.saveBatch(smsWmsBarcodeInfos);
            smsWmsReceiveDetailService.saveBatch(smsWmsReceiveDetails);
            /**
             * 更新po——det信息
             */
            for (SmsWmsReceiveDetail smsWmsReceiveDetail : smsWmsReceiveDetails) {
                String erpPo = smsWmsReceiveDetail.getErpPo();
                Long erpPoItemNo = smsWmsReceiveDetail.getErpPoItemNo();
                SmsWmsPoDet smsWmsPoDet = smsWmsPoDetService.getOne(new UpdateWrapper<SmsWmsPoDet>()
                        .eq("twd_po_no", erpPo)
                        .eq("twd_po_item_no", erpPoItemNo)
                        .eq("is_del", 0)
                );
                if (smsWmsPoDet != null) {
                    smsWmsPoDet.setMesReceiveQty(smsWmsPoDet.getMesReceiveQty()+smsWmsReceiveDetail.getOrderNum());
                    smsWmsPoDetService.updateById(smsWmsPoDet);
                }
            }
            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "保存失败");
        }
    }
    // TODO: 2021/3/18  修改
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateAll(SmsWmsReceiveAllDTO smsWmsReceiveAllDTO) {
        try{
            SmsWmsReceiveDoc smsWmsReceiveDoc = smsWmsReceiveAllDTO.getSmsWmsReceiveDoc();
            SmsWmsReceiveDoc existSmsWmsReceiveDoc = getById(smsWmsReceiveDoc.getId());
            existSmsWmsReceiveDoc.setWrDate(smsWmsReceiveDoc.getWrDate());
            String wrDocNum = existSmsWmsReceiveDoc.getWrDocNum();
            //List<SmsWmsReceiveDetail> smsWmsReceiveDetails = smsWmsReceiveAllDTO.getSmsWmsReceiveDetails();
            List<SmsWmsBarcodeInfo> smsWmsBarcodeInfos = smsWmsReceiveAllDTO.getSmsWmsBarcodeInfos();
            smsWmsBarcodeInfos.forEach(u->u.setTblDocNum(wrDocNum));
            //smsWmsReceiveDetails.forEach(u->u.setWrDocNum(wrDocNum));
            if (wrDocNum != null) {
                smsWmsBarcodeInfoService.remove(new QueryWrapper<SmsWmsBarcodeInfo>().eq("TBL_DOC_NUM",wrDocNum));
            }
            smsWmsReceiveDocMapper.updateById(existSmsWmsReceiveDoc);
            //smsWmsReceiveDetailService.saveOrUpdateBatch(smsWmsReceiveDetails);
            smsWmsBarcodeInfoService.saveOrUpdateBatch(smsWmsBarcodeInfos);
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "修改失败");
        }
        return Result.succeed("修改成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result deleteAll(Map<String, List<String>> map) {
        try{
            List<String> wrDocNums = map.get("wrDocNums");
            for (String wrDocNum : wrDocNums) {
                smsWmsReceiveDocMapper.delete(new QueryWrapper<SmsWmsReceiveDoc>().eq("WR_DOC_NUM",wrDocNum));
                smsWmsReceiveDetailService.remove(new QueryWrapper<SmsWmsReceiveDetail>().eq("WR_DOC_NUM",wrDocNum));
                smsWmsReceiveDetailSubService.remove(new QueryWrapper<SmsWmsReceiveDetailSub>().eq("WR_DOC_NUM",wrDocNum));
            }
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "删除失败");
        }
        return Result.succeed("删除成功");
    }

    @Override
    public Page<Map> allList(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return smsWmsReceiveDocMapper.allList(pages, params);
    }

    @Override
    public Page<Map> allSelectList(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return smsWmsReceiveDocMapper.allSelectList(pages, params);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result otherWarehouseSave(SmsWmsReceiveAllDTO smsWmsReceiveAllDTO) {
        try{
            SmsWmsReceiveDoc smsWmsReceiveDoc = smsWmsReceiveAllDTO.getSmsWmsReceiveDoc();
            List<SmsWmsReceiveDetail> smsWmsReceiveDetails = smsWmsReceiveAllDTO.getSmsWmsReceiveDetails();
            String newDocNum = smsWmsReceiveDoc.getWrDocNum();
            String finalNewWrDocNum = newDocNum;
            smsWmsReceiveDoc.setWrDocNum(newDocNum);
            smsWmsReceiveDetails.forEach(u->u.setWrDocNum(finalNewWrDocNum));
            smsWmsReceiveDocMapper.insert(smsWmsReceiveDoc);
            smsWmsReceiveDetailService.saveBatch(smsWmsReceiveDetails);
            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "保存失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result otherWarehouseUpdate(SmsWmsReceiveAllDTO smsWmsReceiveAllDTO) {
        String finalDocNo="";
        try{
            SmsWmsReceiveDoc smsWmsReceiveDoc = smsWmsReceiveAllDTO.getSmsWmsReceiveDoc();
            String wrDocNum = smsWmsReceiveDoc.getWrDocNum();
            finalDocNo=wrDocNum;
            hasPda(wrDocNum);
            smsWmsReceiveDocMapper.updateById(smsWmsReceiveDoc);
            smsWmsReceiveDetailService.remove(new QueryWrapper<SmsWmsReceiveDetail>()
                    .eq("WR_DOC_NUM",finalDocNo));
            List<SmsWmsReceiveDetail> smsWmsReceiveDetails = smsWmsReceiveAllDTO.getSmsWmsReceiveDetails();
            smsWmsReceiveDetails.forEach(u->u.setWrDocNum(wrDocNum));
            smsWmsReceiveDetailService.saveBatch(smsWmsReceiveDetails);
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "单号："+finalDocNo+"已扫码，无法修改");
        }
        return Result.succeed("修改成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result pdaReceive(Map<String, Object> map) {
        try{
            String barcode = MapUtil.getStr(map, "barcode");
            String empNo = MapUtil.getStr(map, "empNo");
            String docNum = MapUtil.getStr(map, "docNum");
            String dtCode = MapUtil.getStr(map, "dtCode");
            Long amount = MapUtil.getLong(map, "amount");
            SmsWmsIoType smsWmsIoType = smsWmsIoTypeService.getOne(new LambdaQueryWrapper<SmsWmsIoType>().eq(SmsWmsIoType::getDtCode, dtCode));
            String dtName="";
            if (smsWmsIoType!=null){
                dtName=smsWmsIoType.getDtName();
            }
            UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
            if (user == null) {
                return Result.failed("无此员工");
            }
            String empName=user.getUserName();
            SmsWmsBarcodeInfo barcodeInfo = smsWmsBarcodeInfoService.getOne(new QueryWrapper<SmsWmsBarcodeInfo>()
                    .eq("TBL_BARCODE", barcode));
            if (barcodeInfo == null) {
                return Result.failed("无此条码");
            }
            Long skAmount = barcodeInfo.getTblNum();
            if (SysConst.DT_CODE.SC_TL.equals(dtCode)){
                if (amount<=0||amount>skAmount){
                    return Result.failed("数量超限");
                }
                skAmount=amount;
            }
            String poNo = barcodeInfo.getTblPoNum();
            SmsWmsStockInfo stockInfo = smsWmsStockInfoService.getOne(new QueryWrapper<SmsWmsStockInfo>()
                    .eq("DOC_NUM", docNum)
                    .eq("TBL_BARCODE", barcode)
            );
            if (stockInfo!=null){
                return Result.failed("重复扫码");
            }
            SmsWmsStockInfo smsWmsStockInfo = new SmsWmsStockInfo();
            String wrDocNum = barcodeInfo.getTblDocNum();
            if (SysConst.DT_CODE.CG.equals(dtCode)&&!wrDocNum.equals(docNum)){
                return Result.failed("单号条码信息不一致");
            }
            String itemCode = barcodeInfo.getTblItemcode();
            SmsWmsReceiveDoc receiveDoc = smsWmsReceiveDocMapper.selectOne(new QueryWrapper<SmsWmsReceiveDoc>()
                    .eq("WR_DOC_NUM", docNum));
            if (receiveDoc == null) {
                return Result.failed("无此单号");
            }
            if(!("1".equals(receiveDoc.getWrState())||"2".equals(receiveDoc.getWrState()))){
                return Result.failed("此单号不为录入状态");
            }
            if (!receiveDoc.getDtCode().equals(dtCode)){
                return Result.failed("扫码类型出错");
            }


            String whCode = receiveDoc.getWhCode();

            SmsWmsReceiveDetail smsWmsReceiveDetail = smsWmsReceiveDetailService.getOne(new QueryWrapper<SmsWmsReceiveDetail>()
                    .eq("WR_DOC_NUM", docNum)
                    .eq("CO_ITEM_CODE", itemCode)
            );

            if (smsWmsReceiveDetail != null){
                Long planTotal=smsWmsReceiveDetail.getPlanNum();
                Long receiveTotal=smsWmsReceiveDetail.getReceiveNum();
                Long remainTotal=planTotal-receiveTotal;

                if (remainTotal<skAmount){
                    return Result.failed("条码数量超出待收数量");
                }
                /**
                 * 更新入库单详情
                 */
                smsWmsReceiveDetail.setReceiveNum(smsWmsReceiveDetail.getReceiveNum()+skAmount);
                smsWmsReceiveDetailService.updateById(smsWmsReceiveDetail);
                /**
                 * 绑定入库条码明细
                 * */
                SmsWmsReceiveDetailSub smsWmsReceiveDetailSub = new SmsWmsReceiveDetailSub();
                smsWmsReceiveDetailSub.setWrDocNum(smsWmsReceiveDetail.getWrDocNum());
                smsWmsReceiveDetailSub.setCoItemCode(smsWmsReceiveDetail.getCoItemCode());

                smsWmsReceiveDetailSub.setWrdDid(smsWmsReceiveDetail.getId());
                smsWmsReceiveDetailSub.setTblBarcode(barcode);
                smsWmsReceiveDetailSub.setReceiveNum(skAmount);

                smsWmsReceiveDetailSub.setWrdsInDate(receiveDoc.getWrDate());
                smsWmsReceiveDetailSub.setWhCode(smsWmsReceiveDetail.getWhCode());
                smsWmsReceiveDetailSub.setTblManufacturerBat(barcodeInfo.getTblManufacturerBat());
                smsWmsReceiveDetailSub.setTblManufacturerDate(barcodeInfo.getTblManufacturerDate());
                smsWmsReceiveDetailSub.setWrdsEmpNo(empNo);
                smsWmsReceiveDetailSub.setWrdsEmpName(empName);
                smsWmsReceiveDetailSub.setSupplierCode(barcodeInfo.getTblSupCode());
                smsWmsReceiveDetailSub.setCanToErp("0");
                SmsWmsReceiveDetailSub one = smsWmsReceiveDetailSubService.getOne(new QueryWrapper<SmsWmsReceiveDetailSub>()
                        .eq("WR_DOC_NUM", docNum)
                        .eq("TBL_BARCODE", barcode)
                );
                if (one!=null){
                    smsWmsReceiveDetailSubService.update(smsWmsReceiveDetailSub,new UpdateWrapper<SmsWmsReceiveDetailSub>()
                            .eq("WR_DOC_NUM", docNum)
                            .eq("TBL_BARCODE", barcode)
                    );
                }else{
                    smsWmsReceiveDetailSubService.save(smsWmsReceiveDetailSub);
                }
                /**
                 * 添加库存表
                 * */
                smsWmsStockInfoService.remove(new QueryWrapper<SmsWmsStockInfo>().eq("tbl_barcode",barcode));

                smsWmsStockInfo.setDocNum(docNum);
                smsWmsStockInfo.setCoItemCode(itemCode);
                smsWmsStockInfo.setLotNumber(barcodeInfo.getTblManufacturerBat());
                smsWmsStockInfo.setTblBarcode(barcode);
                smsWmsStockInfo.setAmount(skAmount);
                smsWmsStockInfo.setOrderNo(barcodeInfo.getTblPoNum());
                smsWmsStockInfo.setWhCode(receiveDoc.getWhCode());
                smsWmsStockInfo.setSupplierCode(barcodeInfo.getTblSupCode());
                smsWmsStockInfo.setPdate(barcodeInfo.getTblManufacturerDate());
                smsWmsStockInfo.setFreezeFlag("N");

                smsWmsStockInfo.setHaveCheck(!StrUtil.isEmpty(whCode)&&whCode.equals("W-M-MPA")?"Y":"N");
                smsWmsStockInfo.setInStockTime(new Date());
                smsWmsStockInfo.setInStockMan(empNo);
                smsWmsStockInfo.setStockFlag("1");
                smsWmsStockInfoService.save(smsWmsStockInfo);
                /**
                 * 如果是生产退料，需要更新条码数量以及状态状态
                 * */
                if (SysConst.DT_CODE.SC_TL.equals(dtCode)){
                    barcodeInfo.setTblNum(amount);
                    barcodeInfo.setIsOpen(true);
                    barcodeInfo.setHasPrint(false);
                    smsWmsBarcodeInfoService.updateById(barcodeInfo);
                }
                /**
                 * 如果是换料入库，需要更新sms_wms_reload_doc_detail表中入库总数，sms_wms_reload_doc状态
                 * */
                if (SysConst.DT_CODE.TL.equals(dtCode)){
                    String reloadNo = receiveDoc.getWrDocNum();
                    SmsWmsReloadDocDetail reloadDocDetail = smsWmsReloadDocDetailService.getOne(new QueryWrapper<SmsWmsReloadDocDetail>()
                            .eq("reload_no", reloadNo)
                            .eq("po_no", smsWmsReceiveDetail.getErpPo())
                            .eq("item_code", itemCode)
                    );
                    if (reloadDocDetail != null) {
                        reloadDocDetail.setReceiveAmount(reloadDocDetail.getReceiveAmount()+barcodeInfo.getTblNum());
                        smsWmsReloadDocDetailService.updateById(reloadDocDetail);
                    }
                    int totalCount = smsWmsReloadDocDetailService.count(new QueryWrapper<SmsWmsReloadDocDetail>().eq("reload_no", reloadNo));
                    int receivedCount=smsWmsReloadDocDetailService.count(new QueryWrapper<SmsWmsReloadDocDetail>().eq("reload_no",reloadNo).last("and reality_amount=receive_amount"));
                    if (totalCount==receivedCount){
                        smsWmsReloadDocService.update(new UpdateWrapper<SmsWmsReloadDoc>()
                                .eq("reload_no",reloadNo)
                                .set("reload_status",4)
                        );
                    }
                }
                /**
                 * 更新入库单状态
                 * */
                receiveDoc.setWrState("2");
                smsWmsReceiveDocMapper.updateById(receiveDoc);
            }
            //pda操作添加日志
            pdaMesLogService.save(PdaMesLog.builder()
                    .type(dtName+"入库")
                    .barcode(barcode)
                    .itemCode(smsWmsStockInfo.getCoItemCode())
                    .num(smsWmsStockInfo.getAmount())
                    .tblManufacturerBat(smsWmsStockInfo.getLotNumber())
                    .tblManufacturerDate(smsWmsStockInfo.getPdate())
                    .docNo(docNum)
                    .createUser(user.getUserName())
                    .build()
            );
            return Result.succeed("入库成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "入库失败");
        }
    }
    @Override
    public Result close(Map<String, Object> map) {
        String empNo = MapUtil.getStr(map, "empNo");
        String docNum = MapUtil.getStr(map, "docNum");
        int receiveDetailTotal = smsWmsReceiveDetailService.count(new QueryWrapper<SmsWmsReceiveDetail>().eq("WR_DOC_NUM", docNum));
        int receivedCount = smsWmsReceiveDetailService.count(new QueryWrapper<SmsWmsReceiveDetail>().eq("WR_DOC_NUM", docNum).last("and PLAN_NUM=RECEIVE_NUM"));
        if (receiveDetailTotal!=receivedCount){
            return Result.failed("实收数量不符");
        }
        SmsWmsReceiveDoc receiveDoc = smsWmsReceiveDocMapper.selectOne(new QueryWrapper<SmsWmsReceiveDoc>()
                .eq("WR_DOC_NUM", docNum));
        String whCode = receiveDoc.getWhCode();
        /**生成iqc质检单*/
        if (whCode.equals("W-M-MIN")){
            Map<String,Object> oqaMap=new HashMap<>();
            oqaMap.put("wrDocNum",docNum);
            oqaMap.put("userAccount",empNo);
            iqcOqaService.saveOqaAndBath(oqaMap);
        }
        receiveDoc.setWrState("4");
        smsWmsReceiveDocMapper.updateById(receiveDoc);
        return Result.succeed("关结成功");
    }

    @Override
    public Result pdaReceiveCancel(Map<String, Object> map) {
        try{
            String barcode = MapUtil.getStr(map, "barcode");
            String empNo = MapUtil.getStr(map, "empNo");
            String docNum = MapUtil.getStr(map, "docNum");
            String dtCode = MapUtil.getStr(map, "dtCode");
            Long amount = MapUtil.getLong(map, "amount");
            SmsWmsIoType smsWmsIoType = smsWmsIoTypeService.getOne(new LambdaQueryWrapper<SmsWmsIoType>().eq(SmsWmsIoType::getDtCode, dtCode));
            String dtName="";
            if (smsWmsIoType!=null){
                dtName=smsWmsIoType.getDtName();
            }
            UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
            if (user == null) {
                return Result.failed("无此员工");
            }
            SmsWmsBarcodeInfo barcodeInfo = smsWmsBarcodeInfoService.getOne(new QueryWrapper<SmsWmsBarcodeInfo>()
                    .eq("TBL_BARCODE", barcode));
            SmsWmsStockInfo stockInfo = smsWmsStockInfoService.getOne(new QueryWrapper<SmsWmsStockInfo>()
                    .eq("DOC_NUM", docNum)
                    .eq("TBL_BARCODE", barcode)
            );
            if (barcodeInfo == null||stockInfo==null) {
                return Result.failed("无此条码");
            }
            Long skAmount = barcodeInfo.getTblNum();
            String wrDocNum = barcodeInfo.getTblDocNum();
            if (SysConst.DT_CODE.CG.equals(dtCode)&&!wrDocNum.equals(docNum)){
                return Result.failed("单号条码信息不一致");
            }
            String itemCode = barcodeInfo.getTblItemcode();
            SmsWmsReceiveDoc receiveDoc = smsWmsReceiveDocMapper.selectOne(new QueryWrapper<SmsWmsReceiveDoc>()
                    .eq("WR_DOC_NUM", docNum));
            if (receiveDoc == null) {
                return Result.failed("无此单号");
            }
            if("4".equals(receiveDoc.getWrState())){
                return Result.failed("此单已关结，无法取消");
            }
            if (!receiveDoc.getDtCode().equals(dtCode)){
                return Result.failed("扫码类型出错");
            }

            SmsWmsReceiveDetail smsWmsReceiveDetail = smsWmsReceiveDetailService.getOne(new QueryWrapper<SmsWmsReceiveDetail>()
                    .eq("WR_DOC_NUM", docNum)
                    .eq("CO_ITEM_CODE", itemCode)
            );

            /**
             * 删除库存表
             * */
            smsWmsStockInfoService.remove(new QueryWrapper<SmsWmsStockInfo>().eq("tbl_barcode",barcode));
            /**
             * 删除入库条码明细
             * */
            smsWmsReceiveDetailSubService.remove(new QueryWrapper<SmsWmsReceiveDetailSub>()
                    .eq("WR_DOC_NUM", docNum)
                    .eq("TBL_BARCODE", barcode)
            );

            /**
             * 更新入库单详情
             */
            smsWmsReceiveDetail.setReceiveNum(smsWmsReceiveDetail.getReceiveNum()-skAmount);
            smsWmsReceiveDetailService.updateById(smsWmsReceiveDetail);

            /**
             * 如果是换料入库，需要更新sms_wms_reload_doc_detail表中入库总数，sms_wms_reload_doc状态
             * */
            if (SysConst.DT_CODE.TL.equals(dtCode)){
                String reloadNo = receiveDoc.getWrDocNum();
                SmsWmsReloadDocDetail reloadDocDetail = smsWmsReloadDocDetailService.getOne(new QueryWrapper<SmsWmsReloadDocDetail>()
                        .eq("reload_no", reloadNo)
                        .eq("po_no", smsWmsReceiveDetail.getErpPo())
                        .eq("item_code", itemCode)
                );
                if (reloadDocDetail != null) {
                    reloadDocDetail.setReceiveAmount(reloadDocDetail.getReceiveAmount()-barcodeInfo.getTblNum());
                    smsWmsReloadDocDetailService.updateById(reloadDocDetail);
                }
                smsWmsReloadDocService.update(new UpdateWrapper<SmsWmsReloadDoc>()
                        .eq("reload_no",reloadNo)
                        .set("reload_status",2)
                );

            }

            //pda操作添加日志
            pdaMesLogService.save(PdaMesLog.builder()
                    .type(dtName+"取消入库")
                    .barcode(barcode)
                    .itemCode(stockInfo.getCoItemCode())
                    .num(stockInfo.getAmount())
                    .tblManufacturerBat(stockInfo.getLotNumber())
                    .tblManufacturerDate(stockInfo.getPdate())
                    .docNo(docNum)
                    .createUser(user.getUserName())
                    .build()
            );
            return Result.succeed("取消入库成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "取消入库失败");
        }
    }

    @Override
    public JSONObject getInfoBySn(Map<String, Object> params) {
        String result = "";
        try {
            String sn = MapUtil.getStr(params, "sn");
            String userName = MapUtil.getStr(params, "userName");
            String docNo = MapUtil.getStr(params, "docNo");
            log.info("1.请求点料信息：{}",params);
            result= HttpUtil.get("http://192.168.61.203/getQtyInfo?request={\"sn\":\""+sn+"\"}");
            log.info("2.获取到点料信息：{}",result);
            Map map = JSON.parseObject(result, Map.class);
            Integer qty = MapUtil.getInt(map, "qty");
            String infoMapString = HttpUtil.get("http://192.168.50.97:8004/wms/GetMaterialLabelInfo?sn=" +sn+"&qty="+qty+"&userName="+userName+"&docNo="+docNo);
            log.info("3.返回详情信息为：{}",infoMapString);
            Map infoMap = JSON.parseObject(infoMapString, Map.class);
            JSONObject detail = JSONUtil.parseObj(infoMap);
            return detail;
        } catch (JSONException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt("result",0);
            jsonObject.putOpt("msg","调用点料机失败");
            log.error("Json解析异常{}",jsonObject);
            return jsonObject;
        } catch (IORuntimeException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt("result",0);
            jsonObject.putOpt("msg","服务被拒绝");
            log.error("服务被拒绝{}",jsonObject);
            return jsonObject;
        }
    }

    @Override
    public List<String> getReceiveList(Map<String, Object> map) {
        List<String> smsWmsReceiveDocs=smsWmsReceiveDocMapper.getReceiveList(map);
        return smsWmsReceiveDocs;
    }

    @Override
    public Result getDocNum(Map<String, Object> map) {
        String depaCode = MapUtil.getStr(map, "depaCode");
        String dtCode = MapUtil.getStr(map, "dtCode");
        String prefix=depaCode + dtCode + DateUtil.format(new Date(), "yyyyMM");
        String newDocNum = getNewDocNum(prefix);
        return Result.succeed(newDocNum, "查询成功");
    }

    /**
     * 根据单号前缀生成当前单号
     * @param prefix 前缀
     * @return
     */
    @Override
    public String getNewDocNum(String prefix){
        String newDocNum=prefix+"0001";
        SmsWmsReceiveDoc one = getOne(new QueryWrapper<SmsWmsReceiveDoc>()
                .last("where WR_DOC_NUM like '"+prefix+"%' order by WR_DOC_NUM desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getWrDocNum(), prefix));
            newDocNum=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return newDocNum;
    }



    /**
     * 判断该单号是否已经过pda扫码，如果是不允许删除
     * @param docNo
     */
    private void hasPda(String docNo){
        List<SmsWmsReceiveDetailSub> receiveDetailSubs = smsWmsReceiveDetailSubService.list(new QueryWrapper<SmsWmsReceiveDetailSub>().eq("WR_DOC_NUM", docNo));
        if (receiveDetailSubs!=null&&receiveDetailSubs.size()>0){
            int i=1/0;
        }
    }
}
