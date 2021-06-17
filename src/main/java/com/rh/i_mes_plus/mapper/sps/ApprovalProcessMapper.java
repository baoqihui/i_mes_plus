package com.rh.i_mes_plus.mapper.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sps.ApprovalProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 流程控制
 * 
 * @author hbq
 * @date 2021-02-21 16:06:24
 */
@Mapper
public interface ApprovalProcessMapper extends SuperMapper<ApprovalProcess> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);


    ApprovalProcess getOperUsrNo(@Param("p") Map<String, Object> params);
}
