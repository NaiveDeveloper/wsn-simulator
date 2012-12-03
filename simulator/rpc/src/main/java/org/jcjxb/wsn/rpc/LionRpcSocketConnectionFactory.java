package org.jcjxb.wsn.rpc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.SocketFactory;

public class LionRpcSocketConnectionFactory implements LionRpcConnectionFactory {

	protected InetAddress serverAddr;

	protected int port;

	protected SocketFactory socketFactory;

	protected boolean delimited;

	public LionRpcSocketConnectionFactory(InetAddress serverAddr, int port,
			boolean delimited) {
		this(serverAddr, port, null, delimited);
	}

	public LionRpcSocketConnectionFactory(InetAddress serverAddr, int port,
			SocketFactory socketFactory, boolean delimited) {
		if (serverAddr == null) {
			throw new IllegalArgumentException();
		}
		if (socketFactory == null) {
			socketFactory = SocketFactory.getDefault();
		}
		this.serverAddr = serverAddr;
		this.port = port;
		this.socketFactory = socketFactory;
		this.delimited = delimited;
	}

	public Connection createConnection() throws IOException {
		Socket socket = this.socketFactory.createSocket(serverAddr, port);
		return new LionRpcSocketConnection(socket, delimited);
	}
}
