package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.gtoa.CusComplaint;

import java.util.Map;

/**
 * 客诉表
 *
 * @author hbq
 * @date 2020-12-23 19:01:39
 */
public interface ICusComplaintService extends IService<CusComplaint> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

