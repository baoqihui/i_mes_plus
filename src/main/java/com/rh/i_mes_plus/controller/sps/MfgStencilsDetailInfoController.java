package com.rh.i_mes_plus.controller.sps;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.MfgStencilsDetailInfo;
import com.rh.i_mes_plus.service.sps.IMfgStencilsDetailInfoService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 钢网详情信息表
 *
 * @author hbq
 * @date 2021-02-23 16:28:09
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "钢网详情信息表")
@RequestMapping("mfg")
public class MfgStencilsDetailInfoController {
    @Autowired
    private IMfgStencilsDetailInfoService mfgStencilsDetailInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/mfgStencilsDetailInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= mfgStencilsDetailInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/mfgStencilsDetailInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        MfgStencilsDetailInfo model = mfgStencilsDetailInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/mfgStencilsDetailInfo/save")
    public Result save(@RequestBody MfgStencilsDetailInfo mfgStencilsDetailInfo) {
        mfgStencilsDetailInfoService.saveOrUpdate(mfgStencilsDetailInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/mfgStencilsDetailInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<MfgStencilsDetailInfo>> map) {
        List<MfgStencilsDetailInfo> models = map.get("models");
        mfgStencilsDetailInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/mfgStencilsDetailInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        mfgStencilsDetailInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/mfgStencilsDetailInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<MfgStencilsDetailInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, MfgStencilsDetailInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        mfgStencilsDetailInfoService.save(u);
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
    @PostMapping("/mfgStencilsDetailInfo/leadOut")
    public void leadOut(HttpServletResponse response) throws IOException {
        List<MfgStencilsDetailInfo> mfgStencilsDetailInfos =new ArrayList<>();
        List<MfgStencilsDetailInfo> mfgStencilsDetailInfoList = mfgStencilsDetailInfoService.list(new QueryWrapper<MfgStencilsDetailInfo>());
        if (mfgStencilsDetailInfoList.isEmpty()) {mfgStencilsDetailInfos.add(mfgStencilsDetailInfoService.getById(0)); } else {
            for (MfgStencilsDetailInfo mfgStencilsDetailInfo : mfgStencilsDetailInfoList) {
                mfgStencilsDetailInfos.add(mfgStencilsDetailInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(mfgStencilsDetailInfos, "钢网详情信息表导出", "钢网详情信息表导出", MfgStencilsDetailInfo.class, "mfgStencilsDetailInfo.xls", response);

    }
}
