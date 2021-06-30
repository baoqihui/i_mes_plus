package com.rh.i_mes_plus.service.impl.pdt;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtBomDTO;
import com.rh.i_mes_plus.mapper.pdt.PdtBomMapper;
import com.rh.i_mes_plus.model.pdt.PdtBom;
import com.rh.i_mes_plus.model.pdt.PdtBomDetail;
import com.rh.i_mes_plus.model.pdt.PdtBomDetailReplaceItem;
import com.rh.i_mes_plus.service.pdt.IPdtBomDetailReplaceItemService;
import com.rh.i_mes_plus.service.pdt.IPdtBomDetailService;
import com.rh.i_mes_plus.service.pdt.IPdtBomService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        PdtBom pdtBom=pdtBomDTO;
        log.info("新建BOM：{}",pdtBomDTO);
        save(pdtBom);
        //删除原有docNo的详情
        List<PdtBomDetail> pdtBomDetails = pdtBomDTO.getPdtBomDetails();
        if (pdtBomDetails == null||pdtBomDetails.isEmpty()) {
            return Result.failed(  "详情列表不能为空");
        }
        pdtBomDetails= pdtBomDetails.stream().filter(u ->  1 != u.getAssyLevel()).collect(Collectors.toList());
        int size = pdtBomDetails.size();
        Long[] arr = new Long[5];
        arr[1]=pdtBom.getId();
        for (int i = 0; i < size; i++) {
            PdtBomDetail pdtBomDetail = pdtBomDetails.get(i);
            if ("#".equals(pdtBomDetail.getSN())){
                //todo:试产只写入bom中替代料，量产需要查询替代料清单pdt_replace_item中的列表去重后加入pdt_bom_detail_replace_item。
                PdtBomDetailReplaceItem pdtBomDetailReplaceItem = new PdtBomDetailReplaceItem();
                pdtBomDetailReplaceItem.setPbdId(pdtBomDetails.get(i-1).getId());
                pdtBomDetailReplaceItem.setItemCode(pdtBomDetail.getItemCode());
                pdtBomDetailReplaceItem.setItemName(pdtBomDetail.getItemName());
                pdtBomDetailReplaceItemService.save(pdtBomDetailReplaceItem);
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
}
