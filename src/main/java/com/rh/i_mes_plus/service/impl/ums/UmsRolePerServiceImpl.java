package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsRolePerMapper;
import com.rh.i_mes_plus.model.ums.UmsRolePer;
import com.rh.i_mes_plus.service.ums.IUmsRolePerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户权限关联表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Slf4j
@Service
public class UmsRolePerServiceImpl extends ServiceImpl<UmsRolePerMapper, UmsRolePer> implements IUmsRolePerService {
    @Resource
    private UmsRolePerMapper umsRolePerMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsRolePer> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsRolePer> pages = new Page<>(pageNum, pageSize);
        return umsRolePerMapper.findList(pages, params);
    }
}
