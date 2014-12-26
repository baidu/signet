/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.common.spring;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class SpringInit implements ServletContextListener {

    private static ApplicationContext context;

    public void contextInitialized(ServletContextEvent event) {
        context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) {
    }

    public static void setApplicationContext(ApplicationContext testContext) {
        context = testContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

}
