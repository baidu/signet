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
import java.util.List;
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

import com.baidu.stqa.signet.web.bo.Remark;
import com.baidu.stqa.signet.web.service.RemarkService;
import com.baidu.stqa.signet.web.vo.RemarkForm;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Controller("remarkAction")
@RequestMapping(value = "/signet", produces = MediaType.APPLICATION_JSON_VALUE)
public class RemarkAction extends BaseAction {

    @Autowired
    private RemarkService remarkService;

    /**
     * 获得节点评论
     * 
     * @param projectId
     * @param storyId
     * @param nodeId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/node/{nodeId}/remark", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Remark>> getRemarks(@PathVariable long projectId, @PathVariable long storyId,
            @PathVariable long nodeId) {

        doLog(projectId);
        List<Remark> remarks = remarkService.getRemarksByNodeId(nodeId);
        return new ResponseEntity<List<Remark>>(remarks, HttpStatus.OK);
    }

    /**
     * 增加评论
     * 
     * @param projectId
     * @param storyId
     * @param nodeId
     * @param remark
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/node/{nodeId}/remark", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addRemark(@PathVariable long projectId, @PathVariable long storyId,
            @PathVariable long nodeId, @RequestBody RemarkForm remark) {
        doLog(projectId);
        Long remarkId = remarkService.addRemark(nodeId, remark.getContent(), getUser());
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("id", remarkId);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    /**
     * 删除评论
     * 
     * @param projectId
     * @param storyId
     * @param nodeId
     * @param remarkId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/node/{nodeId}/remark/{remarkId}", 
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> removeRemark(@PathVariable long projectId, @PathVariable long storyId,
            @PathVariable long nodeId, @PathVariable long remarkId) {

        doLog(projectId);
        remarkService.removeRemark(nodeId, remarkId);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
