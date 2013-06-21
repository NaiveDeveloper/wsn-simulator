package org.jcjxb.wsn.common;


public class DeployTool {

	public static double gridDistance(int num, int height, int width) {
		return (Math.sqrt(Math.pow(width + height, 2) + (double) 4 * width * height * num) - width - height) / (2 * num);
	}
}
