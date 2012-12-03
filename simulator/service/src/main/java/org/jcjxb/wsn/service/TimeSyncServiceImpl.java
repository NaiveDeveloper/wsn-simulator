package org.jcjxb.wsn.service;

import org.jcjxb.wsn.service.proto.MasterService;
import org.jcjxb.wsn.service.proto.MasterService.TimeAckResponse;
import org.jcjxb.wsn.service.proto.MasterService.TimeSyncRequest;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

public class TimeSyncServiceImpl extends MasterService.TimeSyncService
		implements MasterService.TimeSyncService.BlockingInterface {

	@Override
	public void sync(RpcController controller, TimeSyncRequest request,
			RpcCallback<TimeAckResponse> done) {
		TimeAckResponse.Builder builder = TimeAckResponse.newBuilder();
		builder.setGlobalTime(request.getLocalTime() + 100);
		done.run(builder.build());
	}

	public TimeAckResponse sync(RpcController controller,
			TimeSyncRequest request) throws ServiceException {
		TimeAckResponse.Builder builder = TimeAckResponse.newBuilder();
		builder.setGlobalTime(request.getLocalTime() + 100);
		return builder.build();
	}
}
