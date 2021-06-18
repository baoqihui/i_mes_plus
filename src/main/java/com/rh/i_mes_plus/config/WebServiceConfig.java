package com.rh.i_mes_plus.config;

import com.rh.i_mes_plus.service.WebServiceDemoService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * 描述：webservice 配置类
 *  * @author caojing
 * @create 2020-02-14-14:47
 */
@Configuration
public class WebServiceConfig {
    @Autowired
    private WebServiceDemoService webServiceDemoService;

    //* 此方法被注释后:wsdl访问地址为http://127.0.0.1:8080/services/user?wsdl
    //     * 去掉注释后：wsdl访问地址为：http://127.0.0.1:8080/webservice/user?wsdl
    @Bean(name = "cxfServlet")
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/webservice/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean(name = "webServiceDemoEndPoint")
    public Endpoint webServiceDemoEndPoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), webServiceDemoService);
        endpoint.publish("/webservice");
        return endpoint;
    }
    //如果有其余的服务，继续加
}
