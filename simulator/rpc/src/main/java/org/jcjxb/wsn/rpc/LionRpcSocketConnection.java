package org.jcjxb.wsn.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.jcjxb.wsn.rpc.LionRpcConnectionFactory.Connection;

import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

public class LionRpcSocketConnection implements Connection {

	private Socket socket;

	private boolean delimited;

	public LionRpcSocketConnection(Socket socket, boolean delimited) {
		this.socket = socket;
		this.delimited = delimited;
	}

	public void sendMessage(Message message) throws IOException {
		OutputStream out = socket.getOutputStream();
		if (delimited) {
			message.writeDelimitedTo(out);
			out.flush();
		} else {
			message.writeTo(out);
			out.flush();
			socket.shutdownOutput();
		}
	}

	public void recieveMessage(Builder builder) throws IOException {
		InputStream in = socket.getInputStream();
		if (delimited) {
			builder.mergeDelimitedFrom(in);
		} else {
			builder.mergeFrom(in);
		}
	}

	public void close() throws IOException {
		if (!socket.isClosed()) {
			socket.close();
		}
	}

	public boolean isClosed() {
		return socket.isClosed();
	}
}
