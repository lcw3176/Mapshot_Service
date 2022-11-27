package com.joebrooks.mapshotservice.global.config;

import com.joebrooks.mapshotservice.global.filter.AdminAuthFilter;
import com.joebrooks.mapshotservice.global.filter.HttpRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean httpRequestFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new HttpRequestFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("*");

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean adminFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AdminAuthFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/admin/*");

        return filterRegistrationBean;
    }



}