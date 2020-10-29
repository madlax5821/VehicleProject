//package com.ascending.filter;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//@WebFilter(filterName = "myFilter1",urlPatterns = {"/auth/*"},dispatcherTypes ={DispatcherType.REQUEST})
//public class MyFilter100 implements Filter {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest)servletRequest;
//        HttpServletResponse response = (HttpServletResponse)servletResponse;
//
//        logger.info("1111111111111111 pre action from MyFirstFilter, remoteHost={}",request.getSession());
//        logger.info("1111111111111111 pre action from MyFirstFilter, remoteHost={}",request.getAuthType());
//
//        filterChain.doFilter(request,response);
//
//        logger.info("111111111111111 post action form MyFirstFilter, remoteHost={}",response.getTrailerFields());
//        logger.info("111111111111111 post action form MyFirstFilter, remoteHost={}",response.getStatus());
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
