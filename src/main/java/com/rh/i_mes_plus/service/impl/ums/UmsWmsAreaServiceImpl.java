package com.rh.i_mes_plus.service.impl.ums;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.dto.UmsWmsAreaDTO;
import com.rh.i_mes_plus.mapper.ums.UmsWmsAreaMapper;
import com.rh.i_mes_plus.model.ums.UmsWmsArea;
import com.rh.i_mes_plus.service.ums.IUmsWmsAreaService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.vo.OneVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author hqb
 * @date 2020-09-24 13:06:30
 */
@Slf4j
@Service
public class UmsWmsAreaServiceImpl extends ServiceImpl<UmsWmsAreaMapper, UmsWmsArea> implements IUmsWmsAreaService {
    @Resource
    private UmsWmsAreaMapper umsWmsAreaMapper;
    @Value("${lineBeforeLightsOnPort}")
    private String lineBeforeLightsOnPort;
    @Value("${lineAfterLightsOnPort}")
    private String lineAfterLightsOnPort;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<Map> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return umsWmsAreaMapper.findList(pages, params);
    }

    @Override
    public List<OneVO> treeList(Map<String, Object> params) {
        return umsWmsAreaMapper.treeList(params);
    }

    @Override
    public List<String> getParent(List<String> allWmsName, String arSn) {
        UmsWmsArea umsWmsArea=umsWmsAreaMapper.getParent(arSn);
        if (umsWmsArea!=null && umsWmsArea.getArFatherSn()!=null){
            getParent(allWmsName,umsWmsArea.getArFatherSn());
            allWmsName.add(umsWmsArea.getArName());
        }
        return allWmsName;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result autoGenerate(UmsWmsAreaDTO umsWmsAreaDTO) {
        try{
            String fatherSn = umsWmsAreaDTO.getArFatherSn();
            UmsWmsArea umsWmsArea = getOne(new QueryWrapper<UmsWmsArea>().eq("ar_sn",fatherSn));
            if (!SysConst.WMS_AT_TYPE.AAT.equals(umsWmsArea.getAtCode())||umsWmsArea.getIsDel()||!"Y".equals(umsWmsArea.getArState())){
                return Result.failed("非库区无法添加");
            }
            Integer oneStart = umsWmsAreaDTO.getOneStart();
            Integer oneEnd = umsWmsAreaDTO.getOneEnd();
            Integer twoStart = umsWmsAreaDTO.getTwoStart();
            Integer twoEnd = umsWmsAreaDTO.getTwoEnd();
            String arSn = umsWmsArea.getArSn();
            UmsWmsArea newUmsWmsArea=new UmsWmsArea();
            newUmsWmsArea.setCommandsetId("0");
            newUmsWmsArea.setAtCode("P");
            newUmsWmsArea.setArRemark(umsWmsAreaDTO.getArRemark());
            newUmsWmsArea.setArArea(umsWmsAreaDTO.getArArea());
            newUmsWmsArea.setArAreaUnit(umsWmsAreaDTO.getArAreaUnit());
            newUmsWmsArea.setArCubage(umsWmsAreaDTO.getArCubage());
            newUmsWmsArea.setArCubageUnit(umsWmsAreaDTO.getArCubageUnit());
            newUmsWmsArea.setArState("Y");
            newUmsWmsArea.setArFatherSn(umsWmsArea.getArSn());
            newUmsWmsArea.setArIsAging(umsWmsAreaDTO.getArIsAging());
            //获取上级树形列表地址
            newUmsWmsArea.setArPath("");

            List<String> wmsAreaPathList =getParent(new ArrayList<>(),umsWmsArea.getArSn());
            String wmsAreaPathS="";
            for (String s : wmsAreaPathList) {
                wmsAreaPathS=wmsAreaPathS+s+"*";
            }
            for (int i=oneStart;i<=oneEnd;i++){
                for (int j=twoStart;j<=twoEnd;j++){
                    //String newSn=arSn+umsWmsAreaDTO.getConnector()+String.format("%2d", i).replace(" ", "0")+umsWmsAreaDTO.getConnector()+String.format("%2d",j).replace(" ", "0");
                    String newSn=String.format("%3d", i).replace(" ", "0")+umsWmsAreaDTO.getConnector()+String.format("%4d",j).replace(" ", "0");
                    newUmsWmsArea.setArPath(wmsAreaPathS+newSn);
                    newUmsWmsArea.setArSn(newSn);
                    newUmsWmsArea.setArName(newSn);
                    save(newUmsWmsArea);
                }
            }
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "添加失败,请检查库位是否占用");
        }
        return Result.succeed("保存成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result leadIn(MultipartFile excel) {
        try{
            int rowNum = 0;
            if (!excel.isEmpty()) {
                List<UmsWmsArea> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsWmsArea.class);
                rowNum = list.size();
                if (rowNum > 0) {
                    //无该用户信息
                    list.forEach(u -> {
                        save(u);
                    });
                    return Result.succeed("成功导入信息"+rowNum+"行数据");
                }
            }
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "添加失败,请检查库位是否占用");
        }
        return Result.succeed("导入成功");
    }

    @Override
    public Result allLight(Map<String, Object> params) {
        Integer LightColor = MapUtil.getInt(params, "LightColor");
        Integer LightState = MapUtil.getInt(params, "LightState");
        List<String> mainBoards=umsWmsAreaMapper.findAllMainBoard();
        List<Map<String, Object>> lightMaps=new ArrayList<>();
        for (String mainBoard : mainBoards) {
            //调取亮灯服务
            Map<String, Object> lightMap = new HashMap<>();
            lightMap.put("MainBoard", mainBoard);
            lightMap.put("LightState",LightState);
            lightMap.put("LightColor",LightColor);
            lightMaps.add(lightMap);
        }
        String param = JSONUtil.toJsonStr(lightMaps);
        log.info("亮灯参数{}",param);
        HttpRequest.post(lineBeforeLightsOnPort+"/api/Light/ShelfControl").body(param).execute().body();
        HttpRequest.post(lineAfterLightsOnPort+"/api/Light/ShelfControl").body(param).execute().body();
        HttpRequest.post(lineBeforeLightsOnPort+"/api/Light/LighthouseControl").body(param).execute().body();
        HttpRequest.post(lineAfterLightsOnPort+"/api/Light/LighthouseControl").body(param).execute().body();
        return Result.succeed("保存成功");
    }

    @Override
    public Result allEmptyLight(Map<String, Object> params) {
        Integer LightColor = MapUtil.getInt(params, "LightColor");
        List<UmsWmsArea> umsWmsAreas= umsWmsAreaMapper.getEmptyStock();
        List<Map<String, Object>> lightMaps=new ArrayList<>();
        for (UmsWmsArea umsWmsArea : umsWmsAreas) {
            String arSn = umsWmsArea.getArSn();
            Map<String, Object> lightMap = new HashMap<>();
            lightMap.put("MainBoard",Convert.toInt(arSn.substring(0, 3)));
            lightMap.put("Position", Convert.toInt(arSn.substring(3)));
            lightMap.put("LightState",1);
            lightMap.put("LightColor",LightColor);
            lightMaps.add(lightMap);
        }
        String param = JSONUtil.toJsonStr(lightMaps);
        HttpRequest.post(lineBeforeLightsOnPort+"/api/Light/LightControl").body(param).execute().body();
        HttpRequest.post(lineAfterLightsOnPort+"/api/Light/LightControl").body(param).execute().body();
        return Result.succeed("成功");
    }
}
