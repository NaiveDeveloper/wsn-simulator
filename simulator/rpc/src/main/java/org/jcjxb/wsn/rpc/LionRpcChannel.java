package org.jcjxb.wsn.rpc;

import java.io.IOException;
import java.util.concurrent.Executor;

import org.jcjxb.wsn.rpc.LionRpcConnectionFactory.Connection;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class LionRpcChannel implements RpcChannel, BlockingRpcChannel {

	private LionRpcConnectionFactory connectionFactory;

	private Executor executor;

	LionRpcChannel(LionRpcConnectionFactory connectionFactory, Executor executor) {
		this.connectionFactory = connectionFactory;
		this.executor = executor;
	}

	public Message callBlockingMethod(MethodDescriptor method,
			RpcController controller, Message request, Message responsePrototype)
			throws ServiceException {
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
			if (!sendRpcRequest(method, controller, request, connection)) {
				return null;
			}
			LionRpcMessage.Response rpcResponse = receiveRpcResponse(
					controller, connection);
			if (rpcResponse == null) {
				return null;
			}
			Message response = handleRpcResponse(responsePrototype,
					rpcResponse, controller);
			return response;
		} catch (IOException e) {
			handleError(controller, "Create connection error");
			e.printStackTrace();
			return null;
		} finally {
			closeConnection(connection);
		}
	}

	public void callMethod(final MethodDescriptor method,
			final RpcController controller, final Message request,
			final Message responsePrototype, final RpcCallback<Message> done) {
		executor.execute(new Runnable() {
			public void run() {
				Connection connection = null;
				try {
					connection = connectionFactory.createConnection();
					if (!sendRpcRequest(method, controller, request, connection)) {
						runDone(done, null);
						return;
					}
					LionRpcMessage.Response rpcResponse = receiveRpcResponse(
							controller, connection);
					if (rpcResponse == null) {
						runDone(done, null);
						return;
					}
					Message response = handleRpcResponse(responsePrototype,
							rpcResponse, controller);
					runDone(done, response);
				} catch (IOException e) {
					handleError(controller, "Create connection error");
					e.printStackTrace();
					runDone(done, null);
					return;
				} catch (ServiceException e) {
				} finally {
					closeConnection(connection);
				}
			}
		});
	}

	private boolean sendRpcRequest(MethodDescriptor method,
			RpcController controller, Message request, Connection connection)
			throws ServiceException {

		if (!request.isInitialized()) {
			handleError(controller, "Request is uninitialized");
			return false;
		}

		LionRpcMessage.Request rpcRequest = LionRpcMessage.Request.newBuilder()
				.setRequest(request.toByteString())
				.setServiceName(method.getService().getFullName())
				.setMethodName(method.getName()).build();

		try {
			connection.sendMessage(rpcRequest);
		} catch (IOException e) {
			handleError(controller, "Send message error");
			e.printStackTrace();
			try {
				connection.close();
			} catch (IOException e1) {
			}
			return false;
		}
		return true;
	}

	private LionRpcMessage.Response receiveRpcResponse(
			RpcController controller, Connection connection)
			throws ServiceException {
		try {
			LionRpcMessage.Response.Builder builder = LionRpcMessage.Response
					.newBuilder();
			connection.recieveMessage(builder);
			if (!builder.isInitialized()) {
				handleError(controller, "Recieved message is not complete");
				return null;
			}
			return builder.build();
		} catch (IOException e) {
			handleError(controller, "Recieve message error");
			e.printStackTrace();
			return null;
		}
	}

	private Message handleRpcResponse(Message responsePrototype,
			LionRpcMessage.Response rpcResponse, RpcController controller)
			throws ServiceException {
		if (rpcResponse.hasErrorText()) {
			handleError(controller, rpcResponse.getErrorText());
			return null;
		}

		if (!rpcResponse.hasResponse()) {
			return null;
		}

		try {
			Message.Builder builder = responsePrototype.newBuilderForType()
					.mergeFrom(rpcResponse.getResponse());
			if (!builder.isInitialized()) {
				handleError(controller, "Uninitialized RPC response error");
				return null;
			}
			return builder.build();
		} catch (InvalidProtocolBufferException e) {
			handleError(controller, "Invalid protocol buffer error");
			e.printStackTrace();
			return null;
		}
	}

	private void handleError(RpcController controller, String errorText) {
		controller.setFailed(errorText);
	}

	private void runDone(RpcCallback<Message> done, Message message) {
		if (done != null) {
			done.run(message);
		}
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
