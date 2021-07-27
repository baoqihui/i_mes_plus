package com.rh.i_mes_plus.controller.pdt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtWmsReceiveDocDTO;
import com.rh.i_mes_plus.model.pdt.PdtWmsReceiveDetailSub;
import com.rh.i_mes_plus.model.pdt.PdtWmsReceiveDoc;
import com.rh.i_mes_plus.service.pdt.IPdtWmsReceiveDetailService;
import com.rh.i_mes_plus.service.pdt.IPdtWmsReceiveDetailSubService;
import com.rh.i_mes_plus.service.pdt.IPdtWmsReceiveDocService;
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
 * 入库单表
 *
 * @author hbq
 * @date 2020-12-29 15:41:49
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "成品入库单表")
@RequestMapping("pdt")
public class PdtWmsReceiveDocController {
    @Autowired
    private IPdtWmsReceiveDocService pdtWmsReceiveDocService;
    @Autowired
    private IPdtWmsReceiveDetailSubService pdtWmsReceiveDetailSubService;
    @Autowired
    private IPdtWmsReceiveDetailService pdtWmsReceiveDetailService;

    @ApiOperation(value = "PDA箱码扫描")
    @PostMapping("/mobile/pdaPdtReceive")
    public Result pdaPdtReceive(@RequestBody Map<String,Object> map) {
        return pdtWmsReceiveDocService.pdaPdtReceive(map);
    }
    @ApiOperation(value = "入库单关结")
    @PostMapping("/pdtWmsReceiveDoc/close")
    public Result close(@RequestBody Map<String,Object> map) {
        return pdtWmsReceiveDocService.close(map);
    }
    /**
     * pda根据dtCode查询成品入库单号
     */
    @ApiOperation(value = "pda根据dtCode查询成品入库单号")
    @PostMapping("/mobile/getReceiveDocListByDtCode")
    public List<String> getReceiveDocListByDtCode(@RequestBody Map<String,Object> map) {
        List<String> receiveList=new ArrayList<>();
        receiveList.add("ALL");
        receiveList.addAll(pdtWmsReceiveDocService.getReceiveDocListByDtCode(map));
        return receiveList;
    }

    /**
     * 根据机构代码查询要生成的入库单号
     */
    @ApiOperation(value = "根据机构代码查询要生成的入库单号")
    @PostMapping("/pdtWmsReceiveDoc/getDocNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return pdtWmsReceiveDocService.getDocNo(map);
    }
    
    /**
     * 根据单号查详情
     */
    @ApiOperation(value = "根据单号查详情")
    @PostMapping("/pdtWmsReceiveDoc/selAllByDocNo")
    public Result selAllByDocNo(@RequestBody Map<String, Object> params) {
        String docNo = MapUtil.getStr(params, "docNo");
        PdtWmsReceiveDoc pdtWmsReceiveDoc = pdtWmsReceiveDocService.getOne(new QueryWrapper<PdtWmsReceiveDoc>()
                .eq("doc_no", docNo));
        List<Map<String, Object>> pdtWmsReceiveDetails = pdtWmsReceiveDetailService.listAll(docNo);
        Map<String, Object> smsWmsReloadDocMap = BeanUtil.beanToMap(pdtWmsReceiveDoc);
        smsWmsReloadDocMap.put("pdtWmsReceiveDetails",pdtWmsReceiveDetails);
        return Result.succeed(smsWmsReloadDocMap, "查询成功");
    }
    /**
     * 根据单号查List详情
     */
    @ApiOperation(value = "根据单号查List详情")
    @PostMapping("/pdtWmsReceiveDoc/selListByDid")
    public Result selListByOsdId(@RequestBody Map<String, Object> params) {
        String rdId = MapUtil.getStr(params, "rdId");
        List<PdtWmsReceiveDetailSub> pdtWmsReceiveDetailSubs = pdtWmsReceiveDetailSubService.list(new QueryWrapper<PdtWmsReceiveDetailSub>()
                .eq("rd_id", rdId)
        );
        return Result.succeed(pdtWmsReceiveDetailSubs, "查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtWmsReceiveDoc/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsReceiveDocService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtWmsReceiveDoc/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtWmsReceiveDoc model = pdtWmsReceiveDocService.getById(id);
        return Result.succeed(model, "查询成功");
    }
    /**
     * 保存或更新(更新携带id)
     */
    @ApiOperation(value = "保存或更新(更新携带id)")
    @PostMapping("/pdtWmsReceiveDoc/saveAll")
    public Result saveAll(@RequestBody PdtWmsReceiveDocDTO pdtWmsReceiveDocDTO) {
        return pdtWmsReceiveDocService.saveAll(pdtWmsReceiveDocDTO);
    }
    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtWmsReceiveDoc/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        return pdtWmsReceiveDocService.removeAll(map);
    }
    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtWmsReceiveDoc/save")
    public Result save(@RequestBody PdtWmsReceiveDoc pdtWmsReceiveDoc) {
        pdtWmsReceiveDocService.saveOrUpdate(pdtWmsReceiveDoc);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtWmsReceiveDoc/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtWmsReceiveDoc>> map) {
        List<PdtWmsReceiveDoc> models = map.get("models");
        pdtWmsReceiveDocService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtWmsReceiveDoc/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtWmsReceiveDoc> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtWmsReceiveDoc.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        pdtWmsReceiveDocService.save(u);
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
    @PostMapping("/pdtWmsReceiveDoc/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<PdtWmsReceiveDoc> pdtWmsReceiveDocs =new ArrayList<>();
        List<PdtWmsReceiveDoc> pdtWmsReceiveDocList = pdtWmsReceiveDocService.list(new QueryWrapper<PdtWmsReceiveDoc>().eq("cu_id", cuId));
        if (pdtWmsReceiveDocList.isEmpty()) {pdtWmsReceiveDocs.add(pdtWmsReceiveDocService.getById(0)); } else {
            for (PdtWmsReceiveDoc pdtWmsReceiveDoc : pdtWmsReceiveDocList) {
                pdtWmsReceiveDocs.add(pdtWmsReceiveDoc);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtWmsReceiveDocs, "入库单表导出", "入库单表导出", PdtWmsReceiveDoc.class, "pdtWmsReceiveDoc.xls", response);

    }
}
