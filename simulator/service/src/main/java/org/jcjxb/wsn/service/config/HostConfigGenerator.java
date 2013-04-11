package org.jcjxb.wsn.service.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;
import org.jcjxb.wsn.service.proto.BasicDataType.Host;
import org.jcjxb.wsn.service.proto.SimulatorConfig.HostConfig;

public class HostConfigGenerator {

	private static Logger logger = Logger.getLogger(HostConfigGenerator.class);

	public static void main(String[] args) throws IOException {
		HostConfig.Builder builder = HostConfig.newBuilder();
		builder.setMasterHost(Host.newBuilder().setHost("166.111.70.158").setPort(10000));
		for (int i = 0; i < 2; ++i) {
			builder.addSlaveHost(Host.newBuilder().setHost("166.111.70.158").setPort(10001 + i));
		}
		String userHome = System.getProperty("user.home");
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(userHome + File.separator + "HostConfig.txt"));
		writer.write(builder.build().toString());
		writer.flush();
		writer.close();
		logger.info("HostConfig in generated in " + userHome + File.separator + "HostConfig.txt");

		FileOutputStream out = new FileOutputStream(userHome + File.separator + "HostConfig.bin");
		builder.build().writeTo(out);
		out.close();
		logger.info("HostConfig in generated in " + userHome + File.separator + "HostConfig.bin");
	}
}
