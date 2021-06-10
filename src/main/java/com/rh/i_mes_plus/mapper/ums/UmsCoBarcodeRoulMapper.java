package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsCoBarcodeRoul;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 物料条码规则
 * 
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
@Mapper
public interface UmsCoBarcodeRoulMapper extends SuperMapper<UmsCoBarcodeRoul> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsCoBarcodeRoul> findList(Page<UmsCoBarcodeRoul> pages, @Param("p") Map<String, Object> params);
}
