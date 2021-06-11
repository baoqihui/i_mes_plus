package com.rh.i_mes_plus.service.impl.ums;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.ums.UmsSparesItemTypeInfoMapper;
import com.rh.i_mes_plus.model.ums.UmsSparesItemTypeInfo;
import com.rh.i_mes_plus.service.ums.IUmsSparesItemTypeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 备品类别信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@Service
public class UmsSparesItemTypeInfoServiceImpl extends ServiceImpl<UmsSparesItemTypeInfoMapper, UmsSparesItemTypeInfo> implements IUmsSparesItemTypeInfoService {
    @Resource
    private UmsSparesItemTypeInfoMapper umsSparesItemTypeInfoMapper;
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
        return umsSparesItemTypeInfoMapper.findList(pages, params);
    }

    @Override
    public Result getItemTypeCodeByTypeCode(Map<String, Object> map) {
        String typeCode = MapUtil.getStr(map,"typeCode");

        String prefix=typeCode;
        String maxDocNo=prefix+"00001";
        UmsSparesItemTypeInfo one = getOne(new QueryWrapper<UmsSparesItemTypeInfo>()
                .last("where item_type_code like '" + prefix + "%' order by item_type_code desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getItemTypeCode(), prefix));
            maxDocNo=prefix+String.format("%5d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxDocNo, "查询成功");
    }
}
