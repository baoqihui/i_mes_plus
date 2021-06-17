package com.rh.i_mes_plus.mapper.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.iqc.IqcTestItemModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 机种检验项目
 * 
 * @author hqb
 * @date 2020-10-16 16:35:54
 */
@Mapper
public interface IqcTestItemModelMapper extends SuperMapper<IqcTestItemModel> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
