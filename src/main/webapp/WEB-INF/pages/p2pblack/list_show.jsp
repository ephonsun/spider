<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../includes/includes.jsp"%>
<%@ taglib prefix="convert" uri="/WEB-INF/convert.tld"%>
<%@page contentType="text/html;charset=utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>搜索结果</title>
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
		var url_prifix="/p2pblack/query.do?a=${a}&num=${num}&format=${format}";
		url_prifix +="&text=${convert:urlencode(text)}";
	</script>
	
	<div class="set-area-int">
		搜索结果：<span class="fred">${text}</span><br />
		访问url:<span><a href="${searchurl}">${searchurl}</a></span>
	</div>
	<div class="">
		<table width="100%" cellspacing="0" cellpadding="3" border="1"
			class="tabledetail" bordercolorlight="#B3B3B3"
			bordercolordark="#FFFFFF">
			<tr>
				<th class="serial-number">姓名</th>
				<th class="author">欠款总额</th>
				<th>总罚息</th>
				<th>逾期笔数</th>
				<th>状态</th>
				<th>身份证号</th>
				<th>平台</th>
				<th>详细</th>
			</tr>

			<c:forEach items="${report}" var="v">
				<tr class="">
					<td nowarp>${v.name}</td>
					<td nowarp>${v.fee}</td>
					<td nowrap>${v.penalty}</td>
					<td nowrap>${v.sum}</td>
					<td nowrap>${v.status}</td>
					<td nowrap>${v.idCard}</td>
					<td nowrap>${v.plat}</td>
					<td nowrap><a href="http://www.p2pblack.com/${v.desc}">详情</a></td>
				</tr>
			</c:forEach>

			<tr>
				<td colspan="11">
				<div  class="page"> 
				
				<c:forEach items="${urlPage}" var="v">
				<c:choose>
				<c:when test="${empty v.url}">${v.desc}</c:when>
				<c:otherwise><a href="http://www.p2pblack.com/${v.url}">${v.desc}</a></c:otherwise>
				</c:choose>
				&nbsp;&nbsp;
				</c:forEach>
				</div>
				</td>			
			</tr>
		</table>
	</div>
</body>
</html>

