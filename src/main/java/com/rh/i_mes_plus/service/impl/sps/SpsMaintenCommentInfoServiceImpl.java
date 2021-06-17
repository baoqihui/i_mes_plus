package com.rh.i_mes_plus.service.impl.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.sps.SpsMaintenCommentInfoMapper;
import com.rh.i_mes_plus.model.sps.SpsMaintenCommentInfo;
import com.rh.i_mes_plus.service.sps.ISpsMaintenCommentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 保养内容信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@Service
public class SpsMaintenCommentInfoServiceImpl extends ServiceImpl<SpsMaintenCommentInfoMapper, SpsMaintenCommentInfo> implements ISpsMaintenCommentInfoService {
    @Resource
    private SpsMaintenCommentInfoMapper spsMaintenCommentInfoMapper;
    @Autowired
    @Lazy
    private ISpsMaintenCommentInfoService spsMaintenCommentInfoService;
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
        return spsMaintenCommentInfoMapper.findList(pages, params);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveAll(SpsMaintenCommentInfo spsMaintenCommentInfo) {
        try{
            spsMaintenCommentInfoService.saveOrUpdate(spsMaintenCommentInfo);
            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,重复代码");
        }
    }
}
