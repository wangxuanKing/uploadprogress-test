package com.pawx.uploadprogress.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.pawx.uploadprogress.service.CustomMultipartResolver;



@Configuration
public class CustomBeanConfig implements EmbeddedServletContainerCustomizer  {  
    /** 
     * @param container 
     * @see org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer#customize(org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer) 
     */  
    public void customize(ConfigurableEmbeddedServletContainer container) {  
         container.setContextPath("/upload");  
         container.setPort(8081);  
         container.setSessionTimeout(30);  
    }  
    
    @Bean  
    public ViewResolver viewResolver() {  
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();  
        viewResolver.setViewClass(JstlView.class);  
        viewResolver.setPrefix("/WEB-INF/jsp/");  
        viewResolver.setSuffix(".jsp");  
   
        return viewResolver;  
    }  
    
    
    @Bean
    public MultipartConfigElement multipartConfig() {
    	MultipartConfigFactory factory = new MultipartConfigFactory();
    	factory.setMaxFileSize("1024KB");
    	factory.setMaxRequestSize("1024KB");
    	return factory.createMultipartConfig();
    }
     
    
}  