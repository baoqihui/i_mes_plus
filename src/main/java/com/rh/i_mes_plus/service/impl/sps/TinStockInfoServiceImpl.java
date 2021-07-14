package com.rh.i_mes_plus.service.impl.sps;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.sps.TinStockInfoMapper;
import com.rh.i_mes_plus.service.sps.ITinStockInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.model.sps.TinStockInfo;

/**
 * 锡膏红胶库存表
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@Service
public class TinStockInfoServiceImpl extends ServiceImpl<TinStockInfoMapper, TinStockInfo> implements ITinStockInfoService {
    @Resource
    private TinStockInfoMapper tinStockInfoMapper;
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
        return tinStockInfoMapper.findList(pages, params);
    }

    @Override
    public Result getDocNo(Map<String, Object> map) {
        String typeCode = MapUtil.getStr(map, "typeCode");
        String prefix=typeCode  + DateUtil.format(new Date(), "yyyyMM");
        String maxDocNo=prefix+"001";
        TinStockInfo one = getOne(new QueryWrapper<TinStockInfo>()
                .last("where doc_no like '" + prefix + "%' order by doc_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getDocNo(), prefix));
            maxDocNo=prefix+String.format("%3d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxDocNo, "查询成功");
    }

    @Override
    public Page<Map> findListAll(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return tinStockInfoMapper.findListAll(pages, params);
    }

    @Override
    public Page<Map> findListTotal(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return tinStockInfoMapper.findListTotal(pages, params);
    }
}
