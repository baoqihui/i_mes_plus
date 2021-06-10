package com.rh.i_mes_plus.controller.gtoa;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.common.service.RedisService;
import com.rh.i_mes_plus.dto.TaskExecuteInfoDTO;
import com.rh.i_mes_plus.model.gtoa.*;
import com.rh.i_mes_plus.service.gtoa.*;
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
import java.util.List;
import java.util.Map;

/**
 * 任务执行状态
 *
 * @author hbq
 * @date 2020-10-29 18:10:24
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "任务执行状态")
@RequestMapping("tas")
public class TaskExecuteInfoController {
    @Autowired
    private ITaskExecuteInfoService taskExecuteInfoService;
    @Autowired
    private IEcnDetailInfoService ecnDetailInfoService;
    @Autowired
    private ITaskBaseInfoService taskBaseInfoService;
    @Autowired
    private ITimeLogService timeLogService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IEcnDocInfoService ecnDocInfoService;
    @Autowired
    private IPdtStandardFileInfoService pdtStandardFileInfoService;
    /**
     * 查询子任务列表
     */
    @ApiOperation(value = "查询子任务列表")
    @PostMapping("/ecnDocInfo/listTaskExecuteInfo")
    public Result<PageResult> listTaskExecuteInfo(@RequestBody Map<String, Object> params) {
        Page<Map> list= ecnDetailInfoService.findList(params);
        String depaCode = MapUtil.getStr(params, "depaCode");
        List<Map> records = list.getRecords();
        for (Map record : records) {
            String ecnNo = MapUtil.getStr(record, "ecnNo");
            String[] split = MapUtil.getStr(record, "distributedDept").split(",");
            //总部门数
            Integer total=split.length;
            Integer endCount = Convert.toInt(redisService.get(ecnNo + "EndExe"),0);
            String executeState=total==endCount?"执行完成":"执行中";
            List<TaskExecuteInfo> a = taskExecuteInfoService.list(new QueryWrapper<TaskExecuteInfo>().eq("ecn_no", ecnNo).orderByDesc());
            TimeLog timeLog = timeLogService.getOne(new QueryWrapper<TimeLog>().eq("ecn_no", ecnNo).eq("status", SysConst.EXESTATE.QA_ACCEPTING));
            record.put("time",timeLog==null?"-":timeLog.getTime());
            List<Map<String,Object>> taskExecuteInfos=new ArrayList<>();
            Boolean canExecuteInfo=true;
            for (TaskExecuteInfo taskExecuteInfo : a) {
                if (depaCode.equals(taskExecuteInfo.getDepaCode())){ canExecuteInfo=false; }
                Map<String, Object> map = BeanUtil.beanToMap(taskExecuteInfo);
                TaskBaseInfo taskBaseInfo = taskBaseInfoService.getById(taskExecuteInfo.getTaskId());
                map.put("hasFile",taskBaseInfo.getHasFile());
                map.put("name",taskBaseInfo.getName());
                taskExecuteInfos.add(map);
            }
            record.put("executeState",executeState);
            record.put("canExecuteInfo",canExecuteInfo);
            record.put("taskExecuteInfos",taskExecuteInfos);
        }
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 查询子任务列表
     */
    @ApiOperation(value = "查询子任务列表")
    @PostMapping("/ecnDocInfo/getTaskExecuteInfo")
    public Result getTaskExecuteInfo(@RequestBody Map<String, Object> params) {
        String depaCode = MapUtil.getStr(params, "depaCode");
        List<Map> result=taskExecuteInfoService.getTaskExecuteInfo(depaCode);
        return Result.succeed(result,"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/taskExecuteInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= taskExecuteInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/taskExecuteInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        TaskExecuteInfo model = taskExecuteInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 任务分配保存
     */
    @ApiOperation(value = "任务分配保存")
    @PostMapping("/taskExecuteInfo/save")
    public Result saveAll(@RequestBody List<TaskExecuteInfo> taskExecuteInfos) {
        return taskExecuteInfoService.saveAll(taskExecuteInfos);
    }

    /**
     * 任务执行保存
     */
    @ApiOperation(value = "任务执行保存")
    @PostMapping("/taskExecuteInfo/execute")
    public Result execute(@RequestBody TaskExecuteInfoDTO taskExecuteInfoDTO) {
        return taskExecuteInfoService.execute(taskExecuteInfoDTO);
    }

    /**
     * 任务审核保存
     */
    @ApiOperation(value = "任务审核保存")
    @PostMapping("/taskExecuteInfo/audit")
    public Result audit(@RequestBody TaskExecuteInfo taskExecuteInfo) {
        return taskExecuteInfoService.audit(taskExecuteInfo);
    }
    /**
     * 文件编辑
     */
    @ApiOperation(value = "文件编辑")
    @PostMapping("/taskExecuteInfo/updateFile")
    public Result updateFile(@RequestBody TaskExecuteInfo taskExecuteInfo) {
        TaskExecuteInfo existTaskExecuteInfo = taskExecuteInfoService.getById(taskExecuteInfo.getId());
        EcnDocInfo docInfo = ecnDocInfoService.getOne(new QueryWrapper<EcnDocInfo>()
                .eq("ecn_no", existTaskExecuteInfo.getEcnNo())
                .eq("is_del", 0)
        );
        if (SysConst.EXESTATE.HAS_DEL.equals(docInfo.getExeState())){
            return Result.failed("该ecn已关结，无法编辑");
        }
        taskExecuteInfoService.updateById(taskExecuteInfo);
        
        TaskBaseInfo taskBaseInfo = taskBaseInfoService.getById(existTaskExecuteInfo.getTaskId());
        String ecnNo = existTaskExecuteInfo.getEcnNo();
        if (taskBaseInfo.getTaskDesc() != null) {
            pdtStandardFileInfoService.update(new UpdateWrapper<PdtStandardFileInfo>()
                    .eq("ecn_no",ecnNo)
                    .set(taskBaseInfo.getTaskDesc(),taskExecuteInfo.getExecuteFile())
            );
        }
        return Result.succeed("保存成功");
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/taskExecuteInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<TaskExecuteInfo>> map) {
        List<TaskExecuteInfo> models = map.get("models");
        taskExecuteInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/taskExecuteInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        taskExecuteInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/taskExecuteInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<TaskExecuteInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, TaskExecuteInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        taskExecuteInfoService.save(u);
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
    @PostMapping("/taskExecuteInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<TaskExecuteInfo> taskExecuteInfos =new ArrayList<>();
        List<TaskExecuteInfo> taskExecuteInfoList = taskExecuteInfoService.list(new QueryWrapper<TaskExecuteInfo>().eq("cu_id", cuId));
        if (taskExecuteInfoList.isEmpty()) {taskExecuteInfos.add(taskExecuteInfoService.getById(0)); } else {
            for (TaskExecuteInfo taskExecuteInfo : taskExecuteInfoList) {
                taskExecuteInfos.add(taskExecuteInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(taskExecuteInfos, "任务执行状态导出", "任务执行状态导出", TaskExecuteInfo.class, "taskExecuteInfo.xls", response);

    }
}
