package com.rh.i_mes_plus.service.impl.other;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.other.EccMaterialDetailMapper;
import com.rh.i_mes_plus.model.other.EccMaterialDetail;
import com.rh.i_mes_plus.service.other.IEccMaterialDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * ECC生产BOM
 *
 * @author hbq
 * @date 2021-01-09 15:34:09
 */
@Slf4j
@Service
public class EccMaterialDetailServiceImpl extends ServiceImpl<EccMaterialDetailMapper, EccMaterialDetail> implements IEccMaterialDetailService {
    @Resource
    private EccMaterialDetailMapper eccMaterialDetailMapper;
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
        return eccMaterialDetailMapper.findList(pages, params);
    }
}
