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
	<script type="text/javascript" src="resources/js/tpanel.js"></script>
	<script type="text/javascript" src="resources/js/view.js"></script>
	<script type="text/javascript" src="resources/highcharts/js/highcharts.js"></script>
	<script type="text/javascript" src="resources/highcharts/js/modules/exporting.js"></script>
	<style type="text/css">
		html,body {
		    padding-left: 20px;
			padding-right: 20px;
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
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container">
				<a class="brand" href="resources/page/about.html">大规模无线传感网络并行模拟器</a>
				<form class="navbar-form pull-right margin10">
					<a class="btn" onclick="checkConfig();">查看实验配置</a>
					<button class="btn" id="viewAnimation" onclick="doAnimation();" type="button" disabled>查看动画过程</button>
				</form>
			</div>
		</div>
	</div>
	<div id="status"></div>
	<div style="text-align: center; width: 100%;">
		<div id="errorMsg"></div>
		<h1 class="text-center">实验场景图</h1>
		<div>
			<canvas id="sceneCanvas"></canvas>
		</div>
		<h1 class="text-center">实验数据图表</h1>
		<div id="energyChart"></div>
	</div>
	<div id="simConfig" class="modal hide fade in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">模拟器实验配置信息</h3>
		</div>
		<div class="modal-body">
		</div>
	</div>
</body>
<script type="text/javascript">
	var id = '${id}';
</script>
</html>