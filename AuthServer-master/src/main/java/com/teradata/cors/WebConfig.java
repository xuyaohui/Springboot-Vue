package com.teradata.cors;

import com.teradata.interceptor.JWTInterceptor;
import com.teradata.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //允许全部请求跨域
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST","OPTIONS")
                .allowCredentials(true).maxAge(3600);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .excludePathPatterns("/error")
                .excludePathPatterns("/search/**")
                .excludePathPatterns("/login/**")
                .addPathPatterns("/**");

        //验证有问题，待解决
//        registry.addInterceptor(new LoginInterceptor())
//                .excludePathPatterns("/error")
//                .excludePathPatterns("/login/**")
//                .addPathPatterns("/**");
    }
}
