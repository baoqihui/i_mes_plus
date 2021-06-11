package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsLineBody;
import com.rh.i_mes_plus.vo.ChildVO;
import com.rh.i_mes_plus.vo.OneVO;

import java.util.List;
import java.util.Map;

/**
 * 线别管理
 *
 * @author hbq
 * @date 2021-05-20 15:31:14
 */
public interface IUmsLineBodyService extends IService<UmsLineBody> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    List<OneVO> treeList(Map<String, Object> params);

    List<ChildVO> selectAllTree(String s);
}

