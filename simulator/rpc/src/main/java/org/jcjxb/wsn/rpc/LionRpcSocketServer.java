package org.jcjxb.wsn.rpc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;

public class LionRpcSocketServer extends LionRpcServer {

	public LionRpcSocketServer(int port, String host)
			throws UnknownHostException {
		this(port, InetAddress.getByName(host), true, null, true);
	}

	public LionRpcSocketServer(int port, InetAddress bindAddr) {
		this(port, bindAddr, true, null, true);
	}

	public LionRpcSocketServer(int port, InetAddress bindAddr,
			boolean delimited, Executor executor, boolean identical) {
		super(new LionPersistentRpcSocketServerConnectionFactory(port,
				bindAddr, delimited), executor, identical);
	}
}