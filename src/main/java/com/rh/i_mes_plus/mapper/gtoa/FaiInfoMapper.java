package com.rh.i_mes_plus.mapper.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.gtoa.FaiInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * fal
 * 
 * @author hbq
 * @date 2020-11-05 20:14:46
 */
@Mapper
public interface FaiInfoMapper extends SuperMapper<FaiInfo> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
