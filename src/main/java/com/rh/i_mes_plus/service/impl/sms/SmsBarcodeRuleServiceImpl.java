package com.rh.i_mes_plus.service.impl.sms;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsBarcodeRuleDTO;
import com.rh.i_mes_plus.mapper.sms.SmsBarcodeRuleDetailMapper;
import com.rh.i_mes_plus.mapper.sms.SmsBarcodeRuleMapper;
import com.rh.i_mes_plus.model.sms.SmsBarcodeRuleDetail;
import com.rh.i_mes_plus.service.sms.ISmsBarcodeRuleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.model.sms.SmsBarcodeRule;

/**
 * 条码规则管理
 *
 * @author hbq
 * @date 2021-06-28 18:41:06
 */
@Slf4j
@Service
public class SmsBarcodeRuleServiceImpl extends ServiceImpl<SmsBarcodeRuleMapper, SmsBarcodeRule> implements ISmsBarcodeRuleService {
    @Resource
    private SmsBarcodeRuleMapper smsBarcodeRuleMapper;
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
        return smsBarcodeRuleMapper.findList(pages, params);
    }

    @Override
    public Result saveAll(SmsBarcodeRuleDTO smsBarcodeRuleDTO) {
        SmsBarcodeRule smsBarcodeRule=smsBarcodeRuleDTO;
        if (smsBarcodeRule.getIsDefault()==1){
            update(new LambdaUpdateWrapper<SmsBarcodeRule>()
                    .eq(SmsBarcodeRule::getCustCode,smsBarcodeRule.getCustCode())
                    .eq(SmsBarcodeRule::getIsDefault,1)
                    .set(SmsBarcodeRule::getIsDefault,0)
            );
        }
        saveOrUpdate(smsBarcodeRule);

        smsBarcodeRuleDetailMapper.delete(new LambdaQueryWrapper<SmsBarcodeRuleDetail>()
                .eq(SmsBarcodeRuleDetail::getRuleCode,smsBarcodeRule.getRuleCode())
        );
        List<SmsBarcodeRuleDetail> smsBarcodeRuleDetails = smsBarcodeRuleDTO.getSmsBarcodeRuleDetails();
        smsBarcodeRuleDetails.forEach(u->smsBarcodeRuleDetailMapper.insert(u));
        return Result.succeed("保存成功");
    }
}
