package com.rh.i_mes_plus.controller.ums;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsDepa;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
import com.rh.i_mes_plus.vo.ChildVO;
import com.rh.i_mes_plus.vo.DepaTreeVO;
import com.rh.i_mes_plus.vo.TwoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
@RestController
@Api(tags = "部门")
@RequestMapping("ums")
public class UmsDepaController {
    @Autowired
    private IUmsDepaService umsDepaService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsDepa/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsDepa> list= umsDepaService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsDepa/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsDepa model = umsDepaService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping("/umsDepa/save")
    public Result save(@RequestBody UmsDepa umsDepa) {
        umsDepaService.saveOrUpdate(umsDepa);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/umsDepa/del/{id}")
    public Result delete(@PathVariable Long id) {
        umsDepaService.removeById(id);
        return Result.succeed("删除成功");
    }

    /**
     * 通过parentCode递归查询树状部门列表 parentCode='0'查询全部
     */
    @ApiOperation(value = "（递归）通过parentCode递归查询树状部门列表 parentCode='0'查询全部")
    @PostMapping("/umsDepa/depaTree/{parentCode}")
    public Result selectAllTree(@PathVariable String parentCode) {
        List<ChildVO> childVOS = umsDepaService.selectAllTree(parentCode);
        return Result.succeed(childVOS, "查询成功");
    }

    @ApiOperation(value = "（三级）查询树形列表（仅机构）")
    @PostMapping("/umsDepa/treeOrgList")
    public Result treeOrgList() {
        List<TwoVO> depaTwoVOS=umsDepaService.treeOrgList();
        return Result.succeed(depaTwoVOS,"查询成功");
    }
    /**********************************************
     ******* 下方接口只做为递归调用示例，不参与项目 ******
     ******************************************** */


    /**
     * 通过parentCode查询树状列表(参数为0或空默认查全部)
     */
    @ApiOperation(value = "通过parentCode查询树状列表(参数为0或空默认查全部)")
    @PostMapping("/umsDepa/selectAllTreeList/{parentCode}")
    public Result selectTreeListByParentCode(@PathVariable String parentCode) {
        List<DepaTreeVO> depaTreeVOS = umsDepaService.selectTreeListByParentCode(StrUtil.isBlank(parentCode)?"0":parentCode);
        return Result.succeed(depaTreeVOS, "查询成功");
    }

    /**
     * 通过某代码查询其下级列表
     */
    @ApiOperation(value = "通过某代码查询其下级列表")
    @PostMapping("/umsDepa/selectListByCode/{code}")
    public Result selectListByCode(@PathVariable String code) {
        return umsDepaService.selectListByCode(code);
    }

    /**
     * 通过某代码查询其“叶子”子级列表（即最底层子级）
     */
    @ApiOperation(value = "通过某代码查询其“叶子”子级列表（即最底层子级）")
    @PostMapping("/umsDepa/selectLeafListByCode/{code}")
    public Result selectLeafListByCode(@PathVariable String code) {
        return umsDepaService.selectLeafListByCode(code);
    }

    /**
     * 通过某代码查询其根级（即最高层父级）
     */
    @ApiOperation(value = "通过某代码查询其根级（即最高层父级）")
    @PostMapping("/umsDepa/selectRootDepaByCode/{code}")
    public Result selectRootDepaByCode(@PathVariable String code) {
        UmsDepa umsDepa = umsDepaService.selectRootDepaByCode(code);
        return Result.succeed(umsDepa,"查询成功");
    }

    /**
     * 通过某代码查询其所有根级列表
     */
    @ApiOperation(value = "通过某代码查询其所有根级列表")
    @PostMapping("/umsDepa/selectRootListByCode/{code}")
    public Result selectRootListByCode(@PathVariable String code) {
        List<UmsDepa> umsDepas = umsDepaService.selectRootListByCode(new ArrayList<>(),code);
        return Result.succeed(umsDepas,"查询成功");
    }


}
