package com.rh.i_mes_plus.service.impl.other;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.other.EccSendInfoMapper;
import com.rh.i_mes_plus.model.other.EccSendInfo;
import com.rh.i_mes_plus.service.other.IEccSendInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * ECC扣料数据
 *
 * @author hbq
 * @date 2021-01-09 15:34:09
 */
@Slf4j
@Service
public class EccSendInfoServiceImpl extends ServiceImpl<EccSendInfoMapper, EccSendInfo> implements IEccSendInfoService {
    @Resource
    private EccSendInfoMapper eccSendInfoMapper;
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
        return eccSendInfoMapper.findList(pages, params);
    }
}
