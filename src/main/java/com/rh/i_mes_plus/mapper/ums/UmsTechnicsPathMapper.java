package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsTechnicsPath;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 工艺路线
 * 
 * @author hbq
 * @date 2021-06-01 10:57:04
 */
@Mapper
public interface UmsTechnicsPathMapper extends SuperMapper<UmsTechnicsPath> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
