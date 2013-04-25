<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset='utf-8'>
	<title>大规模无线传感网络并行模拟器</title>
	<meta charset="">
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
					<a href="#" class="btn" onclick="addConfig();">新增模拟</a>
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
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td>直接发送算法1</td>
					<td>2013-04-23</td>
					<td>成功运行</td>
					<td><button class="btn" type="button">删除</button></td>
				</tr>
				<tr>
					<td>2</td>
					<td>直接发送算法2</td>
					<td>2013-04-23</td>
					<td>成功运行</td>
					<td>
						<button class="btn" type="button">删除</button></td>
				</tr>
				<tr>
					<td>3</td>
					<td>直接发送算法3</td>
					<td>2013-04-23</td>
					<td>成功运行</td>
					<td><button class="btn" type="button">删除</button></td>
				</tr>
				<tr>
					<td>4</td>
					<td>直接发送算法4</td>
					<td>2013-04-23</td>
					<td>成功运行</td>
					<td><button class="btn" type="button">删除</button></td>
				</tr>
				<tr>
					<td>5</td>
					<td>直接发送算法5</td>
					<td>2013-04-23</td>
					<td>成功运行</td>
					<td><button class="btn" type="button">删除</button></td>
				</tr>
				<tr>
					<td>6</td>
					<td>直接发送算法6</td>
					<td>2013-04-23</td>
					<td>成功运行</td>
					<td><button class="btn" type="button">删除</button></td>
				</tr>
				<tr>
					<td>7</td>
					<td>直接发送算法6</td>
					<td>2013-04-23</td>
					<td>成功运行</td>
					<td><button class="btn" type="button">删除</button></td>
				</tr>
				<tr>
					<td>8</td>
					<td>直接发送算法6</td>
					<td>2013-04-23</td>
					<td>成功运行</td>
					<td><button class="btn" type="button">删除</button></td>
				</tr>
			</tbody>
		</table>
		<div class="pagination pagination-centered">
			<ul>
				<li><a href="#">Previous</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">Next</a></li>
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
			<!--<button class="btn" onclick="preStep();" disabled id="pre">上一步</button>-->
			<button class="btn" onclick="nextStep();" id="next">下一步</button>
		</div>
	</div>
</body>
<script type="text/javascript" src="resources/js/config.js"></script>
</html>
