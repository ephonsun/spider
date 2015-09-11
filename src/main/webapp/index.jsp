<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>

<html>
    <body>
        hello,myspider system.<br>
		<hr>
		<a href="/idcard/index_front.html">身份证号查询 http://idcard.911cha.com/</a>
		<hr>
		<a href="/p2pblack/index_front.html">网贷信用黑名单查询http://www.p2pblack.com/</a>
		<hr>
		<a href="/goods/plist_front.html">商品列表解析</a>
		<hr>
		<a href="/goods/index_front.html">商品详情解析</a>
		<hr>
		<a href="/goods/flist_front.html">商品自动爬取</a>
	</body>
	
</html>






