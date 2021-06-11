package com.rh.i_mes_plus.controller.ums;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.model.ums.UmsNotify;
import com.rh.i_mes_plus.service.ums.IUmsNotifyService;
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
 * 用户通知表,包含了所有用户的消息
 *
 * @author hqb
 * @date 2020-09-17 08:34:13
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "用户通知表,包含了所有用户的消息")
@RequestMapping("ums")
public class UmsNotifyController {
    @Autowired
    private IUmsNotifyService umsNotifyService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsNotify/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsNotify> list= umsNotifyService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "读取某信息内容")
    @PostMapping("/umsNotify/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsNotify model = umsNotifyService.getById(id);
        model.setIsRead(SysConst.BYES);
        umsNotifyService.updateById(model);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping("/umsNotify/save")
    public Result save(@RequestBody List<UmsNotify> umsNotifys) {
        umsNotifyService.saveBatch(umsNotifys);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/umsNotify/del/{id}")
    public Result delete(@PathVariable Long id) {
        umsNotifyService.removeById(id);
        return Result.succeed("删除成功");
    }

    @ApiOperation(value = "当前用户查询消息列表")
    @PostMapping("/umsNotify/getNotify/{userId}")
    public Result getNotifyByUserId(@PathVariable Long userId) {
        List<UmsNotify> list = umsNotifyService.list(new QueryWrapper<UmsNotify>()
                .eq("reciver_id", userId)
                .orderByDesc("create_time")
        );
        int total = list.size();
        int notReadCount = umsNotifyService.count(new QueryWrapper<UmsNotify>()
                .eq("reciver_id", userId)
                .orderByDesc("create_time")
                .eq("is_read", SysConst.NO)
        );
        int hasReadCount = total - notReadCount;
        Map<String, Object> notify = new HashMap<>();
        notify.put("notifyList",list);
        notify.put("total",total);
        notify.put("notReadCount",notReadCount);
        notify.put("hasReadCount",hasReadCount);
        return Result.succeed(notify, "查询成功");
    }
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsNotify/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsNotify> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsNotify.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsNotifyService.save(u);
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
    @PostMapping("/umsNotify/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsNotify> umsNotifys =new ArrayList<>();
        List<UmsNotify> umsNotifyList = umsNotifyService.list(new QueryWrapper<UmsNotify>().eq("cu_id", cuId));
        if (umsNotifyList.isEmpty()) {umsNotifys.add(umsNotifyService.getById(0)); } else {
            for (UmsNotify umsNotify : umsNotifyList) {
                umsNotifys.add(umsNotify);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsNotifys, "用户通知表,包含了所有用户的消息导出", "用户通知表,包含了所有用户的消息导出", UmsNotify.class, "umsNotify.xls", response);

    }
}
