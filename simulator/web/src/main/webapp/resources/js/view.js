function initLocation() {
	$.ajax({
		url : 'simData',
		type : 'GET',
		data : {
			id : id,
			type : 'location'
		},
		dataType : 'json',
		success : function(data) {
			if (data.errorMsg) {
				$("#errorMsg").html("<div class='alert alert-success'>" + data.errorMsg + "</div>");
			} else {
				drawNodes(data);
				if(data.outputType == 'DETAIL') {
					$("#viewAnimation").removeAttr("disabled");
				}
			}
		},
		error : function(xhr, errorInfo) {
			$("#errorMsg").html("<div class='alert alert-error'>" + errorInfo + ": " + xhr.statusText + "</div>");
		}
	});
}

function drawNodes(data) {
	var sensorRadius = drawSensorNodes(data.sensorLocation, data.width, data.height);
	drawSinkNodes(data.sinkLocation, data.width, data.height, sensorRadius);
}

function drawSensorNodes(postions, width, height) {
	var nodeNum = postions.length;
	var minDis = Math.min(width, height);
	var nodeSqrt = Math.sqrt(nodeNum);
	var radius = minDis / (nodeSqrt * 10);
	var xPro = canvas.width / width;
	var yPro = canvas.height / height;
	var color = "#0000FF";
	for(var i = 0; i < nodeNum; ++i) {
		drawCircle(cxt, postions[i].x * xPro, postions[i].y * yPro, radius, color);
	}
	return radius;
}

function drawSinkNodes(postions, width, height, sensorRadius) {
	var nodeNum = postions.length;
	var radius = sensorRadius * 2;
	var xPro = canvas.width / width;
	var yPro = canvas.height / height;
	var color = "#FF0000";
	for(var i = 0; i < nodeNum; ++i) {
		drawCircle(cxt, postions[i].x * xPro, postions[i].y * yPro, radius, color);
	}
}

function drawCircle(cxt, cx, cy, r, color) {
	cxt.fillStyle = color;
	cxt.beginPath();
	cxt.arc(cx, cy, r, 0, Math.PI * 2, true);
	cxt.closePath();
	cxt.fill();
}

function initEneryCost() {
	$.ajax({
		url : 'simData',
		type : 'GET',
		data : {
			id : id,
			type : 'energy'
		},
		dataType : 'json',
		success : function(data) {
			if (data.errorMsg) {
				$("#errorMsg").html("<div class='alert alert-success'>" + data.errorMsg + "</div>");
			} else {
				drawEnergyGraph(data);
			}
		},
		error : function(xhr, errorInfo) {
			$("#errorMsg").html("<div class='alert alert-error'>" + errorInfo + ": " + xhr.statusText + "</div>");
		}
	});
}

function drawEnergyGraph(data) {
	// Calculate energy cost percentage distribution
	var result = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
	var oneItem = 100 / data.eneryList.length;
	for(var i = 0; i < data.eneryList.length; ++i) {
		var index = parseInt(data.eneryList[i].eneryLeft * 10 / data.initialEnergy);
		if(index < 0) {
			index = 0;
		} else if(index >9) {
			index = 9;
		}
		result[index] += oneItem;
	}
	$('#energyChart').highcharts({
		chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80]
        },
        title: {
            text: '能量剩余节点比例'
        },
        xAxis: {
            categories: [
                '0%~10%',
                '10%~20%',
                '20%~30%',
                '30%~40%',
                '40%~50%',
                '50%~60%',
                '60%~70%',
                '70%~80%',
                '80%~90%',
                '90%~100%'
            ]
        },
        yAxis: {
            min: 0,
            max: 100,
            title: {
                text: '节点百分比 (%)'
            }
        },
        tooltip: {
            valueSuffix: '%'
        },
        legend: {
            enabled: false
        },
        series: [{
            name: '能量剩余节点比例',
            data: result
        }]
	});
}

function checkConfig() {
	$("#simConfig").modal("show");
	$("#simConfig .modal-body").load("simConfig?id=" + id);
}

function doAnimation() {
	$('#status').html("<div class='alert alert-info fade in'>" + 
			"<a class='close' data-dismiss='alert' href='#'>&times;</a>" +
			"正在获取动画数据中，请稍后...</div>");
	$("#viewAnimation").attr("disabled", "disabled");
	getAnimationData();
}

function getAnimationData() {
	
}
