package com.daley.kiwi.core;

import com.daley.kiwi.bean.Data;
import com.daley.kiwi.bean.Handler;
import com.daley.kiwi.bean.Param;
import com.daley.kiwi.bean.View;
import com.daley.kiwi.helper.BeanHelper;
import com.daley.kiwi.helper.ConfigHelper;
import com.daley.kiwi.helper.ControllerHelper;
import com.daley.kiwi.utils.*;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求调度器
 *
 * @author daley
 * @date 2018/7/30
 */
@WebServlet(urlPatterns = "*.do", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化helper
        ClassLoader.init();

        //获取servletContext
        ServletContext servletContext = config.getServletContext();

        //注册处理jsp的servlet
        ServletRegistration jspServletReg = servletContext.getServletRegistration("jsp");
        jspServletReg.addMapping(ConfigHelper.getAppJspPath() + "*");

        //注册处理静态资源的servlet
        ServletRegistration defaultServletReg = servletContext.getServletRegistration("default");
        defaultServletReg.addMapping(ConfigHelper.getAppAssetPath() + "*");

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获得请求方法与路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        //从HANDLER_MAP获得对应handler处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);

        if (handler != null) {

            //获得对应的bean
            Object bean = BeanHelper.getBean(handler.getControllerClass());

            //保存请求参数对象
            Map<String, Object> paramMap = new HashMap<>(16);
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }

            //解码请求
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));

            if (StringUtil.isNotEmpty(body)) {
                String[] params = StringUtil.splitString(body, "&");

                if (ArrayUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtil.splitString(param, "=");
                        if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                            paramMap.put(array[0], array[1]);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);

            //调用action
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(bean, actionMethod, param);

            //处理结果
            if (result instanceof View) {
                //返回jsp页面
                View view = (View) result;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                    }
                }
            } else if (result instanceof Data) {

                //返回Json
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
