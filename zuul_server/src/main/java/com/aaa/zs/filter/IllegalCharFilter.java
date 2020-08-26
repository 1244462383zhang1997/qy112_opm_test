package com.aaa.zs.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class IllegalCharFilter extends ZuulFilter {

    //获取配置文件中的变量名为illegalIp的值
    @Value("${illegalCharacter}")
    private String illegalChar;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        System.out.println("进入了非法字符过滤器...........");
        //该过滤器的核心部分，要执行主要业务，都在这编写
        //使用zuul组件中提供的上下文类获取请求上下文对象
        RequestContext re = RequestContext.getCurrentContext();
        //使用上下文对象获取HttpServletRequest对象
        HttpServletRequest request = re.getRequest();
        //使用上下文对象获取HttpServletResponse对象
        HttpServletResponse response = re.getResponse();
        //获取IP地址
        String remoteAddr = request.getRemoteAddr();
        System.out.println("访问的IP为:"+remoteAddr);

        //获取请求中参数的名称的集合
        Enumeration<String> parameterNames =  request.getParameterNames();
        //分割非法字符串，使用逗号隔开
        String[] illegalCharArray = illegalChar.split(",");
        //循环获取每一个参数名称
        while(parameterNames.hasMoreElements()){//判断有没有下一个
            String paramName = parameterNames.nextElement();
            //根据参数名称获取参数的值
            String paramValue =  request.getParameter(paramName);
            //判断是否含有非法字符
            for (String illegalC:illegalCharArray){
                if (paramValue.contains(illegalC)){
                    try {
                        response.setCharacterEncoding("utf-8");
                        response.sendError(HttpStatus.SC_FORBIDDEN,"你的请求中含有敏感词，不允许访问我的服务");
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
