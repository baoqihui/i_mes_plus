package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.gtoa.EmailLog;

import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2020-10-21 19:42:58
 */
public interface IEmailLogService extends IService<EmailLog> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

