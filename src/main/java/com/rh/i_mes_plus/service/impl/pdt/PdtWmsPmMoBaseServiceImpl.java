package com.rh.i_mes_plus.service.impl.pdt;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsPmMoBaseMapper;
import com.rh.i_mes_plus.model.pdt.PdtWmsPmMoBase;
import com.rh.i_mes_plus.service.pdt.IPdtWmsPmMoBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 指令单号表
 *
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
@Slf4j
@Service
public class PdtWmsPmMoBaseServiceImpl extends ServiceImpl<PdtWmsPmMoBaseMapper, PdtWmsPmMoBase> implements IPdtWmsPmMoBaseService {
    @Resource
    private PdtWmsPmMoBaseMapper pdtWmsPmMoBaseMapper;
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
        return pdtWmsPmMoBaseMapper.findList(pages, params);
    }
    
    @Override
    public Map selDetailByMoNo(String moNo) {
        return pdtWmsPmMoBaseMapper.selDetailByMoNo(moNo);
    }

    @Override
    public Result saveNew(PdtWmsPmMoBase pdtWmsPmMoBase) {
        saveOrUpdate(pdtWmsPmMoBase);
        return Result.succeed("保存成功");
    }
    /**
     * 获取新的串号
     * @return 当前串号的下一个串号
     */
    @Override
    public String getNewNoByProjectId(String projectId) {
        //生成新的串号
        String prefix=projectId;
        System.out.println(projectId);
        String maxNo=prefix+"01";
        PdtWmsPmMoBase one = getOne(new QueryWrapper<PdtWmsPmMoBase>()
                .last("where mo_no like '" + prefix + "%' order by mo_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getMoNo(), prefix));
            maxNo=prefix+String.format("%2d",aLong+1).replace(" ","0");
        }
        return maxNo;
    }

}