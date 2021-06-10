package com.rh.i_mes_plus.service.impl.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.gtoa.EmailLogMapper;
import com.rh.i_mes_plus.model.gtoa.EmailLog;
import com.rh.i_mes_plus.service.gtoa.IEmailLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2020-10-21 19:42:58
 */
@Slf4j
@Service
public class EmailLogServiceImpl extends ServiceImpl<EmailLogMapper, EmailLog> implements IEmailLogService {
    @Resource
    private EmailLogMapper emailLogMapper;
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
        return emailLogMapper.findList(pages, params);
    }
}
