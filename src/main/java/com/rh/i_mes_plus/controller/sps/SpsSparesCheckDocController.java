package com.rh.i_mes_plus.controller.sps;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SpsSparesCheckDocDTO;
import com.rh.i_mes_plus.model.sps.ApprovalProcess;
import com.rh.i_mes_plus.model.sps.SpsSparesCheckDetail;
import com.rh.i_mes_plus.model.sps.SpsSparesCheckDoc;
import com.rh.i_mes_plus.service.sps.IApprovalProcessService;
import com.rh.i_mes_plus.service.sps.ISpsSparesCheckDetailService;
import com.rh.i_mes_plus.service.sps.ISpsSparesCheckDocService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 盘点单信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "盘点单信息表")
@RequestMapping("sps")
public class SpsSparesCheckDocController {
    @Autowired
    private ISpsSparesCheckDocService spsSparesCheckDocService;
    @Autowired
    @Lazy
    private ISpsSparesCheckDetailService spsSparesCheckDetailService;
    @Autowired
    @Lazy
    private IApprovalProcessService approvalProcessService;
    /**
     * 查询新的盘点单号
     */
    @ApiOperation(value = "查询新的盘点单号")
    @PostMapping("/spsSparesCheckDoc/getDocNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return spsSparesCheckDocService.getDocNo(map);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/spsSparesCheckDoc/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= spsSparesCheckDocService.findList(params);
        List<Map> records = list.getRecords();
        List<Map> records1=new ArrayList<>();
        for (Map record : records) {
            String state = MapUtil.getStr(record, "state");
            if (Convert.toLong(state)>0){
                Map<String, Object> p=new HashMap<>();
                p.put("typeCode","A");
                p.put("operType","PD");
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

    @ApiOperation(value = "盘点")
    @PostMapping("/spsSparesCheckDoc/check")
    public Result check(@RequestBody SpsSparesCheckDocDTO spsSparesCheckDocDTO) {
        return spsSparesCheckDocService.close(spsSparesCheckDocDTO);
    }
    /**
     * 根据盘点单号查询详情
     */
    @ApiOperation(value = "根据盘点单号查询详情")
    @PostMapping("/spsSparesCheckDoc/selAllByCheckNo")
    public Result selAllByCheckNo(@RequestBody Map<String, Object> params) {
        String checkNo = MapUtil.getStr(params, "checkNo");
        SpsSparesCheckDocDTO spsSparesCheckDocDTO = new SpsSparesCheckDocDTO();
        SpsSparesCheckDoc spsSparesCheckDoc = spsSparesCheckDocService.getOne(new QueryWrapper<SpsSparesCheckDoc>()
                .eq("check_no",checkNo)
        );
        List<SpsSparesCheckDetail> sparesCheckDetails = spsSparesCheckDetailService.list(new QueryWrapper<SpsSparesCheckDetail>()
                .eq("check_no", checkNo)
        );
        BeanUtil.copyProperties(spsSparesCheckDoc,spsSparesCheckDocDTO);
        spsSparesCheckDocDTO.setSpsSparesCheckDetails(sparesCheckDetails);
        return Result.succeed(spsSparesCheckDocDTO, "查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/spsSparesCheckDoc/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SpsSparesCheckDoc model = spsSparesCheckDocService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/spsSparesCheckDoc/save")
    public Result save(@RequestBody SpsSparesCheckDocDTO spsSparesCheckDocDTO) {
        SpsSparesCheckDoc spsSparesCheckDoc=spsSparesCheckDocDTO;
        spsSparesCheckDocService.saveOrUpdate(spsSparesCheckDoc);
        String checkNo = spsSparesCheckDoc.getCheckNo();
        List<SpsSparesCheckDetail> spsSparesCheckDetails = spsSparesCheckDocDTO.getSpsSparesCheckDetails();
        spsSparesCheckDetailService.remove(new QueryWrapper<SpsSparesCheckDetail>().eq("check_no",spsSparesCheckDoc.getCheckNo()));
        spsSparesCheckDetails.forEach(u->u.setCheckNo(checkNo));
        spsSparesCheckDetailService.saveBatch(spsSparesCheckDetails);
        return Result.succeed("保存成功");
    }

    /**
     * 审核
     */
    @ApiOperation(value = "审核")
    @PostMapping("/spsSparesCheckDoc/update")
    public Result update(@RequestBody SpsSparesCheckDoc spsSparesCheckDoc) {
        spsSparesCheckDocService.updateById(spsSparesCheckDoc);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/spsSparesCheckDoc/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SpsSparesCheckDoc>> map) {
        List<SpsSparesCheckDoc> models = map.get("models");
        spsSparesCheckDocService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/spsSparesCheckDoc/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        for (Long id : ids) {
            SpsSparesCheckDoc checkDoc = spsSparesCheckDocService.getById(id);
            String checkNo = checkDoc.getCheckNo();
            spsSparesCheckDetailService.remove(new LambdaQueryWrapper<SpsSparesCheckDetail>()
                .eq(SpsSparesCheckDetail::getCheckNo,checkNo)
            );
        }
        spsSparesCheckDocService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/spsSparesCheckDoc/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SpsSparesCheckDoc> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SpsSparesCheckDoc.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        spsSparesCheckDocService.save(u);
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
    @PostMapping("/spsSparesCheckDoc/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SpsSparesCheckDoc> spsSparesCheckDocs =new ArrayList<>();
        List<SpsSparesCheckDoc> spsSparesCheckDocList = spsSparesCheckDocService.list(new QueryWrapper<SpsSparesCheckDoc>().eq("cu_id", cuId));
        if (spsSparesCheckDocList.isEmpty()) {spsSparesCheckDocs.add(spsSparesCheckDocService.getById(0)); } else {
            for (SpsSparesCheckDoc spsSparesCheckDoc : spsSparesCheckDocList) {
                spsSparesCheckDocs.add(spsSparesCheckDoc);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(spsSparesCheckDocs, "盘点单信息表导出", "盘点单信息表导出", SpsSparesCheckDoc.class, "spsSparesCheckDoc.xls", response);

    }
}
