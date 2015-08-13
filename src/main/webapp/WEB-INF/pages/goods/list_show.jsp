<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../includes/includes.jsp"%>
<%@ taglib prefix="convert" uri="/WEB-INF/convert.tld"%>
<%@page contentType="text/html;charset=utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>商品解析结果</title>
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
	
	<div class="set-area-int">
		商品名称：<span class="fred">${product.productName }</span><br />
		商品地址：<span class="fred">${product_url }</span><br />
		商家：<span class="fred">${sellercode }</span><br />
		价格：<span class="fred">${product.price }</span><br />
		最大价格：<span class="fred">${product.maxPrice }</span><br />
		详情：<span class="fred">${product.contents }</span><br />
	</div>
	
</body>
</html>

