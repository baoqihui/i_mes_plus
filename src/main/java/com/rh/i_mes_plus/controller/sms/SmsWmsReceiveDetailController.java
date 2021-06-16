package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetail;
import com.rh.i_mes_plus.service.sms.ISmsWmsReceiveDetailService;
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
 * 
 *
 * @author hqb
 * @date 2020-10-07 15:25:52
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "入库单明细表")
@RequestMapping("sms")
public class SmsWmsReceiveDetailController {
    @Autowired
    private ISmsWmsReceiveDetailService smsWmsReceiveDetailService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsReceiveDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<SmsWmsReceiveDetail> list= smsWmsReceiveDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsReceiveDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsReceiveDetail model = smsWmsReceiveDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsReceiveDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsReceiveDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsReceiveDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsReceiveDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsReceiveDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsReceiveDetailService.save(u);
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
    @PostMapping("/smsWmsReceiveDetail/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsReceiveDetail> smsWmsReceiveDetails =new ArrayList<>();
        List<SmsWmsReceiveDetail> smsWmsReceiveDetailList = smsWmsReceiveDetailService.list(new QueryWrapper<SmsWmsReceiveDetail>().eq("cu_id", cuId));
        if (smsWmsReceiveDetailList.isEmpty()) {smsWmsReceiveDetails.add(smsWmsReceiveDetailService.getById(0)); } else {
            for (SmsWmsReceiveDetail smsWmsReceiveDetail : smsWmsReceiveDetailList) {
                smsWmsReceiveDetails.add(smsWmsReceiveDetail);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsReceiveDetails, "入库单明细表导出", "入库单明细表导出", SmsWmsReceiveDetail.class, "smsWmsReceiveDetail.xls", response);

    }
}
