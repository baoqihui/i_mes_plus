package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcCoErrorCode;

import java.util.List;
import java.util.Map;

/**
 * 错误代码
 *
 * @author hbq
 * @date 2020-10-23 08:51:08
 */
public interface IIqcCoErrorCodeService extends IService<IqcCoErrorCode> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    List<String> getErrDesc(String oqcNo);
}

