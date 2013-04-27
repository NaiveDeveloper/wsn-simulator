<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset='utf-8'>
	<title>大规模无线传感网络并行模拟器－具体数据查看</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css" media="screen"></link>
	<script type="text/javascript" src="resources/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="resources/js/json2.js"></script>
	<script type="text/javascript" src="resources/bootstrap/js/bootstrap.js"></script>
	<style type="text/css">
		html,body {
		    margin:0;
		    width:100%;
		}
	</style>
</head>
<body>
	<div style="text-align: center; width: 100%;">
		<h1 class="text-center">实验场景图</h1>
		<div>
			<canvas id="sceneCanvas" style="border:1px solid #c3c3c3; width: 80%; height : 600px;"></canvas>
		</div>
		<h1 class="text-center">实验数据图表</h1>
	</div>
</body>
<script type="text/javascript">
	var c=document.getElementById("sceneCanvas");
	var cxt=c.getContext("2d");
	cxt.fillStyle="#FF0000";
	cxt.fillRect(0,0,10,10);
</script>
</html>