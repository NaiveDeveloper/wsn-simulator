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
	<script type="text/javascript" src="resources/js/view.js"></script>
	<script type="text/javascript" src="resources/highcharts/js/highcharts.js"></script>
	<script type="text/javascript" src="resources/highcharts/js/modules/exporting.js"></script>
	<style type="text/css">
		html,body {
		    margin:0;
		    width:100%;
		}
		
		#sceneCanvas {
			border: 1px solid #c3c3c3;
			width: 1000px;
			height: 750px;
		}
		
		#energyChart {
			min-width: 400px;
			height: 400px;
			margin: 0 auto;
		}
	</style>
</head>
<body>
	<div style="text-align: center; width: 100%;">
		<div id="errorMsg"></div>
		<h1 class="text-center">实验场景图</h1>
		<div>
			<canvas id="sceneCanvas"></canvas>
		</div>
		<h1 class="text-center">实验数据图表</h1>
		<div id="energyChart"></div>
	</div>
</body>
<script type="text/javascript">
	var canvas = null;
	var cxt = null;
	var id = '${id}';
	$(document).ready(function() {
		canvas = document.getElementById("sceneCanvas");
		canvas.width = 1000;
		canvas.height = 750;
		cxt = canvas.getContext("2d");
		initLocation();
		initEneryCost();
	});
</script>
</html>