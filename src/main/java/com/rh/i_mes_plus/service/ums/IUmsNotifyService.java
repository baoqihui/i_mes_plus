package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsNotify;

import java.util.Map;

/**
 * 用户通知表,包含了所有用户的消息
 *
 * @author hqb
 * @date 2020-09-17 08:34:13
 */
public interface IUmsNotifyService extends IService<UmsNotify> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsNotify> findList(Map<String, Object> params);
}

