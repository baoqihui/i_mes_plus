package com.rh.i_mes_plus.service.impl.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.gtoa.ItemChangeInfoMapper;
import com.rh.i_mes_plus.model.gtoa.ItemChangeInfo;
import com.rh.i_mes_plus.service.gtoa.IItemChangeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 变更项目表
 *
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
@Slf4j
@Service
public class ItemChangeInfoServiceImpl extends ServiceImpl<ItemChangeInfoMapper, ItemChangeInfo> implements IItemChangeInfoService {
    @Resource
    private ItemChangeInfoMapper itemChangeInfoMapper;
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
        return itemChangeInfoMapper.findList(pages, params);
    }
}
