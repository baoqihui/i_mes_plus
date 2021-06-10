package com.rh.i_mes_plus.controller.gtoa;

import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.CusComplaint;
import com.rh.i_mes_plus.service.gtoa.ICusComplaintService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 客诉表
 *
 * @author hbq
 * @date 2020-12-23 19:01:39
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "客诉表")
@RequestMapping("cus")
public class CusComplaintController {
    @Autowired
    private ICusComplaintService cusComplaintService;
    @Value("${zhaoIpAndPort}")
    private String zhaoIpAndPort;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/cusComplaint/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= cusComplaintService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/cusComplaint/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        CusComplaint model = cusComplaintService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/cusComplaint/save")
    public Result save(@RequestBody CusComplaint cusComplaint) {
        String sn = cusComplaint.getSn();
        String result= HttpUtil.get(zhaoIpAndPort+"/Api/gtoa/complaint/history/exists?qn="+sn);
        cusComplaint.setIsMaintain(Boolean.valueOf(result));
        cusComplaintService.saveOrUpdate(cusComplaint);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/cusComplaint/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<CusComplaint>> map) {
        List<CusComplaint> models = map.get("models");
        cusComplaintService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/cusComplaint/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        cusComplaintService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/cusComplaint/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<CusComplaint> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, CusComplaint.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        cusComplaintService.save(u);
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
    @PostMapping("/cusComplaint/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<CusComplaint> cusComplaints =new ArrayList<>();
        List<CusComplaint> cusComplaintList = cusComplaintService.list(new QueryWrapper<CusComplaint>().eq("cu_id", cuId));
        if (cusComplaintList.isEmpty()) {cusComplaints.add(cusComplaintService.getById(0)); } else {
            for (CusComplaint cusComplaint : cusComplaintList) {
                cusComplaints.add(cusComplaint);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(cusComplaints, "客诉表导出", "客诉表导出", CusComplaint.class, "cusComplaint.xls", response);

    }
}
