package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.ums.UmsItemSapMapper;
import com.rh.i_mes_plus.model.ums.UmsItemMes;
import com.rh.i_mes_plus.model.ums.UmsItemSap;
import com.rh.i_mes_plus.model.other.SapMesLog;
import com.rh.i_mes_plus.service.ums.IUmsItemSapService;
import com.rh.i_mes_plus.service.other.ISapMesLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 物料信息（sap获取）
 *
 * @author hqb
 * @date 2020-09-21 11:13:27
 */
@Slf4j
@Service
public class UmsItemSapServiceImpl extends ServiceImpl<UmsItemSapMapper, UmsItemSap> implements IUmsItemSapService {
    @Resource
    private UmsItemSapMapper umsItemSapMapper;
    @Autowired
    private ISapMesLogService sapMesLogService;
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
        Page<UmsItemSap> pages = new Page<>(pageNum, pageSize);
        return umsItemSapMapper.findList(pages, params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveAll(List<UmsItemSap> umsItemSaps) {
        try{
            List<String> s=new ArrayList<>();
            List<String> f=new ArrayList<>();
            Map<String,List<String>> resultMap=new HashMap();
            for (UmsItemSap umsItemSap : umsItemSaps) {
                UmsItemSap existItemSap = getOne(new QueryWrapper<UmsItemSap>()
                        .eq("co_item_code", umsItemSap.getCoItemCode())
                        .eq("is_del", 0)
                );
                boolean successFlag;
                if (existItemSap != null) {
                    successFlag = update(umsItemSap, new UpdateWrapper<UmsItemSap>()
                            .eq("co_item_code", umsItemSap.getCoItemCode())
                            .eq("is_del", 0)
                    );
                }else{
                    successFlag = save(umsItemSap);
                }
                if (successFlag){
                    s.add(umsItemSap.getCoItemCode());
                }else{
                    f.add(umsItemSap.getCoItemCode());
                    SapMesLog sapMesLog = new SapMesLog(
                            "物料主数据错误，co_item_code="+umsItemSap.getCoItemCode(),
                            "/ums/umsItemSap/save",
                            1,
                            "sap同步物料主数据出错"
                    );
                    //直接存入日志表
                    sapMesLogService.save(sapMesLog);
                }
                resultMap.put("s",s);
                resultMap.put("f",f);
            }
            return Result.succeed(resultMap,"导入成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "添加失败");
        }
    }

    @Override
    public Page<UmsItemMes> findList2(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsItemSap> pages = new Page<>(pageNum, pageSize);
        return umsItemSapMapper.findList2(pages, params);
    }
}
