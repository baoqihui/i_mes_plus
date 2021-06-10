package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsCustomerGradeMapper;
import com.rh.i_mes_plus.model.ums.UmsCustomerGrade;
import com.rh.i_mes_plus.service.ums.IUmsCustomerGradeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 客户等级（暂时不用）
 *
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
@Slf4j
@Service
public class UmsCustomerGradeServiceImpl extends ServiceImpl<UmsCustomerGradeMapper, UmsCustomerGrade> implements IUmsCustomerGradeService {
    @Resource
    private UmsCustomerGradeMapper umsCustomerGradeMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsCustomerGrade> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsCustomerGrade> pages = new Page<>(pageNum, pageSize);
        return umsCustomerGradeMapper.findList(pages, params);
    }
}
