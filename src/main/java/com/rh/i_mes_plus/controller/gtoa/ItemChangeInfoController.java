package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.ItemChangeInfo;
import com.rh.i_mes_plus.service.gtoa.IItemChangeInfoService;
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
 * 变更项目表
 *
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "变更项目表")
@RequestMapping("ite")
public class ItemChangeInfoController {
    @Autowired
    private IItemChangeInfoService itemChangeInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/itemChangeInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= itemChangeInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/itemChangeInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        ItemChangeInfo model = itemChangeInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/itemChangeInfo/save")
    public Result save(@RequestBody ItemChangeInfo itemChangeInfo) {
        itemChangeInfoService.saveOrUpdate(itemChangeInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/itemChangeInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<ItemChangeInfo>> map) {
        List<ItemChangeInfo> models = map.get("models");
        itemChangeInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/itemChangeInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        itemChangeInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/itemChangeInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<ItemChangeInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, ItemChangeInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        itemChangeInfoService.save(u);
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
    @PostMapping("/itemChangeInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<ItemChangeInfo> itemChangeInfos =new ArrayList<>();
        List<ItemChangeInfo> itemChangeInfoList = itemChangeInfoService.list(new QueryWrapper<ItemChangeInfo>().eq("cu_id", cuId));
        if (itemChangeInfoList.isEmpty()) {itemChangeInfos.add(itemChangeInfoService.getById(0)); } else {
            for (ItemChangeInfo itemChangeInfo : itemChangeInfoList) {
                itemChangeInfos.add(itemChangeInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(itemChangeInfos, "变更项目表导出", "变更项目表导出", ItemChangeInfo.class, "itemChangeInfo.xls", response);

    }
}
