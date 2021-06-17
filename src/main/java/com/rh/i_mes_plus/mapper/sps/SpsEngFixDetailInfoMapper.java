package com.rh.i_mes_plus.mapper.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sps.SpsEngFixDetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工程治具详情表
 * 
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Mapper
public interface SpsEngFixDetailInfoMapper extends SuperMapper<SpsEngFixDetailInfo> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    List<Map<String, Object>> selItemTypeInfosByModelCode(@Param("modelCode") String modelCode);

    String getPosesByFixNos(@Param("fixNos") List<String> fixNos);
}
