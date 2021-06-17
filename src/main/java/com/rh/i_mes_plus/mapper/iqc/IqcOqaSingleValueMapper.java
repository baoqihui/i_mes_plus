package com.rh.i_mes_plus.mapper.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaSingleValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 样品检测项目值
 * 
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
@Mapper
public interface IqcOqaSingleValueMapper extends SuperMapper<IqcOqaSingleValue> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    Integer countNg(@Param("oqcNo")String oqcNo,@Param("tiyId") Long tiyId);
}
