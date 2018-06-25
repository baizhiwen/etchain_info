//package com.lbcy.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by 吴晓冬 on 2017/8/10.
// */
//@Configuration
//public class LbcyFilterConfig
//{
//    @Bean
//    public FilterRegistrationBean corsFilter()
//    {
//        CorsFilter corsFilter = new CorsFilter();
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setName("corsFilter");
//        registrationBean.setFilter(corsFilter);
//        registrationBean.setOrder(1);
//
//        List<String> urlList = new ArrayList<>();
//        urlList.add("/*");
//        registrationBean.setUrlPatterns(urlList);
//
//        return registrationBean;
//    }
//}