package com.rh.i_mes_plus.service.impl.pdt;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.pdt.PdtReplaceItemMapper;
import com.rh.i_mes_plus.model.pdt.PdtReplaceItem;
import com.rh.i_mes_plus.service.pdt.IPdtReplaceItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 替代料
 *
 * @author hbq
 * @date 2021-03-30 13:20:00
 */
@Slf4j
@Service
public class PdtReplaceItemServiceImpl extends ServiceImpl<PdtReplaceItemMapper, PdtReplaceItem> implements IPdtReplaceItemService {
    @Resource
    private PdtReplaceItemMapper pdtReplaceItemMapper;
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
        return pdtReplaceItemMapper.findList(pages, params);
    }

    @Override
    public Result presentToBefore(Map<String,Object> map){
        Long id = MapUtil.getLong(map,"id");
        PdtReplaceItem replaceItem = pdtReplaceItemMapper.selectById(id);
        String replaceGroup = replaceItem.getReplaceGroup();
        Long ownerSort = replaceItem.getSort();
        PdtReplaceItem beforeReplaceItem = pdtReplaceItemMapper.getBefore(ownerSort,replaceGroup);
        if (beforeReplaceItem == null) {
            return Result.failed("已为顶部，无需上移");
        }
        Long beforeSort = beforeReplaceItem.getSort();
        replaceItem.setSort(beforeSort);
        beforeReplaceItem.setSort(ownerSort);
        pdtReplaceItemMapper.updateById(replaceItem);
        pdtReplaceItemMapper.updateById(beforeReplaceItem);
        return Result.succeed("上移成功");
    }
    @Override
    public Result presentToAfter(Map<String,Object> map){
        Long id = MapUtil.getLong(map,"id");
        PdtReplaceItem replaceItem = pdtReplaceItemMapper.selectById(id);
        String replaceGroup = replaceItem.getReplaceGroup();
        Long ownerSort = replaceItem.getSort();
        PdtReplaceItem afterReplaceItem = pdtReplaceItemMapper.getAfter(ownerSort,replaceGroup);
        if (afterReplaceItem == null) {
            return Result.failed("已为底部，无需下移");
        }
        Long afterSort = afterReplaceItem.getSort();
        replaceItem.setSort(afterSort);
        afterReplaceItem.setSort(ownerSort);
        pdtReplaceItemMapper.updateById(replaceItem);
        pdtReplaceItemMapper.updateById(afterReplaceItem);
        return Result.succeed("下移成功");
    }
    @Override
    public Result head(Map<String, Object> map){
        Long id = MapUtil.getLong(map, "id");
        PdtReplaceItem replaceItem = pdtReplaceItemMapper.selectById(id);
        String replaceGroup = replaceItem.getReplaceGroup();
        Long ownerSort = replaceItem.getSort();
        PdtReplaceItem beforeReplaceItem = pdtReplaceItemMapper.getBefore(ownerSort,replaceGroup);
        if (beforeReplaceItem == null) {
            return Result.failed("已为顶部，无需上移");
        }
        Long minSort = pdtReplaceItemMapper.minSort(replaceGroup);
        replaceItem.setSort(minSort - 1);
        pdtReplaceItemMapper.updateById(replaceItem);
        return Result.succeed("置顶成功");
    }
    @Override
    public Result tail(Map<String, Object> map){
        Long id = MapUtil.getLong(map, "id");
        PdtReplaceItem replaceItem = pdtReplaceItemMapper.selectById(id);
        String replaceGroup = replaceItem.getReplaceGroup();
        Long ownerSort = replaceItem.getSort();
        PdtReplaceItem afterReplaceItem = pdtReplaceItemMapper.getAfter(ownerSort,replaceGroup);
        if (afterReplaceItem == null) {
            return Result.failed("已为底部，无需下移");
        }
        Long maxSort = pdtReplaceItemMapper.maxSort(replaceGroup);
        replaceItem.setSort(maxSort + 1);
        pdtReplaceItemMapper.updateById(replaceItem);
        return Result.succeed("置底成功");
    }
    @Override
    public Result exchange(Map<String, Object> map){
        Long ownerId = MapUtil.getLong(map,"ownerId");
        Long otherId = MapUtil.getLong(map,"otherId");
        PdtReplaceItem owner = pdtReplaceItemMapper.selectById(ownerId);
        PdtReplaceItem other = pdtReplaceItemMapper.selectById(otherId);
        long ownerSort = owner.getSort();
        long otherSort = other.getSort();
        owner.setSort(otherSort);
        other.setSort(ownerSort);
        pdtReplaceItemMapper.updateById(owner);
        pdtReplaceItemMapper.updateById(other);
        return Result.succeed("交换成功");
    }

    @Override
    public List<PdtReplaceItem> getGroupItemListByItemCode(String itemCode,String modelCode,  int isExcludeItself) {
        return pdtReplaceItemMapper.getGroupItemListByItemCode(itemCode,modelCode,isExcludeItself);
    }
}
