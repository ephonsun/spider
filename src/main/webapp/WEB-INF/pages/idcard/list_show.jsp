<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../includes/includes.jsp"%>
<%@ taglib prefix="convert" uri="/WEB-INF/convert.tld"%>
<%@page contentType="text/html;charset=utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>身份证查询结果</title>
	<link href="../css/front.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		img {
		border: 0;
	}
	.user-img img {
		margin: 5px 0;
		width: 120px;
		height: 90px;
	}
	.selected {
		color: red;
	}
	</style>
</head>

<body>
	<script type="text/javascript" language="javascript">
		var url_prifix="/idcard/query.do?a=${a}&num=${num}&format=${format}";
		url_prifix +="&text=${convert:urlencode(text)}";
	</script>
	
	<div class="set-area-int">
		身份地址：<span class="fred">${card.address }</span><br />
	</div>
	
</body>
</html>

