package com.rh.i_mes_plus.service.impl.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.gtoa.PdtStandardFileInfoMapper;
import com.rh.i_mes_plus.model.gtoa.PdtStandardFileInfo;
import com.rh.i_mes_plus.service.gtoa.IPdtStandardFileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 机型任务表（包含10文件以及当前执行所有文件）
 *
 * @author hbq
 * @date 2020-10-19 18:59:55
 */
@Slf4j
@Service
public class PdtStandardFileInfoServiceImpl extends ServiceImpl<PdtStandardFileInfoMapper, PdtStandardFileInfo> implements IPdtStandardFileInfoService {
    @Resource
    private PdtStandardFileInfoMapper pdtStandardFileInfoMapper;
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
        return pdtStandardFileInfoMapper.findList(pages, params);
    }
}
