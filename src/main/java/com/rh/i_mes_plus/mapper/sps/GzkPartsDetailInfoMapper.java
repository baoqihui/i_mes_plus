package com.rh.i_mes_plus.mapper.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sps.GzkPartsDetailInfo;
import com.rh.i_mes_plus.vo.GzkPartsDetailInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工装备品详情信息表
 * 
 * @author hbq
 * @date 2021-02-21 10:39:50
 */
@Mapper
public interface GzkPartsDetailInfoMapper extends SuperMapper<GzkPartsDetailInfo> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    Page<Map> statement(Page<Map> pages,@Param("p") Map<String, Object> params);

    List<GzkPartsDetailInfoVO> leadOutByIds(@Param("ids") List<Long> ids);
}
