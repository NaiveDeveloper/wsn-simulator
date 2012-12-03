package org.jcjxb.wsn.rpc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import javax.net.ServerSocketFactory;

public class LionRpcSocketServerConnectionFactory implements
		LionRpcConnectionFactory {

	private int port;

	protected boolean delimited;

	private InetAddress bindAddr;

	private ServerSocketFactory serverSocketFactory;

	protected volatile ServerSocket serverSocket = null;

	public LionRpcSocketServerConnectionFactory(int port, boolean delimited) {
		this(port, null, delimited);
	}

	public LionRpcSocketServerConnectionFactory(int port, InetAddress bindAddr,
			boolean delimited) {
		this(port, bindAddr, delimited, ServerSocketFactory.getDefault());
	}

	public LionRpcSocketServerConnectionFactory(int port, InetAddress bindAddr,
			boolean delimited, ServerSocketFactory serverSocketFactory) {
		this.port = port;
		this.bindAddr = bindAddr;
		this.delimited = delimited;
		this.serverSocketFactory = serverSocketFactory;
	}

	public synchronized Connection createConnection() throws IOException {
		if (this.serverSocket == null) {
			initServerSocket();
		}
		return acceptConnection();
	}
	
	protected Connection acceptConnection() throws IOException {
		return new LionRpcSocketConnection(serverSocket.accept(), delimited);
	}

	private synchronized ServerSocket initServerSocket() throws IOException {
		if (this.serverSocket == null) {
			this.serverSocket = serverSocketFactory.createServerSocket(port, 5,
					bindAddr);
		}
		return this.serverSocket;
	}
}
