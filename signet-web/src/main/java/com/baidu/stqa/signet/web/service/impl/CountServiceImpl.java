/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.stqa.signet.web.bo.Log;
import com.baidu.stqa.signet.web.bo.Project;
import com.baidu.stqa.signet.web.mapper.LogMapper;
import com.baidu.stqa.signet.web.mapper.ProjectMapper;
import com.baidu.stqa.signet.web.service.CountService;
import com.baidu.stqa.signet.web.vo.Count;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Service("countService")
public class CountServiceImpl implements CountService {

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public List<Count> countAllVisit() {
        Map<Long, String> projectMap = getProjectMap();
        List<Log> logList = logMapper.countProjectLog();
        List<Count> countList = new ArrayList<Count>();
        long other = 0L;
        long otherCount = 0L;
        for (Log log : logList) {
            if (log != null && log.getSpaceId() != null && log.getSpaceId() > 0) {
                if (log.getNum() > 5000) {
                    if (projectMap.get(log.getSpaceId()) != null) {
                        countList.add(new Count(projectMap.get(log.getSpaceId()), log.getNum()));
                    } else {
                        countList.add(new Count(log.getSpaceId().toString(), log.getNum()));
                    }

                } else {
                    other = other + log.getNum();
                    if (log.getNum() > 1000) {
                        otherCount++;
                    }
                }
            }
        }
        countList.add(new Count("其他(共" + otherCount + "个)", other));
        return countList;
    }

    @Override
    public List<Count> countNewlyVisit(String date) {
        Map<Long, String> projectMap = getProjectMap();
        List<Log> logList = logMapper.countProjectLogByDate(date);
        List<Count> countList = new ArrayList<Count>();
        for (Log log : logList) {
            if (log != null && log.getSpaceId() != null && log.getSpaceId() > 0) {
                if (projectMap.get(log.getSpaceId()) != null) {
                    countList.add(new Count(projectMap.get(log.getSpaceId()), log.getNum()));
                } else {
                    countList.add(new Count(log.getSpaceId().toString(), log.getNum()));
                }
            }
        }
        return countList;
    }

    @Override
    public List<Count> countAllLine() {
        List<Log> logList = logMapper.count();
        List<Count> countList = new ArrayList<Count>();
        for (Log log : logList) {
            if (log != null && log.getSpaceId() != null && log.getSpaceId() > 0) {
                countList.add(new Count(log.getAccessDate(), log.getNum()));
            }
        }
        return countList;
    }

    private Map<Long, String> getProjectMap() {
        List<Project> projects = projectMapper.selectAll();
        Map<Long, String> resultMap = new HashMap<Long, String>();
        for (Project project : projects) {
            resultMap.put(project.getProjectId(), project.getProjectDesc());
        }
        return resultMap;
    }
}
