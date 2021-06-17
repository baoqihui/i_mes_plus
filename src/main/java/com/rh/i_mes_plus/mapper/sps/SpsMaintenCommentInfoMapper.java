package com.rh.i_mes_plus.mapper.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sps.SpsMaintenCommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 保养内容信息表
 * 
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Mapper
public interface SpsMaintenCommentInfoMapper extends SuperMapper<SpsMaintenCommentInfo> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
