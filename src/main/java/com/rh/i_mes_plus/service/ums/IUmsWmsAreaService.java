package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.UmsWmsAreaDTO;
import com.rh.i_mes_plus.model.ums.UmsWmsArea;
import com.rh.i_mes_plus.vo.OneVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author hqb
 * @date 2020-09-24 13:06:30
 */
public interface IUmsWmsAreaService extends IService<UmsWmsArea> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    List<OneVO> treeList(Map<String, Object> params);

    List<String> getParent(List<String> allWmsName, String arSn);

    Result autoGenerate(UmsWmsAreaDTO umsWmsAreaDTO);

    Result leadIn(MultipartFile excel) throws Exception;

    Result allLight(Map<String, Object> params);

    Result allEmptyLight(Map<String, Object> params);
}

