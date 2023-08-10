package com.mobile_service_provider.config;

import com.mobile_service_provider.interceptor.HttpInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final HttpInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
