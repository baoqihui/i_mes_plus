package com.rh.i_mes_plus.service.impl.sms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Month;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.sms.SmsWmsChooseBarcodeMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsChooseBarcode;
import com.rh.i_mes_plus.model.sms.SmsWmsChooseBarcodeLog;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDetail;
import com.rh.i_mes_plus.service.sms.ISmsWmsChooseBarcodeLogService;
import com.rh.i_mes_plus.service.sms.ISmsWmsChooseBarcodeService;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2021-02-25 16:12:27
 */
@Slf4j
@Service
public class SmsWmsChooseBarcodeServiceImpl extends ServiceImpl<SmsWmsChooseBarcodeMapper, SmsWmsChooseBarcode> implements ISmsWmsChooseBarcodeService {
    @Resource
    private SmsWmsChooseBarcodeMapper smsWmsChooseBarcodeMapper;
    @Autowired
    private ISmsWmsOutStockDetailService smsWmsOutStockDetailService;
    @Autowired
    private ISmsWmsChooseBarcodeLogService smsWmsChooseBarcodeLogService;
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
        return smsWmsChooseBarcodeMapper.findList(pages, params);
    }

    @Override
    public Result choose(Map<String, Object> params) {
        String docNo = MapUtil.getStr(params, "docNo");
        List<SmsWmsOutStockDetail> stockDetails = smsWmsOutStockDetailService.list(new QueryWrapper<SmsWmsOutStockDetail>()
                .eq("doc_no", docNo)
        );
        String message="超期条码:";
        for (SmsWmsOutStockDetail stockDetail : stockDetails) {
            String itemCode = stockDetail.getItemCode();
            Long osdAmountPlan = stockDetail.getOsdAmountPlan();
            Long qtyReal = 0L;
            SmsWmsChooseBarcode one = getOne(new QueryWrapper<SmsWmsChooseBarcode>()
                    .eq("doc_no", docNo)
                    .eq("item_code", itemCode)
                    .eq("is_lack", false)
            );
            if (one==null){
                List<Map<String, Object>> smsWmsStockInfos=smsWmsChooseBarcodeMapper.getStockInfos(itemCode);
                for (Map<String, Object> smsWmsStockInfo : smsWmsStockInfos) {
                    Date pdate = MapUtil.getDate(smsWmsStockInfo, "pdate");
                    Long ioItemAvail = MapUtil.getLong(smsWmsStockInfo, "ioItemAvail");
                    Long amount = MapUtil.getLong(smsWmsStockInfo, "amount");

                    String tblBarcode = MapUtil.getStr(smsWmsStockInfo, "tblBarcode");
                    String areaSn = MapUtil.getStr(smsWmsStockInfo, "areaSn");
                    //超期不生成记录，返回给前端
                    if (DateUtil.between(new Date(),pdate, DateUnit.DAY)-ioItemAvail>0){
                        message=message+tblBarcode+";";
                    }else{
                        if (qtyReal<=osdAmountPlan){
                            qtyReal=qtyReal+amount;
                            SmsWmsChooseBarcode smsWmsChooseBarcode = new SmsWmsChooseBarcode();
                            smsWmsChooseBarcode.setDocNo(docNo);
                            smsWmsChooseBarcode.setItemCode(itemCode);
                            smsWmsChooseBarcode.setQtyPlan(osdAmountPlan);
                            smsWmsChooseBarcode.setQtyReal(qtyReal);
                            smsWmsChooseBarcode.setBarcode(tblBarcode);
                            smsWmsChooseBarcode.setQtyBarcode(amount);
                            smsWmsChooseBarcode.setIsLack(osdAmountPlan-qtyReal>0?true:false);
                            smsWmsChooseBarcode.setIsSnip(false);
                            smsWmsChooseBarcode.setIsSend(false);
                            smsWmsChooseBarcode.setLightColor(0);
                            smsWmsChooseBarcode.setLoc(areaSn);

                            save(smsWmsChooseBarcode);
                            SmsWmsChooseBarcodeLog smsWmsChooseBarcodeLog = new SmsWmsChooseBarcodeLog();
                            BeanUtil.copyProperties(smsWmsChooseBarcode,smsWmsChooseBarcodeLog);
                            smsWmsChooseBarcodeLog.setRemark("新增");
                            smsWmsChooseBarcodeLogService.save(smsWmsChooseBarcodeLog);
                        }
                    }
                }
            }
        }
        return Result.succeed(message,"保存成功");
    }

    @Override
    public Result close(Map<String, Object> params) {
        String docNo = MapUtil.getStr(params, "docNo");
        //删除挑料
        List<SmsWmsChooseBarcode> chooseBarcodes = list(new QueryWrapper<SmsWmsChooseBarcode>().eq("doc_no", docNo));
        remove(new QueryWrapper<SmsWmsChooseBarcode>().eq("doc_no", docNo));

        //增加删除挑料日志
        for (SmsWmsChooseBarcode chooseBarcode : chooseBarcodes) {
            SmsWmsChooseBarcodeLog smsWmsChooseBarcodeLog = new SmsWmsChooseBarcodeLog();
            BeanUtil.copyProperties(chooseBarcode,smsWmsChooseBarcodeLog);
            smsWmsChooseBarcodeLog.setRemark("删除");
            smsWmsChooseBarcodeLogService.save(smsWmsChooseBarcodeLog);
        }
        return Result.succeed(chooseBarcodes,"取消成功");
    }
}
