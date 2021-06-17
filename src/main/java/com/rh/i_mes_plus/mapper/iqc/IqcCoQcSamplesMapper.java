package com.rh.i_mes_plus.mapper.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.iqc.IqcCoQcSamples;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 样本代码
 * 
 * @author hbq
 * @date 2020-10-23 15:24:30
 */
@Mapper
public interface IqcCoQcSamplesMapper extends SuperMapper<IqcCoQcSamples> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    String getCodeBySendAmount(@Param("oqcSendAmount")Long oqcSendAmount,@Param("otgId")Long otgId);

    List<Map<String, Object>> selList(@Param("p") Map<String, Object> params);
}
