package org.jcjxb.wsn.common;

import java.util.List;

public class CommonTool {

	public static String toJSArray(List<String> dataList) {
		StringBuffer result = new StringBuffer("[");
		if (dataList != null) {
			if (dataList.size() > 0) {
				result.append("'");
				result.append(dataList.get(0));
				result.append("'");
			}
			for (int i = 1; i < dataList.size(); ++i) {
				result.append(",'");
				result.append(dataList.get(i));
				result.append("'");
			}
		}
		result.append("]");
		return result.toString();
	}

	public static double distance(double x1, double y1, double x2, double y2) {
		double xDis = x1 - x2;
		double yDis = y1 - y2;
		return Math.sqrt(xDis * xDis + yDis * yDis);
	}
}
