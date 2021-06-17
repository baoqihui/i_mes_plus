package com.rh.i_mes_plus.controller.sps;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.MfgStencilsDetailInfo;
import com.rh.i_mes_plus.model.sps.MfgStencilsReqInfo;
import com.rh.i_mes_plus.service.sps.IMfgStencilsDetailInfoService;
import com.rh.i_mes_plus.service.sps.IMfgStencilsReqInfoService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 钢网借出详情表
 *
 * @author hbq
 * @date 2021-06-03 20:11:19
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "钢网借出详情表")
@RequestMapping("mfg")
public class MfgStencilsReqInfoController {
    @Autowired
    private IMfgStencilsReqInfoService mfgStencilsReqInfoService;
    @Autowired
    private IMfgStencilsDetailInfoService mfgStencilsDetailInfoService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/mfgStencilsReqInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= mfgStencilsReqInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/mfgStencilsReqInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        MfgStencilsReqInfo model = mfgStencilsReqInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/mfgStencilsReqInfo/save")
    public Result save(@RequestBody MfgStencilsReqInfo mfgStencilsReqInfo) {
        mfgStencilsReqInfoService.saveOrUpdate(mfgStencilsReqInfo);
        return Result.succeed("保存成功");
    }

    /**
     * 归还
     */
    @ApiOperation(value = "归还")
    @PostMapping("/mfgStencilsReqInfo/close")
    public Result close(@RequestBody MfgStencilsReqInfo mfgStencilsReqInfo) {
        mfgStencilsReqInfoService.updateById(mfgStencilsReqInfo);
        String stencilNo = mfgStencilsReqInfo.getStencilNo();
        MfgStencilsDetailInfo mfgStencilsDetailInfo = mfgStencilsDetailInfoService.getOne(new LambdaQueryWrapper<MfgStencilsDetailInfo>().eq(MfgStencilsDetailInfo::getStencilNo, stencilNo));
        mfgStencilsDetailInfo.setAppearanceInspection(mfgStencilsReqInfo.getReturnAppearanceInspection());
        mfgStencilsDetailInfo.setCentre(mfgStencilsReqInfo.getReturnCentre());
        mfgStencilsDetailInfo.setLeftUpper(mfgStencilsReqInfo.getReturnLeftUpper());
        mfgStencilsDetailInfo.setLeftLower(mfgStencilsReqInfo.getReturnLeftLower());
        mfgStencilsDetailInfo.setRightUpper(mfgStencilsReqInfo.getReturnRightUpper());
        mfgStencilsDetailInfo.setRightLower(mfgStencilsReqInfo.getReturnRightLower());
        mfgStencilsDetailInfo.setUsedTimes(mfgStencilsDetailInfo.getUsedTimes()+mfgStencilsReqInfo.getThisUsedTimes());
        mfgStencilsDetailInfo.setPos(mfgStencilsReqInfo.getReturnPos());
        mfgStencilsDetailInfoService.updateById(mfgStencilsDetailInfo);
        return Result.succeed("保存成功");
    }
    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/mfgStencilsReqInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<MfgStencilsReqInfo>> map) {
        List<MfgStencilsReqInfo> models = map.get("models");
        mfgStencilsReqInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/mfgStencilsReqInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        mfgStencilsReqInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/mfgStencilsReqInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<MfgStencilsReqInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, MfgStencilsReqInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        mfgStencilsReqInfoService.save(u);
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
    @PostMapping("/mfgStencilsReqInfo/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<MfgStencilsReqInfo> mfgStencilsReqInfoList = ids==null||ids.isEmpty()? mfgStencilsReqInfoService.list():(List)mfgStencilsReqInfoService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(mfgStencilsReqInfoList, "钢网借出详情表导出", "钢网借出详情表导出", MfgStencilsReqInfo.class, "mfgStencilsReqInfo.xls", response);
    }
}
