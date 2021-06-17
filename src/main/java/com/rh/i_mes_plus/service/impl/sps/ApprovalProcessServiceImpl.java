package com.rh.i_mes_plus.service.impl.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.ApprovalProcessMapper;
import com.rh.i_mes_plus.model.sps.ApprovalProcess;
import com.rh.i_mes_plus.service.sps.IApprovalProcessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 流程控制
 *
 * @author hbq
 * @date 2021-02-21 16:06:24
 */
@Slf4j
@Service
public class ApprovalProcessServiceImpl extends ServiceImpl<ApprovalProcessMapper, ApprovalProcess> implements IApprovalProcessService {
    @Resource
    private ApprovalProcessMapper approvalProcessMapper;
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
        return approvalProcessMapper.findList(pages, params);
    }

    @Override
    public ApprovalProcess getOperUsrNo(Map<String, Object> params) {
        return approvalProcessMapper.getOperUsrNo(params);
    }
}
