/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.stqa.signet.web.bo.Property;
import com.baidu.stqa.signet.web.mapper.PropertyMapper;
import com.baidu.stqa.signet.web.service.PropertyService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {

    private static final String MULI_SIGNET = "muli_signet";
    private static final String BUG_SPACE_ID = "bug_space_id";
    private static final Long MULI = 1L;
    private static final Long SINGLE = 0L;

    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public void saveRoleMode(Long projectId, String flag) {
        Property condi = new Property(projectId, MULI_SIGNET);

        Property property = propertyMapper.selectByCondi(condi);
        // flag 0 is single ,1 is multi
        condi.setValue(flag);
        if (property == null) {
            propertyMapper.insert(condi);
        } else {
            propertyMapper.update(condi);
        }

    }

    @Override
    public Map<String, Object> queryProperty(Long spaceId) {
        Map<String, Object> propertyMap = new HashMap<String, Object>();
        List<Property> propertys = propertyMapper.selectById(spaceId);
        for (Property p : propertys) {
            if (p.getProperty().equals(MULI_SIGNET)) {
                if ("1".equals(p.getValue())) {
                    propertyMap.put(MULI_SIGNET, MULI);
                } else {
                    propertyMap.put(MULI_SIGNET, SINGLE);
                }
                continue;
            } else if (p.getProperty().equals(BUG_SPACE_ID)) {
                propertyMap.put(BUG_SPACE_ID, p.getValue());
                continue;
            }
        }
        if (!propertyMap.containsKey(MULI_SIGNET)) {
            propertyMap.put(MULI_SIGNET, SINGLE);
        }
        if (!propertyMap.containsKey(BUG_SPACE_ID)) {
            propertyMap.put(BUG_SPACE_ID, "");
        }
        return propertyMap;
    }

    @Override
    public void saveBugPath(Long projectId, String bugPath) {
        Property property = new Property(projectId, BUG_SPACE_ID);
        property.setValue(bugPath);
        if (propertyMapper.selectByCondi(property) == null) {
            propertyMapper.insert(property);
        } else {
            propertyMapper.update(property);
        }
    }

    @Override
    public String queryProperty(Long spaceId, String property) {
        Property projectProperty = new Property(spaceId, property);
        Property result = propertyMapper.selectByCondi(projectProperty);

        if (result == null) {
            return null;
        } else {
            return result.getValue();
        }
    }

}
