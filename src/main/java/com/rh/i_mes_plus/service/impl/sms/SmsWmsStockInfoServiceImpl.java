package com.rh.i_mes_plus.service.impl.sms;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.sms.SmsWmsStockInfoMapper;
import com.rh.i_mes_plus.model.other.PdaMesLog;
import com.rh.i_mes_plus.model.sms.SmsWmsStockInfo;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.other.IPdaMesLogService;
import com.rh.i_mes_plus.service.sms.ISmsWmsStockInfoService;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 库存信息表(修改此表：如果在库数量为0时，需要把相关信息保存至表T_WMS_STOCK_ZERO_INFO，退料时才能得到SN原有的相关信息)
 *
 * @author hbq
 * @date 2020-12-09 10:11:55
 */
@Slf4j
@Service
public class SmsWmsStockInfoServiceImpl extends ServiceImpl<SmsWmsStockInfoMapper, SmsWmsStockInfo> implements ISmsWmsStockInfoService {
    @Resource
    private SmsWmsStockInfoMapper smsWmsStockInfoMapper;
    @Autowired
    private IPdaMesLogService pdaMesLogService;
    @Autowired
    private IUmsUserService umsUserService;
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
        return smsWmsStockInfoMapper.findList(pages, params);
    }

    @Override
    public Page<Map> listAll(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return smsWmsStockInfoMapper.listAll(pages, params);
    }

    @Override
    public Page<Map> listAllCollect(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return smsWmsStockInfoMapper.listAllCollect(pages, params);
    }

    @Override
    public Result putAway(Map<String, Object> params) {
        String barcode = MapUtil.getStr(params, "barcode");
        String areaSn = MapUtil.getStr(params, "areaSn");
        String empNo = MapUtil.getStr(params, "empNo");
        UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
        if (user == null) {
            return Result.failed("无此员工");
        }
        SmsWmsStockInfo stockInfo = getOne(new QueryWrapper<SmsWmsStockInfo>().eq("tbl_barcode", barcode));
        if (stockInfo==null){
            return Result.failed("无此条码");
        }
        if (StrUtil.isNotBlank(stockInfo.getAreaSn())){
            return Result.failed("该条码已绑定库位");
        }
        SmsWmsStockInfo areaSn1 = getOne(new QueryWrapper<SmsWmsStockInfo>().eq("area_sn", areaSn));
        if (areaSn1 != null) {
            return Result.failed("该库位已绑定条码:"+areaSn1.getTblBarcode());
        }
        stockInfo.setAreaSn(areaSn);
        updateById(stockInfo);
        pdaMesLogService.save(PdaMesLog.builder()
                .type("上架")
                .barcode(barcode)
                .itemCode(stockInfo.getCoItemCode())
                .num(stockInfo.getAmount())
                .tblManufacturerBat(stockInfo.getLotNumber())
                .tblManufacturerDate(stockInfo.getPdate())
                .createUser(user.getUserName())
                .build()
        );
        return Result.succeed("保存成功");
    }

    @Override
    public Result soldOut(Map<String, Object> params) {
        String barcode = MapUtil.getStr(params, "barcode");
        String empNo = MapUtil.getStr(params, "empNo");
        UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", empNo));
        if (user == null) {
            return Result.failed("无此员工");
        }
        SmsWmsStockInfo stockInfo = getOne(new QueryWrapper<SmsWmsStockInfo>().eq("tbl_barcode", barcode));
        if (stockInfo==null){
            return Result.failed("无此条码");
        }

        if (!StrUtil.isNotBlank(stockInfo.getAreaSn())){
            return Result.failed("该条码未绑定库位");
        }
        stockInfo.setAreaSn("");
        updateById(stockInfo);
        pdaMesLogService.save(PdaMesLog.builder()
                .type("下架")
                .barcode(barcode)
                .itemCode(stockInfo.getCoItemCode())
                .num(stockInfo.getAmount())
                .tblManufacturerBat(stockInfo.getLotNumber())
                .tblManufacturerDate(stockInfo.getPdate())
                .createUser(user.getUserName())
                .build()
        );
        return Result.succeed("保存成功");
    }

    @Override
    public Result getLocation(Map<String, Object> params) {
        String barcode = MapUtil.getStr(params, "barcode");
        SmsWmsStockInfo stockInfo = getOne(new QueryWrapper<SmsWmsStockInfo>().eq("tbl_barcode", barcode));
        if (stockInfo==null){
            return Result.failed("无此条码");
        }

        return Result.succeed(stockInfo.getAreaSn());
    }

    @Override
    public Result getInfoBySnAndQty(Map<String, Object> map) {
        String sn = MapUtil.getStr(map, "sn");
        String qty = MapUtil.getStr(map, "qty");
        return Result.succeed(sn+"-----"+qty);
    }
}
