package com.rh.i_mes_plus.mapper.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.iqc.IqcCoQcPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 抽样方案明细
 * 
 * @author hqb
 * @date 2020-10-16 10:06:17
 */
@Mapper
public interface IqcCoQcPlanMapper extends SuperMapper<IqcCoQcPlan> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<IqcCoQcPlan> findList(Page<IqcCoQcPlan> pages, @Param("p") Map<String, Object> params);
}
