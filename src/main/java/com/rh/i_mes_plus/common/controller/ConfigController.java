package com.rh.i_mes_plus.common.controller;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@Api(tags = "配置服务")
public class ConfigController {
    /**
     * 直接访问首页
     */
    @Configuration
    public class WebConfigurer implements WebMvcConfigurer {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("forward:/login.html");
            registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        }
    }

}
