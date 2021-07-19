package com.rh.i_mes_plus.controller.sps;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.model.sps.GzkPartsDetailInfo;
import com.rh.i_mes_plus.model.sps.GzkPartsReqInfo;
import com.rh.i_mes_plus.service.sps.IGzkPartsDetailInfoService;
import com.rh.i_mes_plus.service.sps.IGzkPartsReqInfoService;
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
 * 工装备品借用记录表
 *
 * @author hbq
 * @date 2021-02-21 10:39:50
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工装备品借用记录表")
@RequestMapping("gzk")
public class GzkPartsReqInfoController {
    @Autowired
    private IGzkPartsReqInfoService gzkPartsReqInfoService;
    @Autowired
    @Lazy
    private IGzkPartsDetailInfoService gzkPartsDetailInfoService;
    /**
     * 查询新的借用单号
     */
    @ApiOperation(value = "查询新的借用单号")
    @PostMapping("/gzkPartsReqInfo/getDocNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return gzkPartsReqInfoService.getDocNo(map);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/gzkPartsReqInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= gzkPartsReqInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/gzkPartsReqInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        GzkPartsReqInfo model = gzkPartsReqInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    @ApiOperation(value = "新建保存")
    @PostMapping("/gzkPartsReqInfo/save")
    public Result save(@RequestBody GzkPartsReqInfo gzkPartsReqInfo) {
        gzkPartsReqInfoService.saveOrUpdate(gzkPartsReqInfo);
        return Result.succeed("保存成功");
    }
    /**
     * 借出
     */
    @ApiOperation(value = "借出")
    @PostMapping("/gzkPartsReqInfo/req")
    public Result req(@RequestBody GzkPartsReqInfo gzkPartsReqInfo) {
        GzkPartsDetailInfo info = gzkPartsDetailInfoService.getOne(new LambdaQueryWrapper<GzkPartsDetailInfo>()
                .eq(GzkPartsDetailInfo::getKgtNo, gzkPartsReqInfo.getKgtNo())
        );
        if (info==null){
            return Result.failed("无此备品");
        }
        if (!SysConst.FIX_STATE.ZK.equals(info.getState())){
            return Result.failed("备品非在库状态");
        }
        gzkPartsReqInfoService.updateById(gzkPartsReqInfo);
        //更改备品状态
        info.setLoc("4");
        info.setState(SysConst.FIX_STATE.JC);
        gzkPartsDetailInfoService.updateById(info);

        return Result.succeed("保存成功");
    }
    /**
     * 归还
     */
    @ApiOperation(value = "归还")
    @PostMapping("/gzkPartsReqInfo/close")
    public Result close(@RequestBody GzkPartsReqInfo gzkPartsReqInfo) {
        return gzkPartsReqInfoService.close(gzkPartsReqInfo);
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/gzkPartsReqInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<GzkPartsReqInfo>> map) {
        List<GzkPartsReqInfo> models = map.get("models");
        gzkPartsReqInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/gzkPartsReqInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        gzkPartsReqInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/gzkPartsReqInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<GzkPartsReqInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, GzkPartsReqInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        gzkPartsReqInfoService.save(u);
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
    @PostMapping("/gzkPartsReqInfo/leadOut")
    public void leadOut(HttpServletResponse response) throws IOException {
        List<GzkPartsReqInfo> gzkPartsReqInfos =new ArrayList<>();
        List<GzkPartsReqInfo> gzkPartsReqInfoList = gzkPartsReqInfoService.list(new QueryWrapper<GzkPartsReqInfo>());
        if (gzkPartsReqInfoList.isEmpty()) {gzkPartsReqInfos.add(gzkPartsReqInfoService.getById(0)); } else {
            for (GzkPartsReqInfo gzkPartsReqInfo : gzkPartsReqInfoList) {
                gzkPartsReqInfos.add(gzkPartsReqInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(gzkPartsReqInfos, "工装备品借用记录表导出", "工装备品借用记录表导出", GzkPartsReqInfo.class, "gzkPartsReqInfo.xls", response);

    }
}
