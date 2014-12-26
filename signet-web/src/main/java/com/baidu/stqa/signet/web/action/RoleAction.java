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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

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

import com.baidu.stqa.signet.web.bo.Role;
import com.baidu.stqa.signet.web.service.RoleService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Controller("roleAction")
@RequestMapping(value = "/signet", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleAction extends BaseAction {

    @Autowired
    private RoleService roleService;

    /**
     * 获得角色签章信息
     * 
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/role", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Role>> queryRole(@PathVariable Long projectId) {

        doLog(projectId);
        String user = getUser();
        List<Role> roles = roleService.getAllRoleByProjectId(projectId);
        List<Integer> currentUserSignetList = new ArrayList<Integer>();
        for (int i = 0; i < roles.size(); i++) {
            if (user.contains(HanyuToPinyin(roles.get(i).getName()))) {
                currentUserSignetList.add(i);
            }
        }
        if (currentUserSignetList.isEmpty()) {
            return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
        } else {
            List<Role> fixList = new LinkedList<Role>();
            for (Integer num : currentUserSignetList) {
                fixList.add(roles.get(num));
            }
            for (int k = 0; k < currentUserSignetList.size(); k++) {
                roles.remove(currentUserSignetList.get(k) - k);
            }
            fixList.addAll(roles);
            return new ResponseEntity<List<Role>>(fixList, HttpStatus.OK);
        }

    }

    /**
     * 删除角色签章
     * 
     * @param projectId
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/role/{roleId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Boolean> removeRole(@PathVariable Long projectId, @PathVariable Long roleId) {

        doLog(projectId);
        roleService.deleteRole(roleId);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    /**
     * 增加角色签章
     * 
     * @param projectId
     * @param role
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/role", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addRole(@PathVariable Long projectId, @RequestBody Role role) {

        doLog(projectId);

        Boolean result = false;
        String tag = new Long(new Date().getTime()).toString();
        try {
            result = roleService.generateRole(role.getName(), tag, projectId, role.getRoleSignType());
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result);
        resultMap.put("tag", result);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    protected String HanyuToPinyin(String name) {
        char[] nameChar = name.toCharArray();
        String pinyinName = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }
        }
        return pinyinName;
    }
}
