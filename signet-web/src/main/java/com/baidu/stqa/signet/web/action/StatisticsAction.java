/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.stqa.signet.web.service.StatisticsService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Controller("statisticsAction")
@RequestMapping(value = "/signet", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsAction extends BaseAction {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 获得项目级别统计信息
     * 
     * @param projectId
     * @param storyIds
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/statistics", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> queryStatistics(@PathVariable long projectId, String storyIds) {
        String[] idStrs = storyIds.split(",");
        List<Long> ids = new ArrayList<Long>();
        for (String id : idStrs) {
            ids.add(new Long(id));
        }
        Map<String, Object> resultMap = statisticsService.queryStatistics(ids, projectId);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
