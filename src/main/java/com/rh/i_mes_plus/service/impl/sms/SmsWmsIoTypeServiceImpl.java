package com.rh.i_mes_plus.service.impl.sms;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.sms.SmsWmsIoTypeMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsIoType;
import com.rh.i_mes_plus.service.sms.ISmsWmsIoTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 单据类型表
 *
 * @author hbq
 * @date 2021-01-19 09:52:44
 */
@Slf4j
@Service
public class SmsWmsIoTypeServiceImpl extends ServiceImpl<SmsWmsIoTypeMapper, SmsWmsIoType> implements ISmsWmsIoTypeService {
    @Resource
    private SmsWmsIoTypeMapper smsWmsIoTypeMapper;
    @Value("${liIpAndPort}")
    private String liIpAndPort;
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
        return smsWmsIoTypeMapper.findList(pages, params);
    }

    @Override
    public Result LightControl(List<Map<String, Object>> params) {
        log.info("亮灯程序参数{}",params);
        String result= HttpRequest.post(liIpAndPort+"/api/Light/LightControl").body(JSON.toJSONString(params)).execute().body();
        return Result.succeed(result,"成功");
    }
}
