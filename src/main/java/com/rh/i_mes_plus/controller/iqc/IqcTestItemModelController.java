package com.rh.i_mes_plus.controller.iqc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcTestItemModel;
import com.rh.i_mes_plus.service.iqc.IIqcTestItemModelService;
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
 * 物料检验项目
 *
 * @author hqb
 * @date 2020-10-16 16:30:18
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "物料检验项目")
@RequestMapping("iqc")
public class IqcTestItemModelController {
    @Autowired
    private IIqcTestItemModelService iqcTestItemModelService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcTestItemModel/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= iqcTestItemModelService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "修改时查询")
    @PostMapping("/iqcTestItemModel/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcTestItemModel model = iqcTestItemModelService.getById(id);
        String itemCode = model.getItemCode();
        String otsCode = model.getOtsCode();
        String msCode = model.getMsCode();
        Long tiyId = model.getTiyId();
        List<IqcTestItemModel> list = iqcTestItemModelService.list(new QueryWrapper<IqcTestItemModel>()
                .eq("item_code", itemCode).eq("ots_code", otsCode)
                .eq("ms_code", msCode).eq("tiy_id", tiyId));
        return Result.succeed(list, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcTestItemModel/save")
    public Result save(@RequestBody IqcTestItemModel iqcTestItemModel) {
        iqcTestItemModelService.saveOrUpdate(iqcTestItemModel);
        return Result.succeed("保存成功");
    }

    @ApiOperation(value = "复制保存")
    @PostMapping("/iqcTestItemModel/saveBatchCopy")
    public Result saveBatchCopy(@RequestBody Map<String,List<IqcTestItemModel>> map) {
        List<IqcTestItemModel> models = map.get("models");
        for (IqcTestItemModel model : models) {
            iqcTestItemModelService.remove(new QueryWrapper<IqcTestItemModel>()
                    .eq("item_code",model.getItemCode()));
        }
        iqcTestItemModelService.saveBatch(models);
        return Result.succeed("保存成功");
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/iqcTestItemModel/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<IqcTestItemModel>> map) {
        List<IqcTestItemModel> models = map.get("models");
        IqcTestItemModel model = iqcTestItemModelService.getById(models.get(0).getId());
        if (model != null) {
            String itemCode = model.getItemCode();
            String otsCode = model.getOtsCode();
            String msCode = model.getMsCode();
            Long tiyId = model.getTiyId();
            iqcTestItemModelService.remove(new QueryWrapper<IqcTestItemModel>()
                    .eq("item_code", itemCode).eq("ots_code", otsCode)
                    .eq("ms_code", msCode).eq("tiy_id", tiyId));
        }

        iqcTestItemModelService.saveBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcTestItemModel/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcTestItemModelService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcTestItemModel/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcTestItemModel> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcTestItemModel.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcTestItemModelService.save(u);
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
    @PostMapping("/iqcTestItemModel/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcTestItemModel> iqcTestItemModels =new ArrayList<>();
        List<IqcTestItemModel> iqcTestItemModelList = iqcTestItemModelService.list(new QueryWrapper<IqcTestItemModel>().eq("cu_id", cuId));
        if (iqcTestItemModelList.isEmpty()) {iqcTestItemModels.add(iqcTestItemModelService.getById(0)); } else {
            for (IqcTestItemModel iqcTestItemModel : iqcTestItemModelList) {
                iqcTestItemModels.add(iqcTestItemModel);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcTestItemModels, "物料检验项目导出", "物料检验项目导出", IqcTestItemModel.class, "iqcTestItemModel.xls", response);

    }
}
