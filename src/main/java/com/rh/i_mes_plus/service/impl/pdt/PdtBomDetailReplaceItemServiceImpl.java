package com.rh.i_mes_plus.service.impl.pdt;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.pdt.PdtBomDetailReplaceItemMapper;
import com.rh.i_mes_plus.model.pdt.PdtBomDetailReplaceItem;
import com.rh.i_mes_plus.service.pdt.IPdtBomDetailReplaceItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * bom详情替代料信息
 *
 * @author hbq
 * @date 2021-06-09 20:13:20
 */
@Slf4j
@Service
public class PdtBomDetailReplaceItemServiceImpl extends ServiceImpl<PdtBomDetailReplaceItemMapper, PdtBomDetailReplaceItem> implements IPdtBomDetailReplaceItemService {
    @Resource
    private PdtBomDetailReplaceItemMapper pdtBomDetailReplaceItemMapper;
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
        return pdtBomDetailReplaceItemMapper.findList(pages, params);
    }
}
