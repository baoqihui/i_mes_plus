package com.rh.i_mes_plus.service.impl.sms;
import cn.hutool.core.map.MapUtil;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.sms.SmsBarcodeRuleDetailMapper;
import com.rh.i_mes_plus.model.pdt.PdtReplaceItem;
import com.rh.i_mes_plus.service.sms.ISmsBarcodeRuleDetailService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.model.sms.SmsBarcodeRuleDetail;

/**
 * 条码规则详情
 *
 * @author hbq
 * @date 2021-06-28 18:41:07
 */
@Slf4j
@Service
public class SmsBarcodeRuleDetailServiceImpl extends ServiceImpl<SmsBarcodeRuleDetailMapper, SmsBarcodeRuleDetail> implements ISmsBarcodeRuleDetailService {
    @Resource
    private SmsBarcodeRuleDetailMapper smsBarcodeRuleDetailMapper;
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
        return smsBarcodeRuleDetailMapper.findList(pages, params);
    }

    @Override
    public Result presentToBefore(Map<String, Object> map) {
        Long id = MapUtil.getLong(map,"id");
        SmsBarcodeRuleDetail smsBarcodeRuleDetail = smsBarcodeRuleDetailMapper.selectById(id);
        String ruleCode = smsBarcodeRuleDetail.getRuleCode();
        Long ownerSort = smsBarcodeRuleDetail.getSort();
        SmsBarcodeRuleDetail beforeBarcodeRuleDetail = smsBarcodeRuleDetailMapper.getBefore(ownerSort,ruleCode);
        if (beforeBarcodeRuleDetail == null) {
            return Result.failed("已为顶部，无需上移");
        }
        Long beforeSort = beforeBarcodeRuleDetail.getSort();
        smsBarcodeRuleDetail.setSort(beforeSort);
        beforeBarcodeRuleDetail.setSort(ownerSort);
        smsBarcodeRuleDetailMapper.updateById(smsBarcodeRuleDetail);
        smsBarcodeRuleDetailMapper.updateById(beforeBarcodeRuleDetail);
        return Result.succeed("上移成功");
    }

    @Override
    public Result presentToAfter(Map<String, Object> map) {
        Long id = MapUtil.getLong(map,"id");
        SmsBarcodeRuleDetail smsBarcodeRuleDetail = smsBarcodeRuleDetailMapper.selectById(id);
        String ruleCode = smsBarcodeRuleDetail.getRuleCode();
        Long ownerSort = smsBarcodeRuleDetail.getSort();
        SmsBarcodeRuleDetail afterReplaceItem = smsBarcodeRuleDetailMapper.getAfter(ownerSort,ruleCode);
        if (afterReplaceItem == null) {
            return Result.failed("已为底部，无需下移");
        }
        Long afterSort = afterReplaceItem.getSort();
        smsBarcodeRuleDetail.setSort(afterSort);
        afterReplaceItem.setSort(ownerSort);
        smsBarcodeRuleDetailMapper.updateById(smsBarcodeRuleDetail);
        smsBarcodeRuleDetailMapper.updateById(afterReplaceItem);
        return Result.succeed("下移成功");
    }
}
