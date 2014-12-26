/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.mapper;

import com.baidu.stqa.signet.web.bo.Card;
import com.baidu.stqa.signet.web.common.mybatis.MybatisMapper;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@MybatisMapper
public interface CardMapper {

    public Card queryByProjectIdAndStoryId(Card card);

    public void insert(Card card);

    public void update(Card card);
}
