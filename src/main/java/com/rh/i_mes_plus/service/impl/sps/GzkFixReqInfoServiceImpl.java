package com.rh.i_mes_plus.service.impl.sps;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.mapper.sps.GzkFixReqInfoMapper;
import com.rh.i_mes_plus.model.sps.GzkFixDetailInfo;
import com.rh.i_mes_plus.model.sps.GzkFixReqInfo;
import com.rh.i_mes_plus.service.sps.IGzkFixDetailInfoService;
import com.rh.i_mes_plus.service.sps.IGzkFixReqInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 工装治具借用记录表
 *
 * @author hbq
 * @date 2021-03-09 10:59:40
 */
@Slf4j
@Service
public class GzkFixReqInfoServiceImpl extends ServiceImpl<GzkFixReqInfoMapper, GzkFixReqInfo> implements IGzkFixReqInfoService {
    @Resource
    @Lazy
    private GzkFixReqInfoMapper gzkFixReqInfoMapper;
    @Autowired
    @Lazy
    private IGzkFixReqInfoService gzkFixReqInfoService;
    @Autowired
    @Lazy
    private IGzkFixDetailInfoService gzkFixDetailInfoService;
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
        return gzkFixReqInfoMapper.findList(pages, params);
    }

    @Override
    public Result getDocNo(Map<String, Object> map) {
        String depaCode = "REQ"+ SysConst.TYPE_CODE.GZZJ;
        String prefix=depaCode + DateUtil.format(new Date(), "yyyyMM");
        String maxDocNo=prefix+"0001";
        GzkFixReqInfo one = getOne(new QueryWrapper<GzkFixReqInfo>()
                .last("where req_no like '" + prefix + "%' order by req_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getReqNo(), prefix));
            maxDocNo=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxDocNo, "查询成功");
    }

    @Override
    public Result close(GzkFixReqInfo gzkFixReqInfo) {
        GzkFixReqInfo reqInfo = gzkFixReqInfoService.getById(gzkFixReqInfo);
        String fixNo = reqInfo.getFixNo();
        if (!"1".equals(reqInfo.getState())){
            return Result.failed( "借用单非借出状态");
        }
        gzkFixReqInfoService.update(gzkFixReqInfo,new LambdaUpdateWrapper<GzkFixReqInfo>().eq(GzkFixReqInfo::getFixNo,fixNo));

        //更改备品状态
        gzkFixDetailInfoService.update(new LambdaUpdateWrapper<GzkFixDetailInfo>()
                .eq(GzkFixDetailInfo::getFixNo,fixNo)
                .set(GzkFixDetailInfo::getState,SysConst.FIX_STATE.ZK)
        );
        return Result.succeed( "保存成功");
    }
}
