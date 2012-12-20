package org.jcjxb.wsn.service.tool;

import java.util.Comparator;

import org.jcjxb.wsn.service.proto.BasicDataType.Host;

public class HostComparator implements Comparator<Host> {

	@Override
	public int compare(Host left, Host right) {
		int hostCompare = left.getHost().compareTo(right.getHost());
		if (hostCompare < 0) {
			return -1;
		} else if (hostCompare == 0) {
			return left.getPort() - right.getPort();
		} else {
			return 1;
		}
	}
}
