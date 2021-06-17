package com.rh.i_mes_plus.mapper.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.iqc.IqcTestItemType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 检测项目类型
 * 
 * @author hqb
 * @date 2020-10-16 10:06:18
 */
@Mapper
public interface IqcTestItemTypeMapper extends SuperMapper<IqcTestItemType> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<IqcTestItemType> findList(Page<IqcTestItemType> pages, @Param("p") Map<String, Object> params);
}
