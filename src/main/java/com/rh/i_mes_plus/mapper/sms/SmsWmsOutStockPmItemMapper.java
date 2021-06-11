package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockPmItem;
import com.rh.i_mes_plus.vo.MassageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工单和备料单绑定表
 *
 * @author hbq
 * @date 2020-11-02 17:19:51
 */
@Mapper
public interface SmsWmsOutStockPmItemMapper extends SuperMapper<SmsWmsOutStockPmItem> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    List<MassageVO> getMassageList(@Param("id") Long id);

    List<MassageVO> getMassageLotList(@Param("id") Long id);
}
