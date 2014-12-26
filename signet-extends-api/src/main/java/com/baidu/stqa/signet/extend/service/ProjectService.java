/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.extend.service;

import java.util.List;

import com.baidu.stqa.signet.extend.bo.Project;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface ProjectService {

    /**
     * 获得用户可访问项目列表
     * 
     * @param username
     * @return
     */
    public List<Project> listProject(String username);

}
