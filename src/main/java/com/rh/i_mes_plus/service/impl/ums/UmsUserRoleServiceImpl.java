package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsUserRoleMapper;
import com.rh.i_mes_plus.model.ums.UmsUserRole;
import com.rh.i_mes_plus.service.ums.IUmsUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户角色关联表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Slf4j
@Service
public class UmsUserRoleServiceImpl extends ServiceImpl<UmsUserRoleMapper, UmsUserRole> implements IUmsUserRoleService {
    @Resource
    private UmsUserRoleMapper umsUserRoleMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsUserRole> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsUserRole> pages = new Page<>(pageNum, pageSize);
        return umsUserRoleMapper.findList(pages, params);
    }
}
