package com.rh.i_mes_plus.service.other;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.ProjectAndFaceDTO;
import com.rh.i_mes_plus.dto.WmsProjectDTO;
import com.rh.i_mes_plus.model.other.WmsProjectDetail;

import java.util.List;
import java.util.Map;

/**
 * 工单明细表
 *
 * @author hbq
 * @date 2020-10-30 11:19:39
 */
public interface IWmsProjectDetailService extends IService<WmsProjectDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveAll(List<WmsProjectDTO> wmsProjectDTOS);

    Result getDetailByProjectIds(Map<String, List<ProjectAndFaceDTO>> params);
}

