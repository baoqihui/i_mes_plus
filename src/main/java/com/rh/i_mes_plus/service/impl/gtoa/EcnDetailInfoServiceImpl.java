package com.rh.i_mes_plus.service.impl.gtoa;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.dto.EcnDetailInfoDTO;
import com.rh.i_mes_plus.dto.EcnDetailInfoTwoDTO;
import com.rh.i_mes_plus.mapper.gtoa.EcnDetailInfoMapper;
import com.rh.i_mes_plus.model.gtoa.*;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.gtoa.*;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

/**
 * ECN详情表
 *
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
@Slf4j
@Service
public class EcnDetailInfoServiceImpl extends ServiceImpl<EcnDetailInfoMapper, EcnDetailInfo> implements IEcnDetailInfoService {
    @Resource
    private EcnDetailInfoMapper ecnDetailInfoMapper;
    @Autowired
    @Lazy
    private IEcnDetailDiscriptionInfoService ecnDetailDiscriptionInfoService;
    @Autowired
    private IEcnDetailAuditLogService ecnDetailAuditLogService;
    @Autowired
    @Lazy
    private IEcnDocInfoService ecnDocInfoService;
    @Autowired
    private IVerControlDeliveryService verControlDeliveryService;
    @Autowired
    private ITimeLogService timeLogService;
    @Autowired
    private IEmailConfigService emailConfigService;
    @Value("${zhaoIpAndPort}")
    private String zhaoIpAndPort;
    @Autowired
    private IEmailLogService emailLogService;
    @Autowired
    private IUmsUserService umsUserService;
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
        return ecnDetailInfoMapper.findList(pages, params);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveOrUpdateAll(EcnDetailInfoDTO ecnDetailInfoDTO) {
        try{
            /**
             * 保存详细
             */
            EcnDetailInfo ecnDetailInfo = new EcnDetailInfo();
            BeanUtil.copyProperties(ecnDetailInfoDTO,ecnDetailInfo);
            saveOrUpdate(ecnDetailInfo);
            String ecnNo = ecnDetailInfo.getEcnNo();
            EcnDocInfo ecnDocInfo = ecnDocInfoService.getOne(new QueryWrapper<EcnDocInfo>().eq("ecn_no", ecnNo));
            String modelName = ecnDocInfo.getModelName();
            Boolean quickCloseFlag = ecnDocInfo.getQuickCloseFlag();
            /** ecn单执行状态到 草稿 */

            /**
             * 删除原变更描述，并填加新描述
             */
            Long ediId = ecnDetailInfo.getId();
            ecnDetailDiscriptionInfoService.remove(new QueryWrapper<EcnDetailDiscriptionInfo>().eq("edi_id",ediId));
            List<EcnDetailDiscriptionInfo> ecnDetailDiscriptionInfos = ecnDetailInfoDTO.getEcnDetailDiscriptionInfos();
            ecnDetailDiscriptionInfos.forEach(u->u.setEdiId(ediId));
            ecnDetailDiscriptionInfoService.saveBatch(ecnDetailDiscriptionInfos);

            if (ecnDetailInfo.getAuditStatus()==0){
                ecnDocInfo.setExeState(SysConst.EXESTATE.DRAFT);
                ecnDocInfoService.updateById(ecnDocInfo);
            }
            /** ecn单执行状态到 审核中 */
            if (ecnDetailInfo.getAuditStatus()==1){
                ecnDocInfoService.updateExeStateAndTimeLog(ecnNo,SysConst.EXESTATE.SUBMITTED);
                for (EcnDetailDiscriptionInfo ecnDetailDiscriptionInfo : ecnDetailDiscriptionInfos) {
                    String itemName = ecnDetailDiscriptionInfo.getItemName();
                    /**
                     * 是否需要fai
                     * */
                    if("PCBA版本".equals(itemName)){
                        if (!quickCloseFlag){
                            Map<String, Object> params=new HashMap<>();
                            params.put("ver",ecnDetailDiscriptionInfo.getAfterChange());
                            params.put("modelName",modelName);
                            String param = JSONUtil.toJsonStr(params);
                            String result= HttpRequest.post(zhaoIpAndPort+"/ApiEcnCheckFai").body(param).execute().body();
                            JSONObject jsonObject = JSONUtil.parseObj(result);
                            System.out.println(result);
                            String flag = jsonObject.get("FAI").toString();
                            /**添加时间日志*/
                            List<TimeLog> timeLogs=new ArrayList<>();
                            timeLogs.add(new TimeLog(ecnNo,SysConst.EXESTATE.SUBMITTED,"ECR待审核"));
                            timeLogs.add(new TimeLog(ecnNo,SysConst.EXESTATE.APPROVED,"ECR审核通过"));
                            timeLogs.add(new TimeLog(ecnNo,SysConst.EXESTATE.SUB_EXECUTION,"子任务执行中"));
                            timeLogs.add(new TimeLog(ecnNo,SysConst.EXESTATE.QA_ACCEPTING,"QA待验收"));
                            timeLogs.add(new TimeLog(ecnNo,SysConst.EXESTATE.QA_ACCEPTED,"QA验收通过"));
                            if ("Y".equals(flag)){
                                timeLogs.add(new TimeLog(ecnNo,SysConst.EXESTATE.FAI,"FAI"));
                                update(new UpdateWrapper<EcnDetailInfo>().eq("ecn_no",ecnNo).set("has_fai",1));
                            }
                            timeLogs.add(new TimeLog(ecnNo,SysConst.EXESTATE.HAS_DEL,"已导入(关结)"));
                            timeLogService.saveBatch(timeLogs);

                        }else {
                            /**添加时间日志*/
                            List<TimeLog> timeLogs=new ArrayList<>();
                            timeLogs.add(new TimeLog(ecnNo,SysConst.EXESTATE.SUBMITTED,"ECR待审核"));
                            timeLogs.add(new TimeLog(ecnNo,SysConst.EXESTATE.APPROVED,"ECR审核通过"));
                            timeLogs.add(new TimeLog(ecnNo,SysConst.EXESTATE.QUICK_CLOSE,"已导入(快速关结)"));
                            timeLogService.saveBatch(timeLogs);
                        }
                    }
                    /**
                     * 不允许发货版本控制
                     * */
                    if("PCBA版本".equals(itemName)){
                        VerControlDelivery verControlDelivery = new VerControlDelivery();
                        verControlDelivery.setModelName(modelName);
                        verControlDelivery.setVer(ecnDetailDiscriptionInfo.getAfterChange());
                        VerControlDelivery one = verControlDeliveryService.getOne(new QueryWrapper<VerControlDelivery>()
                                .eq("model_name", modelName)
                                .eq("ver", ecnDetailDiscriptionInfo.getAfterChange())
                                .eq("is_del", 0));
                        if (one==null){
                            verControlDeliveryService.save(verControlDelivery);
                        }
                    }
                }
                /**
                 * 邮件通知
                 */
                EmailConfig emailConfig = emailConfigService.getById(5);
                if (emailConfig.getStatus()){
                    Map<String,Object> map=new HashMap<>();
                    List<String> umsDepas=new ArrayList<>();
                    umsDepas.add("T1321");
                    map.put("umsDepas",umsDepas);
                    //查找所有部门管理者(2020.12.16改为所有人)
                    List<UmsUser> manager = umsUserService.getManager(map);
                    ArrayList<String> tos =new ArrayList<>();
                    if (manager != null&& manager.size()>0) {
                        for (UmsUser umsUser : manager) {
                            String email = umsUser.getEmail();
                            if (email != null&& (!"".equals(email))) {
                                System.out.println("向邮箱："+email+"发送邮件");
                                tos.add(email);
                            }
                        }
                    }
                    EcnDocInfo ecnDocInfo1 = ecnDocInfoService.getOne(new QueryWrapper<EcnDocInfo>().eq("ecn_no", ecnNo));
                    String context=emailConfig.getPrefix()+";&emsp;ECN单号："+ecnNo+";&emsp;"+emailConfig.getSuffix();
                    MailUtil.send(tos, ecnNo+"("+ecnDocInfo1.getModelName()+")"+emailConfig.getTitle()+" "+ DateUtil.format(ecnDocInfo1.getCreateTime(),"yyyy/MM/dd"), context, true);
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
            }
            return Result.succeed("保存或更新成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result audit(EcnDetailInfoTwoDTO ecnDetailInfoTwoDTO) {
        try{
            EcnDetailInfo ecnDetailInfo = ecnDetailInfoTwoDTO;
            ecnDetailInfo.setAuditTime(new Date());
            EcnDetailInfo detailInfo = getById(ecnDetailInfo.getId());
            ecnDetailInfo.setValidBeginDate(detailInfo.getValidBeginDate());
            updateById(ecnDetailInfo);
            EcnDetailInfo ecnDetailInfo1 = getById(ecnDetailInfo.getId());
            String ecnNo = ecnDetailInfo1.getEcnNo();

            /** 添加审核日志*/
            EcnDetailAuditLog ecnDetailAuditLog = new EcnDetailAuditLog();
            BeanUtils.copyProperties(ecnDetailInfo1,ecnDetailAuditLog);
            ecnDetailAuditLog.setId(null);
            ecnDetailAuditLogService.save(ecnDetailAuditLog);

            if (ecnDetailInfo1.getAuditStatus()==2){
                ecnDocInfoService.updateExeStateAndTimeLog(ecnNo,SysConst.EXESTATE.APPROVED);
                Map<String,Object> depasMap=new HashMap<>();
                List<String> umsDepas=Arrays.asList(ecnDetailInfo1.getDistributedDept().split(","));
                depasMap.put("umsDepas",umsDepas);
                //发送邮件
                return emailConfigService.sendEmail(2l,ecnNo,depasMap);
            }
            if (ecnDetailInfo1.getAuditStatus()==3){
                ecnDocInfoService.update(new UpdateWrapper<EcnDocInfo>().eq("ecn_no",ecnNo).set("exe_state",ecnDetailInfoTwoDTO.getRollBackStep()));
                timeLogService.remove(new QueryWrapper<TimeLog>().eq("ecn_no", ecnNo).gt("status",ecnDetailInfoTwoDTO.getRollBackStep()));
                if (ecnDetailInfoTwoDTO.getRollBackStep()==0){
                    remove(new QueryWrapper<EcnDetailInfo>().eq("ecn_no",ecnNo));
                    Long ediId = ecnDetailInfo1.getId();
                    ecnDetailDiscriptionInfoService.remove(new QueryWrapper<EcnDetailDiscriptionInfo>().eq("edi_id",ediId));
                }
            }
           return Result.succeed("审核成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result perfect(EcnDetailInfo ecnDetailInfo) {
        try{
            ecnDetailInfo.setValidTime(new Date());
            updateById(ecnDetailInfo);
            EcnDetailInfo ecnDetailInfo1 = getById(ecnDetailInfo.getId());
            /**完善时间后主任务到子任务执行状态*/
            ecnDocInfoService.updateExeStateAndTimeLog(ecnDetailInfo1.getEcnNo(),SysConst.EXESTATE.SUB_EXECUTION);
            Map<String,Object> depasMap=new HashMap<>();
            List<String> umsDepas=Arrays.asList(ecnDetailInfo1.getDistributedDept().split(","));
            depasMap.put("umsDepas",umsDepas);
            return emailConfigService.sendEmail(3l,ecnDetailInfo1.getEcnNo(),depasMap);
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }
}
