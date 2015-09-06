<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../includes/includes.jsp"%>
<%@ taglib prefix="convert" uri="/WEB-INF/convert.tld"%>
<%@page contentType="text/html;charset=utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>商品列表解析结果</title>
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
var url_prifix="/goods/query.do?format=${format}";

function nav(product_url,sellercode)
{
	var url = url_prifix ;
	url +="&product_url=${convert:urlencode(product_url)}"
	url+="&sellercode=1005";
	location.replace(url);
}

</script>

	<div class="">
		<table width="100%" cellspacing="0" cellpadding="3" border="1"
			class="tabledetail" bordercolorlight="#B3B3B3"
			bordercolordark="#FFFFFF">
			<tr>
				<th>地址</th>
				<th>操作</th>
			</tr>
			
			<c:forEach items="${plist}" var="v">
			<tr class="">
					<td nowarp>${v}</td>
					<td nowrap>
					<a href="/goods/query.do?&product_url=${convert:urlencode(v)}&sellercode=1005&classiccode=${classiccode}
					&maincat=${maincat}&secondcat=${secondcat}&range=${range}" 
					target="_blank">爬取</a></td>
			</c:forEach>
		</table>
	</div>
		
	
</body>
</html>

