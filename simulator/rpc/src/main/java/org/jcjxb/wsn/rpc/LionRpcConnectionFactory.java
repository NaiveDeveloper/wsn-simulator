package org.jcjxb.wsn.rpc;

import java.io.IOException;

import com.google.protobuf.Message;

public interface LionRpcConnectionFactory {

	public Connection createConnection() throws IOException;
	
	public interface Connection {
		
		public void sendMessage(Message message) throws IOException;
		
		public void recieveMessage(Message.Builder builder) throws IOException;
		
		public void close() throws IOException;
		
		public boolean isClosed();
	}
}
