package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsAcceArea;
import com.rh.i_mes_plus.vo.ChildVO;

import java.util.List;
import java.util.Map;

/**
 * 辅料储位
 *
 * @author hqb
 * @date 2020-09-22 13:34:31
 */
public interface IUmsAcceAreaService extends IService<UmsAcceArea> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsAcceArea> findList(Map<String, Object> params);

    List<ChildVO> selectAllTree(String s);
}

