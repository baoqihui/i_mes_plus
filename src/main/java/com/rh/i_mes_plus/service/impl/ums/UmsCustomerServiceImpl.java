package com.rh.i_mes_plus.service.impl.ums;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.ums.UmsCustomerMapper;
import com.rh.i_mes_plus.model.other.SapMesLog;
import com.rh.i_mes_plus.model.ums.UmsCustomer;
import com.rh.i_mes_plus.model.ums.UmsCustomerAddress;
import com.rh.i_mes_plus.service.other.ISapMesLogService;
import com.rh.i_mes_plus.service.ums.IUmsCustomerAddressService;
import com.rh.i_mes_plus.service.ums.IUmsCustomerService;
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
 * 客户管理表
 *
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
@Slf4j
@Service
public class UmsCustomerServiceImpl extends ServiceImpl<UmsCustomerMapper, UmsCustomer> implements IUmsCustomerService {
    @Resource
    private UmsCustomerMapper umsCustomerMapper;
    @Autowired
    private ISapMesLogService sapMesLogService;
    @Autowired
    private IUmsCustomerAddressService umsCustomerAddressService;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsCustomer> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsCustomer> pages = new Page<>(pageNum, pageSize);
        return umsCustomerMapper.findList(pages, params);
    }

    @Override
    public Result saveAll(List<Map<String, Object>> maps) {

            List<String> s=new ArrayList<>();
            List<String> f=new ArrayList<>();
            Map<String,List<String>> resultMap=new HashMap();
            for (Map<String, Object> map : maps) {
                boolean successFlag;
                String code = MapUtil.getStr(map, "code");
                String name = MapUtil.getStr(map, "name");
                String address = MapUtil.getStr(map, "address");
                String projectCode = MapUtil.getStr(map, "projectCode");
                UmsCustomer umsCustomer = new UmsCustomer();
                umsCustomer.setCustomer(name);
                umsCustomer.setCustCode(code);
                umsCustomer.setCustomerAddress(address);
                umsCustomer.setProjectCode(projectCode);
/*                UmsCustomerAddress umsCustomerAddress = new UmsCustomerAddress();
                umsCustomerAddress.setCustCode(code);
                umsCustomerAddress.setCustomerAddress(address);*/
                UmsCustomer existUmsCustomer = getOne(new QueryWrapper<UmsCustomer>()
                        .eq("CUST_CODE", code)
                        .eq("is_del", 0)
                );
                if (existUmsCustomer != null) {
                    successFlag=update(umsCustomer,new UpdateWrapper<UmsCustomer>()
                            .eq("CUST_CODE", code)
                            .eq("is_del", 0)
                    );
                }else {
                    successFlag=save(umsCustomer);
                }
               /* UmsCustomerAddress existUmsCustomerAddress = umsCustomerAddressService.getOne(new QueryWrapper<UmsCustomerAddress>()
                        .eq("CUST_CODE", code)
                        .eq("is_del", 0)
                );
                if (existUmsCustomerAddress != null) {
                    umsCustomerAddressService.update(umsCustomerAddress,new UpdateWrapper<UmsCustomerAddress>()
                            .eq("CUST_CODE", code)
                            .eq("is_del", 0)
                    );
                }else {
                    umsCustomerAddressService.save(umsCustomerAddress);
                }*/
                if (successFlag){
                    s.add(umsCustomer.getCustCode());
                }else{
                    f.add(umsCustomer.getCustCode());
                    SapMesLog sapMesLog = new SapMesLog(
                            "客户数据错误，CUST_CODE="+umsCustomer.getCustCode(),
                            "/ums/umsCustomer/saveAll",
                            1,
                            "sap同步客户数据出错"
                    );
                    //直接存入日志表
                    sapMesLogService.save(sapMesLog);
                }

                resultMap.put("s",s);
                resultMap.put("f",f);
            }
        try{  return Result.succeed(resultMap,"保存成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "添加失败");
        }
    }
}
