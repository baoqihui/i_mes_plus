package com.rh.i_mes_plus.controller.pdt;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtReplaceItem;
import com.rh.i_mes_plus.service.pdt.IPdtReplaceItemService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 替代料
 *
 * @author hbq
 * @date 2021-03-30 13:20:00
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "替代料")
@RequestMapping("pdt")
public class PdtReplaceItemController {
    @Autowired
    private IPdtReplaceItemService pdtReplaceItemService;

    @ApiOperation(value = "上移 ")
    @PostMapping("/pdtReplaceItem/upper")
    public Result increase(@RequestBody Map<String,Object> map) {
        return pdtReplaceItemService.presentToBefore(map);
    }
    @ApiOperation(value = "下移 ")
    @PostMapping("/pdtReplaceItem/lower")
    public Result lower(@RequestBody Map<String,Object> map) {
        return pdtReplaceItemService.presentToAfter(map);
    }
    @ApiOperation(value = "置顶 ")
    @PostMapping("/pdtReplaceItem/top")
    public Result head(@RequestBody Map<String, Object> map) {
        return pdtReplaceItemService.head(map);
    }
    @ApiOperation(value = "置底 ")
    @PostMapping("/pdtReplaceItem/bottom")
    public Result tail(@RequestBody Map<String, Object> map) {
        return  pdtReplaceItemService.tail(map);
    }
    @ApiOperation(value = "互换 ")
    @PostMapping("/pdtReplaceItem/exchange")
    public Result exchange(@RequestBody  Map<String, Object> map) {
        return pdtReplaceItemService.exchange(map);
    }


    @ApiOperation(value = "通过机种和料号查询同物料组料号 ")
    @PostMapping("/pdtReplaceItem/getGroupItemListByItemCodeAndModelCode")
    public Result getGroupItemListByItemCode(@RequestBody  Map<String, Object> map) {
        String itemCode = MapUtil.getStr(map, "itemCode");
        String modelCode = MapUtil.getStr(map, "modelCode");
        List<PdtReplaceItem> replaceItems = pdtReplaceItemService.getGroupItemListByItemCode(itemCode,modelCode, 0);
        return Result.succeed(replaceItems,"查询成功");
    }

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtReplaceItem/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtReplaceItemService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtReplaceItem/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtReplaceItem model = pdtReplaceItemService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtReplaceItem/save")
    public Result save(@RequestBody PdtReplaceItem pdtReplaceItem) {
        pdtReplaceItemService.saveOrUpdate(pdtReplaceItem);
        return Result.succeed("保存成功");
    }
    /**
     * 通过替代组更新量产bom
     */
    @ApiOperation(value = "通过替代组更新量产bom")
    @PostMapping("/pdtReplaceItem/updateBomByReplaceGroup")
    public Result updateBomByReplaceGroup(@RequestBody PdtReplaceItem pdtReplaceItem) {
        return pdtReplaceItemService.updateBomByReplaceGroup(pdtReplaceItem);
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtReplaceItem/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtReplaceItem>> map) {
        List<PdtReplaceItem> models = map.get("models");
        Long id = models.get(0).getId();
        if (id == null) {
            String finalMaxNo = getNewNo();
            models.forEach(u->u.setReplaceGroup(finalMaxNo));
        }
        pdtReplaceItemService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtReplaceItem/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtReplaceItemService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtReplaceItem/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtReplaceItem> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtReplaceItem.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        pdtReplaceItemService.save(u);
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
    @PostMapping("/pdtReplaceItem/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<PdtReplaceItem> pdtReplaceItemList = ids==null||ids.isEmpty()? pdtReplaceItemService.list(new QueryWrapper<>()):(List)pdtReplaceItemService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtReplaceItemList, "替代料导出", "替代料导出", PdtReplaceItem.class, "pdtReplaceItem.xls", response);
    }

    /**
     * 获取新的串号
     * @return 当前串号的下一个串号
     */
    private String getNewNo(){
        //生成新的串号
        String prefix=DateUtil.format(new Date(), "yyyyMM");
        String maxNo=prefix+"0001";
        PdtReplaceItem one = pdtReplaceItemService.getOne(new QueryWrapper<PdtReplaceItem>()
                .last("where replace_group like '" + prefix + "%' order by replace_group desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getReplaceGroup(), prefix));
            maxNo=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return maxNo;
    }

}
