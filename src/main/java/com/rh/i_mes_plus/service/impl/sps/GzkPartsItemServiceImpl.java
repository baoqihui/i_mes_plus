package com.rh.i_mes_plus.service.impl.sps;
import com.rh.i_mes_plus.mapper.sps.GzkPartsItemMapper;
import com.rh.i_mes_plus.model.sps.GzkPartsItem;
import com.rh.i_mes_plus.service.sps.IGzkPartsItemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * 工装备品物料
 *
 * @author hbq
 * @date 2021-06-23 15:54:13
 */
@Slf4j
@Service
public class GzkPartsItemServiceImpl extends ServiceImpl<GzkPartsItemMapper, GzkPartsItem> implements IGzkPartsItemService {
    @Resource
    private GzkPartsItemMapper gzkPartsItemMapper;
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
        return gzkPartsItemMapper.findList(pages, params);
    }
}
