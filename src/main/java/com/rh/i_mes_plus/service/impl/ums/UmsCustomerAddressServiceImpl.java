package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsCustomerAddressMapper;
import com.rh.i_mes_plus.model.ums.UmsCustomerAddress;
import com.rh.i_mes_plus.service.ums.IUmsCustomerAddressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 客户地址
 *
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
@Slf4j
@Service
public class UmsCustomerAddressServiceImpl extends ServiceImpl<UmsCustomerAddressMapper, UmsCustomerAddress> implements IUmsCustomerAddressService {
    @Resource
    private UmsCustomerAddressMapper umsCustomerAddressMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsCustomerAddress> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsCustomerAddress> pages = new Page<>(pageNum, pageSize);
        return umsCustomerAddressMapper.findList(pages, params);
    }
}
