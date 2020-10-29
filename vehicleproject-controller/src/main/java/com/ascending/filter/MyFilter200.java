//package com.ascending.filter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//@WebFilter(filterName = "myFilter2",urlPatterns = {"/auth/*"},dispatcherTypes ={DispatcherType.REQUEST})
//public class MyFilter200 implements Filter {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse resp = (HttpServletResponse)servletResponse;
//
//        logger.info("22222222222 pre action from MyFirstFilter, remoteHost={}", req.getRemoteHost());
//        logger.info("22222222222 pre action from MyFirstFilter, remoteHost={}", req.getRequestURI());
//        logger.info("22222222222 pre action from MyFirstFilter, remoteHost={}", req.getHeader("testHeader"));
//
//        filterChain.doFilter(req,resp);
//
//        logger.info("22222222222 post action from MyFirstFilter, status={}", resp.getStatus());
//        logger.info("22222222222 post action from MyFirstFilter, Headernames={}", resp.getHeaderNames());
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
