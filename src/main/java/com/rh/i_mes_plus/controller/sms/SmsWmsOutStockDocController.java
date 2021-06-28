package com.rh.i_mes_plus.controller.sms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsOutStockDocDTO;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDetail;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDoc;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockPmItem;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockDetailService;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockDocService;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockListService;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockPmItemService;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.vo.MassageVO;
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
import java.util.stream.Collectors;

/**
 * 出库单主表
 *
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "出库单主表")
@RequestMapping("sms")
public class SmsWmsOutStockDocController {
    @Autowired
    private ISmsWmsOutStockDocService smsWmsOutStockDocService;
    @Autowired
    private IUmsDepaService umsDepaService;
    @Autowired
    private ISmsWmsOutStockDetailService smsWmsOutStockDetailService;
    @Autowired
    private ISmsWmsOutStockPmItemService smsWmsOutStockPmItemService;
    @Autowired
    private ISmsWmsOutStockListService smsWmsOutStockListService;

    /**
     * 挑料亮灯
     */
    @ApiOperation(value = "挑料亮灯")
    @PostMapping("/smsOutStockDoc/lightUp")
    public Result lightUp(@RequestBody Map<String, Object> params) {
        return smsWmsOutStockDocService.lightUp(params);
    }
    /**
     * 取消挑料
     */
    @ApiOperation(value = "取消挑料")
    @PostMapping("/smsOutStockDoc/cancelLightUp")
    public Result cancelLightUp(@RequestBody Map<String, Object> params) {
        return smsWmsOutStockDocService.cancelLightUp(params);
    }
    @ApiOperation(value = "备料报表")
    @PostMapping("/smsWmsOutStockDoc/statement")
    public Result statement(@RequestBody Map<String,Object> map) {
        return smsWmsOutStockDocService.statement(map);
    }
    /**
     * 通过工单或备料单号查询备料单列表
     */
    @ApiOperation(value = "通过工单或备料单号查询备料单列表")
    @PostMapping("/smsWmsOutStockDoc/getDocNoByProjectIdAndDocNo")
    public Result getDocNoByProjectIdAndDocNo(@RequestBody Map<String,Object> map) {
        return smsWmsOutStockDocService.getDocNoByProjectIdAndDocNo(map);
    }
    /**
     * 通过工单号集合查询退料单物料列表
     */
    @ApiOperation(value = "通过工单号集合查询退料单物料列表")
    @PostMapping("/smsWmsOutStockDoc/getItemNumByDocNos")
    public Result getItemNumByDocNos(@RequestBody Map<String,List<String>> map) {
        return smsWmsOutStockDocService.getItemNumByDocNos(map);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsOutStockDoc/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<Map> list= smsWmsOutStockDocService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 根据机构代码查询要生成的备料单号
     */
    @ApiOperation(value = "根据机构代码查询要生成的备料单号")
    @PostMapping("/smsWmsOutStockDoc/getDocNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return smsWmsOutStockDocService.getDocNo(map);
    }

    /**
     * PDA扫码备料
     */
    @ApiOperation(value = "PDA扫码备料")
    @PostMapping("/smsWmsOutStockDoc/pdaOutStock")
    public Result pdaOutStock(@RequestBody Map<String,Object> map) {
        return smsWmsOutStockDocService.pdaOutStock(map);
    }
    /**
     * PDA扫码取消备料
     */
    @ApiOperation(value = "PDA扫码取消备料")
    @PostMapping("/smsWmsOutStockDoc/pdaCancelOutStock")
    public Result pdaCancelOutStock(@RequestBody Map<String,Object> map) {
        return smsWmsOutStockDocService.pdaCancelOutStock(map);
    }
    /**
     * 保存或更新(更新携带id)
     */
    @ApiOperation(value = "保存或更新(更新携带id)")
    @PostMapping("/smsWmsOutStockDoc/saveAll")
    public Result saveAll(@RequestBody SmsWmsOutStockDocDTO smsWmsOutStockDocDTO) {
        return smsWmsOutStockDocService.saveAll(smsWmsOutStockDocDTO);
    }

    /**
     * 工单保存或更新（更新携带id）
     */
    @ApiOperation(value = "工单保存或更新（更新携带id）")
    @PostMapping("/smsWmsOutStockDoc/saveProjectAll")
    public Result saveProjectAll(@RequestBody SmsWmsOutStockDocDTO smsWmsOutStockDocDTO) {
        return smsWmsOutStockDocService.saveProjectAll(smsWmsOutStockDocDTO);
    }
    /**
     * 换货出库新增
     */
    @ApiOperation(value = "换货出库新增")
    @PostMapping("/smsWmsOutStockDoc/saveListAll")
    public Result saveListAll(@RequestBody SmsWmsOutStockDocDTO smsWmsOutStockDocDTO) {
        return smsWmsOutStockDocService.saveListAll(smsWmsOutStockDocDTO);
    }
    /**
     * 根据单号查详情
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsOutStockDoc/selAllByDocNo")
    public Result selAllByDocNo(@RequestBody Map<String, Object> params) {
        String docNo = MapUtil.getStr(params, "docNo");
        SmsWmsOutStockDoc smsWmsOutStockDoc = smsWmsOutStockDocService.getOne(new QueryWrapper<SmsWmsOutStockDoc>()
                .eq("doc_no",docNo));
        Map<String, Object> outStockDocMap = BeanUtil.beanToMap(smsWmsOutStockDoc);
        List<SmsWmsOutStockDetail> smsWmsOutStockDetails = smsWmsOutStockDetailService.list(new QueryWrapper<SmsWmsOutStockDetail>().eq("doc_no", docNo));
        List<Map<String, Object>> smsWmsOutStockDetailMaps=new ArrayList<>();
        for (SmsWmsOutStockDetail smsWmsOutStockDetail : smsWmsOutStockDetails) {
            Long id = smsWmsOutStockDetail.getId();
            Map<String, Object> smsWmsOutStockDetailMap = BeanUtil.beanToMap(smsWmsOutStockDetail);
            List<MassageVO> massaged = smsWmsOutStockPmItemService.getMassageList(id);
            List<MassageVO> massagedLot=smsWmsOutStockPmItemService.getMassageLotList(id);
            massaged.forEach(u->u.setMassage(u.getMassage()+"盘"));
            massagedLot.forEach(u->u.setMassage(u.getMassage()+"包"));
            String materialTrayData = massaged.stream().map(MassageVO::getMassage).collect(Collectors.joining(","));
            String materialTrayDataLot = massagedLot.stream().map(MassageVO::getMassage).collect(Collectors.joining(","));
            smsWmsOutStockDetailMap.put("materialTrayData",materialTrayData);
            smsWmsOutStockDetailMap.put("materialTrayDataLot",materialTrayDataLot);

            smsWmsOutStockDetailMaps.add(smsWmsOutStockDetailMap);
        }
        List<SmsWmsOutStockPmItem> smsWmsOutStockPmItems = smsWmsOutStockPmItemService.list(new QueryWrapper<SmsWmsOutStockPmItem>().eq("doc_no", docNo));
        outStockDocMap.put("smsWmsOutStockDetails",smsWmsOutStockDetailMaps);
        outStockDocMap.put("smsWmsOutStockPmItems",smsWmsOutStockPmItems);
        return Result.succeed(outStockDocMap, "查询成功");
    }
    /**
     * 根据单号查List详情
     */
    @ApiOperation(value = "根据单号查List详情")
    @PostMapping("/smsWmsOutStockDoc/selListByOsdId")
    public Result selListByOsdId(@RequestBody Map<String, Object> params) {
        String osdId = MapUtil.getStr(params, "osdId");
        List<Map<String, Object>> outStockLists = smsWmsOutStockListService.listAndBarcode(osdId);
        return Result.succeed(outStockLists, "查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsOutStockDoc/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsOutStockDoc model = smsWmsOutStockDocService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsOutStockDoc/save")
    public Result save(@RequestBody SmsWmsOutStockDoc smsWmsOutStockDoc) {
        smsWmsOutStockDocService.saveOrUpdate(smsWmsOutStockDoc);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsOutStockDoc/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        return smsWmsOutStockDocService.removeAll(map);
    }

    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsOutStockDoc/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsOutStockDoc> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsOutStockDoc.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                    smsWmsOutStockDocService.save(u);
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
    @PostMapping("/smsWmsOutStockDoc/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsOutStockDoc> smsWmsOutStockDocs =new ArrayList<>();
        List<SmsWmsOutStockDoc> smsWmsOutStockDocList = smsWmsOutStockDocService.list(new QueryWrapper<SmsWmsOutStockDoc>().eq("cu_id", cuId));
        if (smsWmsOutStockDocList.isEmpty()) {smsWmsOutStockDocs.add(smsWmsOutStockDocService.getById(0)); } else {
            for (SmsWmsOutStockDoc smsWmsOutStockDoc : smsWmsOutStockDocList) {
                smsWmsOutStockDocs.add(smsWmsOutStockDoc);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsOutStockDocs, "出库单主表导出", "出库单主表导出", SmsWmsOutStockDoc.class, "smsWmsOutStockDoc.xls", response);

    }
}
