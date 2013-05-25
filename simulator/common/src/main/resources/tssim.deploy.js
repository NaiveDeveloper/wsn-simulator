var fs = require('fs');
var path = require('path');
var util = require('util');
var spawn = require('child_process').spawn;
var waitpid = require('waitpid');

// copy files to remote server
var pkdir = '/Users/koulinyuan/.m2/repository/org/jcjxb/wsn/';
var packages = ['slave/0.0.1-SNAPSHOT/slave-0.0.1-SNAPSHOT.jar', 
				'master/0.0.1-SNAPSHOT/master-0.0.1-SNAPSHOT.jar',
				'web/0.0.1-SNAPSHOT/web-0.0.1-SNAPSHOT.war'];
var desdir = 'yuankl@166.111.70.158:/var/tssim/package';
console.info('Preparing to scp files to : ' + desdir);
var scp = spawn("scp", [pkdir + packages[0], pkdir + packages[1], pkdir + packages[2], desdir], {stdio: 'inherit'});
scp.unref();
waitpid(scp.pid);
console.info('Scping files to server is finished');

// start applications on remote server
