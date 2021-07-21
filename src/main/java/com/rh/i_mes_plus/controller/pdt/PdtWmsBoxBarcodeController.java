package com.rh.i_mes_plus.controller.pdt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtWmsBox;
import com.rh.i_mes_plus.model.pdt.PdtWmsBoxBarcode;
import com.rh.i_mes_plus.service.pdt.IPdtWmsBoxBarcodeService;
import com.rh.i_mes_plus.service.pdt.IPdtWmsBoxService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "箱码与成品码绑定")
@RequestMapping("pdt")
public class PdtWmsBoxBarcodeController {
    @Autowired
    private IPdtWmsBoxBarcodeService pdtWmsBoxBarcodeService;
    @Autowired
    private IPdtWmsBoxService pdtWmsBoxService;
    /**https://web.doggo.top/auth/register?code=Th3t
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtWmsBoxBarcode/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsBoxBarcodeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 通过指令单号查询已包装条码
     */
    @ApiOperation(value = "通过指令单号查询已包装条码")
    @PostMapping("/pdtWmsBoxBarcode/getListByMoNo")
    public Result getListByMoNo(@RequestBody Map<String, Object> params) {
        String moNo = MapUtil.getStr(params, "moNo");
        List<PdtWmsBox> boxes = pdtWmsBoxService.list(new QueryWrapper<PdtWmsBox>().eq("mo_no", moNo));
        List<Map<String, Object>> boxMapList = new ArrayList<>();
        for (PdtWmsBox box : boxes) {
            Map<String, Object> boxMap = BeanUtil.beanToMap(box);
            List<Map<String, Object>> barcodeList=pdtWmsBoxBarcodeService.listByBoxNo(box.getBoxNo());
            boxMap.put("barcodeList",barcodeList);
            boxMapList.add(boxMap);
        }
        return Result.succeed(boxMapList,"查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtWmsBoxBarcode/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtWmsBoxBarcode model = pdtWmsBoxBarcodeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtWmsBoxBarcode/save")
    public Result save(@RequestBody PdtWmsBoxBarcode pdtWmsBoxBarcode) {
        pdtWmsBoxBarcodeService.saveOrUpdate(pdtWmsBoxBarcode);
        return Result.succeed("保存成功");
    }
    /**
     * 拆箱
     */
    @ApiOperation(value = "拆箱")
    @PostMapping("/mobile/unboxing")
    public Result unboxing(@RequestBody Map<String,Object> map) {
        return pdtWmsBoxBarcodeService.unboxing(map);
    }
    /**
     * 合箱
     */
    @ApiOperation(value = "合箱")
    @PostMapping("/mobile/boxing")
    public Result boxing(@RequestBody Map<String,Object> map) {
        return pdtWmsBoxBarcodeService.boxing(map);
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtWmsBoxBarcode/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtWmsBoxBarcode>> map) {
        List<PdtWmsBoxBarcode> models = map.get("models");
        pdtWmsBoxBarcodeService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtWmsBoxBarcode/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtWmsBoxBarcodeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtWmsBoxBarcode/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtWmsBoxBarcode> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtWmsBoxBarcode.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        pdtWmsBoxBarcodeService.save(u);
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
    @PostMapping("/pdtWmsBoxBarcode/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<PdtWmsBoxBarcode> pdtWmsBoxBarcodes =new ArrayList<>();
        List<PdtWmsBoxBarcode> pdtWmsBoxBarcodeList = pdtWmsBoxBarcodeService.list(new QueryWrapper<PdtWmsBoxBarcode>().eq("cu_id", cuId));
        if (pdtWmsBoxBarcodeList.isEmpty()) {pdtWmsBoxBarcodes.add(pdtWmsBoxBarcodeService.getById(0)); } else {
            for (PdtWmsBoxBarcode pdtWmsBoxBarcode : pdtWmsBoxBarcodeList) {
                pdtWmsBoxBarcodes.add(pdtWmsBoxBarcode);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtWmsBoxBarcodes, "导出", "导出", PdtWmsBoxBarcode.class, "pdtWmsBoxBarcode.xls", response);

    }
}
