package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.gtoa.FmsBurnFile;

import java.util.Map;

/**
 * 烧录文件管理
 *
 * @author hbq
 * @date 2020-12-02 09:14:13
 */
public interface IFmsBurnFileService extends IService<FmsBurnFile> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

