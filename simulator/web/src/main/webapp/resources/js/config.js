var simulationConfig = null;
var step = 0;

$("#addSimModal").on("show", function() {
	simulationConfig = new Object();
});

$("#addSimModal").on("hide", function() {
	simulationConfig = null;
	step = 0;
});

function addConfig() {
	$("#addSimModal").modal("show");
	$("#addSimModal .modal-body").load("config");
	checkPreButton();
}

function preStep() {
	checkPreButton();
}

function nextStep() {
	if(step == 0) {
		if(!step0()) {
			alert("该步骤配置信息填写错误");
			return;
		}
	} else {
		if(!algorithms[simulationConfig.algorithmConfig.name].funcs[step - 1]()) {
			alert("该步骤配置信息填写错误");
			return;
		}
		if(step >= algorithms[simulationConfig.algorithmConfig.name].steps.length) {
			// 提交数据
			submitConfig();
			return;
		}
	}
	step++;
	$("#addSimModal .modal-body").load("config?step=" + algorithms[simulationConfig.algorithmConfig.name].steps[step - 1]);
	if(step >= algorithms[simulationConfig.algorithmConfig.name].steps.length) {
		// 修改下一步为提交
		$("#addSimModal #next").text("提交");
	}
	// checkPreButton();
}

function checkPreButton() {
	if(step <= 1) {
		$("#addSimModal #pre").attr("disabled", "disabled");
	} else {
		$("#addSimModal #pre").removeAttr("disabled");
	}
}

var algorithms = {
	'DIRECT': {
		steps : [1, 2, 3, 4, 5, 6, 7],
		funcs : [step1, step2, step3, step4, step5, step6, step7]
	}
};

function submitConfig() {
	//alert(JSON.stringify(simulationConfig));
	$("#addSimModal #next").attr("disabled", "disabled");
	$("#addSimModal .modal-body").html("<div class='alert alert-info'>正在提交实验配置信息，请耐心等待...</div>");
}

// 选择算法
function step0() {
	var name = $("#addSimModal .modal-body form input[name=name]").val();
	if(name) {
		simulationConfig.algorithmConfig = {name : name};
	} else {
		return false;
	}
	return true;
}

// 配置区域大小
function step1() {
	var formData = $("#addSimModal .modal-body form").serializeArray();
	if(!simulationConfig.deployConfig) {
		simulationConfig.deployConfig = {};
	}
	for(var i = 0; i < formData.length; ++i) {
		var item = formData[i];
		if(!item.value) {
			return false;
		}
		simulationConfig.deployConfig[item.name] = item.value;
	}
	return true;
}

// 部署配置(传感器配置)
function step2() {
	var formData = $("#addSimModal .modal-body form").serializeArray();
	if(!simulationConfig.deployConfig) {
		simulationConfig.deployConfig = {};
	}
	simulationConfig.deployConfig.sensorNodeDeployConfig = {};
	for(var i = 0; i < formData.length; ++i) {
		var item = formData[i];
		if(!item.value) {
			return false;
		}
		simulationConfig.deployConfig.sensorNodeDeployConfig[item.name] = item.value;
	}
	return true;
}

// 部署配置(汇集节点配置)
function step3() {
	var formData = $("#addSimModal .modal-body form").serializeArray();
	if(!simulationConfig.deployConfig) {
		simulationConfig.deployConfig = {};
	}
	simulationConfig.deployConfig.sinkNodeDeployConfig = {};
	for(var i = 0; i < formData.length; ++i) {
		var item = formData[i];
		if(!item.value) {
			return false;
		}
		simulationConfig.deployConfig.sinkNodeDeployConfig[item.name] = item.value;
	}
	return true;
}

// 部署配置(事件源配置)
function step4() {
	var formData = $("#addSimModal .modal-body form").serializeArray();
	if(!simulationConfig.deployConfig) {
		simulationConfig.deployConfig = {};
	}
	simulationConfig.deployConfig.sourceEventDeployConfig = {};
	for(var i = 0; i < formData.length; ++i) {
		var item = formData[i];
		if(!item.value) {
			return false;
		}
		simulationConfig.deployConfig.sourceEventDeployConfig[item.name] = item.value;
	}
	return true;
}

// 能量消耗模型配置
function step5() {
	var formData = $("#addSimModal .modal-body form").serializeArray();
	if(!simulationConfig.energyConsumeConfig) {
		simulationConfig.energyConsumeConfig = {};
	}
	for(var i = 0; i < formData.length; ++i) {
		var item = formData[i];
		if(!item.value) {
			return false;
		}
		simulationConfig.energyConsumeConfig[item.name] = item.value;
	}
	return true;
}

// 命令参数
function step6() {
	var formData = $("#addSimModal .modal-body form").serializeArray();
	if(!simulationConfig.commandConfig) {
		simulationConfig.commandConfig = {};
	}
	for(var i = 0; i < formData.length; ++i) {
		var item = formData[i];
		if(!item.value) {
			return false;
		}
		simulationConfig.commandConfig[item.name] = item.value;
	}
	return true;
}

// 其他配置
function step7() {
	var formData = $("#addSimModal .modal-body form").serializeArray();
	for(var i = 0; i < formData.length; ++i) {
		var item = formData[i];
		if(!item.value) {
			return false;
		}
		simulationConfig.commandConfig[item.name] = item.value;
	}
	return true;
}
