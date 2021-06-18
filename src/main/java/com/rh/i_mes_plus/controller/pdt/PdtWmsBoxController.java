package com.rh.i_mes_plus.controller.pdt;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtWmsBoxDTO;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsBoxBarcodeMapper;
import com.rh.i_mes_plus.model.pdt.PdtWmsBox;
import com.rh.i_mes_plus.model.pdt.PdtWmsBoxBarcode;
import com.rh.i_mes_plus.service.pdt.IPdtWmsBoxService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 *
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "箱码")
@RequestMapping("pdt")
public class PdtWmsBoxController {
    @Autowired
    private IPdtWmsBoxService pdtWmsBoxService;
    @Resource
    private PdtWmsBoxBarcodeMapper pdtWmsBoxBarcodeMapper;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtWmsBox/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsBoxService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 根据机构代码查询要生成的箱号
     */
    @ApiOperation(value = "根据机构代码查询要生成的箱号")
    @PostMapping("/pdtWmsBox/getBoxNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return pdtWmsBoxService.getBoxNo(map);
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtWmsBox/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtWmsBox model = pdtWmsBoxService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 保存箱码
     */
    @ApiOperation(value = "保存箱码")
    @PostMapping("/pdtWmsBox/saveAll")
    public Result saveAll(@RequestBody PdtWmsBoxDTO pdtWmsBoxDTO) {
        return pdtWmsBoxService.saveAll(pdtWmsBoxDTO);
    }
    /**
     * 条码克隆查询箱码
     */
    @ApiOperation(value = "条码克隆查询箱码")
    @PostMapping("/pdtWmsBox/getBoxInfo")
    public Result getBoxInfo(@RequestBody Map<String, Object> params) {
        return pdtWmsBoxService.getBoxInfo(params);
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtWmsBox/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtWmsBox>> map) {
        List<PdtWmsBox> models = map.get("models");
        pdtWmsBoxService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtWmsBox/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtWmsBoxService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 箱码克隆导入
     */
    @ApiOperation(value = "箱码克隆导入")
    @PostMapping("/pdtWmsBox/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int total=0;
        if (!excel.isEmpty()) {
            int i=0;
            List<PdtWmsBox> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtWmsBox.class);
            List<Map<String, Object>> results=new ArrayList<>();
            if (list.size() > 0) {
                for (PdtWmsBox pdtWmsBox : list) {
                    String boxNo = pdtWmsBox.getBoxNo();
                    if (boxNo!=null){
                        System.out.println(pdtWmsBox);
                        total++;
                        Map<String, Object> stringObjectMap = pdtWmsBoxBarcodeMapper.selBoxBarcode(boxNo);
                        if (stringObjectMap==null){
                            i++;
                            log.info("批量克隆缺失箱码：{}",boxNo);
                        }
                        else {
                            Map<String,Object> result= MapUtil.toCamelCaseMap(stringObjectMap);
                            List<PdtWmsBoxBarcode> boxBarcodes = pdtWmsBoxBarcodeMapper.selectList(new QueryWrapper<PdtWmsBoxBarcode>()
                                    .eq("box_no", boxNo));

                            List<String> tos=boxBarcodes.stream().map(u->u.getBarcode()).collect(Collectors.toList());
                            result.put("boxBarcodeInfos",tos);
                            results.add(result);
                        }
                    }
                }
            }
            return Result.succeed(results,"导入成功，共解析"+total+"条数据，其中"+i+"条数据无法正常导入");
        }
        return Result.failed("导入失败");
    }
    
    /**
     * 导出
     */
    @ApiOperation(value = "导出")
    @PostMapping("/pdtWmsBox/leadOut")
    public void leadOut( HttpServletResponse response) throws IOException {
        List<PdtWmsBox> pdtWmsBoxList = pdtWmsBoxService.list();
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtWmsBoxList, "箱码导出", "箱码导出", PdtWmsBox.class, "pdtWmsBox.xls", response);
    }
}
