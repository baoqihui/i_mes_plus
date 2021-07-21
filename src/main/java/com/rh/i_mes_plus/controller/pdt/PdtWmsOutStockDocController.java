package com.rh.i_mes_plus.controller.pdt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtWmsOutStockDocDTO;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockDetail;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockDoc;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockList;
import com.rh.i_mes_plus.service.pdt.IPdtWmsOutStockDetailService;
import com.rh.i_mes_plus.service.pdt.IPdtWmsOutStockDocService;
import com.rh.i_mes_plus.service.pdt.IPdtWmsOutStockListService;
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
 * 成品出库单主表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "成品出库单主表")
@RequestMapping("pdt")
public class PdtWmsOutStockDocController {
    @Autowired
    private IPdtWmsOutStockDocService pdtWmsOutStockDocService;
    @Autowired
    private IPdtWmsOutStockDetailService pdtWmsOutStockDetailService;
    @Autowired
    private IPdtWmsOutStockListService pdtWmsOutStockListService;
    @Value("${zhaoIpAndPort}")
    private String zhaoIpAndPort;

    @ApiOperation(value = "备料报表")
    @PostMapping("/pdtWmsOutStockDoc/statement")
    public Result statement(@RequestBody Map<String,Object> map) {
        return pdtWmsOutStockDocService.statement(map);
    }

    @ApiOperation(value = "通过so号查询详情")
    @PostMapping(value = "/Api/wms/delivery/so")
    protected Result ApiEcnGetMoDtInfo(@RequestBody Map<String, Object> params)  {
        String modelCode = MapUtil.getStr(params, "modelCode");
        Integer soNo = MapUtil.getInt(params, "soNo");

        String result= HttpUtil.get(zhaoIpAndPort+"/Api/wms/delivery/so?modelCode="+modelCode+"&soNo="+soNo);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    /**
     * PDA扫码备料
     */
    @ApiOperation(value = "成品PDA扫码出库")
    @PostMapping("/mobile/pdaPdtOutStock")
    public Result pdaPdtOutStock(@RequestBody Map<String,Object> map) {
        return pdtWmsOutStockDocService.pdaPdtOutStock(map);
    }
    @ApiOperation(value = "出库单手动关结")
    @PostMapping("/pdtWmsOutStockDoc/close")
    public Result close(@RequestBody Map<String,Object> map) {
        return pdtWmsOutStockDocService.close(map);
    }
    /**
     * pda根据dtCode查询成品出库单号
     */
    @ApiOperation(value = "pda根据dtCode查询成品出库单号")
    @PostMapping("/mobile/getOutStockDocListByDtCode")
    public Result getOutStockDocListByDtCode(@RequestBody Map<String,Object> map) {
        List<String> strings=pdtWmsOutStockDocService.getOutStockDocListByDtCode(map);
        return Result.succeed(strings,"查询成功");
    }
    /**
     * 根据机构代码查询要生成的出库单号
     */
    @ApiOperation(value = "根据机构代码查询要生成的出库单号")
    @PostMapping("/pdtWmsOutStockDoc/getDocNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return pdtWmsOutStockDocService.getDocNo(map);
    }
    /**
     * 保存或更新(更新携带id)
     */
    @ApiOperation(value = "保存或更新(更新携带id)")
    @PostMapping("/pdtWmsOutStockDoc/saveAll")
    public Result saveAll(@RequestBody PdtWmsOutStockDocDTO pdtWmsOutStockDocDTO) {
        return pdtWmsOutStockDocService.saveAll(pdtWmsOutStockDocDTO);
    }
    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtWmsOutStockDoc/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        return pdtWmsOutStockDocService.removeAll(map);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtWmsOutStockDoc/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsOutStockDocService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 根据单号查详情
     */
    @ApiOperation(value = "根据单号查详情")
    @PostMapping("/pdtWmsOutStockDoc/selAllByDocNo")
    public Result selAllByDocNo(@RequestBody Map<String, Object> params) {
        String docNo = MapUtil.getStr(params, "docNo");
        PdtWmsOutStockDoc pdtWmsOutStockDoc = pdtWmsOutStockDocService.getOne(new QueryWrapper<PdtWmsOutStockDoc>()
                .eq("doc_no", docNo));
        List<PdtWmsOutStockDetail> pdtWmsOutStockDetails = pdtWmsOutStockDetailService.list(new QueryWrapper<PdtWmsOutStockDetail>().eq("doc_no", docNo));
        Map<String, Object> pdtWmsOutStockDocMap = BeanUtil.beanToMap(pdtWmsOutStockDoc);
        pdtWmsOutStockDocMap.put("pdtWmsOutStockDetails",pdtWmsOutStockDetails);
        return Result.succeed(pdtWmsOutStockDocMap, "查询成功");
    }
    /**
     * 根据单号查List详情
     */
    @ApiOperation(value = "根据单号查List详情")
    @PostMapping("/pdtWmsOutStockDoc/selListByDid")
    public Result selListByOsdId(@RequestBody Map<String, Object> params) {
        String osdId = MapUtil.getStr(params, "osdId");
        List<PdtWmsOutStockList> pdtWmsOutStockLists = pdtWmsOutStockListService.list(new QueryWrapper<PdtWmsOutStockList>()
                .eq("osd_id", osdId)
        );
        return Result.succeed(pdtWmsOutStockLists, "查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtWmsOutStockDoc/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtWmsOutStockDoc model = pdtWmsOutStockDocService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtWmsOutStockDoc/save")
    public Result save(@RequestBody PdtWmsOutStockDoc pdtWmsOutStockDoc) {
        pdtWmsOutStockDocService.saveOrUpdate(pdtWmsOutStockDoc);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtWmsOutStockDoc/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtWmsOutStockDoc>> map) {
        List<PdtWmsOutStockDoc> models = map.get("models");
        pdtWmsOutStockDocService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }


    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtWmsOutStockDoc/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtWmsOutStockDoc> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtWmsOutStockDoc.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        pdtWmsOutStockDocService.save(u);
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
    @PostMapping("/pdtWmsOutStockDoc/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<PdtWmsOutStockDoc> pdtWmsOutStockDocs =new ArrayList<>();
        List<PdtWmsOutStockDoc> pdtWmsOutStockDocList = pdtWmsOutStockDocService.list(new QueryWrapper<PdtWmsOutStockDoc>().eq("cu_id", cuId));
        if (pdtWmsOutStockDocList.isEmpty()) {pdtWmsOutStockDocs.add(pdtWmsOutStockDocService.getById(0)); } else {
            for (PdtWmsOutStockDoc pdtWmsOutStockDoc : pdtWmsOutStockDocList) {
                pdtWmsOutStockDocs.add(pdtWmsOutStockDoc);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtWmsOutStockDocs, "成品出库单主表导出", "成品出库单主表导出", PdtWmsOutStockDoc.class, "pdtWmsOutStockDoc.xls", response);

    }
}
