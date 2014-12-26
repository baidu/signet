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

import com.baidu.stqa.signet.web.bo.EditStatus;
import com.baidu.stqa.signet.web.bo.Node;
import com.baidu.stqa.signet.web.service.CaseSuiteService;
import com.baidu.stqa.signet.web.service.NodeService;
import com.baidu.stqa.signet.web.vo.NodeForm;
import com.baidu.stqa.signet.web.vo.NodeVo;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Controller("caseAction")
@RequestMapping(value = "/signet", produces = MediaType.APPLICATION_JSON_VALUE)
public class NodeAction extends BaseAction {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private CaseSuiteService caseSuiteService;

    /**
     * 获得节点列表
     * 
     * @param projectId
     * @param storyId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/node", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<NodeVo>> queryNode(@PathVariable long projectId, @PathVariable long storyId) {
        doLog(projectId);
        NodeVo node = nodeService.queryNodes(storyId, projectId);
        List<NodeVo> voList = new ArrayList<NodeVo>();
        voList.add(node);
        return new ResponseEntity<List<NodeVo>>(voList, HttpStatus.OK);
    }

    /**
     * 新增节点
     * 
     * @param projectId
     * @param storyId
     * @param node
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/node", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Long> addNode(@PathVariable long projectId, @PathVariable long storyId,
            @RequestBody NodeForm node) {
        doLog(projectId);
        String editUser = getNowEditUser(projectId, storyId, getUser());
        if (editUser != null) {
            return new ResponseEntity<Long>(0L, HttpStatus.OK);
        }
        Long nodeId = nodeService.addNode(projectId, storyId, node.getParentId(), node.getNodeText(), getUser());
        return new ResponseEntity<Long>(nodeId, HttpStatus.OK);
    }

    /**
     * 删除节点
     * 
     * @param projectId
     * @param storyId
     * @param nodeId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/node/{nodeId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Long> deleteNode(@PathVariable long projectId, @PathVariable long storyId,
            @PathVariable long nodeId) {
        doLog(projectId);
        String editUser = getNowEditUser(projectId, storyId, getUser());
        if (editUser != null) {
            return new ResponseEntity<Long>(0L, HttpStatus.OK);
        }
        nodeService.deleteNode(nodeId);
        return new ResponseEntity<Long>(nodeId, HttpStatus.OK);
    }

    /**
     * 更新节点信息
     * 
     * @param projectId
     * @param storyId
     * @param nodeId
     * @param type
     * @param node
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/node/{nodeId}/{type}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Long> updateNode(@PathVariable long projectId, @PathVariable long storyId,
            @PathVariable long nodeId, @PathVariable String type, @RequestBody NodeForm node) {
        doLog(projectId);
        String editUser = getNowEditUser(projectId, storyId, getUser());
        if (editUser != null) {
            return new ResponseEntity<Long>(0L, HttpStatus.OK);
        }
        Long id = -1L;
        if ("position".equals(type)) {
            // 改变节点位置
            id = nodeService.saveNodePosition(nodeId, node.getParentId());
        } else if ("role".equals(type)) {
            // 修改角色id
            id = nodeService.saveNodeRole(nodeId, node.getRoleSignId());
        } else if ("text".equals(type)) {
            // 修改内容
            id = nodeService.saveNodeText(nodeId, node.getNodeText());
        } else if ("seq".equals(type)) {
            // 修改顺序
            boolean result = nodeService.changeSeq(nodeId, node.getDirection());
            if (result) {
                id = nodeId;
            }
        } else if ("retest".equals(type)) {
            id = nodeService.saveNodeRetest(nodeId, node.getIsRetest() == 1 ? 0 : 1);
        }

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    /**
     * Story间整体复制
     * 
     * @param projectId
     * @param sId
     * @param tId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{sId}/copy/{tId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Long> copyNodesBetweenStories(@PathVariable long projectId, @PathVariable long sId,
            @PathVariable long tId) {
        doLog(projectId);
        Long id = nodeService.batchCopyNodes(sId, tId, projectId, projectId, getUser());
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    /**
     * Story内复制
     * 
     * @param projectId
     * @param sId
     * @param tId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/copy/{sId}/{tId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<NodeVo> copyNodes(@PathVariable long projectId, @PathVariable long storyId,
            @PathVariable long sId, @PathVariable long tId) {
        doLog(projectId);
        String editUser = getNowEditUser(projectId, storyId, getUser());
        if (editUser != null) {
            return new ResponseEntity<NodeVo>(new NodeVo(), HttpStatus.OK);
        }
        NodeVo vo = nodeService.copyNodes(sId, tId, projectId, storyId, getUser());
        return new ResponseEntity<NodeVo>(vo, HttpStatus.OK);
    }

    /**
     * 创建兄弟节点
     * 
     * @param projectId
     * @param storyId
     * @param nodeId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/story/{storyId}/brother/{nodeId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addBrotherNode(@PathVariable long projectId, @PathVariable long storyId,
            @PathVariable long nodeId) {
        doLog(projectId);
        Map<String, Object> map = new HashMap<String, Object>();
        String editUser = getNowEditUser(projectId, storyId, getUser());
        if (editUser != null) {
            map.put("nodeId", 0);
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        }
        Node node = nodeService.addBrothorNode(nodeId, storyId, projectId, editUser);
        map.put("nodeId", node.getNodeId());
        map.put("parentId", node.getParentId());
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    private String getNowEditUser(Long projectId, Long storyId, String user) {
        EditStatus es = caseSuiteService.renewCardEditLock(projectId, storyId, user);
        if (es.isCardCanEdit()) {
            return null;
        }
        if (!es.isCardCanEdit() && es.getEditUserName() == null) {
            return null;
        } else {
            return es.getEditUserName();
        }
    }

}
