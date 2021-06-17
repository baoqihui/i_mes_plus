package com.rh.i_mes_plus.controller.sps;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.ApprovalProcess;
import com.rh.i_mes_plus.service.sps.IApprovalProcessService;
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
 * 流程控制
 *
 * @author hbq
 * @date 2021-02-21 16:06:24
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "流程控制")
@RequestMapping("app")
public class ApprovalProcessController {
    @Autowired
    private IApprovalProcessService approvalProcessService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/approvalProcess/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= approvalProcessService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 根据当前所在步数求下一步待审核人以及状态
     *
     */
    @ApiOperation(value = "根据当前所在步数求下一步待审核人以及状态")
    @PostMapping("/approvalProcess/getOperUsrNo")
    public Result getOperUsrNo(@RequestBody Map<String, Object> params) {
        ApprovalProcess model = approvalProcessService.getOperUsrNo(params);
        return Result.succeed(model, "查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/approvalProcess/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        ApprovalProcess model = approvalProcessService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/approvalProcess/save")
    public Result save(@RequestBody ApprovalProcess approvalProcess) {
        approvalProcessService.saveOrUpdate(approvalProcess);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/approvalProcess/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<ApprovalProcess>> map) {
        List<ApprovalProcess> models = map.get("models");
        approvalProcessService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/approvalProcess/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        approvalProcessService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/approvalProcess/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<ApprovalProcess> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, ApprovalProcess.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        approvalProcessService.save(u);
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
    @PostMapping("/approvalProcess/leadOut")
    public void leadOut(HttpServletResponse response) throws IOException {
        List<ApprovalProcess> approvalProcesss =new ArrayList<>();
        List<ApprovalProcess> approvalProcessList = approvalProcessService.list(new QueryWrapper<ApprovalProcess>());
        if (approvalProcessList.isEmpty()) {approvalProcesss.add(approvalProcessService.getById(0)); } else {
            for (ApprovalProcess approvalProcess : approvalProcessList) {
                approvalProcesss.add(approvalProcess);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(approvalProcesss, "流程控制导出", "流程控制导出", ApprovalProcess.class, "approvalProcess.xls", response);

    }
}
