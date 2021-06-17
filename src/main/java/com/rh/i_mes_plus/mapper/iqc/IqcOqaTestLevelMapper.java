package com.rh.i_mes_plus.mapper.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 机种抽样方案
 * 
 * @author hqb
 * @date 2020-10-16 11:25:37
 */
@Mapper
public interface IqcOqaTestLevelMapper extends SuperMapper<IqcOqaTestLevel> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    Integer getModelAndLevel(@Param("itemCode")String itemCode);
}
