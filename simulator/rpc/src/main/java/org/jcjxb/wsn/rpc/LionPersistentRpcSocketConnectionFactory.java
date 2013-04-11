package org.jcjxb.wsn.rpc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.SocketFactory;

public class LionPersistentRpcSocketConnectionFactory extends LionRpcSocketConnectionFactory {

	private Connection connection = null;

	public LionPersistentRpcSocketConnectionFactory(InetAddress serverAddr, int port, boolean delimited) {
		super(serverAddr, port, delimited);
	}

	public LionPersistentRpcSocketConnectionFactory(InetAddress serverAddr, int port, SocketFactory socketFactory, boolean delimited) {
		super(serverAddr, port, socketFactory, delimited);
	}

	@Override
	public Connection createConnection() throws IOException {
		if (connection == null) {
			Socket socket = this.socketFactory.createSocket(serverAddr, port);
			connection = new LionPersistentRpcSocketConnection(socket, delimited);
		}
		return connection;
	}
}
