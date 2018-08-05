package com.daley.kangaroo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

public class ContextListenerTest extends HttpServlet implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ContextListenerTest.class);

    public void contextDestroyed(ServletContextEvent sce) {
        //用于在容器关闭时,操作
    }

    //用于在容器开启时,操作
    public void contextInitialized(ServletContextEvent sce) {
        String showPathStr = sce.getServletContext().getInitParameter("showPath");
        boolean show = false;
        if (showPathStr != null) {
            show = Boolean.valueOf(showPathStr);
        }
        if (show) {
            String rootPath = sce.getServletContext().getRealPath("/");
            if (rootPath != null) {
                rootPath = rootPath.replaceAll("\\\\", "/");
            } else {
                rootPath = "/";
            }
            if (!rootPath.endsWith("/")) {
                rootPath = rootPath + "/";
            }
            logger.info("Application Run Path:" + rootPath);
        }
        String author = sce.getServletContext().getInitParameter("author");
        if (author == null) {
            author = "null";
        }
        logger.info("Author : " + author);
    }
}
