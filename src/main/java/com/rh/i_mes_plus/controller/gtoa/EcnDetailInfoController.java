package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.EcnDetailInfoDTO;
import com.rh.i_mes_plus.dto.EcnDetailInfoTwoDTO;
import com.rh.i_mes_plus.model.gtoa.EcnDetailDiscriptionInfo;
import com.rh.i_mes_plus.model.gtoa.EcnDetailInfo;
import com.rh.i_mes_plus.service.gtoa.IEcnDetailDiscriptionInfoService;
import com.rh.i_mes_plus.service.gtoa.IEcnDetailInfoService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ECN详情表
 *
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "ECN详情表")
@RequestMapping("ecn")
public class EcnDetailInfoController {
    @Autowired
    private IEcnDetailInfoService ecnDetailInfoService;
    @Autowired
    private IEcnDetailDiscriptionInfoService ecnDetailDiscriptionInfoService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/ecnDetailInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= ecnDetailInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 修改时查询
     */
    @ApiOperation(value = "修改时查询")
    @PostMapping("/ecnDetailInfo/sel/{ecnNo}")
    public Result findUserById(@PathVariable String ecnNo) {
        EcnDetailInfoDTO ecnDetailInfoDTO = new EcnDetailInfoDTO();
        EcnDetailInfo model = ecnDetailInfoService.getOne(new QueryWrapper<EcnDetailInfo>().eq("ecn_no",ecnNo));
        BeanUtils.copyProperties(model,ecnDetailInfoDTO);
        Long id = model.getId();
        List<EcnDetailDiscriptionInfo> ecnDetailDiscriptionInfos = ecnDetailDiscriptionInfoService.list(new QueryWrapper<EcnDetailDiscriptionInfo>().eq("edi_id", id));
        ecnDetailInfoDTO.setEcnDetailDiscriptionInfos(ecnDetailDiscriptionInfos);
        return Result.succeed(ecnDetailInfoDTO, "查询成功");
    }
    /**
     * 审核
     */
    @ApiOperation(value = "审核")
    @PostMapping("/ecnDetailInfo/audit")
    public Result audit(@RequestBody EcnDetailInfoTwoDTO ecnDetailInfoTwoDTO) {
        return ecnDetailInfoService.audit(ecnDetailInfoTwoDTO);
    }
    /**
     * 完善剩余时间信息
     */
    @ApiOperation(value = "完善剩余时间信息")
    @PostMapping("/ecnDetailInfo/perfect")
    public Result perfect(@RequestBody EcnDetailInfo ecnDetailInfo) {
        return ecnDetailInfoService.perfect(ecnDetailInfo);
    }
    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/ecnDetailInfo/saveOrUpdateAll")
    public Result saveOrUpdateAll(@RequestBody EcnDetailInfoDTO ecnDetailInfoDTO) {
        return ecnDetailInfoService.saveOrUpdateAll(ecnDetailInfoDTO);
    }
    /**
     * 新增or更新
     */
    @ApiOperation(value = "更新挂起状态")
    @PostMapping("/ecnDetailInfo/updateIsHangUp")
    public Result updateIsHangUp(@RequestBody EcnDetailInfo ecnDetailInfo) {
        ecnDetailInfoService.update(new UpdateWrapper<EcnDetailInfo>()
                .eq("ecn_no",ecnDetailInfo.getEcnNo())
                .set("is_hang_up",ecnDetailInfo.getIsHangUp())
        );
        return Result.succeed("更新成功");
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/ecnDetailInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<EcnDetailInfo>> map) {
        List<EcnDetailInfo> models = map.get("models");
        ecnDetailInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/ecnDetailInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        ecnDetailInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/ecnDetailInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<EcnDetailInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, EcnDetailInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        ecnDetailInfoService.save(u);
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
    @PostMapping("/ecnDetailInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<EcnDetailInfo> ecnDetailInfos =new ArrayList<>();
        List<EcnDetailInfo> ecnDetailInfoList = ecnDetailInfoService.list(new QueryWrapper<EcnDetailInfo>().eq("cu_id", cuId));
        if (ecnDetailInfoList.isEmpty()) {ecnDetailInfos.add(ecnDetailInfoService.getById(0)); } else {
            for (EcnDetailInfo ecnDetailInfo : ecnDetailInfoList) {
                ecnDetailInfos.add(ecnDetailInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(ecnDetailInfos, "ECN详情表导出", "ECN详情表导出", EcnDetailInfo.class, "ecnDetailInfo.xls", response);

    }
}
