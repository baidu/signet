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

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.stqa.signet.extend.bo.Project;
import com.baidu.stqa.signet.extend.service.ProjectService;
import com.baidu.stqa.signet.extend.service.impl.ProjectServiceImpl;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Controller("projectAction")
@RequestMapping(value = "/signet", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectAction extends BaseAction {

    private ProjectService projectService = new ProjectServiceImpl();

    /**
     * 获得项目列表
     * 
     * @return
     */
    @RequestMapping(value = "/project", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Project>> queryUserProjects() {

        doLog(null);

        String user = getUser();

        List<Project> projectList = new ArrayList<Project>();

        if (user != null) {
            projectList = projectService.listProject(user);
        }

        return new ResponseEntity<List<Project>>(projectList, HttpStatus.OK);
    }
}
