package com.example.demo21032501;

import com.estgames.lmstool.controller.interceptor.PropertiesSetInterceptor;
import com.estgames.lmstool.property.DirectiveWordProperty;
import com.estgames.lmstool.property.UrlProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

@Configuration
@Slf4j
public class StaticResourceConfiguration implements WebMvcConfigurer {

    private UrlProperty urlProperty;
    private DirectiveWordProperty directiveWordProperty;

    public StaticResourceConfiguration(UrlProperty urlProperty, DirectiveWordProperty directiveWordProperty){
        this.urlProperty =urlProperty;
        this.directiveWordProperty = directiveWordProperty;
    }

    @Bean
    public PropertiesSetInterceptor propertiesSetInterceptor() {
        return new PropertiesSetInterceptor(urlProperty, directiveWordProperty);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(propertiesSetInterceptor());
    }


    @Bean
    public Executor taskExecutor(){
//    using @async for progress check while submit and push data in db
// for the exception of =>  org.springframework.scheduling.annotation.AnnotationAsyncExecutionInterceptor : Could not find default TaskExecutor bean
//org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.core.task.TaskExecutor' available
        return new SimpleAsyncTaskExecutor();
    }


}
