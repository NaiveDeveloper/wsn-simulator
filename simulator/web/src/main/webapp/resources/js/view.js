var tpanel = null;
var nodeLayer = null;
var sourceLayer = null;
var clusterLayer = null;

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
				
				tpanel.addLayer(nodeLayer);
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
	$.ajax({
		url : 'getAnimationData',
		type : 'GET',
		data : {
			id : id
		},
		dataType : 'json',
		success : function(data) {
			if (data.errorMsg) {
				$('#status').html("<div class='alert alert-error fade in'>" + 
						"<a class='close' data-dismiss='alert' href='#'>&times;</a>" +
						data.errorMsg + "</div>");
			} else {
				$('#status').html("<div class='alert alert-info fade in'>" + 
						"<a class='close' data-dismiss='alert' href='#'>&times;</a>" +
						"正在进行动画...</div>");
				if(sourceLayer == null) {
					sourceLayer = new SourceLayer(1000, 750, nodeLayer.xPro, nodeLayer.yPro);
					tpanel.addLayer(sourceLayer);
				}
				if(clusterLayer == null) {
					clusterLayer = new ClusterLayer(1000, 750, nodeLayer.getNodesData());
					tpanel.addLayer(clusterLayer);
				}
				animationBegin(data);
				// Test first
				//$("#simConfig .modal-body").html(JSON.stringify(data));
				//$("#simConfig").modal("show");
			}
		},
		error : function(xhr, errorInfo) {
			$('#status').html("<div class='alert alert-error fade in'>" + 
					"<a class='close' data-dismiss='alert' href='#'>&times;</a>" +
					errorInfo + ": " + xhr.statusText + "</div>");
		}
	});
}

var animationHandlers = {
	'NodeDie': nodeDieHandler,
	'Source' : sourceHandler,
	'Cluster': clusterHandler
};

function nodeDieHandler(animation) {
	nodeLayer.setNodeDie(animation.nodes);
}

function sourceHandler(animation) {
	var sources = [];
	for(var i = 0; i < animation.positions.length; ++i) {
		var pos = animation.positions[i];
		sources.push(new Source(pos.x, pos.y, animation.radius));
	}
	sourceLayer.setSources(sources);
	sourceLayer.redraw();
}
function clusterHandler(animation) {
	var clusters = [];
	for(var i = 0; i < animation.clusters.length; ++i) {
		var cluster = animation.clusters[i];
		clusters.push(new Cluster(cluster.ch, cluster.members));
	}
	clusterLayer.setClusters(clusters);
	clusterLayer.redraw();
}

var animationIndex = 0;
var animations = [];
function animationTimeout() {
	if(animationIndex >= animations.length) {
		animationEnd();
		return;
	}
	
	var cycle = animations[animationIndex].cycle;
	while(animationIndex < animations.length && cycle == animations[animationIndex].cycle) {
		animationHandlers[animations[animationIndex].name](animations[animationIndex]);
		++animationIndex;
	}
	tpanel.redraw();
	setTimeout("animationTimeout()", 100);
}

function animationBegin(data) {
	animationIndex = 0;
	animations = data.animations;
	setTimeout("animationTimeout()", 100);
}

function animationEnd() {
	$('#status').html("<div class='alert alert-info fade in'>" + 
			"<a class='close' data-dismiss='alert' href='#'>&times;</a>" +
			"动画结束...</div>");
	nodeLayer.clearNodeDie();
	sourceLayer.setSources([]);
	clusterLayer.setClusters([]);
	nodeLayer.redraw();
	sourceLayer.redraw();
	clusterLayer.redraw();
	tpanel.redraw();
	animationIndex = 0;
	animations = [];
	$("#viewAnimation").removeAttr("disabled");
}
