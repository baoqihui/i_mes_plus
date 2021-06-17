package com.rh.i_mes_plus.mapper.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestGrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 检验水平
 * 
 * @author hqb
 * @date 2020-10-16 13:20:30
 */
@Mapper
public interface IqcOqaTestGradeMapper extends SuperMapper<IqcOqaTestGrade> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<IqcOqaTestGrade> findList(Page<IqcOqaTestGrade> pages, @Param("p") Map<String, Object> params);
}
