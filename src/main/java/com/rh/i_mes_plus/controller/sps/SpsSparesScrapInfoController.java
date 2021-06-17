package com.rh.i_mes_plus.controller.sps;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.model.sps.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报废信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "报废信息表")
@RequestMapping("sps")
public class SpsSparesScrapInfoController {
    @Autowired
    private ISpsSparesScrapInfoService spsSparesScrapInfoService;
    @Autowired
    private IApprovalProcessService approvalProcessService;
    @Autowired
    private ISpsEngFixDetailInfoService spsEngFixDetailInfoService;
    @Autowired
    private IGzkPartsDetailInfoService gzkPartsDetailInfoService;
    @Autowired
    private IGzkFixDetailInfoService gzkFixDetailInfoService;
    @Autowired
    private IMfgStencilsDetailInfoService mfgStencilsDetailInfoService;
    @Autowired
    private ISpsFeederInfoService spsFeederInfoService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/spsSparesScrapInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= spsSparesScrapInfoService.findList(params);
        List<Map> records = list.getRecords();
        List<Map> records1=new ArrayList<>();
        for (Map record : records) {
            String state = MapUtil.getStr(record, "state");
            if (Convert.toLong(state)>0){
                Map<String, Object> p=new HashMap<>();
                p.put("typeCode","A");
                p.put("operType","BF");
                p.put("streamNo",1);
                p.put("nodeNo",state);
                ApprovalProcess model = approvalProcessService.getOperUsrNo(p);
                if (model!=null){
                    record.put("nodeNo",model.getNodeNo());
                    record.put("operUsrNo",model.getOperUsrNo());
                }else {
                    record.put("nodeNo","");
                    record.put("operUsrNo","");
                }
            }else{
                record.put("nodeNo","");
                record.put("operUsrNo","");
            }
            records1.add(record);
        }
        list.setRecords(records1);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/spsSparesScrapInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SpsSparesScrapInfo model = spsSparesScrapInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 报废单新建，审核
     */
    @ApiOperation(value = "报废单新建，审核")
    @PostMapping("/spsSparesScrapInfo/save")
    public Result save(@RequestBody SpsSparesScrapInfo spsSparesScrapInfo) {
        SpsSparesScrapInfo existSpsSparesScrapInfo = spsSparesScrapInfoService.getById(spsSparesScrapInfo.getId());
        String typeCode="";
        String sparesNo="";
        if (existSpsSparesScrapInfo==null){
            typeCode=spsSparesScrapInfo.getTypeCode();
            sparesNo=spsSparesScrapInfo.getSparesNo();
        }else {
            typeCode = existSpsSparesScrapInfo.getTypeCode();
            sparesNo = existSpsSparesScrapInfo.getSparesNo();
        }
        System.out.println(sparesNo);
        //审批完成时根据类别修改备品状态
        if ("4".equals(spsSparesScrapInfo.getState())){
            //工程治具
            if (SysConst.TYPE_CODE.GCZJ.equals(typeCode)){
                spsEngFixDetailInfoService.update(new LambdaUpdateWrapper<SpsEngFixDetailInfo>()
                        .eq(SpsEngFixDetailInfo::getFixNo,sparesNo)
                        .set(SpsEngFixDetailInfo::getState,SysConst.FIX_STATE.BF)
                );
            }
            //工装备品
            if (SysConst.TYPE_CODE.GZBP.equals(typeCode)){
                gzkPartsDetailInfoService.update(new LambdaUpdateWrapper<GzkPartsDetailInfo>()
                        .eq(GzkPartsDetailInfo::getKgtNo,sparesNo)
                        .set(GzkPartsDetailInfo::getLoc,"3")
                        .set(GzkPartsDetailInfo::getState,SysConst.FIX_STATE.BF)
                );
            }
            //工装治具
            if (SysConst.TYPE_CODE.GZZJ.equals(typeCode)){
                gzkFixDetailInfoService.update(new LambdaUpdateWrapper<GzkFixDetailInfo>()
                        .eq(GzkFixDetailInfo::getFixNo,sparesNo)
                        .set(GzkFixDetailInfo::getState,SysConst.FIX_STATE.BF)
                );
            }
            //钢网
            if (SysConst.TYPE_CODE.GW.equals(typeCode)){
                mfgStencilsDetailInfoService.update(new LambdaUpdateWrapper<MfgStencilsDetailInfo>()
                        .eq(MfgStencilsDetailInfo::getStencilNo,sparesNo)
                        .set(MfgStencilsDetailInfo::getState,SysConst.FIX_STATE.BF)
                );
            }
            //料枪
            if (SysConst.TYPE_CODE.LQ.equals(typeCode)){
                spsFeederInfoService.update(new LambdaUpdateWrapper<SpsFeederInfo>()
                        .eq(SpsFeederInfo::getFeederSn,sparesNo)
                        .set(SpsFeederInfo::getState,SysConst.FIX_STATE.BF)
                );
            }
        }
        spsSparesScrapInfoService.saveOrUpdate(spsSparesScrapInfo);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/spsSparesScrapInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SpsSparesScrapInfo>> map) {
        List<SpsSparesScrapInfo> models = map.get("models");
        spsSparesScrapInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/spsSparesScrapInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        spsSparesScrapInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }

    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/spsSparesScrapInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SpsSparesScrapInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SpsSparesScrapInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                    spsSparesScrapInfoService.save(u);
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
    @PostMapping("/spsSparesScrapInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SpsSparesScrapInfo> spsSparesScrapInfos =new ArrayList<>();
        List<SpsSparesScrapInfo> spsSparesScrapInfoList = spsSparesScrapInfoService.list(new QueryWrapper<SpsSparesScrapInfo>().eq("cu_id", cuId));
        if (spsSparesScrapInfoList.isEmpty()) {spsSparesScrapInfos.add(spsSparesScrapInfoService.getById(0)); } else {
            for (SpsSparesScrapInfo spsSparesScrapInfo : spsSparesScrapInfoList) {
                spsSparesScrapInfos.add(spsSparesScrapInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(spsSparesScrapInfos, "报废信息表导出", "报废信息表导出", SpsSparesScrapInfo.class, "spsSparesScrapInfo.xls", response);

    }
}
