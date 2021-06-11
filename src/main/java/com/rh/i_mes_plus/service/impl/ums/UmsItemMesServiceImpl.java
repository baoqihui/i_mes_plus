package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.ums.UmsItemMesMapper;
import com.rh.i_mes_plus.model.ums.UmsItemMes;
import com.rh.i_mes_plus.model.ums.UmsItemSap;
import com.rh.i_mes_plus.service.ums.IUmsItemMesService;
import com.rh.i_mes_plus.service.ums.IUmsItemSapService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 物料信息（mes获取）
 *
 * @author hqb
 * @date 2020-09-21 11:13:27
 */
@Slf4j
@Service
public class UmsItemMesServiceImpl extends ServiceImpl<UmsItemMesMapper, UmsItemMes> implements IUmsItemMesService {
    @Resource
    private UmsItemMesMapper umsItemMesMapper;
    @Resource
    private IUmsItemSapService umsItemSapService;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsItemMes> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsItemMes> pages = new Page<>(pageNum, pageSize);
        return umsItemMesMapper.findList(pages, params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveAll(UmsItemMes umsItemMes) {
        try{
            UmsItemSap umsItemSap = umsItemSapService.getOne(new QueryWrapper<UmsItemSap>()
                    .eq("CO_ITEM_CODE", umsItemMes.getCoItemCode())
            );
            if (umsItemSap==null){
                return Result.failed( "添加失败,无此料号");
            }
            if (umsItemSap.getCoItemCode().equals(umsItemMes.getCoItemCode())
                    &&umsItemSap.getMpn().equals(umsItemMes.getMpn())
                    &&umsItemSap.getManufacturerCode().equals(umsItemMes.getManufacturerCode()))
            {
                return Result.failed( "添加失败,请检查料号是否重复");
            }
            UmsItemMes umsItemMes1 = getOne(new QueryWrapper<UmsItemMes>()
                    .eq("MPN", umsItemMes.getMpn())
                    .eq("MANUFACTURER_CODE", umsItemMes.getManufacturerCode())
                    .eq("CO_ITEM_CODE", umsItemMes.getCoItemCode())
            );
            if (umsItemMes1!=null){
                return Result.failed( "添加失败,请检查料号是否重复");
            }

            save(umsItemMes);
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "添加失败");
        }
        return Result.succeed("保存成功");
    }
}
