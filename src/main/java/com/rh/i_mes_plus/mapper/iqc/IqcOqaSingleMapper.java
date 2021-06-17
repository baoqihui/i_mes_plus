package com.rh.i_mes_plus.mapper.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaSingle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 抽验样本信息
 * 
 * @author hbq
 * @date 2020-10-23 11:39:35
 */
@Mapper
public interface IqcOqaSingleMapper extends SuperMapper<IqcOqaSingle> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    Long getMaxOsNoByOqcNoAndsn(@Param("p")Map<String,Object> map);
}
