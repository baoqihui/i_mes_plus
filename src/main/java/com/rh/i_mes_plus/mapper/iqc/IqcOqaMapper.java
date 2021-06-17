package com.rh.i_mes_plus.mapper.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqa;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 检验单
 * 
 * @author hbq
 * @date 2020-10-21 14:29:19
 */
@Mapper
public interface IqcOqaMapper extends SuperMapper<IqcOqa> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    String getMaxOqcNo(@Param("prefix")String prefix);

    List<Map<String, Object>> selOIList(@Param("oqcNo")String oqcNo);

    Map<String, Object> selectAllByOqcNo(@Param("oqcNo")String oqcNo);
}
