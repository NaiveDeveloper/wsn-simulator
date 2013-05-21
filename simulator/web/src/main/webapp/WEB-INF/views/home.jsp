<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset='utf-8'>
	<title>大规模无线传感网络并行模拟器</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css" media="screen"></link>
	<script type="text/javascript" src="resources/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="resources/js/json2.js"></script>
	<script type="text/javascript" src="resources/bootstrap/js/bootstrap.js"></script>
	
	<style type="text/css">
		body {
			padding-left: 20px;
			padding-right: 20px;
		}
	
		.margin10 {
			margin-left: 5px;
			margin-right: 5px;
		}
		
		.tableTitle {
			font-size: x-large;
		}
		
		.pagination li a {
			color: rgb(51, 51, 51);
		}
		
	</style>
</head>
<body>
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container">
				<a class="brand" href="resources/page/about.html">大规模无线传感网络并行模拟器</a>
				<form class="navbar-form pull-right margin10">
					<a class="btn" onclick="addConfig();">新增模拟</a>
					<a href="resources/page/about.html" class="btn">关于我们</a>
				</form>
				<form class="navbar-search pull-right margin10">
					<input type="text" class="search-query" placeholder="搜索">
				</form>
			</div>
		</div>
	</div>
	<div class="container">
		<h1 class="tableTitle text-center">模拟实验列表</h1>
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>序号</th>
					<th>名称</th>
					<th>时间</th>
					<th>状态</th>
					<th>运行时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="log" items="${logs}" varStatus="status">
					<tr>
						<td>${page*pageSize + status.count}</td>
						<td>${log.name}</td>
						<td><fmt:formatDate value="${log.date}" pattern="yyyy-MM-dd HH:mm:ss" type="date"></fmt:formatDate></td>
						<td>${statusMap[log.state]}</td>
						<td>
							<fmt:formatNumber maxFractionDigits="3" value="${(log.endTime - log.startTime) / 1000000}">
							</fmt:formatNumber>
							毫秒
						</td>
						<td>
							<a class="btn" type="button" href="delete?id=${log.id}&query=${query}&page=${page}">删除</a>
							<c:if test="${log.state eq 1}">
								<a class="btn" type="button" href="view?id=${log.id}">查看</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="pagination pagination-centered">
			<ul>
				<c:choose>
					<c:when test="${page <= 0}">
						<li class="disabled"><a href="home?query=${query}&page=0">Previous</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="home?query=${query}&page=${page - 1}">Previous</a></li>
					</c:otherwise>
				</c:choose>
				<c:forEach begin="1" end="${pageNum}" step="1" var="tempPage">
					<c:choose>
						<c:when test="${tempPage - 1 == page}">
							<li class="active"><a href="home?query=${query}&page=${tempPage - 1}" class="">${tempPage}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="home?query=${query}&page=${tempPage - 1}" class="">${tempPage}</a></li>
						</c:otherwise>
					</c:choose>	
				</c:forEach>
				<c:choose>
					<c:when test="${page >= (pageNum - 1)}">
						<li class="disabled"><a href="home?query=${query}&page=${pageNum - 1}">Next</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="home?query=${query}&page=${page + 1}">Next</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
	<div id="addSimModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">模拟器实验配置</h3>
		</div>
		<div class="modal-body">
		</div>
		<div class="modal-footer">
			<button class="btn" onclick="preStep();" disabled id="pre" type="button">上一步</button>
			<button class="btn" onclick="nextStep();" id="next" type="button">下一步</button>
		</div>
	</div>
</body>
<script type="text/javascript" src="resources/js/config.js"></script>
<script type="text/javascript">
	$('.pagination .disabled a, .pagination .active a').on('click', function(e) {
	    e.preventDefault();
	});
</script>
</html>
