package com.carussel.dispatcher.importer.apache2;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static String connectionProperties = "logclient-";
	public static String filePathProperties = "dispatcher-importer-";

	public static String fileformat = "text";

	public static void main(String[] args) {

		if (args.length == 2)
			fileformat = args[1];
		Properties props = new Properties();
		connectionProperties = connectionProperties.concat(args[0] + ".properties");
		filePathProperties = filePathProperties.concat(args[0] + ".properties");
		try {
			props.load(Main.class.getClassLoader().getResourceAsStream("log4j-dev.properties"));
			PropertyConfigurator.configure(props);
		} catch (IOException e) {
			System.err.println("Logger property file couldn't be found.");
		}
		logger.info("The dispatcher-importer started working at: " + new Date().toString());

		LogfileProcessor logFileProcessor = new LogfileProcessor();
		logFileProcessor.readApacheErrorLog();
		logFileProcessor.readApacheAccessLog();

		logger.info("The dispatcher-imporer finished operations at: " + new Date().toString());
	}
}