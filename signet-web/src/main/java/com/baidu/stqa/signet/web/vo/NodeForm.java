/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.vo;

import com.baidu.stqa.signet.web.bo.Node;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class NodeForm extends Node {

    private Integer direction;

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

}
