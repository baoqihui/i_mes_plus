package com.rh.i_mes_plus.mapper.other;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.other.EccSendInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * ECC扣料数据
 * 
 * @author hbq
 * @date 2021-01-09 15:34:09
 */
@Mapper
public interface EccSendInfoMapper extends SuperMapper<EccSendInfo> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
