package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.gtoa.PdtStandardFileInfo;

import java.util.Map;

/**
 * 机型任务表（包含10文件以及当前执行所有文件）
 *
 * @author hbq
 * @date 2020-10-19 18:59:55
 */
public interface IPdtStandardFileInfoService extends IService<PdtStandardFileInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

