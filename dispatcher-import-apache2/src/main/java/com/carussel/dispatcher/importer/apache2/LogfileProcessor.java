package com.carussel.dispatcher.importer.apache2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carussel.dispatcher.api.BaseAccessLog;
import com.carussel.dispatcher.api.BaseErrorLog;
import com.carussel.dispatcher.importer.communication.Client;
import com.carussel.dispatcher.importer.parser.ApacheLogParser;

public class LogfileProcessor {

	private static final Logger logger = LoggerFactory.getLogger(LogfileProcessor.class);

	private static final int httpsStatusCodes[] = { 400, 403, 404, 408, 413, 500 };

	final Properties properties = new Properties();

	public void readProperties() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(Main.filePathProperties);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error("IOException, can't load property file", e);
		}
	}

	public BufferedReader readFile(String fileLocation) {
		InputStream inputStream;
		BufferedReader in = null;
		if (Main.fileformat.equals("gzip")) {
			try {
				FileInputStream fis = new FileInputStream(fileLocation);
				inputStream = new GZIPInputStream(fis);

				InputStreamReader reader = new InputStreamReader(inputStream);
				in = new BufferedReader(reader);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			InputStream is = null;
			try {
				is = new FileInputStream(fileLocation);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			in = new BufferedReader(new InputStreamReader(is));
		}
		return in;
	}

	public void readApacheErrorLog() {
		String str = "";
		BufferedReader reader = null;

		Client client = new Client();
		client.setUpClient();

		readProperties();

		try {
			reader = readFile(properties.getProperty("apache.error.file.location"));

			ApacheLogParser apacheLogParser = new ApacheLogParser();

			logger.info("Start reading Apache error messages from file:"
					+ properties.getProperty("apache.error.file.location"));

			while ((str = reader.readLine()) != null) {
				BaseErrorLog baseErrorLog = new BaseErrorLog();
				apacheLogParser.parseErrorLog(str, baseErrorLog);

				if (baseErrorLog != null) {
					baseErrorLog.setHost(properties.getProperty("host"));
					client.sendData(apacheLogParser.toErrorJSONObject(baseErrorLog) + "\n");
				}
			}
			logger.info("Apache error messages sent.");
			client.closeClient();
		} catch (IOException e) {
			logger.error("IOException: ", e);
		} finally {
			try {
				reader.close();
				logger.info("Connection closed.");
			} catch (IOException e) {
				logger.error("IOException: ", e);
			}
		}
	}

	public boolean isImportantMessage(Integer statuscode) {
		for (int i = 0; i < 6; i++) {
			if (statuscode.equals(httpsStatusCodes[i]))
				return true;
		}
		return false;
	}

	public void readApacheAccessLog() {
		String str = "";
		BufferedReader reader = null;

		Client client = new Client();
		client.setUpClient();
		readProperties();
		try {
			reader = readFile(properties.getProperty("apache.access.file.location"));

			ApacheLogParser apacheLogParser = new ApacheLogParser();

			logger.info("Start reading Apache access messages from file:"
					+ properties.getProperty("apache.access.file.location"));

			while ((str = reader.readLine()) != null) {
				BaseAccessLog apacheLog = new BaseAccessLog();
				apacheLog = apacheLogParser.parseAccessLog(str, apacheLog);

				if (apacheLog != null && apacheLog.getStatusCode() != null) {

					if (isImportantMessage(apacheLog.getStatusCode())) {

						if (apacheLog != null) {

							apacheLog.setHost(properties.getProperty("host"));
							client.sendData((apacheLogParser.toAccessJSONObject(apacheLog) + "\n"));
						}
					}
				}
			}
			logger.info("Apache access messages sent.");

		} catch (IOException e) {
			logger.error("IOExeption: ", e);
		} finally {
			client.closeClient();
			try {
				reader.close();
				logger.info("Connection closed.");
			} catch (IOException e) {
				logger.error("IOExeption: ", e);
			}
		}
	}
}