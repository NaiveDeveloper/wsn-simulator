var exec = require('child_process').exec;
var waitpid = require('waitpid');
var spawn = require('child_process').spawn;
var sleep = require('sleep');

var cmd = process.argv[2];
if("stop" == cmd || "start" == cmd) {
	// kill already running processes
	var cmdline = "ps -fA | grep 0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9";
	var ps = exec(cmdline, function (error, stdout, stderr) {
	});
	ps.unref();
	waitpid(ps.pid);
} else {
	return;
}

if("stop" == cmd) {
	return;
}

// start master and slaves
var pkdir = '/var/tssim/package/';
var modules = ['master-0.0.1-SNAPSHOT.jar', 'slave-0.0.1-SNAPSHOT.jar', 'web-0.0.1-SNAPSHOT.war'];
var log4js = ['log4j.master.properties', 'log4j.slave01.properties', 'log4j.slave02.properties'];
var master = spawn('java', ['-Dlog4j.properties=' +  log4js[0], '-jar', modules[0], '-hostConfig=HostConfig.bin'], {cwd: pkdir, detached: true, stdio: 'ignore'});
master.unref();

// wait untill master is started
sleep.sleep(2); // sleep 2 second

var slave01 = spawn('java', ['-Dlog4j.properties=' +  log4js[1], '-jar', modules[1], '-hostConfig=HostConfig.bin', '-hostIndex=0'], {cwd: pkdir, detached: true, stdio: 'ignore'});
slave01.unref();

var slave02 = spawn('java', ['-Dlog4j.properties=' +  log4js[2], '-jar', modules[1], '-hostConfig=HostConfig.bin', '-hostIndex=1'], {cwd: pkdir, detached: true, stdio: 'ignore'});
slave02.unref();

var web = spawn('curl', ['http://admin:admin@localhost:8080/manager/text/deploy?update=true&path=/web&update=true&war=file:' + pkdir + modules[2]], {stdio: 'inherit'});
web.unref();
waitpid(web.pid);
