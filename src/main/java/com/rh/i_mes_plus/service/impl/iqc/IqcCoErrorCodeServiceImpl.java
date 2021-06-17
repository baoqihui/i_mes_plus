package com.rh.i_mes_plus.service.impl.iqc;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcCoErrorCodeMapper;
import com.rh.i_mes_plus.model.iqc.IqcCoErrorCode;
import com.rh.i_mes_plus.service.iqc.IIqcCoErrorCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 错误代码
 *
 * @author hbq
 * @date 2020-10-23 08:51:08
 */
@Slf4j
@Service
public class IqcCoErrorCodeServiceImpl extends ServiceImpl<IqcCoErrorCodeMapper, IqcCoErrorCode> implements IIqcCoErrorCodeService {
    @Resource
    private IqcCoErrorCodeMapper iqcCoErrorCodeMapper;
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
        return iqcCoErrorCodeMapper.findList(pages, params);
    }

    @Override
    public List<String> getErrDesc(String oqcNo) {
        return iqcCoErrorCodeMapper.getErrDesc(oqcNo);
    }
}
