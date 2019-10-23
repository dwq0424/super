package com.action;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@PropertySource(value = "classpath:param.properties",encoding = "UTF-8")
@WebFilter(urlPatterns = {"/*"})
public class CodeFilter implements Filter{

    @Value("${app.code}")
    private String codestr = null;

    public void destroy() {
        //System.out.println("---destroy()");
        codestr = null;
    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain arg2) throws IOException, ServletException {
        //System.out.println("---doFilter()");
        if(codestr != null){
            arg0.setCharacterEncoding(codestr);//请求乱码
            arg1.setCharacterEncoding(codestr);//响应乱码
            arg1.setContentType("text/html;charset="+codestr);
        }

        arg2.doFilter(arg0, arg1);//放行
    }

    public void init(FilterConfig arg0) throws ServletException {
        //System.out.println("---init()");
//		codestr = arg0.getInitParameter("codestr");
        System.out.println(" codestr = "+codestr);
    }

}

