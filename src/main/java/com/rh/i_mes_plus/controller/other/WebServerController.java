package com.rh.i_mes_plus.controller.other;

import com.rh.i_mes_plus.service.WebServiceDemoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@CrossOrigin
@RestController
@Api(tags = "出库物料表")
public class WebServerController {
    private static String address = "http://127.0.0.1:8084/webservice/webservice?wsdl";
    @GetMapping("/show")
    public List<String> showMsg() {
        try {
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            //添加用户名密码拦截器
            //jaxWsProxyFactoryBean.getOutInterceptors().add(new LoginInterceptor("root","admin"));;
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(WebServiceDemoService.class);
            // 创建一个代理接口实现
            WebServiceDemoService service = (WebServiceDemoService) jaxWsProxyFactoryBean.create();
            // 调用代理接口的方法调用并返回结果
            return service.getReceiveList("11");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/show2")
    public String showMsg2(String userAccount,String userPwd) {
        try {
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            //添加用户名密码拦截器
            //jaxWsProxyFactoryBean.getOutInterceptors().add(new LoginInterceptor("root","admin"));;
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(WebServiceDemoService.class);
            // 创建一个代理接口实现
            WebServiceDemoService service = (WebServiceDemoService) jaxWsProxyFactoryBean.create();
            // 调用代理接口的方法调用并返回结果
            return service.login("{\"userAccount\":\""+userAccount+"\",\"userPwd\":\""+userPwd+"\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
