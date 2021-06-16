package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDoc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 采购入库单表
 * 
 * @author hqb
 * @date 2020-10-07 15:25:51
 */
@Mapper
public interface SmsWmsReceiveDocMapper extends SuperMapper<SmsWmsReceiveDoc> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    String getMaxDocNum(@Param("yyyyMM")String yyyyMM);

    Page<Map> allList(Page<Map> pages, @Param("p") Map<String, Object> params);

    Page<Map> allSelectList(Page<Map> pages,@Param("p") Map<String, Object> params);
    
    List<String>  getReceiveList(@Param("p")Map<String, Object> map);
}
