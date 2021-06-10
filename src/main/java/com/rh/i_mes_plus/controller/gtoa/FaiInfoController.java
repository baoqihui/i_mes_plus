package com.rh.i_mes_plus.controller.gtoa;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.model.gtoa.EcnDocInfo;
import com.rh.i_mes_plus.model.gtoa.FaiInfo;
import com.rh.i_mes_plus.service.gtoa.IEcnDocInfoService;
import com.rh.i_mes_plus.service.gtoa.IFaiInfoService;
import com.rh.i_mes_plus.util.BuildPath;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.util.poiUtil.PoiReportImgUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * fai
 *
 * @author hbq
 * @date 2020-11-05 20:14:46
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "fai")
@RequestMapping("fai")
public class FaiInfoController {
    @Autowired
    private IFaiInfoService faiInfoService;
    @Autowired
    private IEcnDocInfoService ecnDocInfoService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/faiInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= faiInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 根据ecnNo查询fai
     */
    @ApiOperation(value = "根据ecnNo查询fai")
    @PostMapping("/faiInfo/sel/{ecnNo}")
    public Result findUserById(@PathVariable String ecnNo) {
        FaiInfo model = faiInfoService.getOne(new QueryWrapper<FaiInfo>().eq("ecn_no",ecnNo));
        return Result.succeed(model, "查询成功");
    }

    /**
     * fai完善
     */
    @ApiOperation(value = "fai完善")
    @PostMapping("/faiInfo/save")
    public Result save(@RequestBody FaiInfo faiInfo) {
        faiInfo.setEcnImportTime(new Date());
        faiInfo.setEcnReleaseTime(new Date());
        faiInfoService.updateById(faiInfo);
        ecnDocInfoService.updateExeStateAndTimeLog(faiInfo.getEcnNo(), SysConst.EXESTATE.FAI);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/faiInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<FaiInfo>> map) {
        List<FaiInfo> models = map.get("models");
        faiInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/faiInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        faiInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/faiInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<FaiInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, FaiInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        faiInfoService.save(u);
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
    @GetMapping("/faiInfo/leadOut")
    public void leadOut(Long id, HttpServletResponse response) throws Exception {
        FaiInfo faiInfo = faiInfoService.getById(id);
        String ecnNo = faiInfo.getEcnNo();
        EcnDocInfo ecnDocInfo = ecnDocInfoService.getOne(new QueryWrapper<EcnDocInfo>().eq("ecn_no", ecnNo));
        String modelName = ecnDocInfo.getModelName();
        String ecnReleaseTime = DateUtil.format(faiInfo.getEcnReleaseTime(),"yyyy-MM-dd");
        String ecnImportTime = DateUtil.format(faiInfo.getEcnImportTime(),"yyyy-MM-dd");
        String pcbaVer = faiInfo.getPcbaVer();
        String ecnPurpose = faiInfo.getEcnPurpose();
        String mfgChanageFlag = faiInfo.getMfgChanageFlag()!=null&&faiInfo.getMfgChanageFlag()?"是":"否";
        String btfChanageFlag = faiInfo.getBtfChanageFlag()!=null&&faiInfo.getBtfChanageFlag()?"是":"否";
        String picLable = StrUtil.isEmpty(faiInfo.getPicLable())?"http://192.168.50.96/file/img/992f49907781416880e999f5b8dd0f29-占位图.png":faiInfo.getPicLable();
        String picCR =  StrUtil.isEmpty(faiInfo.getPicCR())?"http://192.168.50.96/file/img/992f49907781416880e999f5b8dd0f29-占位图.png":faiInfo.getPicLable();
        String picTop =  StrUtil.isEmpty(faiInfo.getPicTop())?"http://192.168.50.96/file/img/992f49907781416880e999f5b8dd0f29-占位图.png":faiInfo.getPicTop();
        String picBot =  StrUtil.isEmpty(faiInfo.getPicBot())?"http://192.168.50.96/file/img/992f49907781416880e999f5b8dd0f29-占位图.png":faiInfo.getPicBot();
        
        
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //边框
        /*cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.MEDIUM);*/
        //自动换行
        cellStyle.setWrapText(true);
    
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("FAI");
    
        //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        
        //设置每列宽度
        sheet.setDefaultColumnWidth(10);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 6333);
        sheet.setColumnWidth(3, 5333);
        sheet.setColumnWidth(4, 8333);
        sheet.setColumnWidth(5, 5333);
        sheet.setColumnWidth(6, 8000);
        
        /**1 start*/
        //创建行的单元格，从0开始
        HSSFRow row1 = sheet.createRow(0);
        row1.setRowStyle(cellStyle);
        row1.setHeight((short) (24*20));
        //创建单元格
        HSSFCell row1cell1=row1.createCell(0);
        //合并列
        row1cell1.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
        row1cell1.getCellStyle().setVerticalAlignment(VerticalAlignment.CENTER);
        row1cell1.setCellValue("Inspur EC Break In FAI Report");
        CellRangeAddress region=new CellRangeAddress(0, 0, 0, 6);
        sheet.addMergedRegion(region);
        /**1 end*/
    
        /**2 start */
        /*2-1*/
        HSSFRow row2 = sheet.createRow(1);
        row2.setRowStyle(cellStyle);
        row2.setHeight((short) (24*20));
        //合并行
        HSSFCell row2cell1 = row2.createCell(0);
        row2cell1.setCellValue("板卡名称及料号");
        region=new CellRangeAddress(1, 1, 0, 1);
        sheet.addMergedRegion(region);
        /*2-2*/
        //合并行
        HSSFCell row2cell2 = row2.createCell(2);
        row2cell2.setCellValue(modelName);
        region=new CellRangeAddress(1, 1, 2,4 );
        sheet.addMergedRegion(region);
        /*2-3*/
        HSSFCell row2cell3 = row2.createCell(5);
        row2cell3.setCellValue("EC 编号");
        /*2-4*/
        HSSFCell row2cell4 = row2.createCell(6);
        row2cell4.setCellValue(ecnNo);
        /**2 end */
        /**3 start */
        /*3-1*/
        HSSFRow row3 = sheet.createRow(2);
        row3.setHeight((short) (24*20));
        //合并行
        HSSFCell row3cell1 = row3.createCell(0);
        row3cell1.setCellValue("EC发布时间");
        region=new CellRangeAddress(2, 2, 0, 1);
        sheet.addMergedRegion(region);
        /*3-2*/
        HSSFCell row3cell2 = row3.createCell(2);
        row3cell2.setCellValue(ecnReleaseTime);
        /*3-3*/
        HSSFCell row3cell3 = row3.createCell(3);
        row3cell3.setCellValue("EC导入时间");
        /*3-4*/
        HSSFCell row3cell4 = row3.createCell(4);
        row3cell4.setCellValue(ecnImportTime);
        /*3-5*/
        HSSFCell row3cell5 = row3.createCell(5);
        row3cell5.setCellValue("变更后PCBA版本");
        /*3-6*/
        HSSFCell row3cell6 = row3.createCell(6);
        row3cell6.setCellValue(pcbaVer);
        /**3 end */
        /**4 start*/
        /*4-1*/
        HSSFRow row4 = sheet.createRow(3);
        row4.setHeight((short) (58*20));
        //合并行
        HSSFCell row4cell1 = row4.createCell(0);
        row4cell1.setCellValue("EC 目的/内容");
        region=new CellRangeAddress(3, 3, 0, 1);
        sheet.addMergedRegion(region);
        /*4-2*/
        //合并行
        HSSFCell row4cell2 = row4.createCell(2);
        row4cell2.setCellValue(ecnPurpose);
        region=new CellRangeAddress(3, 3, 2,6);
        sheet.addMergedRegion(region);
        /**4 end*/
        /**5 start*/
        /*5-1*/
        HSSFRow row5 = sheet.createRow(4);
        row5.setHeight((short) (126*20));
        //合并行
        HSSFCell row5cell1 = row5.createCell(0);
        row5cell1.setCellValue("条码图片");
        region=new CellRangeAddress(4, 4, 0, 1);
        sheet.addMergedRegion(region);
        /*5-2*/
        //合并行
        HSSFCell row5cell2 = row5.createCell(2);
        row5cell2.setCellValue("picLable");
        region=new CellRangeAddress(4, 4, 2,6);
        sheet.addMergedRegion(region);
        
        PoiReportImgUtil.reportExeclImg(workbook,patriarch,picLable,(short)2,4,(short)6,4);
        /**6 end*/
        /**6 start*/
        /*6-1*/
        HSSFRow row6 = sheet.createRow(5);
        row6.setHeight((short) (126*20));
        //合并行
        HSSFCell row6cell1 = row6.createCell(0);
        row6cell1.setCellValue("3C认证及RHOS图片");
        region=new CellRangeAddress(5, 5, 0, 1);
        sheet.addMergedRegion(region);
        /*6-2*/
        //合并行
        HSSFCell row6cell2 = row6.createCell(2);
        row6cell2.setCellValue("picLable2");
        region=new CellRangeAddress(5, 5, 2,6);
        sheet.addMergedRegion(region);
        //添加图片
        PoiReportImgUtil.reportExeclImg(workbook,patriarch,picCR,(short)2,5,(short)6,5);
        /**6 end*/
        /**7 start*/
        /*7-1*/
        HSSFRow row7 = sheet.createRow(6);
        row7.setHeight((short) (135*20));
        //合并行
        HSSFCell row7cell1 = row7.createCell(0);
        row7cell1.setCellValue("整体图片");
        region=new CellRangeAddress(6, 6, 0, 1);
        sheet.addMergedRegion(region);
        /*7-2*/
        //合并行
        HSSFCell row7cell2 = row7.createCell(2);
        row7cell2.setCellValue("picLable3");
        region=new CellRangeAddress(6, 6, 2,6);
        sheet.addMergedRegion(region);
        
        PoiReportImgUtil.reportExeclImg(workbook,patriarch,picTop,(short)2,6,(short)6,6);
        PoiReportImgUtil.reportExeclImg(workbook,patriarch,picBot,(short)7,6,(short)7,6);
    
        /**7 end*/
        /**8 start*/
        /*8-1*/
        HSSFRow row8 = sheet.createRow(7);
        row8.setHeight((short) (135*20));
        //合并行
        HSSFCell row8cell1 = row8.createCell(0);
        row8cell1.setCellValue("侧面局部图片");
        region=new CellRangeAddress(7, 7, 0, 1);
        sheet.addMergedRegion(region);
        /*7-2*/
        //合并行
        HSSFCell row8cell2 = row8.createCell(2);
        row8cell2.setCellValue("picLable4");
        region=new CellRangeAddress(7, 7, 2,6);
        sheet.addMergedRegion(region);
        String picsAdd =  faiInfo.getPicsAdd();
        PoiReportImgUtil.reportExeclImg(workbook,patriarch,StrUtil.isEmpty(picsAdd)?"http://192.168.50.96/file/img/992f49907781416880e999f5b8dd0f29-占位图.png":picsAdd.split(",")[0],(short)2,7,(short)6,7);
        if (!StrUtil.isEmpty(picsAdd)){
            String[] split = picsAdd.split(",");
            for (int i=1;i<split.length;i++){
                PoiReportImgUtil.reportExeclImg(workbook,patriarch,split[i],(short)(6+i),7,(short)(6+i),7);
            }
        }
        /**8 end*/
        /**9 start*/
        /*9-1*/
        HSSFRow row9 = sheet.createRow(8);
        row9.setHeight((short) (31*20));
        //合并行
        HSSFCell row9cell1 = row9.createCell(0);
        row9cell1.setCellValue("生产治具/工艺程式是否变更");
        region=new CellRangeAddress(8, 8, 0, 1);
        sheet.addMergedRegion(region);
        /*9-2*/
        //合并行
        HSSFCell row9cell2 = row9.createCell(2);
        row9cell2.setCellValue(mfgChanageFlag);
        region=new CellRangeAddress(8, 8, 2,6);
        sheet.addMergedRegion(region);
        /**9 end*/
        /**10 start*/
        /*10-1*/
        HSSFRow row10 = sheet.createRow(9);
        row10.setHeight((short) (31*20));
        //合并行
        HSSFCell row10cell1 = row10.createCell(0);
        row10cell1.setCellValue("BFT测试程式是否变更");
        region=new CellRangeAddress(9, 9, 0, 1);
        sheet.addMergedRegion(region);
        /*10-2*/
        //合并行
        HSSFCell row10cell2 = row10.createCell(2);
        row10cell2.setCellValue(btfChanageFlag);
        region=new CellRangeAddress(9, 9, 2,6);
        sheet.addMergedRegion(region);
        /**10 end*/
    
        /**11 start*/
        /*11-1*/
        HSSFRow row11 = sheet.createRow(11);
        row11.setHeight((short) (31*20));
        //合并行
        HSSFCell row11cell1 = row11.createCell(0);
        row11cell1.setCellValue("Approved By:");
        /*11-2*/
        //合并行
        HSSFCell row11cell2 = row11.createCell(3);
        row11cell2.setCellValue("Checked By:");
        /*11-3*/
        //合并行
        HSSFCell row11cell3 = row11.createCell(5);
        row11cell3.setCellValue("Inspected By:");
        /**11 end*/
        /** 导出图片 */
    
        //文档输出
        OutputStream out= BuildPath.Manual_Saving(response, modelName+"FAI.xls");
        workbook.write(out);
        out.close();
    }
    
    
    
}
