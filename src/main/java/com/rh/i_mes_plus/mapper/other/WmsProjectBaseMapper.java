package com.rh.i_mes_plus.mapper.other;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.other.WmsProjectBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 工单信息主表
 *
 * @author hbq
 * @date 2021-05-27 08:41:55
 */
@Mapper
public interface WmsProjectBaseMapper extends SuperMapper<WmsProjectBase> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
