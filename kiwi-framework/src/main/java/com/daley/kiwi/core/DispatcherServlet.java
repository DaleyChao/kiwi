package com.daley.kiwi.core;

import com.daley.kiwi.helper.ConfigHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求调度器
 * @author daley
 * @date 2018/7/30
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化helper
        ClassLoader.init();

        //获取servletContext
        ServletContext servletContext = config.getServletContext();

        //注册处理jsp的servlet
        ServletRegistration jspServletReg = servletContext.getServletRegistration("jsp");
        jspServletReg.addMapping(ConfigHelper.getAppJspPath()+"*");

        //注册处理静态资源的servlet
        ServletRegistration defaultServletReg = servletContext.getServletRegistration("default")；
        defaultServletReg.addMapping(ConfigHelper.getAppAssetPath()+"*");

    }
}
