package com.hdfs_cloud.cloud.config;

import com.hdfs_cloud.cloud.component.LoginHandlerInterceptor;
import com.hdfs_cloud.cloud.component.MyLocaleResolver;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    //重新设置css所在目录
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
    //路径映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("hdfsweb");
        registry.addViewController("/register.html").setViewName("register");



    }
    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/login.html",
                "/index.html",
                "/register.html",
                "/",
                "/login",
                "/register",
                "/img/bootstrap-solid.svg",
                "/js/*.js",
                "/css/*.css",
                "/webjars/**"
                );

    }

    //国际化设置
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }
    /**
     * 文件上传临时路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("/tmp/tomcat");
        return factory.createMultipartConfig();
    }


}