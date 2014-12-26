/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.extend.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baidu.stqa.signet.extend.bo.Project;
import com.baidu.stqa.signet.extend.service.ProjectService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class ProjectServiceImpl implements ProjectService {

    public List<Project> listProject(String username) {

        List<Project> projectList = new ArrayList<Project>();
        for (long i = 1; i <= 10; i++) {
            Project project = new Project();
            project.setId(i);
            project.setName("项目" + i);
            project.setDescr("project" + i);
            projectList.add(project);
        }
        return projectList;
    }

}
