package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsMoStep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 工单阶别(0-SMT,1-WAVE,2-Hand Soldering,3-Assembly,4-Others)
 * 
 * @author hbq
 * @date 2021-05-31 10:07:36
 */
@Mapper
public interface UmsMoStepMapper extends SuperMapper<UmsMoStep> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
