function Panel(canvas) {
	var layers = [];

	var context = canvas.getContext("2d");

	this.addLayer = function(layer) {
		layers.push(layer);
		return layer;
	};

	this.redraw = function() {
		context.clearRect(0, 0, canvas.width, canvas.height);
		for ( var i = 0; i < layers.length; ++i) {
			var layer = layers[i];
			if (layer.isVisible) {
				context.drawImage(layer.getCanvas(), 0, 0);
			}
		}
	};
}

function Layer(width, height) {
	this.offsetX = 0;
	this.offsetY = 0;

	this.setOffset = function(x, y) {
		this.offsetX = x;
		this.offsetY = y;
	};

	var canvas = document.createElement('canvas');
	canvas.width = width;
	canvas.height = height;
	this.context = canvas.getContext('2d');

	this.isVisible = true;

	this.getCanvas = function() {
		return canvas;
	};
}

function NodeLayer(width, height, nodesData) {
	Layer.apply(this, [ width, height ]);
	var nodeDies = [];

	// Compute node radius
	var nodeNum = nodesData.sensorLocation.length;
	var minDis = Math.min(width, height);
	var nodeSqrt = Math.sqrt(nodeNum);
	var nodeRadius = minDis / (nodeSqrt * 10);
	var sinkRadius = nodeRadius * 2;

	this.sensorColor = "#0000FF";
	this.sendorDieColor = "#000000";
	this.sinkColor = "#FF0000";
	this.xPro = width / nodesData.width;
	this.yPro = height / nodesData.height;

	this.redraw = function() {
		this.context.save();
		this.context.clearRect(0, 0, width, height);

		for ( var i = 0; i < nodeNum; ++i) {
			var color = null;
			if (nodeDies[i]) {
				color = this.sendorDieColor;
			} else {
				color = this.sensorColor;
			}
			drawNode(this.context, nodesData.sensorLocation[i].x * this.xPro, nodesData.sensorLocation[i].y * this.yPro, nodeRadius, color);
		}
		for ( var i = 0; i < nodesData.sinkLocation.length; ++i) {
			drawNode(this.context, nodesData.sinkLocation[i].x * this.xPro, nodesData.sinkLocation[i].y * this.yPro, sinkRadius,
					this.sinkColor);
		}
		this.context.restore();
	};

	this.setNodeDie = function(nodesDead) {
		this.context.save();
		for ( var i = 0; i < nodesDead.length; ++i) {
			var j = nodesDead[i];
			nodeDies[j] = true;
			drawNode(this.context, nodesData.sensorLocation[j].x * this.xPro, nodesData.sensorLocation[j].y * this.yPro, nodeRadius,
					this.sendorDieColor);
		}
		this.context.restore();
	};
	
	this.clearNodeDie = function() {
		nodeDies = [];
	};

	this.getNodesData = function() {
		return nodesData;
	};
}

function SourceLayer(width, height, xPro, yPro) {
	Layer.apply(this, [ width, height ]);
	var sources = [];
	
	this.sourceColor = "#00FF00";
	
	this.redraw = function() {
		this.context.save();
		this.context.clearRect(0, 0, width, height);
		for(var i = 0; i < sources.length; ++i) {
			var source = sources[i];
			drawSource(this.context, source.cx * xPro, source.cy * yPro, source.radius * Math.sqrt(xPro * yPro), this.sourceColor);
		}
		this.context.restore();
	};
	
	this.setSources = function(sourcestoSet) {
		sources = sourcestoSet;
	};
}

function ClusterLayer(width, height, nodesData) {
	Layer.apply(this, [ width, height ]);
	var clusters = [];

	this.clusterColor = "#00FFFF";
	this.lineColor = "#00AAAA";

	var nodeNum = nodesData.sensorLocation.length;
	var minDis = Math.min(width, height);
	var nodeSqrt = Math.sqrt(nodeNum);
	var clusterRadius = minDis / (nodeSqrt * 7);

	var xPro = width / nodesData.width;
	var yPro = height / nodesData.height;

	this.redraw = function() {
		this.context.save();
		this.context.clearRect(0, 0, width, height);
		for ( var i = 0; i < clusters.length; ++i) {
			var cluster = clusters[i];
			drawNode(this.context, nodesData.sensorLocation[cluster.chId].x * xPro, nodesData.sensorLocation[cluster.chId].y * yPro,
					clusterRadius, this.clusterColor);

			var members = cluster.members;
			for ( var j = 0; j < members.length; ++j) {
				drawLine(this.context, nodesData.sensorLocation[cluster.chId].x * xPro, nodesData.sensorLocation[cluster.chId].y * yPro, 
						nodesData.sensorLocation[members[j]].x * xPro, nodesData.sensorLocation[members[j]].y * yPro, this.lineColor);
			}
		}
		this.context.restore();
	};

	this.setClusters = function(clusterstoSet) {
		clusters = clusterstoSet;
	};
}

function drawNode(cxt, cx, cy, r, color) {
	cxt.fillStyle = color;
	cxt.beginPath();
	cxt.arc(cx, cy, r, 0, Math.PI * 2, true);
	cxt.closePath();
	cxt.fill();
}

function drawSource(cxt, cx, cy, r, color) {
	cxt.strokeStyle = color;
	cxt.beginPath();
	cxt.arc(cx, cy, r, 0, Math.PI * 2, true);
	cxt.closePath();
	cxt.stroke();
}

function drawLine(cxt, sx, sy, ex, ey, color) {
	cxt.fillStyle = color;
	cxt.moveTo(sx,sy);
	cxt.lineTo(ex,ey);
	cxt.stroke();
}

function Source(cx, cy, radius) {
	this.cx = cx;
	this.cy = cy;
	this.radius = radius;
}

function Cluster(chId, members) {
	this.chId = chId;
	this.members = members;
}