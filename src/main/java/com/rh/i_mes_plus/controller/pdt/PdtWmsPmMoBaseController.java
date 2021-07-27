package com.rh.i_mes_plus.controller.pdt;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtWmsPmMoBase;
import com.rh.i_mes_plus.service.pdt.IPdtWmsPmMoBaseService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 指令单号表
 *
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "制令单表")
@RequestMapping("pdt")
public class PdtWmsPmMoBaseController {
    @Autowired
    private IPdtWmsPmMoBaseService pdtWmsPmMoBaseService;
    @Value("${zhaoIpAndPort}")
    private String zhaoIpAndPort;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtWmsPmMoBase/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsPmMoBaseService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    @ApiOperation(value = "查询制令单列表")
    @PostMapping("/mobile/getMoNoList")
    public List<String> getMoNoList(String param) {
        Map<String,Object> map=JSON.parseObject(param, Map.class);
        log.info("pda查询制令单列表：{}",map);
        List<PdtWmsPmMoBase> list = pdtWmsPmMoBaseService.list(new LambdaQueryWrapper<PdtWmsPmMoBase>().eq(PdtWmsPmMoBase::getMsCode,"SMT"));
        List<String> tos=new ArrayList<>();
        tos.add("");
        tos.addAll(list.stream().map(PdtWmsPmMoBase::getMoNo).collect(Collectors.toList()));
        return tos;
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtWmsPmMoBase/selDetailByMoNo/{moNo}")
    public Result selDetailByMoNo(@PathVariable String moNo) {
        Map model = pdtWmsPmMoBaseService.selDetailByMoNo(moNo);
        if (model==null){
            return Result.failed("无此单号");
        }
        String result= HttpUtil.get(zhaoIpAndPort+"/Api/wms/packing?moNumber="+moNo);
        Map parse = (Map) JSON.parse(result);
        parse.putAll(model);
        Map map = MapUtil.toCamelCaseMap(parse);
        return Result.succeed(map, "查询成功");
    }
    
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtWmsPmMoBase/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtWmsPmMoBase model = pdtWmsPmMoBaseService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtWmsPmMoBase/save")
    public Result save(@RequestBody PdtWmsPmMoBase pdtWmsPmMoBase) {
        return pdtWmsPmMoBaseService.saveNew(pdtWmsPmMoBase);
    }

    @ApiOperation(value = "根据工单号查询下一个制令单号")
    @PostMapping("/pdtWmsPmMoBase/getNewNoByProjectId")
    public Result getNewNoByProjectId(@RequestBody Map<String, Object> map) {
        String projectId = MapUtil.getStr(map, "projectId");
        String newNo=pdtWmsPmMoBaseService.getNewNoByProjectId(projectId);
        return Result.succeed(newNo,"查询成功");
    }

    /**
         * sap同步mo到mes
         */
    @ApiOperation(value = "sap同步mo到mes")
    @PostMapping("/pdtWmsPmMoBase/saveBatch")
    public Result saveBatch(@RequestBody List<PdtWmsPmMoBase> pdtWmsPmMoBases) {
        for (PdtWmsPmMoBase model : pdtWmsPmMoBases) {
            PdtWmsPmMoBase existMo = pdtWmsPmMoBaseService.getOne(new QueryWrapper<PdtWmsPmMoBase>().eq("mo_no", model.getMoNo()));
            if (existMo!=null){
                pdtWmsPmMoBaseService.update(model,new UpdateWrapper<PdtWmsPmMoBase>().eq("mo_no", model.getMoNo()));
            }
            else {
                pdtWmsPmMoBaseService.save(model);
            }
        }
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtWmsPmMoBase/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtWmsPmMoBaseService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtWmsPmMoBase/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtWmsPmMoBase> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtWmsPmMoBase.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        pdtWmsPmMoBaseService.save(u);
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
    @PostMapping("/pdtWmsPmMoBase/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<PdtWmsPmMoBase> pdtWmsPmMoBases =new ArrayList<>();
        List<PdtWmsPmMoBase> pdtWmsPmMoBaseList = pdtWmsPmMoBaseService.list(new QueryWrapper<PdtWmsPmMoBase>().eq("cu_id", cuId));
        if (pdtWmsPmMoBaseList.isEmpty()) {pdtWmsPmMoBases.add(pdtWmsPmMoBaseService.getById(0)); } else {
            for (PdtWmsPmMoBase pdtWmsPmMoBase : pdtWmsPmMoBaseList) {
                pdtWmsPmMoBases.add(pdtWmsPmMoBase);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtWmsPmMoBases, "指令单号表导出", "指令单号表导出", PdtWmsPmMoBase.class, "pdtWmsPmMoBase.xls", response);

    }
}
