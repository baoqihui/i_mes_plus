package com.rh.i_mes_plus.controller.sps;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.model.sps.GzkFixDetailInfo;
import com.rh.i_mes_plus.model.sps.GzkFixReqInfo;
import com.rh.i_mes_plus.service.sps.IGzkFixDetailInfoService;
import com.rh.i_mes_plus.service.sps.IGzkFixReqInfoService;
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
 * 工装治具借用记录表
 *
 * @author hbq
 * @date 2021-03-09 10:59:40
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工装治具借用记录表")
@RequestMapping("gzk")
public class GzkFixReqInfoController {
    @Autowired
    private IGzkFixReqInfoService gzkFixReqInfoService;
    @Autowired
    @Lazy
    private IGzkFixDetailInfoService gzkFixDetailInfoService;
    /**
     * 查询新的借用单号
     */
    @ApiOperation(value = "查询新的借用单号")
    @PostMapping("/gzkFixReqInfo/getDocNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return gzkFixReqInfoService.getDocNo(map);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/gzkFixReqInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= gzkFixReqInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/gzkFixReqInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        GzkFixReqInfo model = gzkFixReqInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    @ApiOperation(value = "借出")
    @PostMapping("/gzkFixReqInfo/save")
    public Result save(@RequestBody GzkFixReqInfo gzkFixReqInfo) {
        GzkFixDetailInfo fixDetailInfo = gzkFixDetailInfoService.getOne(new LambdaQueryWrapper<GzkFixDetailInfo>()
                .eq(GzkFixDetailInfo::getFixNo, gzkFixReqInfo.getFixNo())
                .eq(GzkFixDetailInfo::getState, 1)
        );
        if (fixDetailInfo == null) {
            return Result.failed("备品不存在或已借出");
        }
        gzkFixReqInfoService.saveOrUpdate(gzkFixReqInfo);
        return Result.succeed("保存成功");
    }
    /**
     * 借出
     */
    @ApiOperation(value = "借出")
    @PostMapping("/gzkFixReqInfo/req")
    public Result req(@RequestBody GzkFixReqInfo gzkFixReqInfo) {
        gzkFixReqInfoService.updateById(gzkFixReqInfo);
        gzkFixDetailInfoService.update(new LambdaUpdateWrapper<GzkFixDetailInfo>()
                .eq(GzkFixDetailInfo::getFixNo,gzkFixReqInfo.getFixNo())
                .set(GzkFixDetailInfo::getState, SysConst.FIX_STATE.JC)
        );
        return Result.succeed("保存成功");
    }
    /**
     * 归还
     */
    @ApiOperation(value = "归还")
    @PostMapping("/gzkFixReqInfo/close")
    public Result close(@RequestBody GzkFixReqInfo gzkFixReqInfo) {
        return gzkFixReqInfoService.close(gzkFixReqInfo);
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/gzkFixReqInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<GzkFixReqInfo>> map) {
        List<GzkFixReqInfo> models = map.get("models");
        gzkFixReqInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/gzkFixReqInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        gzkFixReqInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/gzkFixReqInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<GzkFixReqInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, GzkFixReqInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        gzkFixReqInfoService.save(u);
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
    @PostMapping("/gzkFixReqInfo/leadOut")
    public void leadOut(HttpServletResponse response) throws IOException {
        List<GzkFixReqInfo> gzkFixReqInfos =new ArrayList<>();
        List<GzkFixReqInfo> gzkFixReqInfoList = gzkFixReqInfoService.list(new QueryWrapper<GzkFixReqInfo>());
        if (gzkFixReqInfoList.isEmpty()) {gzkFixReqInfos.add(gzkFixReqInfoService.getById(0)); } else {
            for (GzkFixReqInfo gzkFixReqInfo : gzkFixReqInfoList) {
                gzkFixReqInfos.add(gzkFixReqInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(gzkFixReqInfos, "工装治具借用记录表导出", "工装治具借用记录表导出", GzkFixReqInfo.class, "gzkFixReqInfo.xls", response);

    }
}
