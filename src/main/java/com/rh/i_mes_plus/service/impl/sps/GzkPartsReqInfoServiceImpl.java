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
import com.rh.i_mes_plus.mapper.sps.GzkPartsReqInfoMapper;
import com.rh.i_mes_plus.model.sps.GzkPartsDetailInfo;
import com.rh.i_mes_plus.model.sps.GzkPartsReqInfo;
import com.rh.i_mes_plus.service.sps.IGzkPartsDetailInfoService;
import com.rh.i_mes_plus.service.sps.IGzkPartsReqInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 工装备品借用记录表
 *
 * @author hbq
 * @date 2021-02-21 10:39:50
 */
@Slf4j
@Service
public class GzkPartsReqInfoServiceImpl extends ServiceImpl<GzkPartsReqInfoMapper, GzkPartsReqInfo> implements IGzkPartsReqInfoService {
    @Resource
    private GzkPartsReqInfoMapper gzkPartsReqInfoMapper;
    @Autowired
    @Lazy
    private IGzkPartsReqInfoService gzkPartsReqInfoService;
    @Autowired
    @Lazy
    private IGzkPartsDetailInfoService gzkPartsDetailInfoService;
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
        return gzkPartsReqInfoMapper.findList(pages, params);
    }

    @Override
    public Result getDocNo(Map<String, Object> map) {
        String depaCode = "REQ"+ SysConst.TYPE_CODE.GZBP;
        String prefix=depaCode + DateUtil.format(new Date(), "yyyyMM");
        String maxDocNo=prefix+"0001";
        GzkPartsReqInfo one = getOne(new QueryWrapper<GzkPartsReqInfo>()
                .last("where req_no like '" + prefix + "%' order by req_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getReqNo(), prefix));
            maxDocNo=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxDocNo, "查询成功");
    }

    @Override
    public Result close(GzkPartsReqInfo gzkPartsReqInfo) {
        GzkPartsReqInfo reqInfo = gzkPartsReqInfoService.getById(gzkPartsReqInfo);
        String kgtNo = reqInfo.getKgtNo();
        if (!"1".equals(reqInfo.getState())){
            return Result.failed( "借用单非借出状态");
        }
        gzkPartsReqInfo.setState("2");
        gzkPartsReqInfoService.updateById(gzkPartsReqInfo);

        //更改备品状态
        gzkPartsDetailInfoService.update(new UpdateWrapper<GzkPartsDetailInfo>()
                .eq("kgt_no",kgtNo)
                .set("loc","1")
                .set("state",SysConst.FIX_STATE.ZK)
        );
        return Result.succeed( "保存成功");
    }

}
