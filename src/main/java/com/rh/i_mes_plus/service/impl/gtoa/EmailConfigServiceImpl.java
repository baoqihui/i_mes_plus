package com.rh.i_mes_plus.service.impl.gtoa;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.gtoa.EmailConfigMapper;
import com.rh.i_mes_plus.model.gtoa.EcnDocInfo;
import com.rh.i_mes_plus.model.gtoa.EmailConfig;
import com.rh.i_mes_plus.model.gtoa.EmailLog;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.gtoa.IEcnDocInfoService;
import com.rh.i_mes_plus.service.gtoa.IEmailConfigService;
import com.rh.i_mes_plus.service.gtoa.IEmailLogService;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author hbq
 * @date 2020-10-21 19:50:27
 */
@Slf4j
@Service
public class EmailConfigServiceImpl extends ServiceImpl<EmailConfigMapper, EmailConfig> implements IEmailConfigService {
    @Resource
    private EmailConfigMapper emailConfigMapper;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private IEmailLogService emailLogService;
    @Autowired
    private IEcnDocInfoService ecnDocInfoService;
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
        return emailConfigMapper.findList(pages, params);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result sendEmail(Long id,String ecnNo,Map<String,Object> depasMap) {
        try{
            EmailConfig emailConfig = getById(id);
            EcnDocInfo one = ecnDocInfoService.getOne(new QueryWrapper<EcnDocInfo>().eq("ecn_no", ecnNo).eq("is_del", 0));
            Date createTime = one.getCreateTime();
            if (emailConfig.getStatus()){
                //查找所有部门管理者(2020.12.16改为所有人)
                List<UmsUser> manager = umsUserService.getManager(depasMap);
                ArrayList<String> tos =new ArrayList<>();
                if (manager != null&& manager.size()>0) {
                    for (UmsUser umsUser : manager) {
                        String email = umsUser.getEmail();
                        if (email != null&& (!"".equals(email))) {
                            log.info("向邮箱：{}发送邮件",email);
                            tos.add(email);
                        }
                    }
                }
                if (tos.size()<=0){
                    return Result.succeed( "保存成功，邮件未发送,未配置主管邮箱");
                }
                String context=emailConfig.getPrefix()+";&emsp;ECN单号："+ecnNo+";&emsp;"+emailConfig.getSuffix();
                MailUtil.send(tos, ecnNo+"("+one.getModelName()+")"+emailConfig.getTitle()+" "+ DateUtil.format(createTime,"yyyy/MM/dd"), context, true);
                for (String to : tos) {
                    EmailLog emailLog=new EmailLog();
                    emailLog.setRecipient(to);
                    emailLog.setTitle(emailConfig.getTitle());
                    emailLog.setContext(context);
                    emailLogService.save(emailLog);
                }
                log.info("编号为：{}的ECN变更单邮件已发送",ecnNo);
                return Result.succeed( "保存成功,邮件已发送");
            }
            return Result.succeed( "保存成功，邮件未发送");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }
}
