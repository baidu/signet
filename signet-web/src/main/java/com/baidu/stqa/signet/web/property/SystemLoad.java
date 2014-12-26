/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.property;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class SystemLoad implements InitializingBean{

	private String imgPath;
	
	public void loadSystemConf() {
		//
		SysProperty.setImgPath(imgPath);
		//
	}

	public void afterPropertiesSet() throws Exception {
		this.loadSystemConf();		
	}

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
	
	
}
