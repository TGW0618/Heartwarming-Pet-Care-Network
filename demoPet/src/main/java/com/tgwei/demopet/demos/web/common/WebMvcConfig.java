package com.tgwei.demopet.demos.web.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/sysOrder/**", "/ClientUserInfo/**", "/petsInfo/**") // 添加需要验证Token的路径
                .excludePathPatterns("/client/login", "/admin/login"); // 排除登录接口
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取文件上传目录
        String filePath = System.getProperty("user.dir") + "/files/";
        // 映射URL路径到本地文件目录
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + filePath);
    }
}
