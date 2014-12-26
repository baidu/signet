/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.util;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class Constants {
    public static final int PM = 1;
    public static final int RD = 2;
    public static final int QA = 3;

    public static final int STATUS_VAL_NO_USERID = 100;

    public static final String USER_NAME = "SIGN_USERNAME";

    public static final String USER_VIRTUAL_PASSWORD = "virtualPassword";

    public static final String USER_AUTH_SET = "userAuthSet";

    public static final String USER_MENU = "userMenu";

    public static final String DATA_DEF = "dataItem";

    public static final String SCHEMA = "metaData";

    public static final String CHAIN = "dataFlow";

    public static final String SYSTEM = "system";

    public static final Integer STATUS_SUCCESS = 0;

    public static final Integer STATUS_FALURE = 1;

    public static final Integer STATUS_NOAUTH = 102;

    public static final Integer NOT_DELETE = 0;

    public static final Integer DELETE = 1;

    // used for paged query

    public static final String sortPropertyConst = "sortProperty";

    public static final String sortTypeConst = "sortType";

    public static final String curPageConst = "curPage";

    public static final String curIndexConst = "curIndex";

    public static final String pageSizeConst = "pageSize";

    public static final String DESC = "desc";

    public static final String ASC = "asc";

    public static final Long FIRST_PAGE = 1L;

    public static final Long DEFAULT_PAGE_SIZE = 20L;

    public static final Long PAGE_SIZE_TEN = 10L;

    // solr

    public static final String SOLR_SERVER_HTTP = "http";

    public static final String SOLR_SERVER_EMBED = "embed";

    public static final String JAVA_ENV = "java:comp/env";

    public static final String SOLR_HOME = "solr/home";

    public static final String SOLR_XML = "solr.xml";

    public static final String SPECIAL_CHARACTERS =
            "[\\\\`\"~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    public static final String SYMBOL_COLON = ":";

    public static final String SYMBOL_BLANK = " ";

    public static final String SYMBOL_SEMICOLON = ";";

    public static final String SYMBOL_SEMICOLON_CN = "；";

    public static final String SYMBOL_STR_NO_VALUE = "-";

    public static final Long SYMBOL_LONG_NO_VALUE = -1L;

    public static final String SYMBOL_DATE_NO_VALUE = "9999-12-31 00:00:00";

    public static final String SYMBOL_DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";

    public static final Integer SOLR_START = 0;

    public static final Integer SOLR_ROWS = 100;

}
