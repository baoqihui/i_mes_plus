package com.rh.i_mes_plus.controller.sps;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.SpsSparesCheckDetail;
import com.rh.i_mes_plus.service.sps.ISpsSparesCheckDetailService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
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
 * 盘点明细表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "盘点明细表")
@RequestMapping("sps")
public class SpsSparesCheckDetailController {
    @Autowired
    private ISpsSparesCheckDetailService spsSparesCheckDetailService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/spsSparesCheckDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= spsSparesCheckDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/spsSparesCheckDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SpsSparesCheckDetail model = spsSparesCheckDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/spsSparesCheckDetail/save")
    public Result save(@RequestBody SpsSparesCheckDetail spsSparesCheckDetail) {
        spsSparesCheckDetailService.saveOrUpdate(spsSparesCheckDetail);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/spsSparesCheckDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SpsSparesCheckDetail>> map) {
        List<SpsSparesCheckDetail> models = map.get("models");
        spsSparesCheckDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/spsSparesCheckDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        spsSparesCheckDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/spsSparesCheckDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SpsSparesCheckDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SpsSparesCheckDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        spsSparesCheckDetailService.save(u);
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
    @PostMapping("/spsSparesCheckDetail/leadOut")
    public void leadOut(String checkNo, HttpServletResponse response) throws IOException {
        List<SpsSparesCheckDetail> spsSparesCheckDetailList = spsSparesCheckDetailService.list(new QueryWrapper<SpsSparesCheckDetail>()
                .eq("check_no", checkNo)
        );
        System.out.println(checkNo);
        //导出操作
        EasyPoiExcelUtil.exportExcel(spsSparesCheckDetailList, "盘点明细表导出", "盘点明细表导出", SpsSparesCheckDetail.class, "spsSparesCheckDetail.xls", response);

    }
}
