package com.rh.i_mes_plus.service.impl.sms;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsOutStockDocDTO;
import com.rh.i_mes_plus.mapper.sms.SmsWmsOutStockDocMapper;
import com.rh.i_mes_plus.model.other.PdaMesLog;
import com.rh.i_mes_plus.model.sms.*;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.other.IPdaMesLogService;
import com.rh.i_mes_plus.service.sms.*;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

/**
 * 出库单主表
 *
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
@Slf4j
@Service
public class SmsWmsOutStockDocServiceImpl extends ServiceImpl<SmsWmsOutStockDocMapper, SmsWmsOutStockDoc> implements ISmsWmsOutStockDocService {
    @Resource
    private SmsWmsOutStockDocMapper smsWmsOutStockDocMapper;
    @Autowired
    private ISmsWmsOutStockDetailService smsWmsOutStockDetailService;
    @Autowired
    private ISmsWmsOutStockPmItemService smsWmsOutStockPmItemService;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private ISmsWmsStockInfoService smsWmsStockInfoService;
    @Autowired
    private ISmsWmsOutStockListService smsWmsOutStockListService;
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
        return smsWmsOutStockDocMapper.findList(pages, params);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveAll(SmsWmsOutStockDocDTO smsWmsOutStockDocDTO) {
        String finalDocNo="";
        try{
            SmsWmsOutStockDoc smsWmsOutStockDoc=smsWmsOutStockDocDTO;
            String docNo = smsWmsOutStockDoc.getDocNo();
            finalDocNo=docNo;
            hasPda(docNo);
            saveOrUpdate(smsWmsOutStockDoc);
            //删除原有docNo的详情
            smsWmsOutStockDetailService.remove(new QueryWrapper<SmsWmsOutStockDetail>().eq("doc_no",docNo));

            List<SmsWmsOutStockDetail> smsWmsOutStockDetails = smsWmsOutStockDocDTO.getSmsWmsOutStockDetails();
            smsWmsOutStockDetails.forEach(u->u.setDocNo(docNo));
            smsWmsOutStockDetailService.saveBatch(smsWmsOutStockDetails);
            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed(  "单号："+finalDocNo+"已扫码，无法修改");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveProjectAll(SmsWmsOutStockDocDTO smsWmsOutStockDocDTO) {
        String finalDocNo="";
        try{
            //保存主表
            SmsWmsOutStockDoc smsWmsOutStockDoc=smsWmsOutStockDocDTO;
            String docNo = smsWmsOutStockDoc.getDocNo();
            finalDocNo=docNo;
            hasPda(docNo);

            saveOrUpdate(smsWmsOutStockDoc);
            //删除原有docNo的详情
            smsWmsOutStockDetailService.remove(new QueryWrapper<SmsWmsOutStockDetail>().eq("doc_no",docNo));
            //保存详情表
            List<SmsWmsOutStockDetail> smsWmsOutStockDetails = smsWmsOutStockDocDTO.getSmsWmsOutStockDetails();
            smsWmsOutStockDetails.forEach(u->u.setDocNo(docNo));
            smsWmsOutStockDetailService.saveBatch(smsWmsOutStockDetails);

            //删除原有docNo的绑定
            smsWmsOutStockPmItemService.remove(new QueryWrapper<SmsWmsOutStockPmItem>().eq("doc_no",docNo));
            //保存备料单工单绑定表
            List<SmsWmsOutStockPmItem> smsWmsOutStockPmItems = smsWmsOutStockDocDTO.getSmsWmsOutStockPmItems();
            smsWmsOutStockPmItems.forEach(u->u.setDocNo(docNo));
            smsWmsOutStockPmItemService.saveBatch(smsWmsOutStockPmItems);

            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "单号："+finalDocNo+"已扫码，无法修改");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveListAll(SmsWmsOutStockDocDTO smsWmsOutStockDocDTO) {
        String finalDocNo="";
        try{
            //保存主表
            SmsWmsOutStockDoc smsWmsOutStockDoc=smsWmsOutStockDocDTO;
            String docNo = smsWmsOutStockDoc.getDocNo();
            finalDocNo=docNo;
            hasPda(docNo);

            saveOrUpdate(smsWmsOutStockDoc);
            //删除原有docNo的详情
            smsWmsOutStockDetailService.remove(new QueryWrapper<SmsWmsOutStockDetail>().eq("doc_no",docNo));
            //保存详情表
            List<SmsWmsOutStockDetail> smsWmsOutStockDetails = smsWmsOutStockDocDTO.getSmsWmsOutStockDetails();
            smsWmsOutStockDetails.forEach(u->u.setDocNo(docNo));
            smsWmsOutStockDetailService.saveBatch(smsWmsOutStockDetails);

            //删除原有docNo的绑定
            smsWmsOutStockListService.remove(new QueryWrapper<SmsWmsOutStockList>().eq("doc_no",docNo));
            //保存备料单工单绑定表
            List<SmsWmsOutStockList> smsWmsOutStockLists = smsWmsOutStockDocDTO.getSmsWmsOutStockLists();
            smsWmsOutStockLists.forEach(u->u.setDocNo(docNo));
            smsWmsOutStockListService.saveBatch(smsWmsOutStockLists);

            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "单号："+finalDocNo+"已扫码，无法修改");
        }
    }

    @Override
    public Result statement(Map<String, Object> map) {
        List<Map<String,Object>> result=smsWmsOutStockDocMapper.statement(map);
        List<Map<String,Object>> smsWmsOutStockMap=smsWmsOutStockDetailService.getListByDocNo(map);
        for (Map<String, Object> smsWmsOutStock : smsWmsOutStockMap) {
            Long osdAmountPlan = MapUtil.getLong(smsWmsOutStock, "osdAmountPlan");
            Long osdAmountReal = MapUtil.getLong(smsWmsOutStock, "osdAmountReal");
            if (osdAmountPlan-osdAmountReal>0){
                List<Map<String,Object>> outStockDocS = smsWmsOutStockDocMapper.statementStock(MapUtil.getStr(smsWmsOutStock,"coItemCode"));
                for (Map<String, Object> outStockDoc : outStockDocS) {
                    outStockDoc.putAll(smsWmsOutStock);
                    result.add(outStockDoc);
                }
            }
        }
        return Result.succeed(result,"查询成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result pdaCancelOutStock(Map<String, Object> map) {
        try {
            String barcode = MapUtil.getStr(map, "barcode");
            String empNo = MapUtil.getStr(map, "empNo");
            String docNum = MapUtil.getStr(map, "docNum");
            String dtCode = MapUtil.getStr(map, "dtCode");
            SmsWmsIoType smsWmsIoType = smsWmsIoTypeService.getOne(new LambdaQueryWrapper<SmsWmsIoType>().eq(SmsWmsIoType::getDtCode, dtCode));
            String dtName="";
            if (smsWmsIoType!=null){
                dtName=smsWmsIoType.getDtName();
            }
            UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
            if (user == null) {
                return Result.failed("无此员工");
            }
            SmsWmsStockInfo stockInfo = smsWmsStockInfoService.getOne(new QueryWrapper<SmsWmsStockInfo>().eq("TBL_BARCODE", barcode));
            if (stockInfo == null) {
                return Result.failed("无此条码");
            }
            if (!"W-M-MPA".equals(stockInfo.getWhCode())) {
                return Result.failed("此条码不在正式库");
            }
            Long skAmount = stockInfo.getAmount();
            String coItemCode = stockInfo.getCoItemCode();
            if(skAmount<=0){
                return Result.failed("剩余数量不足");
            }
            SmsWmsOutStockDoc outStockDoc = getOne(new QueryWrapper<SmsWmsOutStockDoc>().eq("doc_no", docNum));
            if (outStockDoc == null) {
                return Result.failed("无此单号");
            }
            if (!outStockDoc.getDtCode().equals(dtCode)){
                return Result.failed("扫码类型出错");
            }
            SmsWmsOutStockList outStockList = smsWmsOutStockListService.getOne(new QueryWrapper<SmsWmsOutStockList>()
                    .eq("TBL_BARCODE", barcode)
                    .eq("doc_no",docNum)
            );
            if (outStockList==null){
                return Result.failed("无此条码入库记录");
            }
            Long osdId = outStockList.getOsdId();
            SmsWmsOutStockDetail outStockDetail = smsWmsOutStockDetailService.getById(osdId);
            if (outStockDetail==null){
                return Result.failed("备料单与物料不匹配");
            }
            //删除List表记录
            smsWmsOutStockListService.removeById(outStockList);

            //修改DETAIL表数据
            outStockDetail.setOsdAmountReal(outStockDetail.getOsdAmountReal()-skAmount);
            smsWmsOutStockDetailService.updateById(outStockDetail);

            List<SmsWmsOutStockList> lists = smsWmsOutStockListService.list(new QueryWrapper<SmsWmsOutStockList>().eq("doc_no", docNum));
            //修改出库单状态
            outStockDoc.setDocStatus(lists.size()<=0?"1":"2");

            //修改仓库信息
            stockInfo.setStockFlag("1");
            smsWmsStockInfoService.updateById(stockInfo);

            updateById(outStockDoc);
            //pda操作日志
            pdaMesLogService.save(PdaMesLog.builder()
                    .type(dtName+"取消出库")
                    .barcode(barcode)
                    .itemCode(stockInfo.getCoItemCode())
                    .num(stockInfo.getAmount())
                    .tblManufacturerBat(stockInfo.getLotNumber())
                    .tblManufacturerDate(stockInfo.getPdate())
                    .docNo(docNum)
                    .createUser(user.getUserName())
                    .build()
            );
           return Result.succeed("取消备料成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "取消备料失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result pdaOutStock(Map<String, Object> map) {
        try{
            String barcode = MapUtil.getStr(map, "barcode");
            String empNo = MapUtil.getStr(map, "empNo");
            String docNum = MapUtil.getStr(map, "docNum");
            String dtCode = MapUtil.getStr(map, "dtCode");
            SmsWmsIoType smsWmsIoType = smsWmsIoTypeService.getOne(new LambdaQueryWrapper<SmsWmsIoType>().eq(SmsWmsIoType::getDtCode, dtCode));
            String dtName="";
            if (smsWmsIoType!=null){
                dtName=smsWmsIoType.getDtName();
            }
            UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
            if (user == null) {
                return Result.failed("无此员工");
            }
            SmsWmsStockInfo stockInfo = smsWmsStockInfoService.getOne(new QueryWrapper<SmsWmsStockInfo>().eq("TBL_BARCODE", barcode));
            if (stockInfo == null) {
                return Result.failed("无此条码");
            }
            /*2021.03.15需求更改去除限制
            if (!"W-M-MPA".equals(stockInfo.getWhCode())) {
                return Result.failed("此条码不在正式库");
            }*/
            if (!("1".equals(stockInfo.getStockFlag()))) {
                return Result.failed("此条码库存非在库状态");
            }
            Long skAmount = stockInfo.getAmount();
            String coItemCode = stockInfo.getCoItemCode();
            if(skAmount<=0){
                return Result.failed("剩余数量不足");
            }
            SmsWmsOutStockDoc outStockDoc = getOne(new QueryWrapper<SmsWmsOutStockDoc>().eq("doc_no", docNum));
            if (outStockDoc == null) {
                return Result.failed("无此单号");
            }
            if (!outStockDoc.getDtCode().equals(dtCode)){
                return Result.failed("扫码类型出错");
            }
            SmsWmsOutStockDetail outStockDetail = smsWmsOutStockDetailService.getOne(new QueryWrapper<SmsWmsOutStockDetail>()
                    .eq("item_code", coItemCode)
                    .eq("doc_no", docNum)
            );
            if (outStockDetail==null){
                return Result.failed("备料单与物料不匹配");
            }
            //修改DETAIL表数据
            outStockDetail.setOsdAmountReal(outStockDetail.getOsdAmountReal()+skAmount);

            smsWmsOutStockDetailService.updateById(outStockDetail);

            //修改仓库信息
            stockInfo.setStockFlag("2");
            smsWmsStockInfoService.updateById(stockInfo);

            //添加list表
            SmsWmsOutStockList smsWmsOutStockList = new SmsWmsOutStockList();
            smsWmsOutStockList.setDocNo(docNum);
            smsWmsOutStockList.setOsdId(outStockDetail.getId());
            smsWmsOutStockList.setCoItemCode(coItemCode);
            smsWmsOutStockList.setOslLotNumber(stockInfo.getLotNumber());
            smsWmsOutStockList.setTblBarcode(barcode);
            smsWmsOutStockList.setOslAmount(skAmount);
            smsWmsOutStockList.setOutStockManNo(empNo);
            smsWmsOutStockList.setOutStockTime(new Date());
            smsWmsOutStockList.setOsContainer(stockInfo.getContainer());

            smsWmsOutStockList.setWhCode(stockInfo.getWhCode());
            smsWmsOutStockList.setReservoirCode(stockInfo.getReservoirCode());
            smsWmsOutStockList.setAreaSn(stockInfo.getAreaSn());
            smsWmsOutStockList.setProjectId(stockInfo.getProjectId());
            smsWmsOutStockListService.save(smsWmsOutStockList);

            //修改出库单状态
            outStockDoc.setDocStatus("2");
            updateById(outStockDoc);
            //pda操作日志
            pdaMesLogService.save(PdaMesLog.builder()
                    .type(dtName+"出库")
                    .barcode(barcode)
                    .itemCode(stockInfo.getCoItemCode())
                    .num(stockInfo.getAmount())
                    .tblManufacturerBat(stockInfo.getLotNumber())
                    .tblManufacturerDate(stockInfo.getPdate())
                    .docNo(docNum)
                    .createUser(user.getUserName())
                    .build()
            );
            return Result.succeed("备料成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "备料失败");
        }
    }

    @Override
    public Result getDocNo(Map<String, Object> map) {
        String depaCode = MapUtil.getStr(map, "depaCode");
        String dtCode = MapUtil.getStr(map, "dtCode");
        String prefix=depaCode + dtCode + DateUtil.format(new Date(), "yyyyMM");
        String maxDocNo=prefix+"0001";
        SmsWmsOutStockDoc one = getOne(new QueryWrapper<SmsWmsOutStockDoc>()
                .last("where doc_no like '"+prefix+"%' order by doc_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getDocNo(), prefix));
            maxDocNo=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxDocNo, "查询成功");
    }

    @Override
    public Result getDocNoByProjectIdAndDocNo(Map<String, Object> map) {
        List<Map<String,Object>> list=smsWmsOutStockDocMapper.getDocNoByProjectIdAndDocNo(map);
        return Result.succeed(list,"查询成功");
    }

    @Override
    public Result getItemNumByDocNos(Map<String, List<String>> map) {
        List<String> docNos = map.get("docNos");
        String projects=smsWmsOutStockDocMapper.getAllProjects(map);
        Map<String, Object> result=new HashMap<>();
        List<Map<String, Object>> items=new ArrayList<>();
        if (docNos==null||docNos.size()<=0){
            return Result.succeed(items,"查询成功");
        }
        items=smsWmsOutStockDocMapper.getItemNumByDocNos(map);
        result.put("docNos",docNos);
        result.put("items",items);
        result.put("projects",projects);
        return Result.succeed(result,"查询成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result removeAll(Map<String, List<Long>> map) {
        String finalDocNo="";
        try{
            List<Long> ids = map.get("ids");
            for (Long id : ids) {
                SmsWmsOutStockDoc smsWmsOutStockDoc = getById(id);
                String docNo = smsWmsOutStockDoc.getDocNo();
                finalDocNo=docNo;
                hasPda(docNo);
                smsWmsOutStockDetailService.remove(new QueryWrapper<SmsWmsOutStockDetail>().eq("doc_no",docNo));
                smsWmsOutStockPmItemService.remove(new QueryWrapper<SmsWmsOutStockPmItem>().eq("doc_no",docNo));
            }
            removeByIds(ids);
            return Result.succeed("删除成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed("单号："+finalDocNo+"已扫码，无法删除");
        }
    }



    /**判断该单号是否已经过pda扫码，如果是不允许删除*/
    private void hasPda(String docNo){
        List<SmsWmsOutStockList> smsWmsOutStockLists = smsWmsOutStockListService.list(new QueryWrapper<SmsWmsOutStockList>().eq("doc_no", docNo));
        if (smsWmsOutStockLists!=null&&smsWmsOutStockLists.size()>0){
            int i=1/0;
        }
    }
}
