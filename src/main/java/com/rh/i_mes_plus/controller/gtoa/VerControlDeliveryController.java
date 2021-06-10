package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.VerControlDelivery;
import com.rh.i_mes_plus.service.gtoa.IVerControlDeliveryService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 不允许发货版本控制
 *
 * @author hbq
 * @date 2020-11-02 18:30:05
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "不允许发货版本控制")
@RequestMapping("ver")
public class VerControlDeliveryController {
    @Autowired
    private IVerControlDeliveryService verControlDeliveryService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/verControlDelivery/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= verControlDeliveryService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/verControlDelivery/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        VerControlDelivery model = verControlDeliveryService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PostMapping("/verControlDelivery/update")
    public Result save(@RequestBody VerControlDelivery verControlDelivery) {
        verControlDeliveryService.update(new UpdateWrapper<VerControlDelivery>()
                .eq("model_name",verControlDelivery.getModelName())
                .eq("ver",verControlDelivery.getVer())
                .set("agree_flag",verControlDelivery.getAgreeFlag())
                .set("according_for_disagree",verControlDelivery.getAccordingForDisagree())
                .set("update_name",verControlDelivery.getUpdateName())
        );
        Map<String, Object> params=new HashMap<>();
        params.put("modelName",verControlDelivery.getModelName());
        params.put("ver",verControlDelivery.getVer());
        params.put("validFlag",verControlDelivery.getAgreeFlag());
        verControlDeliveryService.ecnVerControlInfo(params);
        
        return Result.succeed("保存成功");
    }
    @ApiOperation(value = "保存")
    @PostMapping("/verControlDelivery/saveAll")
    public Result saveAll(@RequestBody VerControlDelivery verControlDelivery) {
        verControlDeliveryService.save(verControlDelivery);
        Map<String, Object> params=new HashMap<>();
        params.put("modelName",verControlDelivery.getModelName());
        params.put("ver",verControlDelivery.getVer());
        params.put("validFlag",verControlDelivery.getAgreeFlag());
        verControlDeliveryService.ecnVerControlInfo(params);
        return Result.succeed("保存成功");
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/verControlDelivery/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<VerControlDelivery>> map) {
        List<VerControlDelivery> models = map.get("models");
        verControlDeliveryService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/verControlDelivery/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        verControlDeliveryService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/verControlDelivery/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<VerControlDelivery> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, VerControlDelivery.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        verControlDeliveryService.save(u);
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
    @PostMapping("/verControlDelivery/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<VerControlDelivery> verControlDeliverys =new ArrayList<>();
        List<VerControlDelivery> verControlDeliveryList = verControlDeliveryService.list(new QueryWrapper<VerControlDelivery>().eq("cu_id", cuId));
        if (verControlDeliveryList.isEmpty()) {verControlDeliverys.add(verControlDeliveryService.getById(0)); } else {
            for (VerControlDelivery verControlDelivery : verControlDeliveryList) {
                verControlDeliverys.add(verControlDelivery);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(verControlDeliverys, "不允许发货版本控制导出", "不允许发货版本控制导出", VerControlDelivery.class, "verControlDelivery.xls", response);

    }
}
