package com.rh.i_mes_plus.service.impl.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.MfgStencilsReqInfoMapper;
import com.rh.i_mes_plus.model.sps.MfgStencilsReqInfo;
import com.rh.i_mes_plus.service.sps.IMfgStencilsReqInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 钢网借出详情表
 *
 * @author hbq
 * @date 2021-06-03 20:11:19
 */
@Slf4j
@Service
public class MfgStencilsReqInfoServiceImpl extends ServiceImpl<MfgStencilsReqInfoMapper, MfgStencilsReqInfo> implements IMfgStencilsReqInfoService {
    @Resource
    private MfgStencilsReqInfoMapper mfgStencilsReqInfoMapper;
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
        return mfgStencilsReqInfoMapper.findList(pages, params);
    }
}
