var tpanel = null;
var nodeLayer = null;
var sourceLayer = null;

$(document).ready(function() {
	var canvas = document.getElementById("sceneCanvas");
	canvas.width = 1000;
	canvas.height = 750;
	tpanel = new Panel(canvas);
	initLocation();
	initEneryCost();
});

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
				nodeLayer = new NodeLayer(1000, 750, data);
				nodeLayer.redraw();
				
				// Test
				if(sourceLayer == null) {
					sourceLayer = new SourceLayer(1000, 750, nodeLayer.xPro, nodeLayer.yPro);
				}
				sourceLayer.redraw();
				
				tpanel.addLayer(nodeLayer);
				tpanel.addLayer(sourceLayer);
				tpanel.redraw();
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
