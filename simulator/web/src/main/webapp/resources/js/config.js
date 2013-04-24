var simulationConfig = null;
var algorithm = null;
var step = 0;

$("#addSimModal").on("show", function() {
	simulationConfig = new Object();
});

$("#addSimModal").on("hide", function() {
	simulationConfig = null;
});

function addConfig() {
	$("#addSimModal").modal({remote:"config"});
	$("#addSimModal").mode("show");
}

function nextStep() {
	step++;
	$("#addSimModal .modal-body").load("config?algorithm=1&step=" + step);
}

