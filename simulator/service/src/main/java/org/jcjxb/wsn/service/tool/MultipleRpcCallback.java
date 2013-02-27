package org.jcjxb.wsn.service.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

public class MultipleRpcCallback<ParameterType> implements RpcCallback<ParameterType> {

	private CountDownLatch latch = null;

	private RpcCallback<ParameterType> callback = null;

	private List<RpcController> controllers = new ArrayList<RpcController>();

	public MultipleRpcCallback(int count, RpcCallback<ParameterType> callback) {
		latch = new CountDownLatch(count);
		this.callback = callback;
	}

	@Override
	public void run(ParameterType parameter) {
		if (callback != null) {
			callback.run(parameter);
		}
		latch.countDown();
	}

	public void addRpcController(RpcController controller) {
		controllers.add(controller);
	}

	public boolean failed() {
		for (RpcController controller : controllers) {
			if (controller.failed()) {
				return true;
			}
		}
		return false;
	}

	public String errorText(String delimiter) {
		StringBuilder builder = null;
		for (RpcController controller : controllers) {
			if (controller.failed()) {
				if (builder == null) {
					builder = new StringBuilder();
					builder.append(controller.errorText());
				} else {
					builder.append(delimiter);
					builder.append(controller.errorText());
				}
			}
		}
		return builder == null ? null : builder.toString();
	}

	public void waitEnd() throws InterruptedException {
		latch.await();
	}
}
