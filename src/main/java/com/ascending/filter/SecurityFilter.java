package com.ascending.filter;

import com.ascending.service.JWTService;
import io.jsonwebtoken.Claims;
import org.postgresql.largeobject.BlobOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "SecurityFilter",dispatcherTypes ={DispatcherType.REQUEST})
public class SecurityFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JWTService jwtService;

    private static String AUTH_URI = "/auth";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse res = (HttpServletResponse)servletResponse;
        int statusCode = authorization2(req);
        if(statusCode == HttpServletResponse.SC_ACCEPTED){filterChain.doFilter(req,res);}
        else {res.sendError(statusCode);}
    }

    private int authorization(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_ACCEPTED;
        String uri = req.getRequestURI();
        if (uri.equalsIgnoreCase(AUTH_URI)){
            statusCode = HttpServletResponse.SC_ACCEPTED;
        }
        return  statusCode;
    }

    private int authorization2(HttpServletRequest req){
        logger.info(req.getRequestURI());
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        if (uri.equalsIgnoreCase(AUTH_URI)){
            statusCode = HttpServletResponse.SC_ACCEPTED;
        }
        String httpMethodValue = req.getMethod();
        logger.info("the request method is =={}",httpMethodValue);
        try{
            String wholeTokenString = req.getHeader("Authorization");
            String token = wholeTokenString.replaceAll("^(.*?) ", "");
            if (token == null||token.trim().isEmpty())
                return statusCode;
            Claims claims = jwtService.decryptJwtToken(token);
            String allowedResources = "";
            switch(httpMethodValue){
                case "GET" :
                    allowedResources = (String) claims.get("allowedReadResources");
                    break;
                case "PUT" :
                    allowedResources = (String)claims.get("allowedUpdateResources");
                    break;
                case "DELETE" :
                    allowedResources = (String)claims.get("allowedDeleteResources");
                    break;
                case "POST" :
                    allowedResources = (String)claims.get("allowedCreateResources");
                    break;
            }

            logger.info("this request`s resources information is =={}",allowedResources);
            for (String eachAllowedResources:allowedResources.split(",")){
                if (uri.trim().toLowerCase().startsWith(eachAllowedResources.trim().toLowerCase())){
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }

        }catch (Exception e){
            e.getMessage();
        }
        return statusCode;
    }

    @Override
    public void destroy() {

    }
}
