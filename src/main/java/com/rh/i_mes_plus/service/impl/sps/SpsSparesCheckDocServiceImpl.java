package com.rh.i_mes_plus.service.impl.sps;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SpsSparesCheckDocDTO;
import com.rh.i_mes_plus.mapper.sps.SpsSparesCheckDocMapper;
import com.rh.i_mes_plus.model.sps.SpsSparesCheckDetail;
import com.rh.i_mes_plus.model.sps.SpsSparesCheckDoc;
import com.rh.i_mes_plus.service.sps.ISpsSparesCheckDetailService;
import com.rh.i_mes_plus.service.sps.ISpsSparesCheckDocService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 盘点单信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@Service
public class SpsSparesCheckDocServiceImpl extends ServiceImpl<SpsSparesCheckDocMapper, SpsSparesCheckDoc> implements ISpsSparesCheckDocService {
    @Resource
    private SpsSparesCheckDocMapper spsSparesCheckDocMapper;
    @Autowired
    @Lazy
    private ISpsSparesCheckDetailService spsSparesCheckDetailService;
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
        return spsSparesCheckDocMapper.findList(pages, params);
    }

    @Override
    public Result getDocNo(Map<String, Object> map) {
        String typeCode = MapUtil.getStr(map,"typeCode");
        String itemTypeCode = MapUtil.getStr(map,"itemTypeCode");

        String prefix="CHE"+typeCode +itemTypeCode+ DateUtil.format(new Date(), "yyyyMM");
        String maxDocNo=prefix+"0001";
        SpsSparesCheckDoc one = getOne(new QueryWrapper<SpsSparesCheckDoc>()
                .last("where check_no like '" + prefix + "%' order by check_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getCheckNo(), prefix));
            maxDocNo=prefix+String.format("%4d",aLong+1).replace(" ","0");
        }
        return Result.succeed(maxDocNo, "查询成功");
    }

    @Override
    public Result close(SpsSparesCheckDocDTO spsSparesCheckDocDTO) {
        try{
            SpsSparesCheckDoc spsSparesCheckDoc = spsSparesCheckDocDTO;
            String checkNo = spsSparesCheckDoc.getCheckNo();
            List<SpsSparesCheckDetail> sparesCheckDetails = spsSparesCheckDocDTO.getSpsSparesCheckDetails();

            for (SpsSparesCheckDetail spsSparesCheckDetail : sparesCheckDetails) {
                SpsSparesCheckDetail existingDetail = spsSparesCheckDetailService.getOne(new LambdaQueryWrapper<SpsSparesCheckDetail>()
                        .eq(SpsSparesCheckDetail::getCheckNo, checkNo)
                        .eq(SpsSparesCheckDetail::getSparesNo, spsSparesCheckDetail.getSparesNo())
                );
                //借用记录修改
                existingDetail.setState("2");
                spsSparesCheckDetailService.updateById(existingDetail);
            }
            //当本借用单下所有归还后，单子关结
            /*int receiveDetailTotal = spsSparesCheckDetailService.count(new LambdaQueryWrapper<SpsSparesCheckDetail>().eq(SpsSparesCheckDetail::getCheckNo, checkNo));
            int receivedCount = spsSparesCheckDetailService.count(new LambdaQueryWrapper<SpsSparesCheckDetail>().eq(SpsSparesCheckDetail::getCheckNo, checkNo).last("and state='2'"));
            if (receiveDetailTotal==receivedCount){
                spsSparesCheckDoc.setState("2");
            }
            spsSparesCheckDocService.updateById(spsSparesCheckDoc);*/
            return Result.succeed("保存成功");
        }catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败");
        }
    }
}
