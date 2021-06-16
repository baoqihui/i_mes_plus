package com.rh.i_mes_plus.service.impl.sms;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsReloadDocDTO;
import com.rh.i_mes_plus.mapper.sms.SmsWmsReloadDocMapper;
import com.rh.i_mes_plus.model.other.PdaMesLog;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDoc;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDocDetail;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDocDetailSub;
import com.rh.i_mes_plus.model.sms.SmsWmsStockInfo;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.other.IPdaMesLogService;
import com.rh.i_mes_plus.service.sms.ISmsWmsReloadDocDetailService;
import com.rh.i_mes_plus.service.sms.ISmsWmsReloadDocDetailSubService;
import com.rh.i_mes_plus.service.sms.ISmsWmsReloadDocService;
import com.rh.i_mes_plus.service.sms.ISmsWmsStockInfoService;
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
 * 调拨单
 *
 * @author hbq
 * @date 2020-12-16 14:55:14
 */
@Slf4j
@Service
public class SmsWmsReloadDocServiceImpl extends ServiceImpl<SmsWmsReloadDocMapper, SmsWmsReloadDoc> implements ISmsWmsReloadDocService {
    @Resource
    private SmsWmsReloadDocMapper smsWmsReloadDocMapper;
    @Autowired
    private ISmsWmsReloadDocDetailService smsWmsReloadDocDetailService;
    @Autowired
    private ISmsWmsReloadDocDetailSubService smsWmsReloadDocDetailSubService;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private ISmsWmsStockInfoService smsWmsStockInfoService;
    @Autowired
    private IPdaMesLogService pdaMesLogService;
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
        return smsWmsReloadDocMapper.findList(pages, params);
    }

    @Override
    public Page<Map> getStockInfoBySn(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return smsWmsReloadDocMapper.getStockInfoBySn(pages, params);
    }

    @Override
    public Result getDocNo(Map<String, Object> map) {
        String depaCode = MapUtil.getStr(map, "depaCode");
        String dtCode = MapUtil.getStr(map, "dtCode");
        String prefix=depaCode + dtCode + DateUtil.format(new Date(), "yyyyMM");
        String maxDocNo=prefix+"0001";
        SmsWmsReloadDoc one = getOne(new QueryWrapper<SmsWmsReloadDoc>()
                .last("where reload_no like '" + prefix + "%' order by reload_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getReloadNo(), prefix));
            maxDocNo=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxDocNo, "查询成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveAll(SmsWmsReloadDocDTO smsWmsReloadDocDTO) {
        String finalReloadNo="";
        try{
            SmsWmsReloadDoc smsWmsReloadDoc=smsWmsReloadDocDTO;
            String reloadNo = smsWmsReloadDoc.getReloadNo();
            finalReloadNo=reloadNo;
            hasPda(reloadNo);
            saveOrUpdate(smsWmsReloadDoc);
            //删除原有docNo的详情
            smsWmsReloadDocDetailService.remove(new QueryWrapper<SmsWmsReloadDocDetail>().eq("reload_no",reloadNo));
            List<SmsWmsReloadDocDetail> smsWmsReloadDocDetails = smsWmsReloadDocDTO.getSmsWmsReloadDocDetails();
            if (smsWmsReloadDocDetails == null||smsWmsReloadDocDetails.size()<=0) {
                return Result.failed(  "物料列表不能为空");
            }
            smsWmsReloadDocDetails.forEach(u->{
                u.setReloadNo(reloadNo);
            });
            smsWmsReloadDocDetailService.saveBatch(smsWmsReloadDocDetails);
            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed(  "单号："+finalReloadNo+"已扫码，无法修改");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result removeAll(Map<String, List<Long>> map) {
        String finalDocNo="";
        try{
            List<Long> ids = map.get("ids");
            for (Long id : ids) {
                SmsWmsReloadDoc smsWmsReloadDoc = getById(id);
                String reloadNo = smsWmsReloadDoc.getReloadNo();
                finalDocNo=reloadNo;
                hasPda(reloadNo);
                smsWmsReloadDocDetailService.remove(new QueryWrapper<SmsWmsReloadDocDetail>().eq("reload_no",reloadNo));
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
    public Result pdaReload(Map<String, Object> map) {
        try{
            String barcode = MapUtil.getStr(map, "barcode");
            String empNo = MapUtil.getStr(map, "empNo");
            String docNum = MapUtil.getStr(map, "docNum");
            String dtCode = MapUtil.getStr(map, "dtCode");

            UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
            if (user == null) {
                return Result.failed("无此员工");
            }
            SmsWmsStockInfo stockInfo = smsWmsStockInfoService.getOne(new QueryWrapper<SmsWmsStockInfo>().eq("TBL_BARCODE", barcode));
            if (stockInfo == null) {
                return Result.failed("无此条码");
            }
            if (!("1".equals(stockInfo.getStockFlag()))) {
                return Result.failed("此条码库存非在库状态");
            }
            String poNo = stockInfo.getOrderNo();
            Long skAmount = stockInfo.getAmount();
            String coItemCode = stockInfo.getCoItemCode();
            if(skAmount<=0){
                return Result.failed("剩余数量不足");
            }
            SmsWmsReloadDoc reloadDoc = getOne(new QueryWrapper<SmsWmsReloadDoc>().eq("reload_no", docNum));
            if (reloadDoc == null) {
                return Result.failed("无此单号");
            }
            if (!"0".equals(reloadDoc.getReloadStatus())) {
                return Result.failed("该换料单状态不匹配");
            }
            SmsWmsReloadDocDetail reloadDocDetail = smsWmsReloadDocDetailService.getOne(new QueryWrapper<SmsWmsReloadDocDetail>()
                    .eq("item_code", coItemCode)
                    .eq("reload_no", docNum)
                    .eq("po_no", poNo)
            );
            if (reloadDocDetail==null){
                return Result.failed("换料单与物料不匹配");
            }
            Long planTotal=reloadDocDetail.getTotalAmount();
            Long receiveTotal=reloadDocDetail.getReceiveAmount();
            Long remainTotal=planTotal-receiveTotal;
            if (remainTotal<skAmount){
                return Result.failed("条码数量超出待收数量");
            }
            //更新换料单详情信息
            reloadDocDetail.setRealityAmount(reloadDocDetail.getRealityAmount()+skAmount);
            smsWmsReloadDocDetailService.updateById(reloadDocDetail);

            //修改仓库信息
            stockInfo.setWhCode("huanliaoku");
            stockInfo.setStockFlag("2");
            smsWmsStockInfoService.updateById(stockInfo);

            //添加list表
            SmsWmsReloadDocDetailSub smsWmsReloadDocDetailSub = new SmsWmsReloadDocDetailSub();
            smsWmsReloadDocDetailSub.setReloadDid(reloadDocDetail.getId());
            smsWmsReloadDocDetailSub.setReloadNo(docNum);
            smsWmsReloadDocDetailSub.setItemCode(coItemCode);
            smsWmsReloadDocDetailSub.setBarcode(barcode);
            smsWmsReloadDocDetailSub.setWorker(user.getUserName());
            smsWmsReloadDocDetailSub.setWorkTime(new Date());
            smsWmsReloadDocDetailSub.setToErp("N");
            smsWmsReloadDocDetailSub.setAmount(skAmount);
            smsWmsReloadDocDetailSubService.save(smsWmsReloadDocDetailSub);

            int totalCount = smsWmsReloadDocDetailService.count(new QueryWrapper<SmsWmsReloadDocDetail>().eq("reload_no", docNum));
            int outedCount = smsWmsReloadDocDetailService.count(new QueryWrapper<SmsWmsReloadDocDetail>().eq("reload_no", docNum).last("and total_amount=reality_amount"));
            if (totalCount==outedCount) {
                reloadDoc.setReloadStatus("1");
            }
            updateById(reloadDoc);
            //pda操作日志
            pdaMesLogService.save(PdaMesLog.builder()
                    .type("换料")
                    .barcode(barcode)
                    .itemCode(stockInfo.getCoItemCode())
                    .num(stockInfo.getAmount())
                    .tblManufacturerBat(stockInfo.getLotNumber())
                    .tblManufacturerDate(stockInfo.getPdate())
                    .docNo(docNum)
                    .createUser(user.getUserName())
                    .build()
            );
            return Result.succeed("换料成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "换料失败");
        }
    }

    /**判断该单号是否已经过pda扫码，如果是不允许删除*/
    private void hasPda(String reloadNo){
        List<SmsWmsReloadDocDetailSub> smsWmsReloadDocDetailSubs = smsWmsReloadDocDetailSubService.list(new QueryWrapper<SmsWmsReloadDocDetailSub>().eq("reload_no", reloadNo));
        if (smsWmsReloadDocDetailSubs!=null&&smsWmsReloadDocDetailSubs.size()>0){
            int i=1/0;
        }
    }
}
