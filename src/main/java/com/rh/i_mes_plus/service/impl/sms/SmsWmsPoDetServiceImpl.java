package com.rh.i_mes_plus.service.impl.sms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsBarcodeInfoDTO;
import com.rh.i_mes_plus.dto.UmsPoDTO;
import com.rh.i_mes_plus.mapper.sms.SmsWmsBarcodeInfoLogMapper;
import com.rh.i_mes_plus.mapper.sms.SmsWmsPoDetMapper;
import com.rh.i_mes_plus.model.other.SapMesLog;
import com.rh.i_mes_plus.model.sms.*;
import com.rh.i_mes_plus.model.ums.UmsItemSap;
import com.rh.i_mes_plus.service.other.ISapMesLogService;
import com.rh.i_mes_plus.service.sms.*;
import com.rh.i_mes_plus.service.ums.IUmsItemSapService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

/**
 * 采购订单明细
 *
 * @author hqb
 * @date 2020-09-29 15:58:30
 */
@Slf4j
@Service
public class SmsWmsPoDetServiceImpl extends ServiceImpl<SmsWmsPoDetMapper, SmsWmsPoDet> implements ISmsWmsPoDetService {
    @Resource
    private SmsWmsPoDetMapper smsWmsPoDetMapper;
    @Autowired
    private ISmsWmsBarcodeInfoLogService smsWmsBarcodeInfoLogService;
    @Resource
    private SmsWmsBarcodeInfoLogMapper smsWmsBarcodeInfoLogMapper;
    @Resource
    private IUmsItemSapService umsItemSapService;
    @Autowired
    private ISmsWmsPoService smsWmsPoService;
    @Autowired
    private ISapMesLogService sapMesLogService;
    @Autowired
    private ISmsCoManufacturerService smsCoManufacturerService;
    @Autowired
    private ISmsWmsBarcodeInfoService smsWmsBarcodeInfoService;
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
        Page<SmsWmsPoDet> pages = new Page<>(pageNum, pageSize);
        return smsWmsPoDetMapper.findList(pages, params);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createBarCode(List<SmsWmsBarcodeInfoDTO> smsWmsBarcodeInfoDTOS) {
        List<SmsWmsBarcodeInfoLog> smsWmsBarcodeInfoLogs=new ArrayList<SmsWmsBarcodeInfoLog>();
        try{
            for (SmsWmsBarcodeInfoDTO smsWmsBarcodeInfoDTO : smsWmsBarcodeInfoDTOS) {
                String tblSupcode = smsWmsBarcodeInfoDTO.getTblSupCode();
                String itemCode = smsWmsBarcodeInfoDTO.getTblItemcode();
                String yyMMdd = DateUtil.format(new Date(), "yyMMdd");
                String yyyyMMdd = DateUtil.format(new Date(), "yyyy-MM-dd");
                String maxCode = smsWmsBarcodeInfoLogMapper.getMax(itemCode,yyyyMMdd)!=null?smsWmsBarcodeInfoLogMapper.getMax(itemCode,yyyyMMdd):"0000";


                SmsWmsBarcodeInfoLog smsWmsBarcodeInfoLog=new SmsWmsBarcodeInfoLog();
                BeanUtil.copyProperties(smsWmsBarcodeInfoDTO,smsWmsBarcodeInfoLog);
                String[] codes = maxCode.split("\\|");
                Long l = Convert.toLong(codes[codes.length-1]);
                Integer num = smsWmsBarcodeInfoDTO.getNum();
                Long itemMinPack = smsWmsBarcodeInfoDTO.getItemMinPack();       //最小包装数
                Long deliveryNum = smsWmsBarcodeInfoDTO.getDeliveryNum();       //送货数量
                Long totalNum=0L;
                for (int i=1; i<=num; i++) {
                    String newCode=tblSupcode+"|"+itemCode+"|"+yyMMdd+"|"+String.format("%4d", l+i).replace(" ", "0");
                    if (i*itemMinPack<=deliveryNum){
                        smsWmsBarcodeInfoLog.setTblNum(itemMinPack);
                        totalNum=totalNum+itemMinPack;
                    }else {
                        smsWmsBarcodeInfoLog.setTblNum((deliveryNum-totalNum)<=0?0:deliveryNum-totalNum);
                        totalNum=totalNum+deliveryNum%itemMinPack;
                    }
                    smsWmsBarcodeInfoLog.setTblBarcode(newCode);
                    smsWmsBarcodeInfoLog.setTblCreatedate(new Date());
                    UmsItemSap umsItemSap = umsItemSapService.getOne(new QueryWrapper<UmsItemSap>().eq("CO_ITEM_CODE", smsWmsBarcodeInfoLog.getTblItemcode()));
                    if (umsItemSap == null) {
                        return Result.failed( "生成失败,物料不存在");
                    }
                    smsWmsBarcodeInfoLog.setProjectCode(umsItemSap.getProjectCode());
                    smsWmsBarcodeInfoLog.setMcrMsdCode(umsItemSap.getMcrMsdCode());
                    smsWmsBarcodeInfoLogService.save(smsWmsBarcodeInfoLog);
                    SmsWmsBarcodeInfoLog barcodeInfoLog = smsWmsBarcodeInfoLogService.getById(smsWmsBarcodeInfoLog.getId());
                    SmsCoManufacturer coManufacturer = smsCoManufacturerService.getOne(new QueryWrapper<SmsCoManufacturer>()
                            .eq("MANUFACTURER_CODE", barcodeInfoLog.getTblManufacturerCode()));
                    barcodeInfoLog.setTblManufacturerCode(coManufacturer==null?"":coManufacturer.getManufacturerName());
                    smsWmsBarcodeInfoLogs.add(barcodeInfoLog);
                }
            } }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "生成失败");
        }
        return Result.succeed(smsWmsBarcodeInfoLogs,"生成成功");
    }
    @Override
    public Result createSingleBarCode(Map<String, Object>  params) {
        String barcode = MapUtil.getStr(params, "barcode");
        SmsWmsBarcodeInfo barcodeInfo = smsWmsBarcodeInfoService.getOne(new LambdaQueryWrapper<SmsWmsBarcodeInfo>().eq(SmsWmsBarcodeInfo::getTblBarcode, barcode));
        if (barcode == null) {
            return Result.failed("生成失败，请至少先保存一次条码信息");
        }
        String[] split = barcode.split("\\|");
        String suffix = split[split.length - 1];
        String prefix = StrUtil.removeSuffix(barcode, suffix);
        SmsWmsBarcodeInfo newBarcodeInfo = smsWmsBarcodeInfoService.getOne(new QueryWrapper<SmsWmsBarcodeInfo>()
                .last("where tbl_barcode like '" + prefix + "%' order by tbl_barcode desc limit 1"));
        String newTblBarcode="";
        if (newBarcodeInfo != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(newBarcodeInfo.getTblBarcode(), prefix));
            newTblBarcode=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        barcodeInfo.setId(null);
        barcodeInfo.setTblBarcode(newTblBarcode);
        barcodeInfo.setTblNum(0l);
        return Result.succeed(barcodeInfo,"查询成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveAll(List<UmsPoDTO> umsPoDTO) {
        try{
            Map<String,Object> resultMap=new HashMap();
            List<String> docS=new ArrayList<>();
            List<String> detS=new ArrayList<>();
            for (UmsPoDTO poDTO : umsPoDTO) {
                SmsWmsPo smsWmsPo = poDTO.getSmsWmsPo();
                List<SmsWmsPoDet> smsWmsPoDets = poDTO.getSmsWmsPoDets();

                SmsWmsPo existSmsWmsPo = smsWmsPoService.getOne(new QueryWrapper<SmsWmsPo>()
                        .eq("po_no", smsWmsPo.getPoNo())
                        .eq("is_del",0)
                );

                boolean docSuccessFlag;
                if (existSmsWmsPo != null) {
                    docSuccessFlag = smsWmsPoService.update(smsWmsPo,new QueryWrapper<SmsWmsPo>()
                            .eq("po_no", smsWmsPo.getPoNo())
                            .eq("is_del",0)
                    );
                }else{
                    docSuccessFlag = smsWmsPoService.save(smsWmsPo);
                }
                if (docSuccessFlag){
                    boolean finalFlag=true;
                    for (SmsWmsPoDet smsWmsPoDet : smsWmsPoDets) {
                        boolean detSuccessFlag;
                        SmsWmsPoDet existSmsWmsPoDet = getOne(new QueryWrapper<SmsWmsPoDet>()
                                .eq("twd_po_no", smsWmsPoDet.getTwdPoNo())
                                .eq("twd_po_item_no", smsWmsPoDet.getTwdPoItemNo())
                                .eq("is_del", 0)
                        );
                        if (existSmsWmsPoDet != null) {
                            detSuccessFlag=update(smsWmsPoDet,new QueryWrapper<SmsWmsPoDet>()
                                    .eq("twd_po_no", smsWmsPoDet.getTwdPoNo())
                                    .eq("twd_po_item_no", smsWmsPoDet.getTwdPoItemNo())
                                    .eq("is_del", 0)
                            );
                        }else {
                            smsWmsPoDet.setMesReceiveQty(smsWmsPoDet.getTwdReceiveQty());
                            detSuccessFlag=save(smsWmsPoDet);
                        }
                        if (detSuccessFlag){
                            //如果成功添加det记录
                            detS.add(smsWmsPoDet.getTwdPoNo()+","+smsWmsPoDet.getTwdPoItemNo());
                        }else{
                            //如果存在失败，将docSuccessFlag标记为失败并记录到log
                            SapMesLog sapMesLog = new SapMesLog(
                                    "det错误，po_no="+smsWmsPo.getPoNo(),
                                    "/sms/smsWmsPoDet/saveAll",
                                    1,
                                    "twd_po_item_no="+smsWmsPoDet.getTwdPoItemNo()
                            );
                            //直接存入日志表
                            sapMesLogService.save(sapMesLog);
                            finalFlag=false;
                        }
                    }
                    if (finalFlag){
                        docS.add(smsWmsPo.getPoNo());
                    }
                }else{
                    SapMesLog sapMesLog = new SapMesLog(
                            "doc错误，po_no="+smsWmsPo.getPoNo(),
                            "/sms/smsWmsPoDet/saveAll",
                            1,
                            "doc直接错误，未添加det");
                    //直接存入日志表
                    sapMesLogService.save(sapMesLog);
                }
                resultMap.put("docS",docS);
                resultMap.put("detS",detS);
            }
          return Result.succeed(resultMap,"保存成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "添加失败");
        }
    }


}
