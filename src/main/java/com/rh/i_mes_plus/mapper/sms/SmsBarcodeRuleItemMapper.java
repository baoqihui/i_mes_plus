package com.rh.i_mes_plus.mapper.sms;

import com.rh.i_mes_plus.model.sms.SmsBarcodeRuleItem;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 物料条码规则
 * 
 * @author hbq
 * @date 2021-06-28 18:41:07
 */
@Mapper
public interface SmsBarcodeRuleItemMapper extends SuperMapper<SmsBarcodeRuleItem> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
