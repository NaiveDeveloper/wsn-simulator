package org.jcjxb.wsn.rpc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.RpcChannel;

public class LionRpcChannelFactory {

	private static final Executor SAME_THREAD_EXECUTOR = new Executor() {
		public void execute(Runnable command) {
			command.run();
		}
	};

	public static RpcChannel newRpcChannel(String host, int port)
			throws UnknownHostException {
		return newRpcChannel(new LionPersistentRpcSocketConnectionFactory(
				InetAddress.getByName(host), port, true), null);
	}

	public static RpcChannel newRpcChannel(InetAddress serverAddr, int port,
			boolean delimited) {
		return newRpcChannel(new LionPersistentRpcSocketConnectionFactory(
				serverAddr, port, delimited), null);
	}

	public static RpcChannel newRpcChannel(
			LionRpcConnectionFactory connectionFactory, Executor executor) {
		if (executor == null) {
			executor = Executors.newCachedThreadPool();
		}
		return new LionRpcChannel(connectionFactory, executor);
	}

	public static BlockingRpcChannel newBlockingRpcChannel(String host, int port)
			throws UnknownHostException {
		return newBlockingRpcChannel(new LionPersistentRpcSocketConnectionFactory(
				InetAddress.getByName(host), port, true));
	}

	public static BlockingRpcChannel newBlockingRpcChannel(
			InetAddress serverAddr, int port, boolean delimited) {
		return newBlockingRpcChannel(new LionPersistentRpcSocketConnectionFactory(
				serverAddr, port, delimited));
	}

	public static BlockingRpcChannel newBlockingRpcChannel(
			LionRpcConnectionFactory connectionFactory) {
		return new LionRpcChannel(connectionFactory, SAME_THREAD_EXECUTOR);
	}
}
