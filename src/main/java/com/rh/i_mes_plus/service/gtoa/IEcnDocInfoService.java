package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.EcnDocInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 客户文件
 *
 * @author hbq
 * @date 2020-10-19 18:59:55
 */
public interface IEcnDocInfoService extends IService<EcnDocInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveFile(MultipartFile file, String ecnNoCust , String modelName, String customer,String ecnNo,String createName,Boolean quickCloseFlag);

    Result delete(Long id);

    Result updateFile(MultipartFile file, Long id, String ecnNoCust, String modelName, String customer, String ecnNo, String createName,Boolean quickCloseFlag);


    void updateExeStateAndTimeLog(String ecnNo, Integer qaAccepted);

    Result close(Map<String, Object> map);
    
    Result quickClose(Map<String, Object> map);
    
    Map<String, Object> selCustomerAndBeginDateByEcnNo(String ecnNo);
    
    EcnDocInfo getMaxEcnNo(String prefix);
    
    EcnDocInfo getOneByEcnNo(String ecnNo);
}

