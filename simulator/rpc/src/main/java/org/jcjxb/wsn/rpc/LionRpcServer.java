package org.jcjxb.wsn.rpc;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.rpc.LionRpcConnectionFactory.Connection;

import com.google.protobuf.BlockingService;
import com.google.protobuf.Service;

public class LionRpcServer {
	
	private static Logger logger = Logger.getLogger(LionRpcServer.class);

	private Executor executor;

	private LionRpcConnectionFactory connectionFactory;

	private LionRpcForwarder rpcForwarder;

	private ServerThread serverThread;

	private boolean identical;

	public LionRpcServer(LionRpcConnectionFactory connectionFactory,
			Executor executor, boolean identical) {
		if (connectionFactory == null) {
			throw new IllegalArgumentException();
		}
		if (executor == null) {
			executor = Executors.newCachedThreadPool();
		}
		this.executor = executor;
		this.connectionFactory = connectionFactory;
		this.serverThread = new ServerThread();
		this.serverThread.setDaemon(true);
		this.identical = identical;
		this.rpcForwarder = new LionRpcForwarder();
	}

	public void registerService(Service service) {
		rpcForwarder.registerService(service);
	}
	
	public void registerBlockingService(BlockingService service) {
		rpcForwarder.registerBlockingService(service);
	}

	public void start() {
		this.serverThread.start();
	}

	public void waitFor(int timeout) throws InterruptedException {
		this.serverThread.wait(timeout);
	}

	public void waitEnd() throws InterruptedException {
		this.serverThread.join();
	}

	private class ServerThread extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					Connection connection = connectionFactory
							.createConnection();
					executor.execute(new LionRpcConnectionHandler(connection,
							identical, rpcForwarder));
				} catch (IOException e) {
					logger.error("Exception happens", e);
					return;
				}
			}
		}
	}
}
