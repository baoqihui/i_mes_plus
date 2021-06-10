package com.rh.i_mes_plus.service.impl.gtoa;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.common.service.FileManageService;
import com.rh.i_mes_plus.mapper.gtoa.EcnDocInfoMapper;
import com.rh.i_mes_plus.model.gtoa.*;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.gtoa.*;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * 客户文件
 *
 * @author hbq
 * @date 2020-10-19 18:59:55
 */
@Slf4j
@Service
public class EcnDocInfoServiceImpl extends ServiceImpl<EcnDocInfoMapper, EcnDocInfo> implements IEcnDocInfoService {
    @Resource
    private EcnDocInfoMapper ecnDocInfoMapper;
    @Autowired
    private IPdtStandardFileInfoService pdtStandardFileInfoService;
    @Autowired
    private FileManageService fileManageService;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    @Lazy
    private IEmailConfigService emailConfigService;
    @Autowired
    private IEmailLogService emailLogService;
    @Autowired
    @Lazy
    private IEcnDetailInfoService ecnDetailInfoService;
    @Autowired
    @Lazy
    private IEcnDetailDiscriptionInfoService ecnDetailDiscriptionInfoService;
    @Autowired
    private ITimeLogService timeLogService;
    @Autowired
    private IUmsDepaService umsDepaService;
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
        return ecnDocInfoMapper.findList(pages, params);
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveFile(MultipartFile file, String ecnNoCust, String modelName, String customer,String ecnNo,String createName,Boolean quickCloseFlag) {
        try{
            /**
             * 客户文件
             */
            String filePath = fileManageService.uploadToNginxForDownload(file, modelName);
            EcnDocInfo ecnDocInfo = new EcnDocInfo();
            ecnDocInfo.setEcnNo(ecnNo);
            ecnDocInfo.setEcnNoCust(ecnNoCust);
            ecnDocInfo.setCustomer(customer);
            ecnDocInfo.setModelName(modelName);
            ecnDocInfo.setFilePath(filePath);
            ecnDocInfo.setCreateName(createName);
            ecnDocInfo.setQuickCloseFlag(quickCloseFlag);
            ecnDocInfoMapper.insert(ecnDocInfo);
            /** 添加时间节点*/
            timeLogService.save(new TimeLog(ecnNo, SysConst.EXESTATE.NEW,"新建",new Date()));
            
            /**
             * 机型任务
             */
            PdtStandardFileInfo pdtStandardFileInfo = new PdtStandardFileInfo();
            pdtStandardFileInfo.setEcnNo(ecnNo);
            pdtStandardFileInfo.setEcn(ecnDocInfo.getFilePath());
            pdtStandardFileInfo.setModelName(modelName);
            pdtStandardFileInfo.setValidFlag(2);
            pdtStandardFileInfoService.save(pdtStandardFileInfo);
            /**
             * 邮件通知
             */
            Map<String,Object> depasMap=new HashMap<>();
            List<String> umsDepas=umsDepaService.getDepaexclude();
            depasMap.put("umsDepas",umsDepas);
            return emailConfigService.sendEmail(1L,ecnNo,depasMap);
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result delete(Long id) {
        try{
            EcnDocInfo ecnDocInfo = getById(id);
            String ecnNo = ecnDocInfo.getEcnNo();
            pdtStandardFileInfoService.remove(new QueryWrapper<PdtStandardFileInfo>().eq("ecn_no",ecnNo));
            EcnDetailInfo ecnDetailInfo = ecnDetailInfoService.getOne(new QueryWrapper<EcnDetailInfo>().eq("ecn_no", ecnNo));
            if (ecnDetailInfo != null) {
                Long ediId = ecnDetailInfo.getId();
                ecnDetailDiscriptionInfoService.remove(new QueryWrapper<EcnDetailDiscriptionInfo>()
                        .eq("edi_id",ediId)
                );
                ecnDetailInfoService.removeById(ediId);
            }
            removeById(id);
            
            return Result.succeed("删除成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateFile(MultipartFile file, Long id, String ecnNoCust, String modelName, String customer, String ecnNo, String createName,Boolean quickCloseFlag) {
        try{
            /**
             * 客户文件
             */
            String filePath = fileManageService.uploadToNginxForDownload(file, modelName);
            EcnDocInfo ecnDocInfo = new EcnDocInfo();
            ecnDocInfo.setId(id);
            ecnDocInfo.setEcnNo(ecnNo);
            ecnDocInfo.setEcnNoCust(ecnNoCust);
            ecnDocInfo.setCustomer(customer);
            ecnDocInfo.setModelName(modelName);
            ecnDocInfo.setFilePath(filePath);
            ecnDocInfo.setUpdateName(createName);
            ecnDocInfo.setQuickCloseFlag(quickCloseFlag);
            ecnDocInfoMapper.updateById(ecnDocInfo);
            /**
             * 机型任务
             */
            pdtStandardFileInfoService.remove(new QueryWrapper<PdtStandardFileInfo>().eq("ecn_no",ecnNo));
            PdtStandardFileInfo pdtStandardFileInfo = new PdtStandardFileInfo();
            pdtStandardFileInfo.setEcnNo(ecnNo);
            pdtStandardFileInfo.setEcn(ecnDocInfo.getFilePath());
            pdtStandardFileInfo.setModelName(modelName);
            pdtStandardFileInfo.setValidFlag(2);
            pdtStandardFileInfoService.save(pdtStandardFileInfo);
            
            /**
             * 邮件通知
             */
            EmailConfig emailConfig = emailConfigService.getById(1);
            if (emailConfig.getStatus()){
                Map<String,Object> map=new HashMap<>();
                List<String> umsDepas=umsDepaService.getDepaexclude();
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
                EcnDocInfo ecnDocInfo1 = getOne(new QueryWrapper<EcnDocInfo>().eq("ecn_no", ecnNo));
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
            return Result.succeed( "保存成功，邮件未发送");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }
    
    @Override
    public void updateExeStateAndTimeLog(String ecnNo, Integer exeState) {
        update(new UpdateWrapper<EcnDocInfo>()
                .eq("ecn_no",ecnNo)
                .set("exe_state", exeState)
        );
        /** 修改时间节点 */
        timeLogService.update(new UpdateWrapper<TimeLog>()
                .eq("ecn_no", ecnNo)
                .eq("status", exeState)
                .set("time",new Date())
        );
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result close(Map<String, Object> map) {
        try{
            String ecnNo = MapUtil.getStr(map, "ecnNo");
            String batch = MapUtil.getStr(map, "batch");
            EcnDocInfo ecnDocInfo = getOne(new QueryWrapper<EcnDocInfo>().eq("ecn_no", ecnNo).eq("is_del",0));
            EcnDetailInfo ecnDetailInfo = ecnDetailInfoService.getOne(new QueryWrapper<EcnDetailInfo>().eq("ecn_no", ecnNo));
            String modelName = ecnDocInfo.getModelName();
            if (ecnDocInfo==null||ecnDetailInfo==null){
                return Result.failed("无此ECN");
            }
            if (ecnDetailInfo.getHasFai()){
                if (!SysConst.EXESTATE.FAI.equals(ecnDocInfo.getExeState())){
                    return Result.failed("该ECN未达可关结状态");
                }
            }else if (!SysConst.EXESTATE.QA_ACCEPTED.equals(ecnDocInfo.getExeState())){
                return Result.failed("该ECN未达可关结状态");
            }
            PdtStandardFileInfo fileInfo = pdtStandardFileInfoService.getOne(new QueryWrapper<PdtStandardFileInfo>()
                    .eq("ecn_no", ecnNo)
                    .eq("valid_flag", 2)
                    .eq("is_del", 0)
            );
            List<EcnDocInfo> list=getEcnDocInfoList(modelName);
            
            if (list.size()>1){
                EcnDocInfo existEcnDocInfo = list.get(list.size() - 2);
                if (existEcnDocInfo != null) {
                    String existEcnNo = existEcnDocInfo.getEcnNo();
                    //该机型录入过ecn
                    if (!SysConst.EXESTATE.HAS_DEL.equals(existEcnDocInfo.getExeState())){
                        //已录入ecn未关结
                        return Result.failed(existEcnNo,"该机型存在尚未关结ECN；编号："+existEcnNo+"请先关结");
                    }else {
                        /**
                         * 已录入ecn已关结，
                         * 1.需要复制原始文件到不为空的字段
                         * 2.更改原ecn的文件为已弃用
                         * 3.将新的ecn文件改为在用状态
                         */
                        
                        PdtStandardFileInfo existFileInfo = pdtStandardFileInfoService.getOne(new QueryWrapper<PdtStandardFileInfo>()
                                .eq("ecn_no", existEcnNo)
                                .eq("valid_flag", 1)
                                .eq("is_del", 0)
                        );
                        if (existFileInfo!=null){
                            
                            fileInfo.setVer(fileInfo.getVer()==null?existFileInfo.getVer():fileInfo.getVer());
                            fileInfo.setBom(fileInfo.getBom()==null?existFileInfo.getBom():fileInfo.getBom());
                            fileInfo.setFeelder(fileInfo.getFeelder()==null?existFileInfo.getFeelder():fileInfo.getFeelder());
                            fileInfo.setInstruction(fileInfo.getInstruction()==null?existFileInfo.getInstruction():fileInfo.getInstruction());
                            fileInfo.setEcn(fileInfo.getEcn()==null?existFileInfo.getEcn():fileInfo.getEcn());
                            fileInfo.setFai(fileInfo.getFai()==null?existFileInfo.getFai():fileInfo.getFai());
                            fileInfo.setMark(fileInfo.getMark()==null?existFileInfo.getMark():fileInfo.getMark());
                            fileInfo.setGerber(fileInfo.getGerber()==null?existFileInfo.getGerber():fileInfo.getGerber());
                            fileInfo.setPlace(fileInfo.getPlace()==null?existFileInfo.getPlace():fileInfo.getPlace());
                            fileInfo.setDfm(fileInfo.getDfm()==null?existFileInfo.getDfm():fileInfo.getDfm());
                            fileInfo.setGuidance(fileInfo.getGuidance()==null?existFileInfo.getGuidance():fileInfo.getGuidance());
                            fileInfo.setRework(fileInfo.getRework()==null?existFileInfo.getRework():fileInfo.getRework());
                            fileInfo.setFile3(fileInfo.getFile3()==null?existFileInfo.getFile3():fileInfo.getFile3());
                            
                            //更新原ecn文件状态
                            existFileInfo.setValidFlag(0);
                            pdtStandardFileInfoService.updateById(existFileInfo);
                        }
                    }
                    //更新原ecn状态
                    existEcnDocInfo.setExeState(SysConst.EXESTATE.ABANDON);
                    updateById(existEcnDocInfo);
                }
                
            }
            
            fileInfo.setValidFlag(1);
            //更新新的ecn文件
            pdtStandardFileInfoService.updateById(fileInfo);
            
            //更新新的ecn状态
            updateExeStateAndTimeLog(ecnNo,SysConst.EXESTATE.HAS_DEL);
            
            //更新ecn详情批次信息
            ecnDetailInfo.setBatch(batch);
            ecnDetailInfoService.updateById(ecnDetailInfo);
            return Result.succeed("保存成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }
    
    private List<EcnDocInfo> getEcnDocInfoList(String modelName) {
        return ecnDocInfoMapper.getEcnDocInfoList(modelName);
    }
    
    @Override
    public Result quickClose(Map<String, Object> map) {
        String ecnNo = MapUtil.getStr(map, "ecnNo");
        updateExeStateAndTimeLog(ecnNo,SysConst.EXESTATE.QUICK_CLOSE);
        return Result.succeed("保存成功");
    }
    
    @Override
    public Map<String, Object> selCustomerAndBeginDateByEcnNo(String ecnNo) {
        return ecnDocInfoMapper.selCustomerAndBeginDateByEcnNo(ecnNo);
    }
    
    @Override
    public EcnDocInfo getMaxEcnNo(String prefix) {
        return ecnDocInfoMapper.getMaxEcnNo(prefix);
    }
    
    @Override
    public EcnDocInfo getOneByEcnNo(String ecnNo) {
        return ecnDocInfoMapper.getOneByEcnNo(ecnNo);
    }
}
