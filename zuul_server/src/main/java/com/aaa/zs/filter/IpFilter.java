package com.aaa.zs.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  zuul的过滤器功能
 *  过滤非法IP，不合法的IP不允许请求任何微服务
 */
@Component  //将IpFilter交给Spring管理
public class IpFilter extends ZuulFilter {

    //获取配置文件中的变量名为illegalIp的值
    @Value("${illegalIp}")
    private String illegalIp;

    @Override
    public String filterType() {
        //pre    在业务执行之前，执行的方法
        //route  在业务执行时，执行的方法
        //post   在业务执行之后，执行的方法
        //error  在业务执行出错时，执行的方法
        return "pre";
    }

    @Override
    public int filterOrder() {
        //如果有过个filter  该方法的返回值确定改filter执行的顺序，返回值越小，优先级越高
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //该方法确定当前过滤器是否生效， 返回true生效，返回false不生效
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //该过滤器的核心部分，要执行主要业务，都在这编写
        //使用zuul组件中提供的上下文类获取请求上下文对象
        RequestContext re = RequestContext.getCurrentContext();
        //使用上下文对象获取HttpServletRequest对象
        HttpServletRequest request = re.getRequest();
        //使用上下文对象获取HttpServletResponse对象
        HttpServletResponse response = re.getResponse();
        //获取IP地址
        String remoteAddr = request.getRemoteAddr();

        System.out.println("进入了IP过滤器,访问的IP为:"+remoteAddr);
        //判断IP地址是否合法
        if (illegalIp.contains(remoteAddr)){
            /*re.setResponseStatusCode(HttpStatus.SC_FORBIDDEN);
            re.setResponseBody("FORBIDDEN,禁止访问");*/
            try {
                response.setCharacterEncoding("utf-8");
                response.sendError(HttpStatus.SC_FORBIDDEN,"你的IP禁止访问我的服务");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
