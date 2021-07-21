package com.rh.i_mes_plus.controller.sms;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsBarcodeInfo;
import com.rh.i_mes_plus.service.sms.ISmsWmsBarcodeInfoService;
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
 * 条码信息
 *
 * @author hqb
 * @date 2020-10-07 10:11:58
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "条码信息")
@RequestMapping("sms")
public class SmsWmsBarcodeInfoController {
    @Autowired
    private ISmsWmsBarcodeInfoService smsWmsBarcodeInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsBarcodeInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<SmsWmsBarcodeInfo> list= smsWmsBarcodeInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 拆签
     */
    @ApiOperation(value = "拆签")
    @PostMapping("/mobile/spilt")
    public Result spilt(@RequestBody Map<String, Object> params) {
        return smsWmsBarcodeInfoService.spilt(params);
    }
    /**
     * 合签
     */
    @ApiOperation(value = "合签")
    @PostMapping("/mobile/combine")
    public Result combine(@RequestBody Map<String, Object> params) {
        return smsWmsBarcodeInfoService.combine(params);
    }
    /**
     * 条码克隆查询物料
     */
    @ApiOperation(value = "条码克隆查询物料")
    @PostMapping("/smsWmsBarcodeInfo/getBarcodeInfo")
    public Result getBarcodeInfo(@RequestBody Map<String, Object> params) {
        return smsWmsBarcodeInfoService.getBarcodeInfo(params);
    }
    /**
     * 查询未打印的拆分标签列表
     */
    @ApiOperation(value = "查询未打印的拆分标签列表")
    @PostMapping("/smsWmsBarcodeInfo/getOpenBarcode")
    public Result getOpenBarcode() {
        return smsWmsBarcodeInfoService.getOpenBarcode();
    }
    /**
     * 根据条码修改条码打印状态
     */
    @ApiOperation(value = "根据条码修改条码打印状态")
    @PostMapping("/smsWmsBarcodeInfo/updateBarcodeStatus")
    public Result updateBarcodeStatus(@RequestBody List<SmsWmsBarcodeInfo> smsWmsBarcodeInfos) {
        for (SmsWmsBarcodeInfo smsWmsBarcodeInfo : smsWmsBarcodeInfos) {
            smsWmsBarcodeInfoService.update(new UpdateWrapper<SmsWmsBarcodeInfo>()
                    .eq("TBL_BARCODE",smsWmsBarcodeInfo.getTblBarcode())
                    .set("has_print",1)
            );
        }
        return Result.succeed("保存成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsBarcodeInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsBarcodeInfo model = smsWmsBarcodeInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsBarcodeInfo/save")
    public Result save(@RequestBody SmsWmsBarcodeInfo smsWmsBarcodeInfo) {
        smsWmsBarcodeInfoService.saveOrUpdate(smsWmsBarcodeInfo);
        return Result.succeed("保存成功");
    }
    /**
     * 条码保存
     */
    @ApiOperation(value = "条码保存")
    @PostMapping("/smsWmsBarcodeInfo/saveBatch")
    public Result saveBatch(@RequestBody List<SmsWmsBarcodeInfo> smsWmsBarcodeInfos) {
        for (SmsWmsBarcodeInfo smsWmsBarcodeInfo : smsWmsBarcodeInfos) {
            String tblBarcode = smsWmsBarcodeInfo.getTblBarcode();
            SmsWmsBarcodeInfo barcodeInfo = smsWmsBarcodeInfoService.getOne(new LambdaQueryWrapper<SmsWmsBarcodeInfo>()
                    .eq(SmsWmsBarcodeInfo::getTblBarcode, tblBarcode)
            );
            if (barcodeInfo!=null){
                smsWmsBarcodeInfoService.update(smsWmsBarcodeInfo,new UpdateWrapper<SmsWmsBarcodeInfo>().eq("TBL_BARCODE",barcodeInfo.getTblBarcode()));
            }else {
                smsWmsBarcodeInfoService.save(smsWmsBarcodeInfo);
            }
        }
        return Result.succeed("保存成功");
    }
    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsBarcodeInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsBarcodeInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    /**
     * 条码克隆导入
     */
    @ApiOperation(value = "条码克隆导入")
    @PostMapping("/smsWmsBarcodeInfo/cloneLeadIn")
    public  Result cloneLeadIn(MultipartFile excel) throws Exception {
        int total=0;
        if (!excel.isEmpty()) {
            int i=0;
            List<SmsWmsBarcodeInfo> barcodeInfos=new ArrayList<>();
            List<SmsWmsBarcodeInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsBarcodeInfo.class);
            if (list.size() > 0) {
                for (SmsWmsBarcodeInfo barcodeInfo : list) {
                    String tblBarcode = barcodeInfo.getTblBarcode();
                    if (tblBarcode!=null){
                        total++;
                        SmsWmsBarcodeInfo one = smsWmsBarcodeInfoService.getOne(new LambdaQueryWrapper<SmsWmsBarcodeInfo>()
                                .eq(SmsWmsBarcodeInfo::getTblBarcode, tblBarcode)
                        );
                        if (one==null) {
                            i++;
                            log.info("批量克隆缺失条码：{}",tblBarcode);
                        }else{
                            barcodeInfos.add(one);
                        }
                    }
                }
            }
            return Result.succeed(barcodeInfos,"导入成功，共解析"+total+"条数据，其中"+i+"条数据无法正常导入");
        }
        return Result.failed("导入失败");
    }
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsBarcodeInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsBarcodeInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsBarcodeInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsBarcodeInfoService.save(u);
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
    @PostMapping("/smsWmsBarcodeInfo/leadOut")
    public void leadOut( HttpServletResponse response) throws IOException {
        List<SmsWmsBarcodeInfo> smsWmsBarcodeInfoList = smsWmsBarcodeInfoService.list(new QueryWrapper<SmsWmsBarcodeInfo>());
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsBarcodeInfoList, "条码信息导出", "条码信息导出", SmsWmsBarcodeInfo.class, "smsWmsBarcodeInfo.xls", response);

    }
}
