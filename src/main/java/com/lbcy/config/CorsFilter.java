//package com.lbcy.config;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 支持跨域过滤器
// * Created by 吴晓冬 on 2017/8/11.
// */
//public class CorsFilter implements Filter
//{
//    private String holdPlace = "*";
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException
//    {
//
//    }
//
//    @Override
//    public void destroy()
//    {
//
//    }
//
//    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException
//    {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse response = (HttpServletResponse) res;
//
//        response.setHeader("Access-Control-Allow-Origin", holdPlace);
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
//        response.setHeader("Access-Control-Max-Age", "0");
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token, X-Auth-DEVICE");
//
//        String requestURI = req.getRequestURI();
//        String requestURL = req.getRequestURL().toString();
//        String contextPath = requestURL.substring(0, requestURL.length() - requestURI.length());
//        contextPath = contextPath + req.getContextPath();
//        request.setAttribute("contextPath", contextPath);
//        chain.doFilter(req, res);
//    }
//}
