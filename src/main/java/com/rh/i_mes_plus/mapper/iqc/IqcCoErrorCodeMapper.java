package com.rh.i_mes_plus.mapper.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.iqc.IqcCoErrorCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 错误代码
 * 
 * @author hbq
 * @date 2020-10-23 08:51:08
 */
@Mapper
public interface IqcCoErrorCodeMapper extends SuperMapper<IqcCoErrorCode> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    List<String> getErrDesc(@Param("oqcNo")String oqcNo);
}
