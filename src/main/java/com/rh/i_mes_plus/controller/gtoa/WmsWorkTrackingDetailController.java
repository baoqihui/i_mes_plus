package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.WmsWorkTrackingDetailDTO;
import com.rh.i_mes_plus.model.gtoa.WmsWorkTracingMain;
import com.rh.i_mes_plus.model.gtoa.WmsWorkTrackingDetail;
import com.rh.i_mes_plus.service.gtoa.IWmsWorkTracingMainService;
import com.rh.i_mes_plus.service.gtoa.IWmsWorkTrackingDetailService;
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
 * 任务详情
 *
 * @author hbq
 * @date 2020-12-02 15:59:56
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "任务详情")
@RequestMapping("wms")
public class WmsWorkTrackingDetailController {
    @Autowired
    private IWmsWorkTrackingDetailService wmsWorkTrackingDetailService;
    @Autowired
    private IWmsWorkTracingMainService wmsWorkTracingMainService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/wmsWorkTrackingDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= wmsWorkTrackingDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/wmsWorkTrackingDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        WmsWorkTrackingDetail model = wmsWorkTrackingDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/wmsWorkTrackingDetail/save")
    public Result save(@RequestBody WmsWorkTrackingDetailDTO wmsWorkTrackingDetailDTO) {
        WmsWorkTrackingDetail wmsWorkTrackingDetail=wmsWorkTrackingDetailDTO;
        wmsWorkTracingMainService.update(new UpdateWrapper<WmsWorkTracingMain>()
                .eq("id",wmsWorkTrackingDetail.getWorkId())
                .set("close_flag",wmsWorkTrackingDetailDTO.getCloseFlag())
        );
        if (wmsWorkTrackingDetail.getContent()==null||"".equals(wmsWorkTrackingDetail.getContent())){
            return Result.succeed("保存成功");
        }
        wmsWorkTrackingDetailService.save(wmsWorkTrackingDetail);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/wmsWorkTrackingDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<WmsWorkTrackingDetail>> map) {
        List<WmsWorkTrackingDetail> models = map.get("models");
        wmsWorkTrackingDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/wmsWorkTrackingDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        wmsWorkTrackingDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/wmsWorkTrackingDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<WmsWorkTrackingDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, WmsWorkTrackingDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        wmsWorkTrackingDetailService.save(u);
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
    @PostMapping("/wmsWorkTrackingDetail/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<WmsWorkTrackingDetail> wmsWorkTrackingDetails =new ArrayList<>();
        List<WmsWorkTrackingDetail> wmsWorkTrackingDetailList = wmsWorkTrackingDetailService.list(new QueryWrapper<WmsWorkTrackingDetail>().eq("cu_id", cuId));
        if (wmsWorkTrackingDetailList.isEmpty()) {wmsWorkTrackingDetails.add(wmsWorkTrackingDetailService.getById(0)); } else {
            for (WmsWorkTrackingDetail wmsWorkTrackingDetail : wmsWorkTrackingDetailList) {
                wmsWorkTrackingDetails.add(wmsWorkTrackingDetail);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(wmsWorkTrackingDetails, "任务详情导出", "任务详情导出", WmsWorkTrackingDetail.class, "wmsWorkTrackingDetail.xls", response);

    }
}
