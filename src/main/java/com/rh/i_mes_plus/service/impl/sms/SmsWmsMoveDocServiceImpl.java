package com.rh.i_mes_plus.service.impl.sms;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsMoveDocDTO;
import com.rh.i_mes_plus.mapper.sms.SmsWmsMoveDocMapper;
import com.rh.i_mes_plus.model.other.PdaMesLog;
import com.rh.i_mes_plus.model.sms.*;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.other.IPdaMesLogService;
import com.rh.i_mes_plus.service.sms.ISmsWmsMoveDocDetailService;
import com.rh.i_mes_plus.service.sms.ISmsWmsMoveDocDetailSubService;
import com.rh.i_mes_plus.service.sms.ISmsWmsMoveDocService;
import com.rh.i_mes_plus.service.sms.ISmsWmsStockInfoService;
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
 * 调拨单
 *
 * @author hbq
 * @date 2020-12-11 13:39:17
 */
@Slf4j
@Service
public class SmsWmsMoveDocServiceImpl extends ServiceImpl<SmsWmsMoveDocMapper, SmsWmsMoveDoc> implements ISmsWmsMoveDocService {
    @Resource
    private SmsWmsMoveDocMapper smsWmsMoveDocMapper;
    @Autowired
    private ISmsWmsMoveDocDetailService smsWmsMoveDocDetailService;
    @Autowired
    private ISmsWmsMoveDocDetailSubService smsWmsMoveDocDetailSubService;
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
        return smsWmsMoveDocMapper.findList(pages, params);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveAll(SmsWmsMoveDocDTO smsWmsMoveDocDTO) {
        String finalMovNo="";
        try{
            SmsWmsMoveDoc smsWmsMoveDoc=smsWmsMoveDocDTO;
            String moveNo = smsWmsMoveDoc.getMoveNo();
            String inWhCode = smsWmsMoveDoc.getInWhCode();
            String outWhCode = smsWmsMoveDoc.getOutWhCode();
            finalMovNo=moveNo;
            hasPda(moveNo);
            saveOrUpdate(smsWmsMoveDoc);
            //删除原有docNo的详情
            smsWmsMoveDocDetailService.remove(new QueryWrapper<SmsWmsMoveDocDetail>().eq("move_no",moveNo));
            List<SmsWmsMoveDocDetail> smsWmsMoveDocDetails = smsWmsMoveDocDTO.getSmsWmsMoveDocDetails();
            if (smsWmsMoveDocDetails == null||smsWmsMoveDocDetails.size()<=0) {
                return Result.failed(  "物料列表不能为空");
            }
            smsWmsMoveDocDetails.forEach(u->{
                u.setMoveNo(moveNo);
                u.setInWhCode(inWhCode);
                u.setOutWhCode(outWhCode);
            });
            smsWmsMoveDocDetailService.saveBatch(smsWmsMoveDocDetails);
            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed(  "单号："+finalMovNo+"已扫码，无法修改");
        }
    }
    
    @Override
    public Result getDocNo(Map<String, Object> map) {
        String depaCode = MapUtil.getStr(map, "depaCode");
        String dtCode = MapUtil.getStr(map, "dtCode");
        String prefix=depaCode + dtCode + DateUtil.format(new Date(), "yyyyMM");
        String maxDocNo=prefix+"0001";
        SmsWmsMoveDoc one = getOne(new QueryWrapper<SmsWmsMoveDoc>()
                .last("where move_no like '" + prefix + "%' order by move_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getMoveNo(), prefix));
            maxDocNo=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxDocNo, "查询成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result removeAll(Map<String, List<Long>> map) {
        String finalDocNo="";
        try{
            List<Long> ids = map.get("ids");
            for (Long id : ids) {
                SmsWmsMoveDoc smsWmsMoveDoc = getById(id);
                String moveNo = smsWmsMoveDoc.getMoveNo();
                finalDocNo=moveNo;
                hasPda(moveNo);
                smsWmsMoveDocDetailService.remove(new QueryWrapper<SmsWmsMoveDocDetail>().eq("move_no",moveNo));
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
    public Result pdaMove(Map<String, Object> map) {
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
            Long skAmount = stockInfo.getAmount();
            String coItemCode = stockInfo.getCoItemCode();
            String whCode = stockInfo.getWhCode();
            if(skAmount<=0){
                return Result.failed("剩余数量不足");
            }
            SmsWmsMoveDoc moveDoc = getOne(new QueryWrapper<SmsWmsMoveDoc>().eq("move_no", docNum));
            if (moveDoc == null) {
                return Result.failed("无此单号");
            }
            if (!moveDoc.getOutWhCode().equals(whCode)){
                return Result.failed("该条码不在此库");
            }
            SmsWmsMoveDocDetail moveDocDetail = smsWmsMoveDocDetailService.getOne(new QueryWrapper<SmsWmsMoveDocDetail>()
                    .eq("item_code", coItemCode)
                    .eq("move_no", docNum)
            );
            if (moveDocDetail==null){
                return Result.failed("调拨单单与物料不匹配");
            }
            Long planTotal=moveDocDetail.getPlanAmount();
            Long receiveTotal=moveDocDetail.getRealAmount();
            Long remainTotal=planTotal-receiveTotal;
            if (remainTotal<skAmount){
                return Result.failed("条码数量超出待收数量");
            }
            //修改DETAIL表数据
            moveDocDetail.setRealAmount(moveDocDetail.getRealAmount()+skAmount);
    
            smsWmsMoveDocDetailService.updateById(moveDocDetail);
        
            //修改仓库信息
            stockInfo.setWhCode(moveDoc.getInWhCode());
            smsWmsStockInfoService.updateById(stockInfo);
    
            SmsWmsMoveDocDetailSub smsWmsMoveDocDetailSub = new SmsWmsMoveDocDetailSub();
            smsWmsMoveDocDetailSub.setMoveDid(moveDocDetail.getId());
            smsWmsMoveDocDetailSub.setMoveNo(docNum);
            smsWmsMoveDocDetailSub.setItemCode(coItemCode);
            smsWmsMoveDocDetailSub.setItemName(moveDocDetail.getItemName());
            smsWmsMoveDocDetailSub.setBarcode(barcode);
            smsWmsMoveDocDetailSub.setAmount(skAmount);
            smsWmsMoveDocDetailSub.setWorkerName(user.getUserName());
            smsWmsMoveDocDetailSub.setWorker(empNo);
            smsWmsMoveDocDetailSub.setWorkTime(new Date());
            smsWmsMoveDocDetailSub.setToErp("N");
            smsWmsMoveDocDetailSub.setOsContainer("");
            smsWmsMoveDocDetailSub.setPalletSn("");
            smsWmsMoveDocDetailSub.setId(0L);
            
            smsWmsMoveDocDetailSubService.save(smsWmsMoveDocDetailSub);
        
            //修改出库单状态
            moveDoc.setMoveStatus("1");
            int totalCount = smsWmsMoveDocDetailService.count(new QueryWrapper<SmsWmsMoveDocDetail>().eq("move_no", docNum));
            int outedCount = smsWmsMoveDocDetailService.count(new QueryWrapper<SmsWmsMoveDocDetail>().eq("move_no", docNum).last("and plan_amount=real_amount"));
            if (totalCount==outedCount) {
                moveDoc.setMoveStatus("4");
            }
            updateById(moveDoc);

            //pda操作日志
            pdaMesLogService.save(PdaMesLog.builder()
                    .type("调拨")
                    .barcode(barcode)
                    .itemCode(stockInfo.getCoItemCode())
                    .num(stockInfo.getAmount())
                    .tblManufacturerBat(stockInfo.getLotNumber())
                    .tblManufacturerDate(stockInfo.getPdate())
                    .docNo(docNum)
                    .createUser(user.getUserName())
                    .build()
            );
            return Result.succeed("扫码成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "扫码失败");
        }
    }

    @Override
    public Result lightUp(Map<String, Object> params) {
        return Result.succeed("亮灯成功");
    }

    @Override
    public Result cancelLightUp(Map<String, Object> params) {

        return null;
    }

    /**判断该单号是否已经过pda扫码，如果是不允许删除*/
    private void hasPda(String moveNo){
        List<SmsWmsMoveDocDetailSub> smsWmsMoveDocDetailSubs = smsWmsMoveDocDetailSubService.list(new QueryWrapper<SmsWmsMoveDocDetailSub>().eq("move_no", moveNo));
        if (smsWmsMoveDocDetailSubs!=null&&smsWmsMoveDocDetailSubs.size()>0){
            int i=1/0;
        }
    }
}
