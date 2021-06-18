package com.rh.i_mes_plus.service.impl.pdt;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsStockInfoMapper;
import com.rh.i_mes_plus.model.pdt.PdtWmsStockInfo;
import com.rh.i_mes_plus.model.ums.UmsItemMes;
import com.rh.i_mes_plus.service.pdt.IPdtWmsStockInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 库存信息表(修改此表：如果在库数量为0时，需要把相关信息保存至表T_WMS_STOCK_ZERO_INFO，退料时才能得到SN原有的相关信息)
 *
 * @author hbq
 * @date 2020-12-28 14:29:31
 */
@Slf4j
@Service
public class PdtWmsStockInfoServiceImpl extends ServiceImpl<PdtWmsStockInfoMapper, PdtWmsStockInfo> implements IPdtWmsStockInfoService {
    @Resource
    private PdtWmsStockInfoMapper pdtWmsStockInfoMapper;
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
        return pdtWmsStockInfoMapper.findList(pages, params);
    }

    @Override
    public Page<UmsItemMes> findList2(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return pdtWmsStockInfoMapper.findList2(pages, params);
    }

    @Override
    public Page<Map> listAll(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return pdtWmsStockInfoMapper.listAll(pages, params);
    }

    @Override
    public Page<Map> listAllCollect(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return pdtWmsStockInfoMapper.listAllCollect(pages, params);
    }

    @Override
    public Page<Map> listAllCollectByBoxNo(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return pdtWmsStockInfoMapper.listAllCollectByBoxNo(pages, params);
    }
}
