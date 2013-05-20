package org.jcjxb.wsn.rpc;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.rpc.LionRpcConnectionFactory.Connection;

import com.google.protobuf.RpcCallback;

public class LionRpcConnectionHandler implements Runnable {
	
	private static Logger logger = Logger.getLogger(LionRpcConnectionHandler.class);

	private Connection connection;

	private boolean identical;

	private LionRpcForwarder rpcForwarder;

	public LionRpcConnectionHandler(Connection connection, boolean identical,
			LionRpcForwarder rpcForwarder) {
		if (connection == null) {
			throw new IllegalArgumentException();
		}
		this.connection = connection;
		this.identical = identical;
		this.rpcForwarder = rpcForwarder;
	}

	public void run() {
		try {
			while (!connection.isClosed()) {
				LionRpcMessage.Request.Builder builder = LionRpcMessage.Request
						.newBuilder();
				connection.recieveMessage(builder);
				if (!builder.isInitialized()) {
					sendResponse(handleError("Recived message is not complete"));
					return;
				}
				LionRpcMessage.Request rpcRequest = builder.build();
				if (this.identical) {
					LionRpcMessage.Response rpcResponse = rpcForwarder
							.doBlockCall(rpcRequest);
					sendResponse(rpcResponse);
				} else {
					rpcForwarder.doAsyncCall(rpcRequest,
							new RpcCallback<LionRpcMessage.Response>() {
								public void run(
										LionRpcMessage.Response rpcResponse) {
									try {
										sendResponse(rpcResponse);
									} catch (IOException e) {
										logger.error("Exception happens", e);
										closeConnection(connection);
									}
								}
							});
				}
				// For persistent connection, close do nothing here
				connection.close();
			}
		} catch (IOException e) {
			logger.error("Exception happens", e);
			closeConnection(connection);
		}
	}

	private LionRpcMessage.Response handleError(String errorText) {
		return LionRpcMessage.Response.newBuilder().setErrorText(errorText)
				.build();
	}

	private void sendResponse(LionRpcMessage.Response rpcResponse)
			throws IOException {
		connection.sendMessage(rpcResponse);
	}

	private void closeConnection(Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (IOException e) {
		}
	}
}
