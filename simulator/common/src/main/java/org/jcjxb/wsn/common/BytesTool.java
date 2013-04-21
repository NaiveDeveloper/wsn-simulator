package org.jcjxb.wsn.common;


public class BytesTool {

	public static byte[] toBytes(int data) {
		return new byte[] { (byte) (data >>> 24), (byte) (data >>> 16), (byte) (data >>> 8), (byte) data };
	}

	public static byte[] toBytes(int... datas) {
		byte[] result = new byte[datas.length * 4];
		for (int i = 0; i < datas.length; ++i) {
			byte[] tempData = toBytes(datas[i]);
			for (int j = 0; j < 4; ++j) {
				result[i * 4 + j] = tempData[j];
			}
		}
		return result;
	}
}
