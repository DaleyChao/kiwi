package com.daley.kiwi.config;

/**
 * @author Daniel
 * @date 2018/8/9 10:18
 *
 */
public interface ConfigConstant {
    String CONFIG_FILE = "kiwi.properties";

    String JDBC_DRIVER = "kiwi.framework.jdbc.driver";
    String JDBC_URL = "kiwi.framework.jdbc.url";
    String JDBC_USERNAME = "kiwi.framework.jdbc.username";
    String JDBC_PASSWORD = "kiwi.framework.jdbc.password";

    String APP_BASE_PACKAGE = "kiwi.framework.app.base_package";
    String APP_JSP_PATH = "kiwi.framework.app.jsp_path";
    String APP_ASSET_PATH = "kiwi.framework.app.asset_path";
}
