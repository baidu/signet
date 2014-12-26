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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.stqa.signet.web.bo.Node;
import com.baidu.stqa.signet.web.bo.Role;
import com.baidu.stqa.signet.web.mapper.NodeMapper;
import com.baidu.stqa.signet.web.mapper.RoleMapper;
import com.baidu.stqa.signet.web.service.StatisticsService;
import com.baidu.stqa.signet.web.vo.ItemVo;
import com.baidu.stqa.signet.web.vo.Statistics;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Map<Long, Statistics> queryTestProgress(List<Long> storyIds, Long projectId) {

        Map<Long, Statistics> storyMap = new HashMap<Long, Statistics>();
        List<Node> allNodes = nodeMapper.selectByProjectId(projectId);
        Map<Long, List<Node>> map = new HashMap<Long, List<Node>>();

        for (Node node : allNodes) {
            if (node.getParentId().longValue() > 0) {
                if (map.containsKey(node.getStoryId())) {
                    map.get(node.getStoryId()).add(node);
                } else {
                    map.put(node.getStoryId(), new ArrayList<Node>());
                    map.get(node.getStoryId()).add(node);
                }
            }

        }
        // 处理每一个story
        for (Long storyId : storyIds) {

            List<Node> leafNodeList = getLeafNodes(map.get(storyId));

            int testedCaseNum = 0;
            for (Node node : leafNodeList) {
                if (!node.getRoleSignId().trim().equals("0") && !node.getRoleSignId().trim().equals("-1")) {
                    testedCaseNum++;
                }

            }

            Statistics stat = new Statistics();
            stat.setTestedCaseNum(testedCaseNum);
            stat.setTotalCaseNum(leafNodeList.size());
            stat.setPercent((float) testedCaseNum / leafNodeList.size());

            storyMap.put(storyId, stat);

        }
        return storyMap;
    }

    @Override
    public Map<String, Object> queryStatistics(List<Long> storyIds, Long projectId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Node> allNodes = nodeMapper.selectByProjectId(projectId);
        Map<Long, List<Node>> map = new HashMap<Long, List<Node>>();

        for (Node node : allNodes) {
            if (node.getParentId().longValue() > 0) {
                if (map.containsKey(node.getStoryId())) {
                    map.get(node.getStoryId()).add(node);
                } else {
                    map.put(node.getStoryId(), new ArrayList<Node>());
                    map.get(node.getStoryId()).add(node);
                }
            }

        }

        // find role
        Role role = new Role();
        role.setProjectId(projectId);
        List<Role> roles = roleMapper.selectByCondi(role);
        Map<Long, String> roleNameMap = new HashMap<Long, String>();
        Map<Long, Long> roleTypeMap = new HashMap<Long, Long>();

        for (Role r : roles) {
            roleNameMap.put(r.getRoleId(), r.getName());
            roleTypeMap.put(r.getRoleId(), r.getRoleSignType());
        }
        // 处理每一个story
        int testedCaseNum = 0;
        int totalCaseNum = 0;
        Map<String, Long> createPersonMap = new HashMap<String, Long>();
        Map<String, Long> signPersonMap = new HashMap<String, Long>();

        // type = 1
        int rdNum = 0;
        // type = 3
        int qaNum = 0;
        // type = 2
        int pmNum = 0;
        // 未知
        int nullNum = 0;

        for (Long storyId : storyIds) {

            List<Node> leafNodeList = getLeafNodes(map.get(storyId));

            totalCaseNum = totalCaseNum + leafNodeList.size();

            for (Node node : leafNodeList) {

                if (createPersonMap.containsKey(node.getCreatePerson())) {
                    Long cpmValue = createPersonMap.get(node.getCreatePerson()) + 1;
                    createPersonMap.remove(node.getCreatePerson());
                    createPersonMap.put(node.getCreatePerson(), cpmValue);
                } else {
                    createPersonMap.put(node.getCreatePerson(), 1L);
                }

                if (!node.getRoleSignId().trim().equals("0") && !node.getRoleSignId().trim().equals("-1")) {
                    testedCaseNum++;
                    String[] roleIds = node.getRoleSignId().split(",");
                    String roleName = roleNameMap.get(new Long(roleIds[0]));

                    if (signPersonMap.containsKey(roleName)) {
                        Long spmValue = signPersonMap.get(roleName) + 1;
                        signPersonMap.remove(roleName);
                        signPersonMap.put(roleName, spmValue);
                    } else {
                        signPersonMap.put(roleName, 1L);
                    }
                    Long type = roleTypeMap.get(new Long(roleIds[0]));
                    if (type == null) {
                        nullNum++;
                    } else if (type == 1L) {
                        rdNum++;
                    } else if (type == 2L) {
                        pmNum++;
                    } else if (type == 3L) {
                        qaNum++;
                    }
                }
            }

        }

        resultMap.put("total", totalCaseNum);
        resultMap.put("tested", testedCaseNum);

        List<ItemVo> roleStatisticsList = new ArrayList<ItemVo>();
        roleStatisticsList.add(new ItemVo("rd&fe", rdNum));
        roleStatisticsList.add(new ItemVo("pm", pmNum));
        roleStatisticsList.add(new ItemVo("qa", qaNum));
        if (nullNum > 0) {
            roleStatisticsList.add(new ItemVo("未知", nullNum));
        }
        if ((totalCaseNum - rdNum - pmNum - qaNum - nullNum) > 0) {
            roleStatisticsList.add(new ItemVo("未测试", totalCaseNum - rdNum - pmNum - qaNum - nullNum));
        }
        resultMap.put("roleResult", roleStatisticsList);

        List<ItemVo> createStatisticsList = new ArrayList<ItemVo>();
        Set<String> createPersonKeys = createPersonMap.keySet();
        for (String key : createPersonKeys) {
            createStatisticsList.add(new ItemVo(key, createPersonMap.get(key)));
        }
        resultMap.put("createResult", createStatisticsList);

        List<ItemVo> signStatisticsList = new ArrayList<ItemVo>();
        Set<String> signPersonKeys = signPersonMap.keySet();
        int notest = totalCaseNum;
        for (String key : signPersonKeys) {
            notest = (int) (notest - signPersonMap.get(key));
            signStatisticsList.add(new ItemVo(key, signPersonMap.get(key)));
        }
        if (notest > 0) {
            signStatisticsList.add(new ItemVo("未测试", notest));
        }
        resultMap.put("signResult", signStatisticsList);

        return resultMap;
    }

    private List<Node> getLeafNodes(List<Node> nodes) {
        List<Node> leafNodeList = new ArrayList<Node>();
        if (nodes == null) {
            return leafNodeList;
        }
        Set<Long> parentSet = new HashSet<Long>();
        for (Node n : nodes) {
            parentSet.add(n.getParentId());
        }

        for (Node n : nodes) {
            if (!parentSet.contains(n.getNodeId())) {
                leafNodeList.add(n);
            }
        }
        return leafNodeList;
    }

}
