package com.rh.i_mes_plus.service.impl.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.MfgStencilsDetailInfoMapper;
import com.rh.i_mes_plus.model.sps.MfgStencilsDetailInfo;
import com.rh.i_mes_plus.service.sps.IMfgStencilsDetailInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 钢网详情信息表
 *
 * @author hbq
 * @date 2021-02-23 16:28:09
 */
@Slf4j
@Service
public class MfgStencilsDetailInfoServiceImpl extends ServiceImpl<MfgStencilsDetailInfoMapper, MfgStencilsDetailInfo> implements IMfgStencilsDetailInfoService {
    @Resource
    private MfgStencilsDetailInfoMapper mfgStencilsDetailInfoMapper;
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
        return mfgStencilsDetailInfoMapper.findList(pages, params);
    }
}
