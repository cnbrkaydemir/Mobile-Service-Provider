package com.mobile_service_provider.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class HttpInterceptor implements HandlerInterceptor {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        response.setHeader("user_name", auth.getName());
        String message = "User with credentials "+auth.getName()+" made a "+request.getMethod()+" request on path "+request.getServletPath()+".";
        kafkaTemplate.send("request-response", message);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String message= request.getMethod()+" "+request.getServletPath()+" returned with status code:"+" "+response.getStatus()+". ";
        kafkaTemplate.send("request-response",message);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
