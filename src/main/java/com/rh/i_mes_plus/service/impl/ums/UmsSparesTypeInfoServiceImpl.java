package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsSparesTypeInfoMapper;
import com.rh.i_mes_plus.model.ums.UmsSparesTypeInfo;
import com.rh.i_mes_plus.service.ums.IUmsSparesTypeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 备品大类信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@Service
public class UmsSparesTypeInfoServiceImpl extends ServiceImpl<UmsSparesTypeInfoMapper, UmsSparesTypeInfo> implements IUmsSparesTypeInfoService {
    @Resource
    private UmsSparesTypeInfoMapper umsSparesTypeInfoMapper;
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
        return umsSparesTypeInfoMapper.findList(pages, params);
    }
}
