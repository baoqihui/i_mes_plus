package com.rh.i_mes_plus.controller.sms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsMoveDocDTO;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDoc;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDocDetail;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDocDetailSub;
import com.rh.i_mes_plus.model.ums.UmsWmsArea;
import com.rh.i_mes_plus.service.sms.ISmsWmsMoveDocDetailService;
import com.rh.i_mes_plus.service.sms.ISmsWmsMoveDocDetailSubService;
import com.rh.i_mes_plus.service.sms.ISmsWmsMoveDocService;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
import com.rh.i_mes_plus.service.ums.IUmsWmsAreaService;
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
 * 调拨单
 *
 * @author hbq
 * @date 2020-12-11 13:39:17
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "调拨单")
@RequestMapping("sms")
public class SmsWmsMoveDocController {
    @Autowired
    private ISmsWmsMoveDocService smsWmsMoveDocService;
    @Autowired
    private IUmsDepaService umsDepaService;
    @Autowired
    private ISmsWmsMoveDocDetailService smsWmsMoveDocDetailService;
    @Autowired
    private ISmsWmsMoveDocDetailSubService smsWmsMoveDocDetailSubService;
    @Autowired
    private IUmsWmsAreaService umsWmsAreaService;

    /**
     * 挑料亮灯
     */
    @ApiOperation(value = "挑料亮灯")
    @PostMapping("/smsWmsMoveDoc/lightUp")
    public Result lightUp(@RequestBody Map<String, Object> params) {
        return smsWmsMoveDocService.lightUp(params);
    }
    /**
     * 取消挑料
     */
    @ApiOperation(value = "取消挑料")
    @PostMapping("/smsWmsMoveDoc/cancelLightUp")
    public Result cancelLightUp(@RequestBody Map<String, Object> params) {
        return smsWmsMoveDocService.cancelLightUp(params);
    }
    /**
     * 保存或更新(更新携带id)
     */
    @ApiOperation(value = "保存或更新(更新携带id)")
    @PostMapping("/smsWmsMoveDoc/saveAll")
    public Result saveAll(@RequestBody SmsWmsMoveDocDTO smsWmsMoveDocDTO) {
        return smsWmsMoveDocService.saveAll(smsWmsMoveDocDTO);
    }
    
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsMoveDoc/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<Map> list= smsWmsMoveDocService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    
    /**
     * 根据机构代码查询要生成的调拨单号
     */
    @ApiOperation(value = "根据机构代码查询要生成的调拨单号")
    @PostMapping("/smsWmsMoveDoc/getDocNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return smsWmsMoveDocService.getDocNo(map);
    }
    
    /**
     * PDA扫码调拨
     */
    @ApiOperation(value = "PDA扫码调拨")
    @PostMapping("/smsWmsMoveDoc/pdaMove")
    public Result pdaMove(@RequestBody Map<String,Object> map) {
        return smsWmsMoveDocService.pdaMove(map);
    }
    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsMoveDoc/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        return smsWmsMoveDocService.removeAll(map);
    }
    /**
     * 根据单号查详情
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsMoveDoc/selAllByMoveNo")
    public Result selAllByDocNo(@RequestBody Map<String, Object> params) {
        String moveNo = MapUtil.getStr(params, "moveNo");
        SmsWmsMoveDoc smsWmsMoveDoc = smsWmsMoveDocService.getOne(new QueryWrapper<SmsWmsMoveDoc>()
                .eq("move_no", moveNo));
        Map<String, Object> smsWmsMoveDocMap = BeanUtil.beanToMap(smsWmsMoveDoc);
        String inWhCode = smsWmsMoveDoc.getInWhCode();
        String inWhName ="";
        String outWhName ="";
        if (!StrUtil.isEmpty(inWhCode)){
            inWhName=umsWmsAreaService.getOne(new QueryWrapper<UmsWmsArea>().eq("AR_SN", inWhCode)).getArName();
        }
        String outWhCode = smsWmsMoveDoc.getOutWhCode();
        if (!StrUtil.isEmpty(outWhCode)){
            outWhName=umsWmsAreaService.getOne(new QueryWrapper<UmsWmsArea>().eq("AR_SN", outWhCode)).getArName();
        }
        smsWmsMoveDocMap.put("inWhName",inWhName);
        smsWmsMoveDocMap.put("outWhName",outWhName);
        List<SmsWmsMoveDocDetail> smsWmsMoveDocDetails = smsWmsMoveDocDetailService.list(new QueryWrapper<SmsWmsMoveDocDetail>().eq("move_no", moveNo));
        smsWmsMoveDocMap.put("smsWmsMoveDocDetails",smsWmsMoveDocDetails);
        return Result.succeed(smsWmsMoveDocMap, "查询成功");
    }
    /**
     * 根据单号查List详情
     */
    @ApiOperation(value = "根据单号查List详情")
    @PostMapping("/smsWmsMoveDoc/selListByMoveDid")
    public Result selListByOsdId(@RequestBody Map<String, Object> params) {
        String moveDid = MapUtil.getStr(params, "moveDid");
        List<SmsWmsMoveDocDetailSub> smsWmsMoveDocDetailSubs = smsWmsMoveDocDetailSubService.list(new QueryWrapper<SmsWmsMoveDocDetailSub>()
                .eq("move_did", moveDid)
        );
        return Result.succeed(smsWmsMoveDocDetailSubs, "查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsMoveDoc/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsMoveDoc model = smsWmsMoveDocService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsMoveDoc/save")
    public Result save(@RequestBody SmsWmsMoveDoc smsWmsMoveDoc) {
        smsWmsMoveDocService.saveOrUpdate(smsWmsMoveDoc);
        return Result.succeed("保存成功");
    }
    
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsMoveDoc/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsMoveDoc>> map) {
        List<SmsWmsMoveDoc> models = map.get("models");
        smsWmsMoveDocService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsMoveDoc/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsMoveDoc> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsMoveDoc.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsMoveDocService.save(u);
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
    @PostMapping("/smsWmsMoveDoc/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsMoveDoc> smsWmsMoveDocs =new ArrayList<>();
        List<SmsWmsMoveDoc> smsWmsMoveDocList = smsWmsMoveDocService.list(new QueryWrapper<SmsWmsMoveDoc>().eq("cu_id", cuId));
        if (smsWmsMoveDocList.isEmpty()) {smsWmsMoveDocs.add(smsWmsMoveDocService.getById(0)); } else {
            for (SmsWmsMoveDoc smsWmsMoveDoc : smsWmsMoveDocList) {
                smsWmsMoveDocs.add(smsWmsMoveDoc);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsMoveDocs, "调拨单导出", "调拨单导出", SmsWmsMoveDoc.class, "smsWmsMoveDoc.xls", response);

    }
}
