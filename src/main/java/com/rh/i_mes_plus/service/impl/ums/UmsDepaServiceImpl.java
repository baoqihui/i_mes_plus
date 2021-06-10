package com.rh.i_mes_plus.service.impl.ums;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.ums.UmsDepaMapper;
import com.rh.i_mes_plus.model.ums.UmsDepa;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
import com.rh.i_mes_plus.vo.ChildVO;
import com.rh.i_mes_plus.vo.DepaTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门
 *
 * @author hqb
 * @date 2020-09-17 14:38:50
 */
@Slf4j
@Service
public class UmsDepaServiceImpl extends ServiceImpl<UmsDepaMapper, UmsDepa> implements IUmsDepaService {
    @Resource
    private UmsDepaMapper umsDepaMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsDepa> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsDepa> pages = new Page<>(pageNum, pageSize);
        return umsDepaMapper.findList(pages, params);
    }


    @Override
    public List<String> treeUmsDepaList(List<String> childCodeList, List<UmsDepa> allList, String parentCode) {
        for (UmsDepa umsDepa : allList) {
            if (umsDepa.getParentCode()!= null){
                if (umsDepa.getParentCode().equals(parentCode)){
                    treeUmsDepaList(childCodeList,allList,umsDepa.getCode());
                    childCodeList.add(umsDepa.getCode());
                }
            }
        }
        return childCodeList;
    }

    @Override
    public List<String> getSon(Map<String, Object> params) {
        String parentCode = MapUtil.getStr(params, "depaCode");
        List<UmsDepa> allList=list();
        List<String> umsDepas = treeUmsDepaList(new ArrayList<>(),allList,parentCode);
        umsDepas.add(parentCode);
        return umsDepas;
    }

    @Override
    public String getDepaNameByCode(String depaCode) {
        if(StrUtil.isNotEmpty(depaCode)){
            UmsDepa umsDepa = getOne(new QueryWrapper<UmsDepa>().eq("code", depaCode));
            if (umsDepa != null) {
                return umsDepa.getName();
            }
        }
        return "";
    }

    @Override
    public List<String> getAllDepaBycode(List<String> allDepaName,String depaCode) {
        UmsDepa umsDepa=umsDepaMapper.getParentDepa(depaCode);
        if (umsDepa!=null && umsDepa.getParentCode()!=null){
            getAllDepaBycode(allDepaName,umsDepa.getParentCode());
            allDepaName.add(umsDepa.getName());
        }
        return allDepaName;
    }

    @Override
    public List<ChildVO> selectAllTree(String parentCode) {
        List<ChildVO> childVOS = umsDepaMapper.selectAllTree(parentCode);
        if(childVOS!=null){
            for (ChildVO childVO : childVOS) {
                    childVO.setChildVOS(selectAllTree(childVO.getCode()));
            }
        }
        return childVOS;
    }

    /**
     * 递归需要做的就是 寻找当前parentCode的下级，赋值给children集合。
     * 结束条件即下级列表为空
     * @param parentCode 想要查找的parentCode
     * @return 树形部门列表
     */
    @Override
    public List<DepaTreeVO> selectTreeListByParentCode(String parentCode) {
        List<DepaTreeVO> depaTreeVOS = umsDepaMapper.selectTreeListByParentCode(parentCode);
        //下级列表不为空则继续寻找下级
        if(!depaTreeVOS.isEmpty()){
            for (DepaTreeVO depaTreeVO : depaTreeVOS) {
                //将下级列表封装至本级的children中
                depaTreeVO.setChildren(selectTreeListByParentCode(depaTreeVO.getCode()));
            }
        }
        return depaTreeVOS;
    }

    @Override
    public Result selectListByCode(String code) {
        UmsDepa depa = getOne(new LambdaQueryWrapper<UmsDepa>().eq(UmsDepa::getCode, code));
        if (depa == null) {
            return Result.failed("无此数据");
        }
        List<UmsDepa> childCodeList=new ArrayList<>();
        childCodeList.add(depa);

        List<UmsDepa> umsDepas = selectChildListByParentCode(childCodeList,code);
        return Result.succeed(umsDepas,"查询成功");
    }

    @Override
    public Result selectLeafListByCode(String code) {
        UmsDepa depa = getOne(new LambdaQueryWrapper<UmsDepa>().eq(UmsDepa::getCode, code));
        if (depa == null) {
            return Result.failed("无此数据");
        }
        List<UmsDepa> childCodeList=new ArrayList<>();
        childCodeList.add(depa);

        List<UmsDepa> umsDepas = selectMinChildListByParentCode(childCodeList,depa);
        return Result.succeed(umsDepas,"查询成功");
    }
    /**
     * 递归查找当前对象的最底层对象并封装
     * @param minChildList 当前获取到的最底层级别列表
     * @param parentDepa   父级对象
     * @return 全部最底层级别列表
     */
    public List<UmsDepa> selectMinChildListByParentCode(List<UmsDepa> minChildList,UmsDepa parentDepa) {
        List<UmsDepa> pdtBoms = list(new LambdaQueryWrapper<UmsDepa>().eq(UmsDepa::getParentCode, parentDepa.getCode()));
        //寻找不到下级时，说明此记录已经是当前的最下级
        if (pdtBoms.isEmpty()){
            minChildList.add(parentDepa);
        }else {
            for (UmsDepa bom : pdtBoms) {
                selectMinChildListByParentCode(minChildList,bom);
            }
        }
        return minChildList;
    }
    /**
     * 递归查找当前对象的下层对象并封装
     * @param childCodeList 上次递归获取到的子级列表
     * @param parentCode   父级code
     * @return 所有子级列表
     */
    public List<UmsDepa> selectChildListByParentCode(List<UmsDepa> childCodeList, String parentCode) {
        List<UmsDepa> list = list(new LambdaQueryWrapper<UmsDepa>().eq(UmsDepa::getParentCode, parentCode));
        if (!list.isEmpty()){
            for (UmsDepa umsDepa : list) {
                childCodeList.add(umsDepa);
                selectChildListByParentCode(childCodeList,umsDepa.getCode());
            }
        }
        return childCodeList;
    }

    @Override
    public UmsDepa selectRootDepaByCode(String code) {
        UmsDepa umsDepa = getOne(new LambdaQueryWrapper<UmsDepa>().eq(UmsDepa::getCode, code));
        if (umsDepa == null) {
            return null;
        }
        //parentCode=0结束循环调用
        if ("0".equals(umsDepa.getParentCode())){
            return umsDepa;
        }
        return selectRootDepaByCode(umsDepa.getParentCode());
    }

    @Override
    public List<UmsDepa> selectRootListByCode(List<UmsDepa> umsDepas,String code) {
        UmsDepa umsDepa = getOne(new LambdaQueryWrapper<UmsDepa>().eq(UmsDepa::getCode, code));
        umsDepas.add(umsDepa);
        //parentCode=0结束循环调用
        if ("0".equals(umsDepa.getParentCode())){
            return umsDepas;
        }
        return selectRootListByCode(umsDepas,umsDepa.getParentCode());
    }

    @Override
    public List<String> getDepaexclude() {
        return umsDepaMapper.getDepaexclude();
    }
}
