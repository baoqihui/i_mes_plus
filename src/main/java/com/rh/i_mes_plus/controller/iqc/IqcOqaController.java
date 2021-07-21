package com.rh.i_mes_plus.controller.iqc;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqa;
import com.rh.i_mes_plus.model.iqc.IqcOqaBath;
import com.rh.i_mes_plus.model.iqc.IqcOqaItem;
import com.rh.i_mes_plus.model.iqc.IqcOqaSingleValue;
import com.rh.i_mes_plus.model.sms.SmsWmsStockInfo;
import com.rh.i_mes_plus.service.iqc.IIqcOqaBathService;
import com.rh.i_mes_plus.service.iqc.IIqcOqaItemService;
import com.rh.i_mes_plus.service.iqc.IIqcOqaService;
import com.rh.i_mes_plus.service.iqc.IIqcOqaTestLevelService;
import com.rh.i_mes_plus.service.sms.ISmsWmsStockInfoService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 检验单
 *
 * @author hbq
 * @date 2020-10-21 14:29:19
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "检验单")
@RequestMapping("iqc")
public class IqcOqaController {
    @Autowired
    private IIqcOqaService iqcOqaService;
    @Autowired
    private IIqcOqaTestLevelService iqcOqaTestLevelService;
    @Autowired
    private IIqcOqaItemService iqcOqaItemService;
    @Autowired
    private IIqcOqaBathService iqcOqaBathService;
    @Autowired
    private ISmsWmsStockInfoService smsWmsStockInfoService;
    /**
     * 回传后新建检验单
     */
    @ApiOperation(value = "回传后新建检验单")
    @PostMapping("/iqcOqa/saveOqaAndBath")
    public Result saveOqaAndBath(@RequestBody Map<String,Object> map) {
        return iqcOqaService.saveOqaAndBath(map);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqa/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= iqcOqaService.findList(params);
        List<Map> records = list.getRecords();
        for (Map record : records) {
            String oqcNo = MapUtil.getStr(record, "oqcNo");
            List<IqcOqaItem> iqcOqaItems = iqcOqaItemService.list(new QueryWrapper<IqcOqaItem>().eq("oqc_no", oqcNo));
            Boolean hasTestLevel = false;
            if(iqcOqaItems.size()>0){
                hasTestLevel=true;
            }
            record.put("hasTestLevel",hasTestLevel);
        }
        list.setRecords(records);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询检验单
     */
    @ApiOperation(value = "查询检验单")
    @PostMapping("/iqcOqa/checkList/{oqcNo}")
    public Result checkList(@PathVariable String oqcNo) {
        return iqcOqaService.checkList(oqcNo);
    }
    /**
     * 查询物料详情（等同检验或审核中的抽样信息中送检批次）
     */
    @ApiOperation(value = "查询物料详情（等同检验或审核中的抽样信息中送检批次）")
    @PostMapping("/iqcOqa/oqaBath/{oqcNo}")
    public Result oqaBath(@PathVariable String oqcNo) {
        return iqcOqaService.oqaBath(oqcNo);
    }
    /**
     * 查询物料详情
     */
    @ApiOperation(value = "查询检验历史")
    @PostMapping("/iqcOqa/oqaHistory/{oqcNo}")
    public Result oqaHistory(@PathVariable String oqcNo) {
        return iqcOqaService.oqaHistory(oqcNo);
    }
    /**
     * 查询抽样明细
     */
    @ApiOperation(value = "查询抽样明细")
    @PostMapping("/iqcOqa/oqaSingle/{oqcNo}")
    public Result oqaSingle(@PathVariable String oqcNo) {
        return iqcOqaService.oqaSingle(oqcNo);
    }

    @ApiOperation(value = "生成方案")
    @PostMapping("/iqcOqa/createProject/{oqcNo}")
    public Result createProject(@PathVariable String oqcNo) {
        return iqcOqaService.createProject(oqcNo);
    }

    /**
     * 查询检验项目
     */
    @ApiOperation(value = "查询检验项目")
    @PostMapping("/iqcOqa/oqaSingleValue")
    public Result oqaSingleValue( @RequestBody Map<String, Object> params) {
        return iqcOqaService.oqaSingleValue(params);
    }

    /**
     * 抽样样本生成
     */
    @ApiOperation(value = "抽样样本生成")
    @PostMapping("/iqcOqa/createSample")
    public Result createSample(@RequestBody Map<String,Object> map) {
        return iqcOqaService.createSample(map);
    }
    /**
     * 检验 - 抽样信息- 抽样样本删除
     */
    @ApiOperation(value = "检验 - 抽样信息- 抽样样本删除")
    @PostMapping("/iqcOqa/removeSample")
    public Result removeSample(@RequestBody Map<String,List<Long>> map) {
        return iqcOqaService.removeSample(map);
    }

    /**
     * 检验 - 抽样信息- 检验项目修改
     */
    @ApiOperation(value = "检验 - 抽样信息- 检验项目修改")
    @PostMapping("/iqcOqa/updateSingleValue")
    public Result updateSingleValue(@RequestBody Map<String,List<IqcOqaSingleValue>> map) {
        return iqcOqaService.updateSingleValue(map);
    }
    /**
     * 检验完成
     */
    @ApiOperation(value = "检验完成")
    @PostMapping("/iqcOqa/inspection")
    public Result inspection(@RequestBody IqcOqa iqcOqa) {
        String oqcNo = iqcOqa.getOqcNo();
        IqcOqa one = iqcOqaService.getOne(new QueryWrapper<IqcOqa>().eq("oqc_no", oqcNo));
        if (one.getOqcTestAmount()<one.getOqcSampleAmount()){
            return Result.failed("抽检数量应大于应抽数量");
        }
        List<IqcOqaBath> oqaBaths = iqcOqaBathService.list(new QueryWrapper<IqcOqaBath>().eq("oqc_no", oqcNo));
        boolean barcodeFlag = false;
        for (IqcOqaBath oqaBath : oqaBaths) {
            String serialNumber = oqaBath.getSerialNumber();
            SmsWmsStockInfo stockInfo = smsWmsStockInfoService.getOne(new QueryWrapper<SmsWmsStockInfo>()
                    .eq("tbl_barcode", serialNumber)
            );
            String stockFlag = stockInfo.getStockFlag();
            if ("2".equals(stockFlag)) barcodeFlag=true;
        }
        if (barcodeFlag){
            return Result.failed("检验单中物料未在库");
        }
        iqcOqaService.update(new UpdateWrapper<IqcOqa>().eq("oqc_no",oqcNo)
                .set("oqc_result_date",new Date())
                .set("oqc_status",iqcOqa.getOqcStatus())
                .set("oqc_examiner",iqcOqa.getOqcExaminer())
        );
        return Result.succeed("已保存");
    }
    /**
     * 审核
     */
    @ApiOperation(value = "审核")
    @PostMapping("/iqcOqa/audit")
    public Result audit(@RequestBody IqcOqa iqcOqa) {
        String oqcNo = iqcOqa.getOqcNo();
        Integer oqcResult = iqcOqa.getOqcResult();
        Long okNum=0L;
        List<IqcOqaBath> oqaBathList = iqcOqaBathService.list(new QueryWrapper<IqcOqaBath>().eq("oqc_no", oqcNo));
        //允收
        if (oqcResult==1){
            if (oqaBathList.size()>0){
                for (IqcOqaBath iqcOqaBath : oqaBathList) {
                    String serialNumber = iqcOqaBath.getSerialNumber();
                    smsWmsStockInfoService.update(new UpdateWrapper<SmsWmsStockInfo>()
                            .eq("TBL_BARCODE",serialNumber)
                            .set("have_check","Y")
                            .set("WH_CODE","W-M-MPA")
                    );
                    okNum=iqcOqaBath.getOsAmount()+okNum;
                }
            }
        }
        //拒收
        if(oqcResult==2){
            if (oqaBathList.size()>0){
                for (IqcOqaBath iqcOqaBath : oqaBathList) {
                    String serialNumber = iqcOqaBath.getSerialNumber();
                    smsWmsStockInfoService.update(new UpdateWrapper<SmsWmsStockInfo>()
                            .eq("TBL_BARCODE",serialNumber)
                            .set("have_check","Y")
                            .set("WH_CODE","W-M-UPT")
                    );
                }
            }
        }
        //让步接收
        if (oqcResult==3){
            if (oqaBathList.size()>0){
                for (IqcOqaBath iqcOqaBath : oqaBathList) {
                    String serialNumber = iqcOqaBath.getSerialNumber();
                    smsWmsStockInfoService.update(new UpdateWrapper<SmsWmsStockInfo>()
                            .eq("TBL_BARCODE",serialNumber)
                            .set("WH_CODE","W-M-UPT")
                    );
                }
            }
        }
        iqcOqaService.update(new UpdateWrapper<IqcOqa>().eq("oqc_no",oqcNo)
                .set("oqc_audit_time",new Date())
                .set("oqc_result",iqcOqa.getOqcResult())
                .set("ok_num",okNum)
                .set("oqc_audit",iqcOqa.getOqcAudit())
                .set("oqc_status",3)
                .set("oqc_audit_remark",iqcOqa.getOqcAuditRemark())
        );
        //重工和烘烤
        IqcOqa oqa = iqcOqaService.getOne(new LambdaQueryWrapper<IqcOqa>().eq(IqcOqa::getOqcNo, oqcNo));
        String oqcAuditRemark = oqa.getOqcAuditRemark();
        if (StrUtil.contains(oqcAuditRemark,"重工")||StrUtil.contains(oqcAuditRemark,"烘烤")){
            for (IqcOqaBath iqcOqaBath : oqaBathList) {
                String serialNumber = iqcOqaBath.getSerialNumber();
                smsWmsStockInfoService.update(new UpdateWrapper<SmsWmsStockInfo>()
                        .eq("TBL_BARCODE",serialNumber)
                        .set("remark",oqcAuditRemark)
                );
            }
        }
        return Result.succeed("已保存");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcOqa/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqa model = iqcOqaService.getById(id);
        return Result.succeed(model, "查询成功");
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/iqcOqa/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<IqcOqa>> map) {
        List<IqcOqa> models = map.get("models");
        iqcOqaService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqa/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqa/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqa> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqa.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaService.save(u);
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
    @PostMapping("/iqcOqa/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqa> iqcOqas =new ArrayList<>();
        List<IqcOqa> iqcOqaList = iqcOqaService.list(new QueryWrapper<IqcOqa>().eq("cu_id", cuId));
        if (iqcOqaList.isEmpty()) {iqcOqas.add(iqcOqaService.getById(0)); } else {
            for (IqcOqa iqcOqa : iqcOqaList) {
                iqcOqas.add(iqcOqa);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqas, "检验单导出", "检验单导出", IqcOqa.class, "iqcOqa.xls", response);

    }
}
