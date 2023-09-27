package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Please explain the class!!
 *
 * @author : kma04
 * @fileName : WebMvcConfig
 * @since : 2023-09-27
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        MustacheViewResolver resolver = new MustacheViewResolver();
        resolver.setCharset("UTF-8");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setPrefix("classpath:/templates/"); //classpath: 까지는 프로젝트의 경로 의미
        resolver.setSuffix(".html"); // .html로 만들어도 .mustache로 인식하게된다.

        registry.viewResolver(resolver);
    }
}
