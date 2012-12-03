package org.jcjxb.wsn.rpc;

import java.io.IOException;
import java.net.InetAddress;

import javax.net.ServerSocketFactory;

public class LionPersistentRpcSocketServerConnectionFactory extends
		LionRpcSocketServerConnectionFactory {

	public LionPersistentRpcSocketServerConnectionFactory(int port,
			boolean delimited) {
		super(port, delimited);
	}

	public LionPersistentRpcSocketServerConnectionFactory(int port,
			InetAddress bindAddr, boolean delimited,
			ServerSocketFactory serverSocketFactory) {
		super(port, bindAddr, delimited, serverSocketFactory);
	}

	public LionPersistentRpcSocketServerConnectionFactory(int port,
			InetAddress bindAddr, boolean delimited) {
		super(port, bindAddr, delimited);
	}

	@Override
	protected Connection acceptConnection() throws IOException {
		return new LionPersistentRpcSocketConnection(serverSocket.accept(), delimited);
	}
}
