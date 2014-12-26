/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.util;

import java.util.Comparator;

import com.baidu.stqa.signet.web.vo.NodeVo;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class ComparatorNode implements Comparator<Object> {
    public int compare(Object o1, Object o2) {
        NodeVo c1 = (NodeVo) o1;
        NodeVo c2 = (NodeVo) o2;
        Long s1 = c1.getSeq() == null ? 0L : c1.getSeq();
        Long s2 = c2.getSeq() == null ? 0L : c2.getSeq();
        return s1.compareTo(s2);
    }
}
