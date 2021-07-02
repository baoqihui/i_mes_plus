package com.rh.i_mes_plus.service.impl.pdt;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtBomDTO;
import com.rh.i_mes_plus.mapper.pdt.PdtBomMapper;
import com.rh.i_mes_plus.model.pdt.*;
import com.rh.i_mes_plus.service.pdt.IPdtBomDetailReplaceItemService;
import com.rh.i_mes_plus.service.pdt.IPdtBomDetailService;
import com.rh.i_mes_plus.service.pdt.IPdtBomService;
import com.rh.i_mes_plus.service.pdt.IPdtReplaceItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * bom表
 *
 * @author hbq
 * @date 2021-05-25 13:18:22
 */
@Slf4j
@Service
public class PdtBomServiceImpl extends ServiceImpl<PdtBomMapper, PdtBom> implements IPdtBomService {
    @Resource
    private PdtBomMapper pdtBomMapper;
    @Autowired
    private IPdtBomDetailService pdtBomDetailService;
    @Autowired
    private IPdtBomDetailReplaceItemService pdtBomDetailReplaceItemService;
    @Autowired
    private IPdtReplaceItemService pdtReplaceItemService;
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
        return pdtBomMapper.findList(pages, params);
    }

    @Override
    public Result saveAll(PdtBomDTO pdtBomDTO) {
        log.info("新建BOM：{}",pdtBomDTO);

        List<PdtBomDetail> pdtBomDetails = pdtBomDTO.getPdtBomDetails();
        if (pdtBomDetails == null||pdtBomDetails.isEmpty()) {
            return Result.failed(  "详情列表不能为空");
        }
        PdtBom inputPdtBom=pdtBomDTO;
        String modelCode = inputPdtBom.getItemCode();
        Integer stage = inputPdtBom.getStage();
        PdtBomDetail pdtBomDetail1 = pdtBomDetails.get(0);
        String itemCode = pdtBomDetail1.getItemCode();
        String modelNo = getModelNoByCode(modelCode);
        if (!modelCode.equals(itemCode)){
            //如果机型与第一行不同则为半成品，寻找其成品的机型串码
            PdtBom one = getOne(new LambdaQueryWrapper<PdtBom>()
                    .eq(PdtBom::getItemCode, modelCode)
                    .eq(PdtBom::getIsValue, 1)
            );
            if (one==null){
                return Result.failed("请先上传半成品所属的成品bom");
            }
            modelNo = one.getModelNo();
        }
        //判断以前的同机种并弃用
        update(new LambdaUpdateWrapper<PdtBom>()
                .eq(PdtBom::getItemCode,itemCode)
                .eq(PdtBom::getIsValue,1)
                .set(PdtBom::getIsValue,0)
        );
        inputPdtBom.setItemCode(pdtBomDetail1.getItemCode());
        inputPdtBom.setItemName(pdtBomDetail1.getItemName());
        inputPdtBom.setModelNo(modelNo);
        save(inputPdtBom);
        pdtBomDetails= pdtBomDetails.stream().filter(u ->  1 != u.getAssyLevel()).collect(Collectors.toList());
        int size = pdtBomDetails.size();
        Long[] arr = new Long[5];
        arr[1]=inputPdtBom.getId();
        for (int i = 0; i < size; i++) {
            PdtBomDetail pdtBomDetail = pdtBomDetails.get(i);
            pdtBomDetail.setModelNo(modelNo);
            if ("#".equals(pdtBomDetail.getSN())){
                PdtBomDetail prePdtBomDetail = pdtBomDetails.get(i - 1);
                List<PdtReplaceItem> groupItemLists = pdtReplaceItemService.getGroupItemListByItemCode(prePdtBomDetail.getItemCode(),modelCode,  0);
                if (stage==1||groupItemLists.isEmpty()){
                    //试产只添加表格中替代料
                    PdtBomDetailReplaceItem pdtBomDetailReplaceItem = new PdtBomDetailReplaceItem();
                    pdtBomDetailReplaceItem.setPbdId(prePdtBomDetail.getId());
                    pdtBomDetailReplaceItem.setItemCode(pdtBomDetail.getItemCode());
                    pdtBomDetailReplaceItem.setItemName(pdtBomDetail.getItemName());
                    pdtBomDetailReplaceItemService.save(pdtBomDetailReplaceItem);
                }
                if (stage==2){
                    //量产除了添加表格替代料还要应用替代料清单中替代料,同时选择出优先级最高的替换至PdtBomDetail
                    for (int j = 0; j < groupItemLists.size(); j++) {
                        PdtReplaceItem pdtReplaceItem = groupItemLists.get(j);
                        if (j==0){
                            prePdtBomDetail.setItemCode(pdtReplaceItem.getItemCode());
                            prePdtBomDetail.setItemName(pdtReplaceItem.getItemName());
                            pdtBomDetailService.updateById(prePdtBomDetail);
                        }else {
                            PdtBomDetailReplaceItem pdtBomDetailReplaceItem = new PdtBomDetailReplaceItem();
                            pdtBomDetailReplaceItem.setPbdId(prePdtBomDetail.getId());
                            pdtBomDetailReplaceItem.setItemCode(pdtReplaceItem.getItemCode());
                            pdtBomDetailReplaceItem.setItemName(pdtReplaceItem.getItemName());
                            pdtBomDetailReplaceItemService.save(pdtBomDetailReplaceItem);
                        }
                    }
                }
            }else{
                Integer level = pdtBomDetail.getAssyLevel();
                if (level==2){
                    pdtBomDetail.setParentId(arr[1]);
                }else{
                    PdtBomDetail prePdtBomDetail = pdtBomDetails.get(i -1);
                    Integer preLevel = prePdtBomDetail.getAssyLevel();
                    //当层级发生变化时，新建此记录前要记录上层bomId
                    if (level==preLevel+1){
                        PdtBom parentBom = new PdtBom();
                        parentBom.setItemCode(prePdtBomDetail.getItemCode());
                        parentBom.setItemName(prePdtBomDetail.getItemName());
                        save(parentBom);
                        arr[preLevel] = parentBom.getId();
                    }
                    pdtBomDetail.setParentId(arr[level-1]);
                }
                pdtBomDetailService.save(pdtBomDetail);
            }
        }
        return Result.succeed("保存成功");
    }

    @Override
    public Result getListByItemCode(Map<String, Object> params) {
        String code = MapUtil.getStr(params, "itemCode");
        PdtBom bom = getOne(new LambdaQueryWrapper<PdtBom>().eq(PdtBom::getItemCode, code).eq(PdtBom::getIsValue,1));
        Long id = bom.getId();
        List<PdtBomDetail> bomDetails = pdtBomDetailService.list(new LambdaQueryWrapper<PdtBomDetail>()
                .eq(PdtBomDetail::getParentId, id)
                .eq(PdtBomDetail::getIsValue, 1)
        );
        return Result.succeed(bomDetails,"查询成功");
    }

    @Override
    public Result getPcbMapByItemCode(Map<String, Object> params) {
        Map<String, Object> map=pdtBomMapper.getPcbMapByItemCode(params);
        return Result.succeed(map,"查询成功");
    }

    @Override
    public Result changeStage(PdtBom pdtBom) {
        PdtBom bom = getById(pdtBom);
        String modelCode = bom.getItemCode();
        String modelNo = bom.getModelNo();
        bom.setStage(2);
        updateById(bom);
        List<PdtBomDetail> bomDetails = pdtBomDetailService.list(new LambdaQueryWrapper<PdtBomDetail>()
                .eq(PdtBomDetail::getModelNo, modelNo));
        for (PdtBomDetail bomDetail : bomDetails) {
            Long pbdId = bomDetail.getId();
            int count = pdtBomDetailReplaceItemService.count(new LambdaQueryWrapper<PdtBomDetailReplaceItem>()
                    .eq(PdtBomDetailReplaceItem::getPbdId, pbdId));
            if (count>0){

                List<PdtReplaceItem> groupItemLists = pdtReplaceItemService.getGroupItemListByItemCode(bomDetail.getItemCode(),modelCode,  0);
                if (!groupItemLists.isEmpty()){
                    pdtBomDetailReplaceItemService.remove(new LambdaQueryWrapper<PdtBomDetailReplaceItem>()
                            .eq(PdtBomDetailReplaceItem::getPbdId, pbdId));
                }
                //量产除了添加表格替代料还要应用替代料清单中替代料,同时选择出优先级最高的替换至PdtBomDetail
                for (int j = 0; j < groupItemLists.size(); j++) {
                    PdtReplaceItem pdtReplaceItem = groupItemLists.get(j);
                    if (j==0){
                        bomDetail.setItemCode(pdtReplaceItem.getItemCode());
                        bomDetail.setItemName(pdtReplaceItem.getItemName());
                        pdtBomDetailService.updateById(bomDetail);
                    }else {
                        PdtBomDetailReplaceItem pdtBomDetailReplaceItem = new PdtBomDetailReplaceItem();
                        pdtBomDetailReplaceItem.setPbdId(bomDetail.getId());
                        pdtBomDetailReplaceItem.setItemCode(pdtReplaceItem.getItemCode());
                        pdtBomDetailReplaceItem.setItemName(pdtReplaceItem.getItemName());
                        pdtBomDetailReplaceItemService.save(pdtBomDetailReplaceItem);
                    }
                }
            }
        }
        return Result.succeed("保存成功");
    }

    public String getModelNoByCode(String itemCode) {
        String prefix=itemCode+"-";
        String maxModelNo=prefix+"001";
        PdtBom one = getOne(new QueryWrapper<PdtBom>()
                .last("where model_no like '" + prefix + "%' order by model_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getModelNo(), prefix));
            maxModelNo=prefix+String.format("%3d",aLong+1).replace(" ","0");
        }
        return maxModelNo;
    }
}
