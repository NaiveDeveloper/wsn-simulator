package org.jcjxb.wsn.rpc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.SocketFactory;

public class LionPersistentRpcSocketConnectionFactory extends
		LionRpcSocketConnectionFactory {

	public LionPersistentRpcSocketConnectionFactory(InetAddress serverAddr,
			int port, boolean delimited) {
		super(serverAddr, port, delimited);
	}

	public LionPersistentRpcSocketConnectionFactory(InetAddress serverAddr,
			int port, SocketFactory socketFactory, boolean delimited) {
		super(serverAddr, port, socketFactory, delimited);
	}

	@Override
	public Connection createConnection() throws IOException {
		Socket socket = this.socketFactory.createSocket(serverAddr, port);
		return new LionPersistentRpcSocketConnection(socket, delimited);
	}
}
