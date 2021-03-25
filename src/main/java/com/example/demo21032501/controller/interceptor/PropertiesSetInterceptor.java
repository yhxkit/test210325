package com.example.demo21032501.controller.interceptor;

import com.example.demo21032501.property.DirectiveWordProperty;
import com.example.demo21032501.property.UrlProperty;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PropertiesSetInterceptor implements HandlerInterceptor {

    private UrlProperty urlProperty;
    private DirectiveWordProperty directiveWordProperty;
    public PropertiesSetInterceptor(UrlProperty urlProperty, DirectiveWordProperty directiveWordProperty){
        this.urlProperty = urlProperty;
        this.directiveWordProperty = directiveWordProperty;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("Url", urlProperty);
            modelAndView.addObject("directive", directiveWordProperty);
        }
    }



}
