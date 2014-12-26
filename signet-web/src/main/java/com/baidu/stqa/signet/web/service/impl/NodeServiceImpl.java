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
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.stqa.signet.web.bo.Node;
import com.baidu.stqa.signet.web.mapper.NodeMapper;
import com.baidu.stqa.signet.web.service.NodeService;
import com.baidu.stqa.signet.web.util.ComparatorNode;
import com.baidu.stqa.signet.web.vo.NodeVo;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Service("nodeService")
public class NodeServiceImpl implements NodeService {

    private static Long ROOT_PARENT_ID = 0L;

    private static String DEFAULT_ROLE = "0";

    private static Long DEFAULT_PARENT_ID = -1L;

    private static Boolean DEFAULT_CARE = false;

    private static Integer DEFAULT_REMARK = 0;

    private static Long DEFAULT_SEQ = -1L;

    private static Integer DEFAULT_IS_RETEST = 0;

    @Autowired
    private NodeMapper nodeMapper;

    private List<Long> childList = new ArrayList<Long>();

    @Override
    public NodeVo queryNodes(Long storyId, Long projectId) {

        // 获得根节点
        Node rootNode = findRootNode(projectId, storyId);
        NodeVo rootNodeVo = new NodeVo();

        // 无根节点,则新增根节点
        if (rootNode == null) {
            rootNodeVo.setId(addRootNode(projectId, storyId));
            rootNodeVo.setText("#" + storyId);
            rootNodeVo.setRemarkFlag(0);
            rootNodeVo.setIsRetest(0);
        } else {
            rootNodeVo.setId(rootNode.getNodeId());
            rootNodeVo.setText(rootNode.getNodeText());
            rootNodeVo.setRemarkFlag(rootNode.getRemarkFlag());
            rootNodeVo.setIsRetest(rootNode.getIsRetest());
        }
        rootNodeVo.setRoleId("0");
        rootNodeVo.setSeq(0L);

        creatTheTree(rootNodeVo, projectId, storyId);

        return rootNodeVo;
    }

    @Override
    public void deleteNode(Long nodeId) {

        // 删除节点及其子节点
        childList.clear();
        getChildNodes(nodeId);
        childList.add(nodeId);

        nodeMapper.deleteBatch(childList.toArray(new Long[childList.size()]));

    }

    @Override
    public Long saveNodePosition(Long nodeId, Long parentId) {
        // 需要相应调整节点顺序
        // 获得parentId下所有child node
        List<Node> childNodes = nodeMapper.selectByParentId(parentId);
        // 计算seq，update
        Long seq = 0L;
        for (Node node : childNodes) {
            if (node.getSeq().longValue() > seq.longValue()) {
                seq = node.getSeq();
            }
        }
        Node node = new Node();
        node.setSeq(seq + 1L);
        node.setNodeId(nodeId);
        node.setParentId(parentId);
        nodeMapper.update(node);
        return nodeId;
    }

    @Override
    public Long saveNodeRole(Long nodeId, String roleId) {
        Node node = new Node();
        node.setNodeId(nodeId);
        node.setRoleSignId(roleId);
        nodeMapper.update(node);
        return nodeId;
    }

    @Override
    public Long saveNodeText(Long nodeId, String text) {
        Node node = new Node();
        node.setNodeId(nodeId);
        node.setNodeText(text);
        nodeMapper.update(node);
        return nodeId;
    }

    @Override
    public Node addBrothorNode(Long nodeId, Long storyId, Long projectId, String user) {
        Node sourceNode = nodeMapper.selectById(nodeId);
        Node node = new Node();
        node.setStoryId(storyId);
        node.setProjectId(projectId);
        node.setRoleSignId("0");
        node.setParentId(sourceNode.getParentId());
        node.setNodeText("");
        node.setRemarkFlag(0);
        node.setCreatePerson(user);
        node.setSeq(sourceNode.getSeq() + 1);
        nodeMapper.insert(node);

        List<Node> childNodes = nodeMapper.selectByParentId(sourceNode.getParentId());
        for (Node n : childNodes) {
            if (n.getSeq().longValue() > sourceNode.getSeq().longValue()) {
                n.setSeq(n.getSeq() + 1);
                nodeMapper.update(n);
            }
        }
        return node;
    }

    @Override
    public Long addNode(Long projectId, Long storyId, Long parentId, String text, String user) {
        if (parentId == null) {
            parentId = DEFAULT_PARENT_ID;
        }
        if (text == null) {
            text = "";
        }
        Node node = new Node();
        node.setStoryId(storyId);
        node.setProjectId(projectId);
        node.setRoleSignId(DEFAULT_ROLE);
        node.setParentId(parentId);
        node.setIsCare(DEFAULT_CARE);
        node.setNodeText(text);
        node.setRemarkFlag(DEFAULT_REMARK);
        node.setCreatePerson(user);
        node.setSeq(DEFAULT_SEQ);
        node.setIsRetest(DEFAULT_IS_RETEST);
        if (!DEFAULT_PARENT_ID.equals(parentId)) {
            List<Node> children = nodeMapper.selectByParentId(parentId);
            long count = 0;
            for (Node child : children) {
                if (child.getSeq() > count) {
                    count = child.getSeq();
                }
            }
            node.setSeq(count + 1);
        }
        nodeMapper.insert(node);
        return node.getNodeId();
    }

    @Override
    public boolean changeSeq(Long nodeId, Integer flag) {

        Node node = nodeMapper.selectById(nodeId);
        List<Node> list = nodeMapper.selectByParentId(node.getParentId());

        Node nearNode = getNearNode(list, node, flag);

        if (nearNode == null) {
            return true;
        }

        return exchangNodesSeq(nearNode, node);
    }

    @Override
    public Long batchCopyNodes(Long sStoryId, Long tStoryId, Long sProjcetId, Long tProjectId, String user) {

        Node sRoot = findRootNode(sProjcetId, sStoryId);

        // put all in collect
        NodeVo vo = new NodeVo();
        vo.setId(sRoot.getNodeId());
        vo.setText("#" + tStoryId);
        creatTheTree(vo, sProjcetId, sStoryId);

        // delete all
        Node tRoot = new Node();
        tRoot.setProjectId(tProjectId);
        tRoot.setStoryId(tStoryId);
        nodeMapper.delete(tRoot);

        // 递归 insert it
        return deepInsert(0L, vo, 1, user, tProjectId, tStoryId);

    }

    @Override
    public Long saveNodeRetest(Long nodeId, Integer isRetest) {

        Node node = new Node();
        node.setNodeId(nodeId);
        node.setIsRetest(isRetest);
        nodeMapper.update(node);
        return nodeId;
    }

    @Override
    public boolean batchImportNodes(List<List<String>> contentList, List<String> titleList, Long spaceId, Long cid,
            String user) {
        /*
         * try { // delete Node node = new Node(); node.setStoryId(cid); node.setProjectId(spaceId);
         * nodeMapper.deleteNodes(node);
         * 
         * List<String> temp1ContentList = new ArrayList<String>(); List<Integer> temp1NodeRowNumList = new
         * ArrayList<Integer>(); List<Integer> temp1SubNodeNumList = new ArrayList<Integer>(); List<Long>
         * temp1NodeIdList = new ArrayList<Long>();
         * 
         * List<String> temp2ContentList = new ArrayList<String>(); List<Integer> temp2NodeRowNumList = new
         * ArrayList<Integer>(); List<Integer> temp2SubNodeNumList = new ArrayList<Integer>(); List<Long>
         * temp2NodeIdList = new ArrayList<Long>();
         * 
         * temp1ContentList.add("#" + cid); temp1NodeRowNumList.add(0);
         * temp1SubNodeNumList.add(contentList.get(0).size()); temp1NodeIdList.add(0L);
         * 
         * for (int k = 0; k < contentList.size(); k++) {
         * 
         * temp2ContentList.clear(); temp2NodeRowNumList.clear(); temp2NodeIdList.clear(); temp2SubNodeNumList.clear();
         * 
         * for (int i = 0; i < temp1ContentList.size(); i++) { // 插入父节点 Node root = new Node(); root.setNodeText(k == 0
         * ? "" : (titleList.get(k - 1) + ": <br />") + temp1ContentList.get(i));
         * root.setParentId(temp1NodeIdList.get(i)); root.setAutoSignType(0L); root.setProjectId(spaceId);
         * root.setStoryId(cid); root.setRoleSignId("0"); root.setRemarkFlag(0); root.setAppraiseFlag(0);
         * root.setSeq(new Long(i)); root.setCreatePerson(user); nodeMapper.insertSelective(root);
         * 
         * int startNum = temp1NodeRowNumList.get(i); int size = temp1SubNodeNumList.get(i); int n = 1; boolean flag =
         * true; for (int j = startNum; j < size + startNum; j++) { String con = contentList.get(k).get(j); if (flag &&
         * con.trim().equals("")) { continue; } if (!con.trim().equals("")) { flag = false; temp2ContentList.add(con);
         * temp2NodeRowNumList.add(j); temp2NodeIdList.add(root.getNodeId());
         * 
         * if (j != startNum) { temp2SubNodeNumList.add(n); } n = 1; } else { n++; } if (j == (size + startNum - 1)) {
         * temp2SubNodeNumList.add(n); } } }
         * 
         * temp1ContentList.clear(); temp1NodeRowNumList.clear(); temp1NodeIdList.clear(); temp1SubNodeNumList.clear();
         * 
         * temp1ContentList.addAll(temp2ContentList); temp1NodeRowNumList.addAll(temp2NodeRowNumList);
         * temp1NodeIdList.addAll(temp2NodeIdList); temp1SubNodeNumList.addAll(temp2SubNodeNumList); }
         * 
         * for (int i = 0; i < temp1ContentList.size(); i++) { // 插入父节点 Node root = new Node();
         * root.setNodeText(titleList.get(titleList.size() - 1) + ": <br />" + temp1ContentList.get(i));
         * root.setParentId(temp1NodeIdList.get(i)); root.setAutoSignType(0L); root.setProjectId(spaceId);
         * root.setStoryId(cid); root.setRoleSignId("0"); root.setRemarkFlag(0); root.setAppraiseFlag(0);
         * root.setCreatePerson(user); root.setSeq(new Long(i)); nodeMapper.insertSelective(root); } } catch (Exception
         * e) { e.printStackTrace(); }
         */
        return true;
    }

    @Override
    public NodeVo copyNodes(Long sId, Long tId, Long projectId, Long storyId, String user) {

        Node sNode = nodeMapper.selectById(sId);

        // put all in collect
        NodeVo vo = new NodeVo();
        vo.id = sId;
        vo.text = sNode.getNodeText();
        vo.roleId = "0";

        creatTheTree(vo, projectId, storyId);

        List<Node> list = nodeMapper.selectByParentId(tId);
        // 递归 insert it
        Long rootId = deepInsert(tId, vo, list.size() + 1, user, projectId, storyId);

        // return json
        NodeVo rVo = new NodeVo();
        rVo.id = rootId;
        rVo.text = sNode.getNodeText();
        rVo.roleId = "0";
        rVo.setIsRetest(0);
        rVo.seq = new Long(list.size() + 1);
        rVo.setRemarkFlag(0);
        creatTheTree(rVo, projectId, storyId);
        return rVo;
    }

    private Long deepInsert(Long parentId, NodeVo vo, int seq, String user, Long spaceId, Long cid) {
        // create an node
        // change it's parent

        Node node = new Node();
        node.setRoleSignId("0");
        node.setNodeText(vo.getText());
        node.setSeq(new Long(seq));
        node.setParentId(parentId);
        node.setCreatePerson(user);
        node.setStoryId(cid);
        node.setProjectId(spaceId);
        node.setRemarkFlag(0);
        nodeMapper.insert(node);

        // for
        if (vo.getChildren() != null) {
            for (int i = 0; i < vo.getChildren().length; i++) {
                deepInsert(node.getNodeId(), vo.getChildren()[i], i + 1, user, spaceId, cid);
            }
        }
        return node.getNodeId();
    }

    private Node findRootNode(Long projectId, Long storyId) {
        Node node = new Node();
        node.setProjectId(projectId);
        node.setStoryId(storyId);
        node.setParentId(ROOT_PARENT_ID);
        List<Node> nodes = nodeMapper.selectByCondi(node);
        if (nodes.isEmpty()) {
            return null;
        }
        return nodes.get(0);
    }

    private Long addRootNode(Long projectId, Long storyId) {
        Node node = new Node();
        node.setNodeText("#" + storyId);
        node.setParentId(ROOT_PARENT_ID);
        node.setProjectId(projectId);
        node.setStoryId(storyId);
        node.setRoleSignId("0");
        node.setRemarkFlag(0);
        nodeMapper.insert(node);
        return node.getNodeId();
    }

    private Node getNearNode(List<Node> list, Node node, Integer directFlag) {
        Node nearNode = new Node();
        boolean flag = true;
        if (directFlag == -1) {
            // up
            nearNode.setSeq(-99999L);
            for (Node n : list) {
                if (n.getSeq() < node.getSeq() && n.getSeq() > nearNode.getSeq()) {
                    nearNode = n;
                    flag = false;
                }
            }
        } else {
            // down
            nearNode.setSeq(99999L);
            for (Node n : list) {
                if (n.getSeq() > node.getSeq() && n.getSeq() < nearNode.getSeq()) {
                    nearNode = n;
                    flag = false;
                }
            }
        }
        if (flag) {
            nearNode = null;
        }
        return nearNode;
    }

    private void getChildNodes(Long nodeId) {
        List<Node> list = nodeMapper.selectByParentId(nodeId);
        if (list.size() == 0) {
            return;
        }
        for (Node node : list) {
            childList.add(node.getNodeId());
        }
        for (Node n : list) {
            getChildNodes(n.getNodeId());
        }
    }

    @SuppressWarnings("unchecked")
    private void creatTheTree(NodeVo root, Long projectId, Long storyId) {

        // get child nodes
        Node node = new Node();
        node.setStoryId(storyId);
        node.setProjectId(projectId);
        node.setParentId(root.getId());
        List<Node> items = nodeMapper.selectByCondi(node);

        if (items.size() == 0) {
            return; // 如果没有字节点了，那就返回空
        }

        List<NodeVo> voList = new ArrayList<NodeVo>();
        for (int i = 0; i < items.size(); i++) {
            NodeVo vo = new NodeVo();
            vo.setText(items.get(i).getNodeText());
            vo.setRoleId(items.get(i).getRoleSignId().toString());
            vo.setId(items.get(i).getNodeId());
            vo.setSeq(items.get(i).getSeq());
            vo.setIsRetest(items.get(i).getIsRetest());
            vo.setRemarkFlag(items.get(i).getRemarkFlag() == null ? 0 : items.get(i).getRemarkFlag());
            creatTheTree(vo, items.get(i).getProjectId(), items.get(i).getStoryId());
            voList.add(vo);
        }
        ComparatorNode comparator = new ComparatorNode();
        Collections.sort(voList, comparator);
        root.children = (NodeVo[]) voList.toArray(new NodeVo[voList.size()]);
    }

    private boolean exchangNodesSeq(Node source, Node target) {
        Long sourceSeq = source.getSeq();
        Long targetSeq = target.getSeq();
        source.setSeq(targetSeq);
        target.setSeq(sourceSeq);

        nodeMapper.update(source);
        nodeMapper.update(target);
        return true;
    }
}