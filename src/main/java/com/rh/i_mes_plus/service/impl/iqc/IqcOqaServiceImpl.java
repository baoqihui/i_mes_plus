package com.rh.i_mes_plus.service.impl.iqc;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaMapper;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaSingleMapper;
import com.rh.i_mes_plus.model.iqc.*;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetail;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetailSub;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDoc;
import com.rh.i_mes_plus.service.iqc.*;
import com.rh.i_mes_plus.service.sms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检验单
 *
 * @author hbq
 * @date 2020-10-21 14:29:19
 */
@Slf4j
@Service
public class IqcOqaServiceImpl extends ServiceImpl<IqcOqaMapper, IqcOqa> implements IIqcOqaService {
    @Resource
    private IqcOqaMapper iqcOqaMapper;
    @Autowired
    private ISmsWmsReceiveDocService smsWmsReceiveDocService;
    @Autowired
    private ISmsWmsReceiveDetailService smsWmsReceiveDetailService;
    @Autowired
    private IIqcOqaBathService iqcOqaBathService;
    @Autowired
    private IIqcOqaCheckBasisService iqcOqaCheckBasisService;
    @Autowired
    private IIqcCoErrorCodeService iqcCoErrorCodeService;
    @Autowired
    private IIqcOqaHistoryService iqcOqaHistoryService;
    @Autowired
    private IIqcOqaSingleService iqcOqaSingleService;
    @Autowired
    private IIqcOqaSingleValueService iqcOqaSingleValueService;
    @Autowired
    private IIqcOqaTestLevelService iqcOqaTestLevelService;
    @Autowired
    private IIqcOqaTestTypeService iqcOqaTestTypeService;
    @Autowired
    private IIqcCoQcPlanService iqcCoQcPlanService;
    @Autowired
    private IIqcCoQcSamplesService iqcCoQcSamplesService;
    @Autowired
    private IIqcOqaItemService iqcOqaItemService;
    @Resource
    private IqcOqaSingleMapper iqcOqaSingleMapper;
    @Autowired
    private IIqcTestItemModelService iqcTestItemModelService;
    @Autowired
    private ISmsWmsReceiveDetailSubService smsWmsReceiveDetailSubService;
    @Autowired
    private ISmsWmsStockInfoService smsWmsStockInfoService;
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
        return iqcOqaMapper.findList(pages, params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveOqaAndBath(Map<String, Object> map) {

            String wrDocNum = MapUtil.getStr(map, "wrDocNum");
            String userAccount = MapUtil.getStr(map, "userAccount");
            SmsWmsReceiveDoc receiveDoc = smsWmsReceiveDocService.getOne(new QueryWrapper<SmsWmsReceiveDoc>().eq("WR_DOC_NUM", wrDocNum));
            List<SmsWmsReceiveDetailSub> detailSublist = smsWmsReceiveDetailSubService.getGroup(wrDocNum);
            String dtCode = receiveDoc.getDtCode();
            String oqcNo ="";
            String moNumber="";
            for (SmsWmsReceiveDetailSub smsWmsReceiveDetailSub : detailSublist) {
                String coItemCode = smsWmsReceiveDetailSub.getCoItemCode();
                String tblManufacturerBat = smsWmsReceiveDetailSub.getTblManufacturerBat();
                Date tblManufacturerDate = smsWmsReceiveDetailSub.getTblManufacturerDate();
                SmsWmsReceiveDetail receiveDetail = smsWmsReceiveDetailService.getOne(new QueryWrapper<SmsWmsReceiveDetail>()
                        .eq("WR_DOC_NUM",wrDocNum)
                        .eq("CO_ITEM_CODE", coItemCode));
                List<SmsWmsReceiveDetailSub> smsWmsReceiveDetailSubs = smsWmsReceiveDetailSubService.list(new QueryWrapper<SmsWmsReceiveDetailSub>()
                        .eq("CO_ITEM_CODE", coItemCode)
                        .eq("tbl_manufacturer_bat", tblManufacturerBat)
                        .eq("tbl_manufacturer_date", tblManufacturerDate)
                );
                //采购入库单根据采购单加3位串号生成
                if (SysConst.DT_CODE.CG.equals(dtCode)||SysConst.DT_CODE.TL.equals(dtCode)){
                    String prefix = receiveDetail.getErpPo();
                    moNumber=receiveDetail.getErpPo();
                    oqcNo= getNewDocNum(prefix);
                }
                //其他入库单根据入库单加三位串号生成
                if (SysConst.DT_CODE.QT.equals(dtCode)){
                    moNumber=wrDocNum;
                    oqcNo= getNewDocNum(wrDocNum);
                }
                IqcOqa iqcOqa = new IqcOqa();
                iqcOqa.setOqcNo(oqcNo);
                iqcOqa.setWrDocNum(wrDocNum);
                iqcOqa.setMoNumber(moNumber);
                iqcOqa.setItemCode(coItemCode);
                iqcOqa.setLotNumber(tblManufacturerBat);
                iqcOqa.setCustCode(receiveDoc.getSupplierCode());
                iqcOqa.setOtsCode("IQC");
                iqcOqa.setOqcCreator(userAccount);
                iqcOqa.setOqcSendAmount(smsWmsReceiveDetailSub.getReceiveNum());
                iqcOqa.setOqcSendDate(new Date());
                iqcOqa.setOqcStatus(0);
                iqcOqa.setDepaCode("T1423");
                iqcOqa.setDepaName("仓库部");
                iqcOqaMapper.insert(iqcOqa);
                /**
                 * 填充条码关联表oqc_no
                 */
                String finalOqcNo = oqcNo;
                smsWmsReceiveDetailSubs.forEach(u->u.setOqcNo(finalOqcNo));
                smsWmsReceiveDetailSubService.updateBatchById(smsWmsReceiveDetailSubs);
                /**
                 * 修改库存信息
                 */
                /*smsWmsStockInfoService.update(new UpdateWrapper<SmsWmsStockInfo>()
                        .eq("DOC_NUM",wrDocNum)
                        .set("HAVE_CHECK","Y")
                );*/
                /**
                 * 检验单明细
                 */
                IqcOqaBath iqcOqaBath = new IqcOqaBath();
                iqcOqaBath.setOqcNo(oqcNo);
                iqcOqaBath.setMoNumber(moNumber);
                iqcOqaBath.setItemCode(receiveDetail.getCoItemCode());

                for (SmsWmsReceiveDetailSub wmsReceiveDetailSub : smsWmsReceiveDetailSubs) {
                    iqcOqaBath.setLotNumber(wmsReceiveDetailSub.getTblManufacturerBat());
                    iqcOqaBath.setSerialNumber(wmsReceiveDetailSub.getTblBarcode());
                    iqcOqaBath.setOsAmount(wmsReceiveDetailSub.getReceiveNum());
                    iqcOqaBathService.save(iqcOqaBath);
                }
            }
        try{    return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "保存失败");
        }
    }

    @Override
    public Result checkList(String oqcNo) {
        /**
         * 查主信息
         * */
        Map<String,Object> iqcOqa = iqcOqaMapper.selectAllByOqcNo(oqcNo);

        String otsCode = MapUtil.getStr(iqcOqa,"otsCode");
        /**
         * 检验依据
         */
        List<IqcOqaCheckBasis> ocbList = iqcOqaCheckBasisService.list(new QueryWrapper<IqcOqaCheckBasis>()
                .eq("ots_code",otsCode));
        /**
         * 不良现象
         */
        List<String> errDescs=iqcCoErrorCodeService.getErrDesc(oqcNo);
        /**
         * 查已绑定抽样方案
         */
        List<Map<String,Object>> oiList=iqcOqaMapper.selOIList(oqcNo);

        //封装
        Map<String,Object> map=new HashMap<>();
        map.put("iqcOqa",iqcOqa); //主信息
        map.put("ocbList",ocbList); //检验依据
        map.put("errDescs",errDescs);   //不良现象
        map.put("oiList",oiList);   //查已绑定抽样方案
        return Result.succeed(map);
    }

    @Override
    public Result oqaBath(String oqcNo) {
        List<IqcOqaBath> oqaBathList = iqcOqaBathService.list(new QueryWrapper<IqcOqaBath>().eq("oqc_no", oqcNo));
        return Result.succeed(oqaBathList,"查询成功");
    }

    @Override
    public Result oqaHistory(String oqcNo) {
        List<IqcOqaHistory> oqaHistoryList = iqcOqaHistoryService.list(new QueryWrapper<IqcOqaHistory>().eq("oqc_no", oqcNo));
        return Result.succeed(oqaHistoryList,"查询成功");
    }

    @Override
    public Result oqaSingle(String oqcNo) {
        List<IqcOqaSingle> iqcOqaSingleList = iqcOqaSingleService.list(new QueryWrapper<IqcOqaSingle>().eq("oqc_no", oqcNo));
        return Result.succeed(iqcOqaSingleList,"查询成功");
    }

    @Override
    public Result oqaSingleValue(Map<String, Object> params) {
        return Result.succeed(iqcOqaSingleValueService.findList(params),"查询成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result createProject(String oqcNo) {
        try{
            IqcOqa iqcOqa = getOne(new QueryWrapper<IqcOqa>().eq("oqc_no", oqcNo));

            String itemCode = iqcOqa.getItemCode();
            Long oqcSendAmount = iqcOqa.getOqcSendAmount();
            Integer sameCount= iqcOqaTestLevelService.getModelAndLevel(itemCode);
            int realCount = iqcTestItemModelService.count(new QueryWrapper<IqcTestItemModel>().eq("item_code", itemCode));
            if (sameCount!=realCount){
                return Result.failed("生成失败，请检查配置的抽样方案与抽样项目是否匹配");
            }
            /**
             * 抽样方案与检验单绑定
             */
            //根据物料号找到抽样方案列表
            List<IqcOqaTestLevel> iqcOqaTestLevels = iqcOqaTestLevelService.list(new QueryWrapper<IqcOqaTestLevel>().eq("item_code", itemCode));
            IqcOqaItem iqcOqaItem = new IqcOqaItem();
            iqcOqaItem.setOqcNo(oqcNo);

            /** 删除原有抽样方案*/
            iqcOqaItemService.remove(new QueryWrapper<IqcOqaItem>().eq("oqc_no",oqcNo));
            Long maxSampleQyt=0L;
            for (IqcOqaTestLevel iqcOqaTestLevel : iqcOqaTestLevels) {

                iqcOqaItem.setTiyId(iqcOqaTestLevel.getTiyId());
                Long ottId = iqcOqaTestLevel.getOttId();
                Long otgId = iqcOqaTestLevel.getOtgId();
                IqcOqaTestType iqcOqaTestType = iqcOqaTestTypeService.getById(ottId);

                iqcOqaItem.setOttName(iqcOqaTestType != null?iqcOqaTestType.getOttName():"");
                iqcOqaItem.setOdlCode(iqcOqaTestLevel.getOdlCode());
                iqcOqaItem.setDepaCode(iqcOqaTestLevel.getDepaCode());
                iqcOqaItem.setDepaName(iqcOqaTestLevel.getDepaName());

                /** 根据otgId和送检数量 查询该物料所属样本代码*/
                String code= iqcCoQcSamplesService.getCodeBySendAmount(oqcSendAmount,otgId);

                /** 查询到具体的抽样明细*/
                IqcCoQcPlan iqcCoQcPlan = iqcCoQcPlanService.getOne(new QueryWrapper<IqcCoQcPlan>().eq("code", code).eq("ott_id",ottId));
                if (iqcCoQcPlan==null){
                    return Result.failed("抽样计划未维护");
                }
                iqcOqaItem.setOtgId(iqcOqaTestLevel.getOtgId());
                iqcOqaItem.setOiAql(iqcOqaTestLevel.getAql());
                iqcOqaItem.setOiAc(iqcCoQcPlan.getAcValue());
                iqcOqaItem.setOiRe(iqcCoQcPlan.getReValue());
                iqcOqaItem.setOiNgCount(0L);
                iqcOqaItem.setOiSampleQyt(iqcCoQcPlan.getCount());
                if (iqcCoQcPlan.getCount()>maxSampleQyt){
                    maxSampleQyt=iqcCoQcPlan.getCount();
                }
                iqcOqaItem.setOiCheckQyt(0L);
                iqcOqaItem.setOiResult("OK");
                iqcOqaItem.setIsCheck(false);

                iqcOqaItemService.save(iqcOqaItem);
            }
            /** 检测出最大应抽数量更新质检单*/
            iqcOqa.setOqcSampleAmount(maxSampleQyt);
            updateById(iqcOqa);

            return Result.succeed("生成成功");
        }catch (Exception e){
            // 事务回滚
            log.error("生成方案失败",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "生成失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result createSample(Map<String,Object> map) {
        String oqcNo = MapUtil.getStr(map, "oqcNo");
        IqcOqa iqcOqa = getOne(new QueryWrapper<IqcOqa>().eq("oqc_no", oqcNo));
        if (iqcOqa == null) {
            return Result.failed("无此单号");
        }
        String sn = MapUtil.getStr(map, "sn");
        String osExaminer = MapUtil.getStr(map, "osExaminer");
        String remark = MapUtil.getStr(map, "remark");
        Long count = MapUtil.getLong(map, "count");
        /** 修改实抽数,并修改为检验中*/
        iqcOqa.setOqcTestAmount(iqcOqa.getOqcTestAmount()+count);
        iqcOqa.setOqcStatus(1);
        updateById(iqcOqa);
        /** 修改该检验单下抽样方案的已抽数*/
        List<IqcOqaItem> iqcOqaItems = iqcOqaItemService.list(new QueryWrapper<IqcOqaItem>().eq("oqc_no", oqcNo));
        if (iqcOqaItems!=null){
            for (IqcOqaItem iqcOqaItem : iqcOqaItems) {
                iqcOqaItem.setOiCheckQyt(iqcOqaItem.getOiCheckQyt()+count);
                iqcOqaItemService.updateById(iqcOqaItem);
            }
        }
        /** 生成样本信息*/
        IqcOqaSingle iqcOqaSingle = new IqcOqaSingle();
        iqcOqaSingle.setOqcNo(oqcNo);
        iqcOqaSingle.setSerialNumber(sn);
        iqcOqaSingle.setOsAmount(1l);
        iqcOqaSingle.setOsResult("OK");
        iqcOqaSingle.setOsTestDate(new Date());
        iqcOqaSingle.setOsExaminer(osExaminer);
        iqcOqaSingle.setOsRemark(remark);
        /** 查询最大样本号*/
        Long maxOsNo=iqcOqaSingleMapper.getMaxOsNoByOqcNoAndsn(map)==null?0L:iqcOqaSingleMapper.getMaxOsNoByOqcNoAndsn(map);
        for (Long i=1L;i<=count;i++){
            iqcOqaSingle.setOsNo(maxOsNo+i);
            iqcOqaSingleMapper.insert(iqcOqaSingle);
            /** 生成每个样本的检测项*/
            IqcOqaSingleValue iqcOqaSingleValue = new IqcOqaSingleValue();
            iqcOqaSingleValue.setOqcNo(oqcNo);
            iqcOqaSingleValue.setOsvSerialNumber(sn);
            iqcOqaSingleValue.setOsvMoNumber(StrUtil.sub(oqcNo,0,-3));
            iqcOqaSingleValue.setOsvScarpNumber(0L);
            iqcOqaSingleValue.setOsvResult("OK");
            iqcOqaSingleValue.setOsId(iqcOqaSingle.getId());
            List<IqcTestItemModel> itemModels = iqcTestItemModelService.list(new QueryWrapper<IqcTestItemModel>().eq("item_code", iqcOqa.getItemCode()));
            for (IqcTestItemModel itemModel : itemModels) {
                iqcOqaSingleValue.setOsvTiyId(itemModel.getTiyId());
                iqcOqaSingleValue.setOsvTiName(itemModel.getTiName());
                iqcOqaSingleValue.setOsvTimUnit(itemModel.getUnit());
                iqcOqaSingleValue.setOsvSort(itemModel.getSort());
                iqcOqaSingleValue.setOsvTimRange(itemModel.getTimRange());
                iqcOqaSingleValue.setOsvTimLowerLimit(itemModel.getLowerLimit());
                iqcOqaSingleValue.setOsvTimUpperLimit(itemModel.getUpperLimit());
                iqcOqaSingleValueService.save(iqcOqaSingleValue);
            }
        }
        return Result.succeed("生成成功");
    }

    @Override
    public Result removeSample(Map<String, List<Long>> map) {
        List<Long> ids = map.get("ids");

        IqcOqaSingle iqcOqaSingle = iqcOqaSingleService.getById(ids.get(0));
        String oqcNo = iqcOqaSingle.getOqcNo();
        for (Long osId : ids) {
            //删除样本下的value
            iqcOqaSingleValueService.remove(new QueryWrapper<IqcOqaSingleValue>().eq("oqc_no",oqcNo).eq("os_id",osId));
        }

        //删除样本
        iqcOqaSingleService.removeByIds(ids);
        //该oqcNo剩余样本数（oqa抽检数,oqaItem抽检数）
        Long total = Convert.toLong(iqcOqaSingleService.count(new QueryWrapper<IqcOqaSingle>().eq("oqc_no", oqcNo)));
        //该oqcNo剩余ng数（oqa ng数）
        Long ngCount = Convert.toLong(iqcOqaSingleService.count(new QueryWrapper<IqcOqaSingle>().eq("oqc_no", oqcNo).eq("os_result", "NG")));

        update(new UpdateWrapper<IqcOqa>().eq("oqc_no", oqcNo).set("oqc_test_amount",total).set("oqc_ng_pcb",ngCount)); //更新oqa抽检数，ng数
        /*if (ngCount==0){
            update(new UpdateWrapper<IqcOqa>().eq("oqc_no", oqcNo).set("oqc_status",1)); //更新默认允许接收
        }*/

        iqcOqaItemService.update(new UpdateWrapper<IqcOqaItem>().eq("oqc_no", oqcNo).set("oi_check_qyt",total));    //更新 方案抽检数


        /**删除抽检方案ng*/
        List<IqcOqaItem> iqcOqaItems = iqcOqaItemService.list(new QueryWrapper<IqcOqaItem>().eq("oqc_no", oqcNo).eq("oi_result","NG"));
        for (IqcOqaItem iqcOqaItem : iqcOqaItems) {
            Long ngSingleValueCount =  Convert.toLong(iqcOqaSingleValueService.countNg(oqcNo,iqcOqaItem.getTiyId()));
            iqcOqaItem.setOiNgCount(ngSingleValueCount);
            if (ngSingleValueCount==0){
                iqcOqaItem.setOiResult("OK");
            }
            iqcOqaItemService.updateById(iqcOqaItem);
        }

        return Result.succeed("删除成功");
    }

    @Override
    public Result updateSingleValue(Map<String, List<IqcOqaSingleValue>> map) {
        List<IqcOqaSingleValue> singleValues = map.get("singValues");
        if (singleValues==null||singleValues.size()<=0){
            return Result.failed("请完善检验项目");
        }
        Long osId = singleValues.get(0).getOsId();
        String oqcNo = singleValues.get(0).getOqcNo();
        iqcOqaSingleValueService.updateBatchById(singleValues);
        //所改osId下ng数
        int ngValueCount = iqcOqaSingleValueService.count(new QueryWrapper<IqcOqaSingleValue>()
                .eq("os_id", osId)
                .eq("osv_result","NG")
        );
        IqcOqaSingle oqaSingle = iqcOqaSingleService.getById(osId);
        if (ngValueCount==0){
            oqaSingle.setOsResult("OK");
        }else {
            oqaSingle.setOsResult("NG");
        }
        iqcOqaSingleService.updateById(oqaSingle);

        //该oqcNo剩余ng数（oqa ng数）
        Long ngCount = Convert.toLong(iqcOqaSingleService.count(new QueryWrapper<IqcOqaSingle>().eq("oqc_no", oqcNo).eq("os_result", "NG")));

        update(new UpdateWrapper<IqcOqa>().eq("oqc_no", oqcNo).set("oqc_ng_pcb",ngCount)); //更新ng数
        /*if (ngCount==0){
            update(new UpdateWrapper<IqcOqa>().eq("oqc_no", oqcNo).set("oqc_status",1)); //更新默认允许接收
        }else {
            update(new UpdateWrapper<IqcOqa>().eq("oqc_no", oqcNo).set("oqc_status",2)); //更新默认拒收
        }*/

        /**修改抽检方案ng数*/
        List<IqcOqaItem> iqcOqaItems = iqcOqaItemService.list(new QueryWrapper<IqcOqaItem>().eq("oqc_no", oqcNo));
        for (IqcOqaItem iqcOqaItem : iqcOqaItems) {
            Long ngSingleValueCount =  Convert.toLong(iqcOqaSingleValueService.countNg(oqcNo,iqcOqaItem.getTiyId()));
            iqcOqaItem.setOiNgCount(ngSingleValueCount);
            if (ngSingleValueCount==0){
                iqcOqaItem.setOiResult("OK");
            }else {
                iqcOqaItem.setOiResult("NG");
            }
            iqcOqaItemService.updateById(iqcOqaItem);
        }

        return Result.succeed("更新成功");
    }

    /**
     * 根据单号前缀生成当前单号
     * @param prefix 前缀
     * @return
     */
    private String getNewDocNum(String prefix){
        String oqcNo=iqcOqaMapper.getMaxOqcNo(prefix);
        String newOqcNo="";
        if (oqcNo!=null){
            if (oqcNo.contains("HENA")){
                oqcNo= StrUtil.removePrefix(oqcNo,"HENA");
                newOqcNo= "HENA"+Convert.toStr(Convert.toLong(oqcNo) + 1);
            }else {
                newOqcNo= Convert.toStr(Convert.toLong(oqcNo) + 1);
            }

        }else{
            newOqcNo=prefix+"001";
        }
        return newOqcNo;
    }
}
