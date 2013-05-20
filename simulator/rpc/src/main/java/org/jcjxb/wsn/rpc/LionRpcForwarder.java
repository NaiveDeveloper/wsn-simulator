package org.jcjxb.wsn.rpc;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.protobuf.BlockingService;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;
import com.google.protobuf.ServiceException;

public class LionRpcForwarder {
	
	private static Logger logger = Logger.getLogger(LionRpcForwarder.class);

	private final Map<String, Service> serviceMap = new HashMap<String, Service>();

	private final Map<String, BlockingService> blockingServiceMap = new HashMap<String, BlockingService>();

	public void registerService(Service service) {
		if (service == null) {
			throw new IllegalArgumentException();
		}
		System.out.println("Register Service: "
				+ service.getDescriptorForType().getFullName());
		serviceMap.put(service.getDescriptorForType().getFullName(), service);
	}

	public void registerBlockingService(BlockingService service) {
		if (service == null) {
			throw new IllegalArgumentException();
		}
		System.out.println("Register Blocking Service: "
				+ service.getDescriptorForType().getFullName());
		blockingServiceMap.put(service.getDescriptorForType().getFullName(),
				service);
	}

	public void doAsyncCall(LionRpcMessage.Request rpcRequest,
			final RpcCallback<LionRpcMessage.Response> done) {
		Service rpcService = serviceMap.get(rpcRequest.getServiceName());
		if (rpcService == null) {
			done.run(doBlockCall(rpcRequest));
			return;
		}
		MethodDescriptor methodDescriptor;
		if ((methodDescriptor = getMethod(rpcRequest,
				rpcService.getDescriptorForType())) == null) {
			done.run(handleError("Do not support this method error"));
			return;
		}
		Message request;
		if ((request = getRequest(rpcRequest,
				rpcService.getRequestPrototype(methodDescriptor))) == null) {
			done.run(handleError("Request Param error"));
			return;
		}
		final RpcController controller = new LionRpcController();
		RpcCallback<Message> callback = new RpcCallback<Message>() {
			public void run(Message response) {
				done.run(createRpcResponse(response, controller));
			}
		};
		rpcService.callMethod(methodDescriptor, controller, request, callback);
	}

	public LionRpcMessage.Response doBlockCall(LionRpcMessage.Request rpcRequest) {
		BlockingService rpcService = blockingServiceMap.get(rpcRequest
				.getServiceName());
		if (rpcService == null) {
			return handleError("Do not support this service");
		}
		MethodDescriptor methodDescriptor;
		if ((methodDescriptor = getMethod(rpcRequest,
				rpcService.getDescriptorForType())) == null) {
			return handleError("Do not support this method error");
		}
		Message request;
		if ((request = getRequest(rpcRequest,
				rpcService.getRequestPrototype(methodDescriptor))) == null) {
			return handleError("Request Param error");
		}
		final RpcController controller = new LionRpcController();
		try {
			Message response = rpcService.callBlockingMethod(methodDescriptor,
					controller, request);
			return createRpcResponse(response, controller);
		} catch (ServiceException e) {
			logger.error("Exception happens", e);
			return handleError("Calling blocking method error");
		}
	}

	private LionRpcMessage.Response handleError(String errorText) {
		return LionRpcMessage.Response.newBuilder().setErrorText(errorText)
				.build();
	}

	private MethodDescriptor getMethod(LionRpcMessage.Request rpcRequest,
			ServiceDescriptor descriptor) {
		MethodDescriptor method = descriptor.findMethodByName(rpcRequest
				.getMethodName());
		return method;
	}

	private Message getRequest(LionRpcMessage.Request rpcRequest,
			Message requestPrototype) {
		Message.Builder builder;
		try {
			builder = requestPrototype.newBuilderForType().mergeFrom(
					rpcRequest.getRequest());
			if (!builder.isInitialized()) {
				return null;
			}
		} catch (InvalidProtocolBufferException e) {
			logger.error("Exception happens", e);
			return null;
		}
		return builder.build();
	}

	private LionRpcMessage.Response createRpcResponse(Message response,
			RpcController controller) {
		LionRpcMessage.Response.Builder responseBuilder = LionRpcMessage.Response
				.newBuilder();
		if (!controller.failed()) {
			responseBuilder.setResponse(response.toByteString());
		} else {
			responseBuilder.setErrorText(controller.errorText());
		}
		return responseBuilder.build();
	}
}
