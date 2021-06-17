package com.rh.i_mes_plus.controller.sps;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.GzkPartsDetailInfo;
import com.rh.i_mes_plus.model.ums.UmsSparesItemTypeInfo;
import com.rh.i_mes_plus.service.sps.IGzkPartsDetailInfoService;
import com.rh.i_mes_plus.service.ums.IUmsSparesItemTypeInfoService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.vo.GzkPartsDetailInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 工装备品详情信息表
 *
 * @author hbq
 * @date 2021-02-21 10:39:50
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工装备品详情信息表")
@RequestMapping("gzk")
public class GzkPartsDetailInfoController {
    @Autowired
    private IGzkPartsDetailInfoService gzkPartsDetailInfoService;
    @Autowired
    private IUmsSparesItemTypeInfoService umsSparesItemTypeInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/gzkPartsDetailInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= gzkPartsDetailInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 报表
     */
    @ApiOperation(value = "报表")
    @PostMapping("/gzkPartsDetailInfo/statement")
    public Result<PageResult> statement(@RequestBody Map<String, Object> params) {
        Page<Map> list= gzkPartsDetailInfoService.statement(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/gzkPartsDetailInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        GzkPartsDetailInfo model = gzkPartsDetailInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/gzkPartsDetailInfo/save")
    public Result save(@RequestBody GzkPartsDetailInfo gzkPartsDetailInfo) {
        String itemTypeCode = gzkPartsDetailInfo.getItemTypeCode();
        UmsSparesItemTypeInfo umsSparesItemTypeInfo = umsSparesItemTypeInfoService.getOne(new LambdaQueryWrapper<UmsSparesItemTypeInfo>()
                .eq(UmsSparesItemTypeInfo::getItemTypeCode, itemTypeCode)
        );
        gzkPartsDetailInfo.setItemCode(umsSparesItemTypeInfo.getItemCode());
        gzkPartsDetailInfoService.saveOrUpdate(gzkPartsDetailInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/gzkPartsDetailInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<GzkPartsDetailInfo>> map) {
        List<GzkPartsDetailInfo> models = map.get("models");
        gzkPartsDetailInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/gzkPartsDetailInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        gzkPartsDetailInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/gzkPartsDetailInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<GzkPartsDetailInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, GzkPartsDetailInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        gzkPartsDetailInfoService.save(u);
                });
                return Result.succeed("成功导入信息"+rowNum+"行数据");
            }
        }
        return Result.failed("导入失败");
    }
    
    /**
     * 导出
     */
    @ApiOperation(value = "导出")
    @PostMapping("/gzkPartsDetailInfo/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<GzkPartsDetailInfoVO> gzkPartsDetailInfoVOS=gzkPartsDetailInfoService.leadOutByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(gzkPartsDetailInfoVOS, "工装备品详情信息表导出", "工装备品详情信息表导出", GzkPartsDetailInfoVO.class, "gzkPartsDetailInfo.xls", response);

    }
}
