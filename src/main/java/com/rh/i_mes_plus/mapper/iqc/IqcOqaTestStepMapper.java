package com.rh.i_mes_plus.mapper.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestStep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 检验阶
 * 
 * @author hqb
 * @date 2020-10-16 10:06:17
 */
@Mapper
public interface IqcOqaTestStepMapper extends SuperMapper<IqcOqaTestStep> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<IqcOqaTestStep> findList(Page<IqcOqaTestStep> pages, @Param("p") Map<String, Object> params);
}
