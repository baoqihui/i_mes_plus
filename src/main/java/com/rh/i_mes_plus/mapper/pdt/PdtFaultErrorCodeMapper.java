package com.rh.i_mes_plus.mapper.pdt;

import com.rh.i_mes_plus.model.pdt.PdtFaultErrorCode;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 不良代码
 * 
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
@Mapper
public interface PdtFaultErrorCodeMapper extends SuperMapper<PdtFaultErrorCode> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
