package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsDepa;
import com.rh.i_mes_plus.vo.ChildVO;
import com.rh.i_mes_plus.vo.DepaTreeVO;
import com.rh.i_mes_plus.vo.TwoVO;

import java.util.List;
import java.util.Map;

/**
 * 部门
 *
 * @author hqb
 * @date 2020-09-17 14:38:50
 */
public interface IUmsDepaService extends IService<UmsDepa> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsDepa> findList(Map<String, Object> params);

    List<String> treeUmsDepaList(List<String> childCodeList, List<UmsDepa> allList, String parentCode);

    List<String> getSon(Map<String, Object> params);

    String getDepaNameByCode(String depaCode);

    List<String> getAllDepaBycode(List<String> allDepaName,String depaCode);

    List<ChildVO> selectAllTree(String parentCode);

    List<DepaTreeVO> selectTreeListByParentCode(String parentCode);

    Result selectListByCode(String parentCode);

    Result selectLeafListByCode(String code);

    UmsDepa selectRootDepaByCode(String code);

    List<UmsDepa> selectRootListByCode(List<UmsDepa> umsDepas,String code);

    List<String> getDepaexclude();

    List<TwoVO> treeOrgList();
}

