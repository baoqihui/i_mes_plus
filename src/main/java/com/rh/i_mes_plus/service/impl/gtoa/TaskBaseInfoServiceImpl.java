package com.rh.i_mes_plus.service.impl.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.gtoa.TaskBaseInfoMapper;
import com.rh.i_mes_plus.model.gtoa.TaskBaseInfo;
import com.rh.i_mes_plus.service.gtoa.ITaskBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 任务信息
 *
 * @author hbq
 * @date 2020-10-29 18:10:24
 */
@Slf4j
@Service
public class TaskBaseInfoServiceImpl extends ServiceImpl<TaskBaseInfoMapper, TaskBaseInfo> implements ITaskBaseInfoService {
    @Resource
    private TaskBaseInfoMapper taskBaseInfoMapper;
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
        return taskBaseInfoMapper.findList(pages, params);
    }

    @Override
    public Integer getDepaCount() {
        return taskBaseInfoMapper.getDepaCount();
    }
}
