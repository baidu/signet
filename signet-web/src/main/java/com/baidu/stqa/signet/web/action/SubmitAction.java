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

import com.baidu.stqa.signet.web.bo.Submit;
import com.baidu.stqa.signet.web.service.SubmitService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Controller("submitAction")
@RequestMapping(value = "/signet", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubmitAction extends BaseAction {

    @Autowired
    private SubmitService submitService;

    /**
     * 判断是否已提测
     * 
     * @param projectId
     * @param storyId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/submission", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Boolean> isSubmit(@PathVariable long projectId, @PathVariable long storyId) {

        doLog(projectId);
        Submit submit = submitService.querySubmit(projectId, storyId);
        if (submit == null) {
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
    }

    /**
     * 提测
     * 
     * @param projectId
     * @param storyId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/submission", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Boolean> submitStory(@PathVariable long projectId, @PathVariable long storyId) {

        doLog(projectId);
        submitService.submitStory(projectId, storyId);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    /**
     * 取消提测
     * 
     * @param projectId
     * @param storyId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/submission", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> cancelSubmitStory(@PathVariable long projectId, @PathVariable long storyId) {

        doLog(projectId);
        submitService.cancelSubmitStory(projectId, storyId);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
