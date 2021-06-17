package com.rh.i_mes_plus.controller.sps;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SpsEngFixReqInfoDTO;
import com.rh.i_mes_plus.model.sps.SpsEngFixReqDetailInfo;
import com.rh.i_mes_plus.model.sps.SpsEngFixReqInfo;
import com.rh.i_mes_plus.service.sps.ISpsEngFixDetailInfoService;
import com.rh.i_mes_plus.service.sps.ISpsEngFixReqDetailInfoService;
import com.rh.i_mes_plus.service.sps.ISpsEngFixReqInfoService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工程治具借用记录表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工程治具借用记录表")
@RequestMapping("sps")
public class SpsEngFixReqInfoController {
    @Autowired
    @Lazy
    private ISpsEngFixReqInfoService spsEngFixReqInfoService;
    @Autowired
    @Lazy
    private ISpsEngFixDetailInfoService spsEngFixDetailInfoService;
    @Autowired
    @Lazy
    private ISpsEngFixReqDetailInfoService spsEngFixReqDetailInfoService;

    @Value("${zhaoIpAndPort}")
    private String zhaoIpAndPort;
    /**
     * 查询新的借用单号
     */
    @ApiOperation(value = "查询新的借用单号")
    @PostMapping("/spsEngFixReqInfo/getDocNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return spsEngFixReqInfoService.getDocNo(map);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/spsEngFixReqInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= spsEngFixReqInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/spsEngFixReqInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SpsEngFixReqInfo model = spsEngFixReqInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }
    @ApiOperation(value = "通过mo查询机型和数量")
    @PostMapping(value = "/getModelByMoNo")
    protected Result getModelByMoNo(@RequestBody Map<String, Object> params)  {
        String moNo = MapUtil.getStr(params, "moNo");
        String result= HttpUtil.get("http://192.168.50.97:8004/public/GetProjectDetailByProjectId?projectId="+moNo);
        Map map = JSON.parseObject(result, Map.class);
        if (MapUtil.getInt(map, "result")==0){
            return Result.failed(MapUtil.getStr(map, "msg"));
        }
        String projectDetail = MapUtil.getStr(map, "projectDetail");
        Map map1 = JSON.parseObject(projectDetail, Map.class);
        String modelCode = MapUtil.getStr(map1, "itemCode");
        List<Map<String, Object>> itemTypeInfos=spsEngFixDetailInfoService.selItemTypeInfosByModelCode(modelCode);
        map.clear();
        map.put("moNumber", MapUtil.getStr(map1, "projectId"));
        map.put("modelCode", modelCode);
        map.put("targetQty", MapUtil.getLong(map1, "productQty"));
        map.put("itemTypeInfos",itemTypeInfos);
        return Result.succeed(map,"成功");
    }
    /**
     * 新增or更新
     */
    @ApiOperation(value = "借出")
    @PostMapping("/spsEngFixReqInfo/save")
    public Result save(@RequestBody SpsEngFixReqInfoDTO spsEngFixReqInfoDTO) {
        return spsEngFixReqInfoService.saveAll(spsEngFixReqInfoDTO);
    }
    /**
     * 根据借用单号查询详情
     */
    @ApiOperation(value = "根据借用单号查询详情")
    @PostMapping("/spsEngFixReqInfo/selAllByReqNo")
    public Result selAllByCheckNo(@RequestBody Map<String, Object> params) {
        String reqNo = MapUtil.getStr(params, "reqNo");
        SpsEngFixReqInfo reqInfo = spsEngFixReqInfoService.getOne(new QueryWrapper<SpsEngFixReqInfo>()
                .eq("req_no", reqNo)
        );
        Map<String, Object> map = BeanUtil.beanToMap(reqInfo);
        List<Map<String, Object>> spsEngFixReqDetailInfos = spsEngFixReqDetailInfoService.getReqDetail(reqNo);
        map.put("spsEngFixReqDetailInfos",spsEngFixReqDetailInfos);
        return Result.succeed(map, "查询成功");
    }

    @ApiOperation(value = "归还")
    @PostMapping("/spsEngFixReqInfo/close")
    public Result close(@RequestBody SpsEngFixReqInfoDTO spsEngFixReqInfoDTO) {
        return spsEngFixReqInfoService.close(spsEngFixReqInfoDTO);
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/spsEngFixReqInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SpsEngFixReqInfo>> map) {
        List<SpsEngFixReqInfo> models = map.get("models");
        spsEngFixReqInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/spsEngFixReqInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        for (Long id : ids) {
            SpsEngFixReqInfo fixReqInfo = spsEngFixReqInfoService.getById(id);
            String reqNo = fixReqInfo.getReqNo();
            spsEngFixReqDetailInfoService.remove(new LambdaQueryWrapper<SpsEngFixReqDetailInfo>()
                    .eq(SpsEngFixReqDetailInfo::getReqNo,reqNo)
            );
        }
        spsEngFixReqInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/spsEngFixReqInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SpsEngFixReqInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SpsEngFixReqInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        spsEngFixReqInfoService.save(u);
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
    @PostMapping("/spsEngFixReqInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SpsEngFixReqInfo> spsEngFixReqInfos =new ArrayList<>();
        List<SpsEngFixReqInfo> spsEngFixReqInfoList = spsEngFixReqInfoService.list(new QueryWrapper<SpsEngFixReqInfo>().eq("cu_id", cuId));
        if (spsEngFixReqInfoList.isEmpty()) {spsEngFixReqInfos.add(spsEngFixReqInfoService.getById(0)); } else {
            for (SpsEngFixReqInfo spsEngFixReqInfo : spsEngFixReqInfoList) {
                spsEngFixReqInfos.add(spsEngFixReqInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(spsEngFixReqInfos, "工程治具借用记录表导出", "工程治具借用记录表导出", SpsEngFixReqInfo.class, "spsEngFixReqInfo.xls", response);

    }
}
