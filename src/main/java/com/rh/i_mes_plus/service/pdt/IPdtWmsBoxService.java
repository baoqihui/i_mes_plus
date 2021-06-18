package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtWmsBoxDTO;
import com.rh.i_mes_plus.model.pdt.PdtWmsBox;

import java.util.Map;

/**
 *
 *
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
public interface IPdtWmsBoxService extends IService<PdtWmsBox> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
    
    Result getBoxNo(Map<String, Object> map);
    
    Result saveAll(PdtWmsBoxDTO pdtWmsBoxDTO);

    Result getBoxInfo(Map<String, Object> params);
}

