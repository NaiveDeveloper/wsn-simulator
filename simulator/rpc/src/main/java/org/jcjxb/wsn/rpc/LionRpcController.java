package org.jcjxb.wsn.rpc;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

public class LionRpcController implements RpcController {

	private boolean failed;

	private String errorText;

	public void reset() {
		this.failed = false;
		this.errorText = null;
	}

	public boolean failed() {
		return this.failed;
	}

	public String errorText() {
		return this.errorText;
	}

	public void startCancel() {
		throw new UnsupportedOperationException(
				"Not allowed to call startCancel method");
	}

	public void setFailed(String reason) {
		this.failed = true;
		this.errorText = reason;
	}

	public boolean isCanceled() {
		throw new UnsupportedOperationException(
				"Not allowed to call isCanceled method");
	}

	public void notifyOnCancel(RpcCallback<Object> callback) {
		throw new UnsupportedOperationException(
				"Not allowed to call notifyOnCancel method");
	}
}
