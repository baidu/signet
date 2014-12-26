/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Controller("userAction")
@RequestMapping(value = "/signet", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAction extends BaseAction {

    /**
     * 获得登录用户
     * 
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, String>> queryUser() {

        String user = getUser();
        Map<String, String> result = new HashMap<String, String>();
        result.put("user", user);
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }
}
