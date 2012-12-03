package org.jcjxb.wsn.rpc;

import java.io.IOException;
import java.net.Socket;

public class LionPersistentRpcSocketConnection extends LionRpcSocketConnection {

	public LionPersistentRpcSocketConnection(Socket socket, boolean delimited) {
		super(socket, delimited);
	}

	@Override
	public void close() throws IOException {
	}
}
