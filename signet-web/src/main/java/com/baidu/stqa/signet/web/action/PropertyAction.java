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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.stqa.signet.web.service.PropertyService;
import com.baidu.stqa.signet.web.vo.PropertyForm;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Controller("propertyAction")
@RequestMapping(value = "/signet", produces = MediaType.APPLICATION_JSON_VALUE)
public class PropertyAction extends BaseAction {

    @Autowired
    private PropertyService propertyService;

    /**
     * 获得项目配置信息
     * 
     * @param projectId
     * @param key
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/property", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> queryProperty(@PathVariable long projectId, String key) {

        doLog(projectId);

        Map<String, Object> propertyMap = new HashMap<String, Object>();
        if (key == null) {
            propertyMap = propertyService.queryProperty(projectId);
        } else {
            String value = propertyService.queryProperty(projectId, key);
            propertyMap.put(key, value);
        }
        return new ResponseEntity<Map<String, Object>>(propertyMap, HttpStatus.OK);
    }

    /**
     * 保存项目配置
     * 
     * @param projectId
     * @param property
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/property", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Boolean> saveProperty(@PathVariable long projectId, @RequestBody PropertyForm property) {
        doLog(projectId);
        propertyService.saveRoleMode(projectId, property.getRoleMode());
        propertyService.saveBugPath(projectId, property.getBugPath());
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
