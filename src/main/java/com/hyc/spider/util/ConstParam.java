package com.hyc.spider.util;

public class ConstParam {
	
	
	//查询词最大长度 
	public static final int WORDS_LENGTH_LIMIT = 50;
	
	//翻页 限定最多取50页
	public static final int MAX_PAGE_NUM = 50 ;
	
	//每个统计结果最多返回100个
	public static final  int MAX_FACETED_NUM = 100;
    
	public static final String QUERY_ALL = "all";

	public static final String FORMAT_HTML = "HTML";
	public static final String FORMAT_XML = "XML";
	public static final String FORMAT_JSON = "JSON";
	public static final String FORMAT_DEL = "DEL";

	public static final String ACTION_QUERY = "query";
	
	//精确匹配
	public static final int MATCH_EXACT = 1;
	public static final int MATCH_FUZZY = 0;
	
	   public static final String MSG_SUCCESS = "SUCCESS";
	    public static final String MSG_FAIL = "FALSE";

}
