package com.baidu.stqa.signet.web.service;

import java.util.List;

import javax.naming.NamingException;

import com.baidu.stqa.signet.web.bo.Role;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface RoleService {
    /**
     * 生成签章
     * 
     * @param name
     * @param roleTag
     * @param projectId
     * @param Type
     * @return
     * @throws NamingException
     */
    public boolean generateRole(String name, String roleTag, Long projectId, Long Type) throws NamingException;

    /**
     * 删除签章
     * 
     * @param roleId
     */
    public void deleteRole(Long roleId);

    /**
     * 获得项目全部签章
     * 
     * @param projectId
     * @return
     */
    public List<Role> getAllRoleByProjectId(Long projectId);

    /**
     * 批量生成所有签章，用于数据恢复
     * 
     * @throws NamingException
     */
    public void batchCreateRole() throws NamingException;
}