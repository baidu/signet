/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.service.impl;

import java.io.File;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.baidu.stqa.signet.web.bo.Role;
import com.baidu.stqa.signet.web.mapper.RoleMapper;
import com.baidu.stqa.signet.web.property.SysProperty;
import com.baidu.stqa.signet.web.service.RoleService;
import com.baidu.stqa.signet.web.util.ImgGenerator;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoleByProjectId(Long projectId) {
        Role role = new Role();
        role.setProjectId(projectId);
        return roleMapper.selectByCondi(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleMapper.delete(roleId);
    }

    @Override
    public boolean generateRole(String name, String roleTag, Long spaceId, Long type) throws NamingException {
        InitialContext initialContext = new InitialContext();
        String path = SysProperty.getImgPath();
      
        Role condiRole = new Role();
        condiRole.setRoleTag(roleTag);
        // 如果已经存在，则返回false
        if (!roleMapper.selectByCondi(condiRole).isEmpty()) {
            return false;
        } else {// 如果不存在，则插库、生成图片，返回true
            Role role = new Role();
            role.setName(name);
            role.setRoleTag(roleTag);
            role.setProjectId(spaceId);
            role.setRoleSignType(type);
            try {
                roleMapper.insert(role);
                File f = new File(path + spaceId);
                // 创建文件夹
                if (!f.exists()) {
                    f.mkdirs();
                }
                ImgGenerator.generateImg(name, type, path + spaceId + "/" + roleTag + ".jpg");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public void batchCreateRole() throws NamingException {
        List<Role> list = roleMapper.selectAll();
        for (Role r : list) {
            generateRole(r.getName(), r.getRoleTag(), r.getProjectId(), r.getRoleSignType());
        }
    }
   
}