package com.rh.i_mes_plus.controller.gtoa;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.EcnDocInfo;
import com.rh.i_mes_plus.service.gtoa.IEcnDocInfoService;
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

/**
 * 客户文件
 *
 * @author hbq
 * @date 2020-10-19 18:59:55
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "客户文件")
@RequestMapping("ecn")
public class EcnDocInfoController {
    @Autowired
    private IEcnDocInfoService ecnDocInfoService;
    @Value("${zhaoIpAndPort}")
    private String zhaoIpAndPort;
    
    /*@ApiOperation(value = "通过工单号查询时间")
    @PostMapping(value = "/ApiEcnGetMoDtInfo")
    protected Result ApiEcnGetMoDtInfo(@RequestBody Map<String, Object> params)  {
        String mo = MapUtil.getStr(params, "mo");
        String result= HttpUtil.get(zhaoIpAndPort+"/ApiEcnGetMoDtInfo?mo="+mo);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }*/
    @ApiOperation(value = "检验是否需要fai")
    @PostMapping(value = "/ApiEcnCheckFai")
    protected Result ApiEcnCheckFai(@RequestBody Map<String, Object> params)  {
        String param = JSONUtil.toJsonStr(params);
        String result= HttpRequest.post(zhaoIpAndPort+"/ApiEcnCheckFai").body(param).execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(result);
        System.out.println(jsonObject);
        return Result.succeed(jsonObject,"成功");
    }
    @ApiOperation(value = "获取看板信息1")
    @PostMapping(value = "/ApiEcnGetAffectedInfo")
    protected Result EcnGetStockInfo(@RequestBody Map<String, Object> params)  {
        String param = JSONUtil.toJsonStr(params);
        String result= HttpRequest.post(zhaoIpAndPort+"/ApiEcnGetAffectedInfo")
                .body(param).execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    @ApiOperation(value = "获取看板信息2")
    @PostMapping(value = "/ApiGetCapacityInfo")
    protected Result capacity(@RequestBody Map<String, Object> params)  {
        String startDate = MapUtil.getStr(params, "startDate");
        String result= HttpUtil.get(zhaoIpAndPort+"/Api/gtoa/kanban/capacity?startDate="+startDate);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    @ApiOperation(value = "获取看板信息3")
    @PostMapping(value = "/ApiGetCapacityTrendInfo")
    protected Result trend(@RequestBody Map<String, Object> params)  {
        String modelName = MapUtil.getStr(params, "modelName");
        String result= HttpUtil.get(zhaoIpAndPort+"/Api/gtoa/kanban/capacity/trend?modelName="+modelName);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    @ApiOperation(value = "获取看板信息4")
    @PostMapping(value = "/ApiGetProjectProcessInfo")
    protected Result process(@RequestBody Map<String, Object> params)  {
        String startDate = MapUtil.getStr(params, "startDate");
        String result= HttpUtil.get(zhaoIpAndPort+"/Api/gtoa/kanban/project/process?startDate="+startDate);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    @ApiOperation(value = "获取看板信息5")
    @PostMapping(value = "/ApiGetProjectSummaryInfo")
    protected Result summary(@RequestBody Map<String, Object> params)  {
        String startDate = MapUtil.getStr(params, "startDate");
        String result= HttpUtil.get(zhaoIpAndPort+"/Api/gtoa/kanban/project/summary?startDate="+startDate);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    @ApiOperation(value = "获取看板信息6")
    @PostMapping(value = "/ApiGetFaultInfo")
    protected Result fault()  {
        String result= HttpUtil.get(zhaoIpAndPort+"/Api/gtoa/kanban/fault");
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    @ApiOperation(value = "获取看板信息7")
    @PostMapping(value = "/CheckItemCodeExists")
    protected Result CheckItemCodeExists(@RequestBody Map<String, Object> params)  {
        String itemCode = MapUtil.getStr(params, "itemCode");
        String result= HttpUtil.get("http://192.168.50.97:8004/public/CheckItemCodeExists?itemCode="+itemCode);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/ecnDocInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= ecnDocInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/ecnDocInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        EcnDocInfo model = ecnDocInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }
    @ApiOperation(value = "通过ecn号查询客户和版本信息")
    @PostMapping("/ecnDocInfo/selCustomerAndBeginDateByEcnNo/{ecnNo}")
    public Result selCustomerAndBeginDateByEcnNo(@PathVariable String ecnNo) {
        Map<String,Object> map= ecnDocInfoService.selCustomerAndBeginDateByEcnNo(ecnNo);
        return Result.succeed(map, "查询成功");
    }
    @ApiOperation(value = "查询将要生成的ECN编号")
    @PostMapping("/ecnDocInfo/selMaxEcnNo")
    public Result selMaxEcnNo() {
        String prefix="ECN-ENG-";
        //EcnDocInfo model1 = ecnDocInfoService.getOne(new QueryWrapper<EcnDocInfo>().last("and ecn_no like '"+prefix+"%' order by ecn_no desc limit 1"));
        EcnDocInfo model =ecnDocInfoService.getMaxEcnNo(prefix);
        String maxEcnNo=prefix+"0001";
        if (model != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(model.getEcnNo(), prefix));
            maxEcnNo=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxEcnNo, "查询成功");
    }
    @ApiOperation(value = "查询将要生成的ECN编号是否存在")
    @PostMapping("/ecnDocInfo/selExistEcnNo/{ecnNo}")
    public Result selExistEcnNo(@PathVariable String ecnNo) {
        EcnDocInfo existEcnDocInfo = ecnDocInfoService.getOneByEcnNo(ecnNo);
        if (existEcnDocInfo != null) {
            return Result.failed("ECN单号"+ecnNo+"已存在,无法注册");
        }
        return Result.succeed("ECN单号"+ecnNo+"不存在,可以注册");
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/ecnDocInfo/saveFile")
    public Result saveFile(@RequestParam("file") MultipartFile file, String ecnNoCust ,String modelName,String customer,String ecnNo,String createName,Boolean quickCloseFlag) {
        return ecnDocInfoService.saveFile(file,ecnNoCust,modelName,customer,ecnNo,createName,quickCloseFlag);
    }
    /**
     * 编辑
     */
    @ApiOperation(value = "编辑")
    @PostMapping("/ecnDocInfo/updateFile")
    public Result updateFile(@RequestParam("file") MultipartFile file,Long id, String ecnNoCust ,String modelName,String customer,String ecnNo,String createName,Boolean quickCloseFlag) {
        return ecnDocInfoService.updateFile(file,id,ecnNoCust,modelName,customer,ecnNo,createName,quickCloseFlag);
    }

    /**
     * 关结
     */
    @ApiOperation(value = "关结")
    @PostMapping("/ecnDocInfo/close")
    public Result close(@RequestBody Map<String,Object> map) {
        return ecnDocInfoService.close(map);
    }
    
    /**
     * 关结
     */
    @ApiOperation(value = "快速关结")
    @PostMapping("/ecnDocInfo/quickClose")
    public Result quickClose(@RequestBody Map<String,Object> map) {
        return ecnDocInfoService.quickClose(map);
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/ecnDocInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<EcnDocInfo>> map) {
        List<EcnDocInfo> models = map.get("models");
        ecnDocInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/ecnDocInfo/del/{id}")
    public Result delete(@PathVariable Long id) {
        return ecnDocInfoService.delete(id);
    }

    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/ecnDocInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<EcnDocInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, EcnDocInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                    ecnDocInfoService.save(u);
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
    @PostMapping("/ecnDocInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<EcnDocInfo> ecnDocInfos =new ArrayList<>();
        List<EcnDocInfo> ecnDocInfoList = ecnDocInfoService.list(new QueryWrapper<EcnDocInfo>().eq("cu_id", cuId));
        if (ecnDocInfoList.isEmpty()) {ecnDocInfos.add(ecnDocInfoService.getById(0)); } else {
            for (EcnDocInfo ecnDocInfo : ecnDocInfoList) {
                ecnDocInfos.add(ecnDocInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(ecnDocInfos, "客户文件导出", "客户文件导出", EcnDocInfo.class, "ecnDocInfo.xls", response);

    }

}
