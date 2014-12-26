/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.stqa.signet.web.bo.EditStatus;
import com.baidu.stqa.signet.web.service.CaseSuiteService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */
@Controller("authorityAction")
@RequestMapping(value = "/signet", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorityAction extends BaseAction {

    @Autowired
    private CaseSuiteService caseSuiteService;

    /**
     * 获得编辑权限
     * 
     * @param projectId
     * @param storyId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/authority", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<EditStatus> applyAuthorityAction(@PathVariable long projectId, @PathVariable long storyId) {
        doLog(projectId);
        String user = getUser();
        EditStatus es = caseSuiteService.ApplyForEdit(projectId, storyId, user, "");
        return new ResponseEntity<EditStatus>(es, HttpStatus.OK);
    }

    /**
     * 释放编辑权限
     * 
     * @param projectId
     * @param storyId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/authority", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> relaseAuthorityAction(@PathVariable long projectId, @PathVariable long storyId) {
        doLog(projectId);
        String user = getUser();
        Boolean result = caseSuiteService.releaseCardEditLock(projectId, storyId, user);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
}
