package com.rh.i_mes_plus.service.impl.sps;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.mapper.sps.SpsMaintenLogInfoMapper;
import com.rh.i_mes_plus.model.sps.*;
import com.rh.i_mes_plus.service.sps.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 保养记录信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@Service
public class SpsMaintenLogInfoServiceImpl extends ServiceImpl<SpsMaintenLogInfoMapper, SpsMaintenLogInfo> implements ISpsMaintenLogInfoService {
    @Resource
    private SpsMaintenLogInfoMapper spsMaintenLogInfoMapper;
    @Autowired
    private ISpsEngFixDetailInfoService spsEngFixDetailInfoService;
    @Autowired
    private IGzkFixDetailInfoService gzkFixDetailInfoService;
    @Autowired
    private IMfgStencilsDetailInfoService mfgStencilsDetailInfoService;
    @Autowired
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
        return spsMaintenLogInfoMapper.findList(pages, params);
    }

    @Override
    public Result saveAll(SpsMaintenLogInfo spsMaintenLogInfo) {
        String sparesNo = spsMaintenLogInfo.getSparesNo();
        String typeCode = spsMaintenLogInfo.getTypeCode();
        if (SysConst.TYPE_CODE.GCZJ.equals(typeCode)){
            SpsEngFixDetailInfo detailInfo = spsEngFixDetailInfoService.getOne(new LambdaQueryWrapper<SpsEngFixDetailInfo>()
                    .eq(SpsEngFixDetailInfo::getFixNo, sparesNo)
                    .ne(SpsEngFixDetailInfo::getState,'3')
            );
            //使用外部治具号获取内部治具号填充到保养表
            String fixNo = detailInfo.getFixNo();
            spsMaintenLogInfo.setSparesNo(fixNo);
        }
        //新增时需要增加commentNames
        String commentCodes = spsMaintenLogInfo.getCommentCodes();
        if (StrUtil.isNotEmpty(commentCodes)) {
            String commentNames = "";
            String[] codes = commentCodes.split(",");
            for (String code : codes) {
                SpsMaintenCommentInfo commentInfo = spsMaintenCommentInfoService.getOne(new LambdaQueryWrapper<SpsMaintenCommentInfo>()
                        .eq(SpsMaintenCommentInfo::getCommentCode, code)
                );
                if (commentInfo!=null){
                    commentNames=commentNames+commentInfo.getCommentName()+",";
                }
            }
            commentNames = commentNames.substring(0, commentNames.length() - 1);
            spsMaintenLogInfo.setCommentNames(commentNames);
        }
        saveOrUpdate(spsMaintenLogInfo);
        //工装治具
        if (SysConst.TYPE_CODE.GZZJ.equals(typeCode)&&"2".equals(spsMaintenLogInfo.getState())){
            GzkFixDetailInfo detailInfo = gzkFixDetailInfoService.getOne(new QueryWrapper<GzkFixDetailInfo>()
                    .eq("fix_no", sparesNo)
            );
            if (detailInfo==null){
                return Result.failed("无此备具");
            }
            if (SysConst.FIX_STATE.JC.equals(detailInfo.getState())){
                detailInfo.setState(SysConst.FIX_STATE.BY);
            }
            gzkFixDetailInfoService.updateById(detailInfo);
        }
        return Result.succeed("保存成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result allMaintain(List<SpsMaintenLogInfo> spsMaintenLogInfos) {
        String fixNo="";
        try{
            for (SpsMaintenLogInfo spsMaintenLogInfo : spsMaintenLogInfos) {
                SpsMaintenLogInfo existSpsMaintenLogInfo = getById(spsMaintenLogInfo.getId());
                String typeCode = existSpsMaintenLogInfo.getTypeCode();
                String sparesNo = existSpsMaintenLogInfo.getSparesNo();
                //工程治具
                if (SysConst.TYPE_CODE.GCZJ.equals(typeCode)&&"2".equals(spsMaintenLogInfo.getState())){
                    SpsEngFixDetailInfo detailInfo = spsEngFixDetailInfoService.getOne(new QueryWrapper<SpsEngFixDetailInfo>()
                            .eq("fix_no", sparesNo)
                    );
                    if (detailInfo==null){
                        return Result.failed("无此备具");
                    }
                    if (SysConst.FIX_STATE.JC.equals(detailInfo.getState())){
                        detailInfo.setState(SysConst.FIX_STATE.BY);
                    }
                    spsEngFixDetailInfoService.updateById(detailInfo);
                }
                //工装治具
                if (SysConst.TYPE_CODE.GZZJ.equals(typeCode)&&"2".equals(spsMaintenLogInfo.getState())){
                    GzkFixDetailInfo detailInfo = gzkFixDetailInfoService.getOne(new QueryWrapper<GzkFixDetailInfo>()
                            .eq("fix_no", sparesNo)
                    );
                    if (detailInfo==null){
                        return Result.failed("无此备具");
                    }
                    if (SysConst.FIX_STATE.JC.equals(detailInfo.getState())){
                        detailInfo.setState(SysConst.FIX_STATE.BY);
                    }
                    gzkFixDetailInfoService.updateById(detailInfo);
                }
                //钢网
                if (SysConst.TYPE_CODE.GW.equals(typeCode)&&"2".equals(spsMaintenLogInfo.getState())){
                    MfgStencilsDetailInfo detailInfo = mfgStencilsDetailInfoService.getOne(new QueryWrapper<MfgStencilsDetailInfo>()
                            .eq("stencil_no", sparesNo)
                    );
                    if (detailInfo==null){
                        return Result.failed("无此钢网");
                    }
                    if (SysConst.FIX_STATE.JC.equals(detailInfo.getState())){
                        detailInfo.setState(SysConst.FIX_STATE.BY);
                    }
                    mfgStencilsDetailInfoService.updateById(detailInfo);
                }
                saveOrUpdate(spsMaintenLogInfo);
            }
            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"备具:"+fixNo+"非借出状态");
        }
    }
}
