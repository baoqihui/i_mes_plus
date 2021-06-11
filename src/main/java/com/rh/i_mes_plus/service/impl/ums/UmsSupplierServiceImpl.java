package com.rh.i_mes_plus.service.impl.ums;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.ums.UmsSupplierMapper;
import com.rh.i_mes_plus.model.ums.UmsSupplier;
import com.rh.i_mes_plus.model.other.SapMesLog;
import com.rh.i_mes_plus.model.ums.UmsCustomerAddress;
import com.rh.i_mes_plus.service.ums.IUmsSupplierService;
import com.rh.i_mes_plus.service.other.ISapMesLogService;
import com.rh.i_mes_plus.service.ums.IUmsCustomerAddressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BOM料件供应商表
 *
 * @author hqb
 * @date 2020-09-19 14:42:47
 */
@Slf4j
@Service
public class UmsSupplierServiceImpl extends ServiceImpl<UmsSupplierMapper, UmsSupplier> implements IUmsSupplierService {
    @Resource
    private UmsSupplierMapper umsSupplierMapper;
    @Autowired
    private ISapMesLogService sapMesLogService;
    @Autowired
    private IUmsCustomerAddressService umsCustomerAddressService;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsSupplier> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsSupplier> pages = new Page<>(pageNum, pageSize);
        return umsSupplierMapper.findList(pages, params);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveAll(List<Map<String, Object>> maps) {
        try{
            List<String> s=new ArrayList<>();
            List<String> f=new ArrayList<>();
            Map<String,List<String>> resultMap=new HashMap();
            for (Map<String, Object> map : maps) {
                boolean successFlag;
                String code = MapUtil.getStr(map, "code");
                String name = MapUtil.getStr(map, "name");
                String address = MapUtil.getStr(map, "address");

                UmsSupplier umsSupplier = new UmsSupplier();
                umsSupplier.setSupplierName(name);
                umsSupplier.setSupplierCode(code);
                UmsCustomerAddress umsCustomerAddress = new UmsCustomerAddress();
                umsCustomerAddress.setCustCode(code);
                umsCustomerAddress.setCustomerAddress(address);
                UmsSupplier existUmsSupplier = getOne(new QueryWrapper<UmsSupplier>()
                        .eq("supplier_code", code)
                        .eq("is_del", 0)
                );
                if (existUmsSupplier != null) {
                    successFlag=update(umsSupplier,new UpdateWrapper<UmsSupplier>()
                            .eq("supplier_code", code)
                            .eq("is_del", 0)
                    );
                }else {
                    successFlag=save(umsSupplier);
                }
                UmsCustomerAddress existUmsSupplierAddress = umsCustomerAddressService.getOne(new QueryWrapper<UmsCustomerAddress>()
                        .eq("CUST_CODE", code)
                        .eq("is_del", 0)
                );
                if (existUmsSupplierAddress != null) {
                    umsCustomerAddressService.update(umsCustomerAddress,new UpdateWrapper<UmsCustomerAddress>()
                            .eq("CUST_CODE", code)
                            .eq("is_del", 0)
                    );
                }else {
                    umsCustomerAddressService.save(umsCustomerAddress);
                }
                if (successFlag){
                    s.add(umsSupplier.getSupplierCode());
                }else{
                    f.add(umsSupplier.getSupplierCode());
                    SapMesLog sapMesLog = new SapMesLog(
                            "供应商数据错误，supplier_code="+umsSupplier.getSupplierCode(),
                            "/ums/umsSupplier/saveAll",
                            1,
                            "sap同步供应商数据出错"
                    );
                    //直接存入日志表
                    sapMesLogService.save(sapMesLog);
                }

                resultMap.put("s",s);
                resultMap.put("f",f);
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
