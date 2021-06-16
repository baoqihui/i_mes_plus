package com.rh.i_mes_plus.service.impl.sms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.sms.SmsWmsBarcodeInfoMapper;
import com.rh.i_mes_plus.model.other.PdaMesLog;
import com.rh.i_mes_plus.model.sms.SmsWmsBarcodeInfo;
import com.rh.i_mes_plus.model.sms.SmsWmsStockInfo;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.other.IPdaMesLogService;
import com.rh.i_mes_plus.service.sms.ISmsWmsBarcodeInfoService;
import com.rh.i_mes_plus.service.sms.ISmsWmsStockInfoService;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 条码信息
 *
 * @author hqb
 * @date 2020-10-07 10:11:58
 */
@Slf4j
@Service
public class SmsWmsBarcodeInfoServiceImpl extends ServiceImpl<SmsWmsBarcodeInfoMapper, SmsWmsBarcodeInfo> implements ISmsWmsBarcodeInfoService {
    @Resource
    private SmsWmsBarcodeInfoMapper smsWmsBarcodeInfoMapper;
    @Autowired
    private ISmsWmsStockInfoService smsWmsStockInfoService;
    @Autowired
    private IPdaMesLogService pdaMesLogService;
    @Autowired
    private IUmsUserService umsUserService;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<SmsWmsBarcodeInfo> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<SmsWmsBarcodeInfo> pages = new Page<>(pageNum, pageSize);
        return smsWmsBarcodeInfoMapper.findList(pages, params);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result spilt(Map<String, Object> params) {
        try{
            String tblBarcode = MapUtil.getStr(params, "tblBarcode");
            Long amount = MapUtil.getLong(params, "amount");
            String empNo = MapUtil.getStr(params, "empNo");
            if (amount<=0){
                return Result.failed("拆分数量不符");
            }
            UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
            if (user == null) {
                return Result.failed("无此员工");
            }
            //从最后一个|后截取-
            String[] split = tblBarcode.split("\\|");
            String suffix = split[split.length - 1];
            String prefix = StrUtil.removeSuffix(tblBarcode, suffix)+StrUtil.sub(suffix,0,4)+"-";
            SmsWmsBarcodeInfo barcodeInfo = getOne(new QueryWrapper<SmsWmsBarcodeInfo>().eq("TBL_BARCODE", tblBarcode));
            SmsWmsStockInfo stockInfo = smsWmsStockInfoService.getOne(new QueryWrapper<SmsWmsStockInfo>()
                    .eq("tbl_barcode", tblBarcode)
            );
            Long existAmount = stockInfo.getAmount();
            if (stockInfo == null||barcodeInfo==null) {
                return Result.failed("无此条码");
            }
            if (existAmount<amount){
                return Result.failed("拆分数量不符");
            }
            String newTblBarcode=prefix+"1";
            SmsWmsStockInfo one = smsWmsStockInfoService.getOne(new QueryWrapper<SmsWmsStockInfo>()
                    .last("where tbl_barcode like '" + prefix + "%' order by tbl_barcode desc limit 1"));
            if (one != null) {
                Long aLong = Convert.toLong(StrUtil.removePrefix(one.getTblBarcode(), prefix));
                newTblBarcode=prefix+(aLong+1);
            }
            //1. 添加克隆条码库存信息
            SmsWmsStockInfo newStockInfo=new SmsWmsStockInfo();
            BeanUtil.copyProperties(stockInfo,newStockInfo);
            newStockInfo.setAmount(amount);
            newStockInfo.setTblBarcode(newTblBarcode);
            smsWmsStockInfoService.save(newStockInfo);
            //2. 修改原库存数量
            stockInfo.setAmount(existAmount-amount);
            smsWmsStockInfoService.updateById(stockInfo);
            //3. 创建拆分条码记录
            SmsWmsBarcodeInfo newBarcodeInfo = new SmsWmsBarcodeInfo();
            BeanUtil.copyProperties(barcodeInfo,newBarcodeInfo);
            newBarcodeInfo.setTblNum(amount);
            newBarcodeInfo.setTblBarcode(newTblBarcode);
            newBarcodeInfo.setIsOpen(true);
            newBarcodeInfo.setHasPrint(false);
            save(newBarcodeInfo);
            //4. 修改原条码数据
            Long tblNum = barcodeInfo.getTblNum();
            barcodeInfo.setTblNum(tblNum -amount);
            barcodeInfo.setIsOpen(true);
            barcodeInfo.setHasPrint(false);
            updateById(barcodeInfo);
            //作业日志
            pdaMesLogService.save(PdaMesLog.builder()
                    .type("拆签")
                    .barcode(tblBarcode)
                    .newBarcode(newTblBarcode)
                    .itemCode(barcodeInfo.getTblItemcode())
                    .num(tblNum)
                    .tblManufacturerBat(barcodeInfo.getTblManufacturerBat())
                    .tblManufacturerDate(barcodeInfo.getTblManufacturerDate())
                    .createUser(user.getUserName())
                    .build()
            );
            return Result.succeed("保存成功，新条码："+newTblBarcode);
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "保存失败");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result combine(Map<String, Object> params) {
        try{
            String fromTblBarcode = MapUtil.getStr(params, "fromTblBarcode"); //被合并的标签（删除）
            String toTblBarcode = MapUtil.getStr(params, "toTblBarcode");   //合并到的标签（数量增加）


            String empNo = MapUtil.getStr(params, "empNo");
            UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
            if (user == null) {
                return Result.failed("无此员工");
            }
            String[] fromSplit = fromTblBarcode.split("\\|");
            String fromSuffix = fromSplit[fromSplit.length - 1];
            String fromPrefix = StrUtil.removeSuffix(fromTblBarcode, fromSuffix)+StrUtil.sub(fromSuffix,0,4);

            String[] toSplit = toTblBarcode.split("\\|");
            String toSuffix = toSplit[toSplit.length - 1];
            String toPrefix = StrUtil.removeSuffix(toTblBarcode, toSuffix)+StrUtil.sub(toSuffix,0,4);
            System.out.println(fromPrefix+"   "+toPrefix);
            if (!fromPrefix.equals(toPrefix)){
                return Result.failed("所合并条码信息不符");
            }
            SmsWmsStockInfo fromStockInfo = smsWmsStockInfoService.getOne(new QueryWrapper<SmsWmsStockInfo>()
                    .eq("tbl_barcode", fromTblBarcode)
            );
            SmsWmsStockInfo toStockInfo = smsWmsStockInfoService.getOne(new QueryWrapper<SmsWmsStockInfo>()
                    .eq("tbl_barcode", toTblBarcode)
            );
            if (fromStockInfo==null||toStockInfo==null){
                return Result.failed("条码不存在");
            }
            if ((!"1".equals(fromStockInfo.getStockFlag()))||(!"1".equals(toStockInfo.getStockFlag()))){
                return Result.failed("条码非在库状态");
            }
            if (!fromStockInfo.getWhCode().equals(toStockInfo.getWhCode())){
                return Result.failed("合并条码非同库区");
            }
            if (!fromStockInfo.getCoItemCode().equals(toStockInfo.getCoItemCode())){
                return Result.failed("只有同物料可以进行合并");
            }

            Long amount = fromStockInfo.getAmount();
            toStockInfo.setAmount(toStockInfo.getAmount()+amount);
            smsWmsStockInfoService.updateById(toStockInfo);
            smsWmsStockInfoService.removeById(fromStockInfo);
            remove(new QueryWrapper<SmsWmsBarcodeInfo>().eq("TBL_BARCODE",fromTblBarcode));
            SmsWmsBarcodeInfo toSmsWmsBarcodeInfo = getOne(new QueryWrapper<SmsWmsBarcodeInfo>().eq("TBL_BARCODE", toTblBarcode));
            //4. 修改原条码数据
            Long tblNum = toSmsWmsBarcodeInfo.getTblNum();
            toSmsWmsBarcodeInfo.setTblNum(tblNum +amount);
            toSmsWmsBarcodeInfo.setIsOpen(true);
            toSmsWmsBarcodeInfo.setHasPrint(false);
            updateById(toSmsWmsBarcodeInfo);
            //作业日志
            pdaMesLogService.save(PdaMesLog.builder()
                    .type("合签")
                    .barcode(toTblBarcode)
                    .newBarcode(fromTblBarcode)
                    .itemCode(toSmsWmsBarcodeInfo.getTblItemcode())
                    .num(tblNum)
                    .tblManufacturerBat(toSmsWmsBarcodeInfo.getTblManufacturerBat())
                    .tblManufacturerDate(toSmsWmsBarcodeInfo.getTblManufacturerDate())
                    .createUser(user.getUserName())
                    .build()
            );
            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "保存失败");
        }
    }

    @Override
    public Result getBarcodeInfo(Map<String, Object> params) {
        String tblBarcode = MapUtil.getStr(params, "tblBarcode");
        SmsWmsBarcodeInfo barcodeInfo = getOne(new QueryWrapper<SmsWmsBarcodeInfo>()
                .eq("TBL_BARCODE", tblBarcode));
        if (barcodeInfo==null){
            return Result.failed("无此条码");
        }
        List<SmsWmsBarcodeInfo> barcodeInfos=new ArrayList<>();
        barcodeInfos.add(barcodeInfo);
        return Result.succeed(barcodeInfos,"查询成功");
    }

    @Override
    public Result getOpenBarcode() {
        List<SmsWmsBarcodeInfo> list = list(new QueryWrapper<SmsWmsBarcodeInfo>()
                .eq("is_open", 1)
                .eq("has_print", 0)
        );
        return Result.succeed(list,"查询成功");
    }

    @Override
    public List<Map<String, Object>> getNotPdaBarcodes(String wrDocNum) {
        return smsWmsBarcodeInfoMapper.getNotPdaBarcodes(wrDocNum);
    }


}
