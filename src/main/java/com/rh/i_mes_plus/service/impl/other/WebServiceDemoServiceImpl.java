package com.rh.i_mes_plus.service.impl.other;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDoc;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDoc;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDoc;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.other.WebServiceDemoService;
import com.rh.i_mes_plus.service.pdt.IPdtWmsBoxBarcodeService;
import com.rh.i_mes_plus.service.pdt.IPdtWmsOutStockDocService;
import com.rh.i_mes_plus.service.pdt.IPdtWmsReceiveDocService;
import com.rh.i_mes_plus.service.sms.*;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import com.rh.i_mes_plus.vo.UmsPermissionVO;
import com.rh.i_mes_plus.vo.UmsUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author caojing
 * @create 2020-02-14-14:56
 */
@WebService(serviceName = "fileDeviceService",//对外发布的服务名
        targetNamespace = "http://service.i_mes.rh.com",//指定你想要的名称空间，通常使用使用包名反转
        endpointInterface = "com.rh.i_mes_plus.service.other.WebServiceDemoService")
@Slf4j
@Service
public class WebServiceDemoServiceImpl implements WebServiceDemoService {
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private ISmsWmsReceiveDocService smsWmsReceiveDocService;
    @Autowired
    private ISmsWmsOutStockDocService smsWmsOutStockDocService;
    @Autowired
    private ISmsWmsReloadDocService smsWmsReloadDocService;
    @Autowired
    private ISmsWmsMoveDocService smsWmsMoveDocService;
    @Autowired
    private IPdtWmsBoxBarcodeService pdtWmsBoxBarcodeService;
    @Autowired
    private IPdtWmsOutStockDocService pdtWmsOutStockDocService;
    @Autowired
    private IPdtWmsReceiveDocService pdtWmsReceiveDocService;
    @Autowired
    private ISmsWmsBarcodeInfoService smsWmsBarcodeInfoService;
    @Autowired
    private ISmsWmsStockInfoService smsWmsStockInfoService;
    @Override
    public String login(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda用户登录：{}",map);
        String userAccount = MapUtil.getStr(map,"userAccount");
        String userPwd = MapUtil.getStr(map,"userPwd");
        userPwd= SecureUtil.md5(userPwd);
        UmsUser umsUser = new UmsUser();
        umsUser.setUserAccount(userAccount);
        umsUser.setUserPwd(userPwd);
        Result<UmsUserVO> result = umsUserService.pdaLogin(umsUser);
        if (result.getResp_code()==1){
            return "1";
        }
        List<UmsPermissionVO> umsPermissionVOS = result.getDatas().getUmsPermissionVOS();
        if (umsPermissionVOS==null||umsPermissionVOS.size()<=0){
            return "1";
        }
        List<String> tos=umsPermissionVOS.stream().map(u->u.getHref()).collect(Collectors.toList());
        if (tos.contains("CangKuPDA")&&tos.contains("BaoZhuangPDA")){
            return "4";
        }
        if (tos.contains("CangKuPDA")){
            return "2";
        }
        if (tos.contains("BaoZhuangPDA")){
            return "3";
        }
        return "1";
    }
    
    @Override
    public List<String> getReceiveList(String dtCode) {
        Map<String, Object> map=new HashMap<>();
        map.put("dtCode",dtCode);
        List<String> receiveList=new ArrayList<>();
        receiveList.add("ALL");
        receiveList.addAll(smsWmsReceiveDocService.getReceiveList(map));
        return receiveList;
    }
    
    @Override
    public List<String> getOutList(String dtCode) {
        List<SmsWmsOutStockDoc> outStockDocs = smsWmsOutStockDocService.list(new QueryWrapper<SmsWmsOutStockDoc>()
                .eq("dt_code",dtCode)
                .ne("doc_status", 4)
                .orderByDesc("id")
        );
        List<String> outList=new ArrayList<>();
        outList.add("ALL");
        if (outStockDocs.size()>0){
            for (SmsWmsOutStockDoc outStockDoc : outStockDocs) {
                outList.add(outStockDoc.getDocNo());
            }
        }
        return outList;
    }
    
    @Override
    public String pdaReceive(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码入库：{}",map);
        Result result = smsWmsReceiveDocService.pdaReceive(map);
        return result.getResp_msg();
    }

    @Override
    public String pdaReceiveCancel(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码取消入库：{}",map);
        Result result = smsWmsReceiveDocService.pdaReceiveCancel(map);
        return result.getResp_msg();
    }

    @Override
    public String pdaOutStock(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码备料：{}",map);
        Result result = smsWmsOutStockDocService.pdaOutStock(map);
        return result.getResp_msg();
    }

    @Override
    public String pdaCancelOutStock(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码取消备料：{}",map);
        Result result = smsWmsOutStockDocService.pdaCancelOutStock(map);
        return result.getResp_msg();
    }

    @Override
    public String pdaReload(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码换料：{}",map);
        Result result = smsWmsReloadDocService.pdaReload(map);
        return result.getResp_msg();
    }
    
    @Override
    public String pdaMove(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码调拨：{}",map);
        Result result = smsWmsMoveDocService.pdaMove(map);
        return result.getResp_msg();
    }

    @Override
    public String unboxing(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码拆箱：{}",map);
        Result result = pdtWmsBoxBarcodeService.unboxing(map);
        return result.getResp_msg();
    }

    @Override
    public String boxing(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码合箱：{}",map);
        Result result = pdtWmsBoxBarcodeService.boxing(map);
        return result.getResp_msg();
    }

    @Override
    public String pdaPdtReceive(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("成品PDA箱码扫描入库：{}",map);
        Result result = pdtWmsReceiveDocService.pdaPdtReceive(map);
        return result.getResp_msg();
    }

    @Override
    public String pdaPdtOutStock(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("成品PDA箱码扫描出库：{}",map);
        Result result = pdtWmsOutStockDocService.pdaPdtOutStock(map);
        return result.getResp_msg();
    }

    @Override
    public List<String> getReceiveDocListByDtCode(String dtCode) {
        Map<String, Object> map=new HashMap<>();
        map.put("dtCode",dtCode);
        List<String> receiveList=new ArrayList<>();
        receiveList.add("ALL");
        receiveList.addAll(pdtWmsReceiveDocService.getReceiveDocListByDtCode(map));
        return receiveList;
    }

    @Override
    public List<String> getOutStockDocListByDtCode(String dtCode) {
        Map<String, Object> map=new HashMap<>();
        map.put("dtCode",dtCode);
        List<String> receiveList=new ArrayList<>();
        receiveList.add("ALL");
        receiveList.addAll(pdtWmsOutStockDocService.getOutStockDocListByDtCode(map));
        return receiveList;
    }

    @Override
    public List<String> getReloadDocListByDtCode(String dtCode) {
        List<SmsWmsReloadDoc> reloadDocs = smsWmsReloadDocService.list(new QueryWrapper<SmsWmsReloadDoc>()
                .ne("reload_status", 4)
                .orderByDesc("id")
        );
        List<String> outList=new ArrayList<>();
        outList.add("ALL");
        if (reloadDocs.size()>0){
            for (SmsWmsReloadDoc smsWmsReloadDoc : reloadDocs) {
                outList.add(smsWmsReloadDoc.getReloadNo());
            }
        }
        return outList;
    }

    @Override
    public List<String> getMoveDocListByDtCode(String dtCode) {
        List<SmsWmsMoveDoc> moveDocs = smsWmsMoveDocService.list(new QueryWrapper<SmsWmsMoveDoc>()
                .ne("move_status", 4)
                .orderByDesc("id")
        );
        List<String> outList=new ArrayList<>();
        outList.add("ALL");
        if (moveDocs.size()>0){
            for (SmsWmsMoveDoc smsWmsMoveDoc : moveDocs) {
                outList.add(smsWmsMoveDoc.getMoveNo());
            }
        }
        return outList;
    }

    @Override
    public String spilt(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码拆签：{}",map);
        Result result = smsWmsBarcodeInfoService.spilt(map);
        return result.getResp_msg();
    }

    @Override
    public String combine(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码合签：{}",map);
        Result result = smsWmsBarcodeInfoService.combine(map);
        return result.getResp_msg();
    }

    @Override
    public String putAway(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码上架：{}",map);
        Result result = smsWmsStockInfoService.putAway(map);
        return result.getResp_msg();
    }

    @Override
    public String soldOut(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码下架：{}",map);
        Result result = smsWmsStockInfoService.soldOut(map);
        return result.getResp_msg();
    }

    @Override
    public String getLocation(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码根据条码查库位：{}",map);
        Result result = smsWmsStockInfoService.getLocation(map);
        return result.getResp_msg();
    }

    @Override
    public String getInfoBySnAndQty(String param) {
        Map<String,Object> map=(Map) JSON.parse(param);
        log.info("pda扫码根据条码查库位：{}",map);
        Result result = smsWmsStockInfoService.getInfoBySnAndQty(map);
        return result.getResp_msg();
    }
}