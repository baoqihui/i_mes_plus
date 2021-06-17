package com.rh.i_mes_plus.service.impl.sps;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.dto.SpsEngFixReqInfoDTO;
import com.rh.i_mes_plus.mapper.sps.SpsEngFixReqInfoMapper;
import com.rh.i_mes_plus.model.sps.*;
import com.rh.i_mes_plus.service.sps.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工程治具借用记录表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@Service
public class SpsEngFixReqInfoServiceImpl extends ServiceImpl<SpsEngFixReqInfoMapper, SpsEngFixReqInfo> implements ISpsEngFixReqInfoService {
    @Resource
    @Lazy
    private SpsEngFixReqInfoMapper spsEngFixReqInfoMapper;
    @Autowired
    @Lazy
    private ISpsEngFixDetailInfoService spsEngFixDetailInfoService;
    @Autowired
    @Lazy
    private ISpsEngFixReqInfoService spsEngFixReqInfoService;
    @Autowired
    @Lazy
    private ISpsEngFixReqDetailInfoService spsEngFixReqDetailInfoService;
    @Autowired
    @Lazy
    private ISpsMaintenCommentInfoService spsMaintenCommentInfoService;
    @Autowired
    @Lazy
    private ISpsMaintenLogInfoService spsMaintenLogInfoService;
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
        return spsEngFixReqInfoMapper.findList(pages, params);
    }

    @Override
    public Result getDocNo(Map<String, Object> map) {
        String depaCode = "REQ"+SysConst.TYPE_CODE.GCZJ;
        String prefix=depaCode + DateUtil.format(new Date(), "yyyyMMdd");
        String maxDocNo=prefix+"0001";
        SpsEngFixReqInfo one = getOne(new QueryWrapper<SpsEngFixReqInfo>()
                .last("where req_no like '" + prefix + "%' order by req_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getReqNo(), prefix));
            maxDocNo=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxDocNo, "查询成功");
    }

    @Override
    public Result saveAll(SpsEngFixReqInfoDTO spsEngFixReqInfoDTO) {
        SpsEngFixReqInfo spsEngFixReqInfo=spsEngFixReqInfoDTO;
        String itemTypeCode = spsEngFixReqInfo.getItemTypeCode();
        String reqNo = spsEngFixReqInfo.getReqNo();
        List<SpsMaintenCommentInfo> commentInfos = spsMaintenCommentInfoService.list(new QueryWrapper<SpsMaintenCommentInfo>().eq("item_type_code", itemTypeCode));
        String commentNames = commentInfos.stream().map(SpsMaintenCommentInfo::getCommentName).collect(Collectors.joining(",", "", ""));
        String commentCodes = commentInfos.stream().map(SpsMaintenCommentInfo::getCommentCode).collect(Collectors.joining(",", "", ""));
        System.out.println(commentNames+""+commentCodes);
        List<SpsEngFixReqDetailInfo> spsEngFixReqDetailInfos = spsEngFixReqInfoDTO.getSpsEngFixReqDetailInfos();
        if (spsEngFixReqDetailInfos==null||spsEngFixReqDetailInfos.size()<=0){
            return Result.failed("未选择备品");
        }
        String fixNo1 = spsEngFixReqDetailInfos.get(0).getFixNo();
        //计算备品待使用次数
        SpsEngFixDetailInfo detailInfo1 = spsEngFixDetailInfoService.getOne(new QueryWrapper<SpsEngFixDetailInfo>().eq("fix_no", fixNo1));
        Long pinCount = detailInfo1.getPinCount();
        //spsEngFixReqDetailInfoService.remove(new QueryWrapper<SpsEngFixReqDetailInfo>().eq("req_no",spsEngFixReqInfo.getReqNo()));
        long useTimes = spsEngFixReqInfo.getTargetQty() / spsEngFixReqDetailInfos.size() / pinCount;

        List<String> fixNos = spsEngFixReqDetailInfos.stream().map(SpsEngFixReqDetailInfo::getFixNo).collect(Collectors.toList());
        String poses = spsEngFixDetailInfoService.getPosesByFixNos(fixNos);
        spsEngFixReqInfo.setPoses(poses);
        for (SpsEngFixReqDetailInfo spsEngFixReqDetailInfo : spsEngFixReqDetailInfos) {
            //保存借出详情
            spsEngFixReqDetailInfo.setUseTimes(useTimes);
            spsEngFixReqDetailInfoService.save(spsEngFixReqDetailInfo);
            //更新治具状态
            String fixNo = spsEngFixReqDetailInfo.getFixNo();
            spsEngFixDetailInfoService.update(new UpdateWrapper<SpsEngFixDetailInfo>()
                    .eq("fix_no", fixNo)
                    .set("state", SysConst.FIX_STATE.JC)
            );
            //生成保养记录
            SpsMaintenLogInfo spsMaintenLogInfo = new SpsMaintenLogInfo();
            spsMaintenLogInfo.setTypeCode(SysConst.TYPE_CODE.GCZJ);
            spsMaintenLogInfo.setItemTypeCode(itemTypeCode);
            spsMaintenLogInfo.setSparesNo(fixNo);
            spsMaintenLogInfo.setCycleCode("0");
            spsMaintenLogInfo.setCommentNames(commentNames);
            spsMaintenLogInfo.setCommentCodes(commentCodes);
            spsMaintenLogInfo.setReqNo(reqNo);
            spsMaintenLogInfo.setState("1");

            spsMaintenLogInfoService.save(spsMaintenLogInfo);
        }
        spsEngFixReqInfoService.save(spsEngFixReqInfo);
        return Result.succeed("保存成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result close(SpsEngFixReqInfoDTO spsEngFixReqInfoDTO) {
        try{
            SpsEngFixReqInfo spsEngFixReqInfo = spsEngFixReqInfoDTO;
            String reqNo = spsEngFixReqInfo.getReqNo();
            List<SpsEngFixReqDetailInfo> spsEngFixReqDetailInfos = spsEngFixReqInfoDTO.getSpsEngFixReqDetailInfos();

            for (SpsEngFixReqDetailInfo spsEngFixReqDetailInfo : spsEngFixReqDetailInfos) {
                SpsEngFixReqDetailInfo reqDetailInfo = spsEngFixReqDetailInfoService.getOne(new QueryWrapper<SpsEngFixReqDetailInfo>()
                        .eq("req_no", spsEngFixReqDetailInfo.getReqNo())
                        .eq("fix_no", spsEngFixReqDetailInfo.getFixNo())
                );
                //借用记录修改
                spsEngFixReqDetailInfo.setHasReq("2");
                spsEngFixReqDetailInfoService.updateById(spsEngFixReqDetailInfo);

                //备品状态修改
                String fixNo = spsEngFixReqDetailInfo.getFixNo();
                SpsEngFixDetailInfo detailInfo = spsEngFixDetailInfoService.getOne(new QueryWrapper<SpsEngFixDetailInfo>()
                        .eq("fix_no", fixNo)
                );
                if (detailInfo==null||!SysConst.FIX_STATE.BY.equals(detailInfo.getState())){
                    int i=1/0;
                }
                detailInfo.setUsedTimes(detailInfo.getUsedTimes()+reqDetailInfo.getUseTimes());
                detailInfo.setState(SysConst.FIX_STATE.ZK);
                spsEngFixDetailInfoService.updateById(detailInfo);
            }
            //当本借用单下所有归还后，单子关结
            int receiveDetailTotal = spsEngFixReqDetailInfoService.count(new QueryWrapper<SpsEngFixReqDetailInfo>().eq("req_no", reqNo));
            int receivedCount = spsEngFixReqDetailInfoService.count(new QueryWrapper<SpsEngFixReqDetailInfo>().eq("req_no", reqNo).last("and has_req='2'"));
            if (receiveDetailTotal==receivedCount){
                spsEngFixReqInfo.setState("2");
            }
            spsEngFixReqInfoService.updateById(spsEngFixReqInfo);
           return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"存在未保养备品");
        }
    }
}
