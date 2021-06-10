package com.rh.i_mes_plus.service.impl.gtoa;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.common.service.RedisService;
import com.rh.i_mes_plus.dto.TaskExecuteInfoDTO;
import com.rh.i_mes_plus.mapper.gtoa.TaskExecuteInfoMapper;
import com.rh.i_mes_plus.model.gtoa.*;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.gtoa.*;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 任务执行状态
 *
 * @author hbq
 * @date 2020-10-29 18:10:24
 */
@Slf4j
@Service
public class TaskExecuteInfoServiceImpl extends ServiceImpl<TaskExecuteInfoMapper, TaskExecuteInfo> implements ITaskExecuteInfoService {
    @Resource
    private TaskExecuteInfoMapper taskExecuteInfoMapper;
    @Autowired
    private IPdtStandardFileInfoService pdtStandardFileInfoService;
    @Autowired
    private ITaskBaseInfoService taskBaseInfoService;
    @Autowired
    private IEcnDocInfoService ecnDocInfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IEcnDetailInfoService ecnDetailInfoService;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private IEmailConfigService emailConfigService;
    @Autowired
    private IEmailLogService emailLogService;
    @Autowired
    private IVerControlDeliveryService verControlDeliveryService;
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
        return taskExecuteInfoMapper.findList(pages, params);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result execute(TaskExecuteInfoDTO taskExecuteInfoDTO) {
        
            TaskExecuteInfo taskExecuteInfo=taskExecuteInfoDTO.getTaskExecuteInfo();
            taskExecuteInfo.setExecuteTime(new Date());
            updateById(taskExecuteInfo);
            Long id = taskExecuteInfo.getId();
            TaskExecuteInfo existTaskExecuteInfo = getById(id);
            TaskBaseInfo taskBaseInfo = taskBaseInfoService.getById(existTaskExecuteInfo.getTaskId());
            String ecnNo = existTaskExecuteInfo.getEcnNo();
            if (taskBaseInfo.getTaskDesc() != null) {
                pdtStandardFileInfoService.update(new UpdateWrapper<PdtStandardFileInfo>()
                        .eq("ecn_no",ecnNo)
                        .eq("is_del",0)
                        .set(taskBaseInfo.getTaskDesc(),taskExecuteInfo.getExecuteFile())
                );
            }
            List<VerControlDelivery> verControlDeliveryS = taskExecuteInfoDTO.getVerControlDeliveryS();
            if (verControlDeliveryS!=null&&verControlDeliveryS.size()>0){
                for (VerControlDelivery verControlDelivery : verControlDeliveryS) {
                    if (verControlDelivery!=null){
                        verControlDeliveryService.update(new UpdateWrapper<VerControlDelivery>()
                                .eq("model_name",verControlDelivery.getModelName())
                                .eq("ver",verControlDelivery.getVer())
                                .set("agree_flag",verControlDelivery.getAgreeFlag())
                                .set("according_for_disagree",verControlDelivery.getAccordingForDisagree())
                                .set("update_name",verControlDelivery.getUpdateName())
                        );
                        Map<String, Object> params=new HashMap<>();
                        params.put("modelName",verControlDelivery.getModelName());
                        params.put("ver",verControlDelivery.getVer());
                        params.put("validFlag",verControlDelivery.getAgreeFlag());
                        verControlDeliveryService.ecnVerControlInfo(params);
                    }
                }
            }
        try{    return Result.succeed("提交成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result audit(TaskExecuteInfo taskExecuteInfo) {
        try{
            if (taskExecuteInfo.getState()==0){
                taskExecuteInfo.setAuditTime(new Date());
                updateById(taskExecuteInfo);
                return Result.succeed("审核完成");
            }
            taskExecuteInfo.setAuditTime(new Date());
            updateById(taskExecuteInfo);
            TaskExecuteInfo existTaskExecuteInfo = getById(taskExecuteInfo.getId());
            String ecnNo = existTaskExecuteInfo.getEcnNo();
            String depaCode = existTaskExecuteInfo.getDepaCode();
            
            EcnDetailInfo detailInfo = ecnDetailInfoService.getOne(new QueryWrapper<EcnDetailInfo>().eq("ecn_no", ecnNo));
            String[] split = detailInfo.getDistributedDept().split(",");
            
            //该ecn某部门总任务数
            Integer depaTotalExeCount = Convert.toInt(redisService.get(ecnNo +":"+ depaCode),1);

            /** 全部关结时，修改ecn状态至待验收*/
            //总部门数
            Integer total=split.length;
            //部门下分配任务完成数
            int depaEndExeCount = count(new QueryWrapper<TaskExecuteInfo>()
                    .eq("ecn_no", ecnNo)
                    .eq("depa_code",depaCode)
                    .eq("state", 2));
            //如果该部门完成，该ecn子任务完成数+1
            Integer endCount = Convert.toInt(redisService.get(ecnNo + "EndExe"),0)+1;
            if (depaTotalExeCount==depaEndExeCount){
                redisService.set(ecnNo+"EndExe",endCount);
            }
            log.info("total:{}:endCount:{}",total,endCount);
            if (total==endCount){
                ecnDocInfoService.updateExeStateAndTimeLog(ecnNo,SysConst.EXESTATE.QA_ACCEPTING);
            }
            return Result.succeed("审核完成");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveAll(List<TaskExecuteInfo> taskExecuteInfos ) {
        
            TaskExecuteInfo taskExecuteInfo = taskExecuteInfos.get(0);
            String ecnNo = taskExecuteInfo.getEcnNo();
            String depaCode = taskExecuteInfo.getDepaCode();
            EcnDetailInfo detailInfo = ecnDetailInfoService.getOne(new QueryWrapper<EcnDetailInfo>().eq("ecn_no", ecnNo));
            String[] split = detailInfo.getDistributedDept().split(",");
            boolean contains = Arrays.asList(split).contains(depaCode);
            if (!contains){
                return Result.failed("该ECN未指派本部门");
            }
            Boolean a = redisService.hasKey(ecnNo + ":" + depaCode);
            if (a) {
                return Result.failed("该部门已分配过任务，不可分配");
            }
            List<String> tos=new ArrayList<>();
            for (TaskExecuteInfo t : taskExecuteInfos) {
                String executeAccount = t.getExecuteAccount();
                UmsUser user = umsUserService.getOne(new QueryWrapper<UmsUser>().eq("user_account", executeAccount));
                String email = user.getEmail();
                String userName = user.getUserName();
                if (email!=null&&!"".equals(email)){
                    tos.add(email);
                }
                t.setExecuteName(userName);
            }
            //如果只选择了无，直接创建并审核完成
            boolean b;
            if (taskExecuteInfos.size()==1&&taskExecuteInfo.getTaskId()>15&&taskExecuteInfo.getTaskId()<29){
                //分配
                taskExecuteInfo.setCreateTime(new Date());
                taskExecuteInfo.setState(0);
                b = save(taskExecuteInfo);
                //执行
                taskExecuteInfo.setExecuteName(taskExecuteInfo.getAuditName());
                taskExecuteInfo.setExecuteTime(new Date());
                taskExecuteInfo.setState(1);
                TaskExecuteInfoDTO taskExecuteInfoDTO = new TaskExecuteInfoDTO();
                taskExecuteInfoDTO.setTaskExecuteInfo(taskExecuteInfo);
                execute(taskExecuteInfoDTO);
                //审核
                taskExecuteInfo.setAuditTime(new Date());
                taskExecuteInfo.setAuditName(taskExecuteInfo.getCreateName());
                taskExecuteInfo.setState(2);
                audit(taskExecuteInfo);
                /**
                 * 查找该部门下所有用户并发送邮件
                 * */
                List<UmsUser> depaUsers = umsUserService.list(new QueryWrapper<UmsUser>().eq("depa_code", depaCode));
                if (depaUsers.size()>0){
                    tos=depaUsers.stream().map(u->u.getEmail()).collect(Collectors.toList());
                }
            }else {
                taskExecuteInfos=taskExecuteInfos.stream().filter(u->u.getTaskId()<=15L||u.getTaskId()>=29).collect(Collectors.toList());
                taskExecuteInfos.forEach(u->u.setCreateTime(new Date()));
                b = saveBatch(taskExecuteInfos);
            }
            //发送邮件
            if (tos.size()>0){
                EcnDocInfo one = ecnDocInfoService.getOne(new QueryWrapper<EcnDocInfo>().eq("ecn_no", ecnNo).eq("is_del", 0));
                Date createTime = one.getCreateTime();
                tos=tos.stream().distinct().collect(Collectors.toList());
                EmailConfig emailConfig = emailConfigService.getById(4);
                log.info("向邮箱{}发送邮件",tos);
                String context=emailConfig.getPrefix()+";&emsp;ECN单号："+ecnNo+";&emsp;"+emailConfig.getSuffix();
                MailUtil.send(tos, ecnNo+"("+one.getModelName()+")"+emailConfig.getTitle()+" "+ DateUtil.format(createTime,"yyyy/MM/dd"), context, true);
                for (String to : tos) {
                    EmailLog emailLog=new EmailLog();
                    emailLog.setRecipient(to);
                    emailLog.setTitle(emailConfig.getTitle());
                    emailLog.setContext(context);
                    emailLogService.save(emailLog);
                }
            }
            if (b){
                redisService.set(ecnNo+":"+depaCode,taskExecuteInfos.size());
            }
        try{  return Result.succeed("保存成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }

    @Override
    public List<Map> getTaskExecuteInfo(String depaCode) {
        return taskExecuteInfoMapper.getTaskExecuteInfo(depaCode);
    }
}
