package com.rh.i_mes_plus.mapper.sps;

import com.rh.i_mes_plus.model.sps.TinReturnRecord;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 红锡膏退仓记录
 * 
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Mapper
public interface TinReturnRecordMapper extends SuperMapper<TinReturnRecord> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
