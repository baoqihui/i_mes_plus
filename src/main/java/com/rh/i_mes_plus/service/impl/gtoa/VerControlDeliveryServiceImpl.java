package com.rh.i_mes_plus.service.impl.gtoa;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.gtoa.VerControlDeliveryMapper;
import com.rh.i_mes_plus.model.gtoa.VerControlDelivery;
import com.rh.i_mes_plus.service.gtoa.IVerControlDeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 不允许发货版本控制
 *
 * @author hbq
 * @date 2020-11-02 18:30:05
 */
@Slf4j
@Service
public class VerControlDeliveryServiceImpl extends ServiceImpl<VerControlDeliveryMapper, VerControlDelivery> implements IVerControlDeliveryService {
    @Resource
    private VerControlDeliveryMapper verControlDeliveryMapper;
    @Value("${zhaoIpAndPort}")
    private String zhaoIpAndPort;
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
        return verControlDeliveryMapper.findList(pages, params);
    }
    public Result ecnVerControlInfo( Map<String, Object> params)  {
        String param = JSONUtil.toJsonStr(params);
        log.info("版本控制更新参数",param);
        String result= HttpRequest.post(zhaoIpAndPort+"/ApiEcnVerControlInfo")
                .body(param).execute().body();
        System.out.println(result);
        //JSONObject jsonObject = JSONUtil.parseObj("{"+result+"}");
        return Result.succeed("成功");
    }
}
