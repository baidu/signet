package com.baidu.stqa.signet.web.service;

import java.util.List;

import com.baidu.stqa.signet.web.bo.Node;
import com.baidu.stqa.signet.web.vo.NodeVo;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface NodeService {

    /**
     * 查询空间内测试点
     * 
     * @param storyId
     * @param projectId
     * @return
     */
    public NodeVo queryNodes(Long storyId, Long projectId);

    /**
     * 保存node retest属性
     * 
     * @param nodeId
     * @param isRetest
     * @return
     * 
     */
    public Long saveNodeRetest(Long nodeId, Integer isRetest);

    /**
     * 保存节点位置
     * 
     * @param nodeId
     * @param parentId
     * @return
     */
    public Long saveNodePosition(Long nodeId, Long parentId);

    /**
     * 保存角色id
     * 
     * @param nodeId
     * @param roleId
     * @return
     */
    public Long saveNodeRole(Long nodeId, String roleId);

    /**
     * 保存角色id
     * 
     * @param nodeId
     * @param text
     * @return
     */
    public Long saveNodeText(Long nodeId, String text);

    /**
     * 修改兄弟节点顺序
     * 
     * @param nodeId 需移动节点id
     * @param flag 移动方向标示，-1向上，0向下
     * @return
     * 
     */
    public boolean changeSeq(Long nodeId, Integer flag);

    /**
     * 删除节点
     * 
     * @param node 节点
     * 
     * @return
     * 
     */
    public void deleteNode(Long nodeId);

    /**
     * 
     * @param node 节点
     * 
     * @return
     * 
     */
    public Long addNode(Long projectId, Long storyId, Long parentId, String text, String user);

    /**
     * 批量导入
     * 
     * @param map 导入内容
     * @param spaceId
     * 
     * @param cid
     * 
     * @param user
     * 
     * @return
     * 
     */
    public boolean batchImportNodes(List<List<String>> contentList, List<String> titleList, Long spaceId, Long cid,
            String user);

    /**
     * Story内复制
     * 
     * @param sId
     * @param tId
     * @param projectId
     * @param storyId
     * @param user
     * @return
     */
    public NodeVo copyNodes(Long sId, Long tId, Long projectId, Long storyId, String user);

    /**
     * Story间复制
     * 
     * @param sStoryId
     * @param tStoryId
     * @param sProjcetId
     * @param tProjectId
     * @param user
     * @return
     */
    public Long batchCopyNodes(Long sStoryId, Long tStoryId, Long sProjcetId, Long tProjectId, String user);

    /**
     * 增加兄弟节点
     * 
     * @param nodeId
     * @param storyId
     * @param projectId
     * @param user
     */
    public Node addBrothorNode(Long nodeId, Long storyId, Long projectId, String user);

}