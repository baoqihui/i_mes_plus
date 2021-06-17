package com.rh.i_mes_plus.controller.sps;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.model.sps.SpsEngFixDetailInfo;
import com.rh.i_mes_plus.service.sps.*;
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
 * 工程治具详情表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工程治具详情表")
@RequestMapping("sps")
public class SpsEngFixDetailInfoController {
    @Autowired
    private ISpsEngFixDetailInfoService spsEngFixDetailInfoService;
    @Autowired
    private IGzkPartsDetailInfoService gzkPartsDetailInfoService;
    @Autowired
    private IGzkFixDetailInfoService gzkFixDetailInfoService;
    @Autowired
    private IMfgStencilsDetailInfoService mfgStencilsDetailInfoService;
    @Autowired
    private IEngEquDetailInfoService engEquDetailInfoService;
    @Autowired
    private ISpsFeederInfoService spsFeederInfoService;
    /**
     * 列表
     */
    @ApiOperation(value = "根据typeCode查询")
    @PostMapping("/spsEngFixDetailInfo/listByTypeCode")
    public Result<PageResult> listByTypeCode(@RequestBody Map<String, Object> params) {
        String typeCode = MapUtil.getStr(params, "typeCode");
        if (StrUtil.isEmpty(typeCode)) {
            return Result.failed("请输入typeCode");
        }
        Page<Map> list = null;
        if (SysConst.TYPE_CODE.GCZJ.equals(typeCode)){
            list= spsEngFixDetailInfoService.findList(params);
        }
        if (SysConst.TYPE_CODE.GZBP.equals(typeCode)){
            list= gzkPartsDetailInfoService.findList(params);
        }
        if (SysConst.TYPE_CODE.GZZJ.equals(typeCode)){
            list= gzkFixDetailInfoService.findList(params);
        }
        if (SysConst.TYPE_CODE.GW.equals(typeCode)){
            list= mfgStencilsDetailInfoService.findList(params);
        }
        if (SysConst.TYPE_CODE.GCSB.equals(typeCode)){
            list= engEquDetailInfoService.findList(params);
        }
        if (SysConst.TYPE_CODE.LQ.equals(typeCode)){
            list= spsFeederInfoService.findList(params);
        }
        if (list==null){
            return Result.failed("未查询到数据");
        }
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/spsEngFixDetailInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= spsEngFixDetailInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/spsEngFixDetailInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SpsEngFixDetailInfo model = spsEngFixDetailInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }
    /**
     * 查询用户输入的客户治具编号是否重复
     */
    @ApiOperation(value = "查询用户输入的客户治具编号是否重复")
    @PostMapping("/spsEngFixDetailInfo/selFixNo/{fixNo}")
    public Result selFixNo(@PathVariable String fixNo) {
        SpsEngFixDetailInfo model = spsEngFixDetailInfoService.getOne(new LambdaQueryWrapper<SpsEngFixDetailInfo>()
                .eq(SpsEngFixDetailInfo::getFixNo,fixNo)
                .ne(SpsEngFixDetailInfo::getState,SysConst.FIX_STATE.BF)
        );
        if (model!=null){
            return Result.failed(model, "编号重复");
        }
        return Result.succeed(model, "编号不存在可以添加");
    }
    /**
     * 工程治具编号生成
     */
    @ApiOperation(value = "工程治具编号生成")
    @PostMapping("/spsEngFixDetailInfo/selMaxFixNo")
    public Result selMaxFixNo(@RequestBody Map<String, Object> params) {
        String fixNo = MapUtil.getStr(params, "fixNo");
        Integer num = MapUtil.getInt(params, "num");
        List<String> fixNos = new ArrayList<>();
        String prefix=fixNo+"-";
        SpsEngFixDetailInfo model = spsEngFixDetailInfoService.getOne(new QueryWrapper<SpsEngFixDetailInfo>()
                .last("where fix_no like '" + prefix + "%' order by fix_no desc limit 1")
        );
        String maxFixNo=prefix+"001";
        if (model != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(model.getFixNo(), prefix));
            maxFixNo=prefix+String.format("%3d",aLong+1).replace(" ","0");
        }
        fixNos.add(maxFixNo);
        for (int i=1;i<num;i++){
            Long aLong = Convert.toLong(StrUtil.removePrefix(maxFixNo, prefix));
            maxFixNo=prefix+String.format("%3d",aLong+1).replace(" ","0");
            fixNos.add(maxFixNo);
        }
        return Result.succeed(fixNos, "查询成功");
    }
    /**
     * 单个新增
     */
    @ApiOperation(value = "单个新增")
    @PostMapping("/spsEngFixDetailInfo/saveOne")
    public Result saveOne(@RequestBody SpsEngFixDetailInfo spsEngFixDetailInfo) {
        String fixNo = spsEngFixDetailInfo.getFixNo();
        String[] split = fixNo.split("-");
        if (split==null||split.length<=1){
            return Result.failed("输入格式不正确");
        }
        String suffix = split[split.length - 1];
        String prefix = StrUtil.removeSuffix(fixNo, suffix);
        SpsEngFixDetailInfo model = spsEngFixDetailInfoService.getOne(new QueryWrapper<SpsEngFixDetailInfo>()
                .last("where fix_no like '" + prefix + "%' order by fix_no desc limit 1")
        );
        String maxFixNo=prefix+"001";
        if (model != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(model.getFixNo(), prefix));
            maxFixNo=prefix+String.format("%3d",aLong+1).replace(" ","0");
        }
        spsEngFixDetailInfo.setFixNo(maxFixNo);
        spsEngFixDetailInfoService.save(spsEngFixDetailInfo);
        return Result.succeed("保存成功");
    }
    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增")
    @PostMapping("/spsEngFixDetailInfo/save")
    public Result save(@RequestBody SpsEngFixDetailInfo spsEngFixDetailInfo) {
        String fixNoList = spsEngFixDetailInfo.getFixNo();
        String[] split = fixNoList.split(",");
        for (String fixNo :split){
            spsEngFixDetailInfo.setFixNo(fixNo);
            spsEngFixDetailInfo.setFixNo(fixNo);
            spsEngFixDetailInfoService.save(spsEngFixDetailInfo);
        }
        return Result.succeed("保存成功");
    }
    @ApiOperation(value = "更新")
    @PostMapping("/spsEngFixDetailInfo/updateById")
    public Result updateById(@RequestBody SpsEngFixDetailInfo spsEngFixDetailInfo) {
        spsEngFixDetailInfoService.updateById(spsEngFixDetailInfo);
        return Result.succeed("保存成功");
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/spsEngFixDetailInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SpsEngFixDetailInfo>> map) {
        List<SpsEngFixDetailInfo> models = map.get("models");
        spsEngFixDetailInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/spsEngFixDetailInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        spsEngFixDetailInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/spsEngFixDetailInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SpsEngFixDetailInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SpsEngFixDetailInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        spsEngFixDetailInfoService.save(u);
                });
                return Result.succeed("成功导入信息"+rowNum+"行数据");
            }
        }
        return Result.failed("导入失败");
    }

    /**
     * 导出（传入ids数组，选择指定id）
     */
    @ApiOperation(value = "导出（传入ids数组，选择指定id）")
    @PostMapping("/spsEngFixDetailInfo/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<SpsEngFixDetailInfo> spsEngFixDetailInfoList = ids==null||ids.isEmpty()? spsEngFixDetailInfoService.list(new QueryWrapper<>()):(List)spsEngFixDetailInfoService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(spsEngFixDetailInfoList, "工程治具详情表导出", "工程治具详情表导出", SpsEngFixDetailInfo.class, "spsEngFixDetailInfo.xls", response);
    }
}
