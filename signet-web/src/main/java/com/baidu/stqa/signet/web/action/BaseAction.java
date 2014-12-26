/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.action;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.baidu.stqa.signet.web.bo.Log;
import com.baidu.stqa.signet.web.service.LogService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class BaseAction {

    @Autowired
    public HttpServletRequest request;

    @Autowired
    private LogService logService;

    public static final String USER_NAME = "SIGN_USERNAME";

    public void doLog(Long projectId) {

        HttpSession session =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();

        Log log = new Log();
        Object obj = session.getAttribute(USER_NAME);
        String editUserName = null;
        if (obj != null) {
            editUserName = (String) obj;
        }

        String path = request.getMethod() + request.getRequestURI();
        log.setUser(editUserName);
        log.setAction(path);
        log.setSpaceId(projectId);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);

        log.setAccessDate(dateString);
        logService.addLog(log);
    }

    public String getUser() {
        HttpSession session =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        Object obj = session.getAttribute(USER_NAME);
        String user = null;
        if (obj != null) {
            user = (String) obj;
        }
        return user;
    }

    public HttpHeaders setLocationHeaders(String location) {
        HttpHeaders headers = new HttpHeaders();

        if (StringUtils.isNotBlank(location)) {
            URI uri = UriComponentsBuilder.fromPath(location).build().toUri();
            headers.setLocation(uri);
        }
        return headers;
    }
}
