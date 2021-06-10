package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.EmailConfig;

import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2020-10-21 19:50:27
 */
public interface IEmailConfigService extends IService<EmailConfig> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
    Result sendEmail(Long id,String ecnNo,Map<String,Object> depasMap);
}

