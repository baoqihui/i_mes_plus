package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsNotify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 用户通知表,包含了所有用户的消息
 * 
 * @author hqb
 * @date 2020-09-17 08:34:13
 */
@Mapper
public interface UmsNotifyMapper extends SuperMapper<UmsNotify> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsNotify> findList(Page<UmsNotify> pages, @Param("p") Map<String, Object> params);
}
