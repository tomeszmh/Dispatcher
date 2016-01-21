package com.carussel.dispatcher.importer.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carussel.dispatcher.importer.apache2.Main;

public class Client {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);
	private final Properties properties = new Properties();
	Socket smtpSocket = null;
	DataOutputStream os = null;
	DataInputStream is = null;

	public void readProperties() {
		InputStream inputStream = Client.class.getClassLoader().getResourceAsStream(Main.connectionProperties);

		try {
			logger.info(Main.connectionProperties);
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error("IOException, can't load property file", e);
		}
	}

	public void setUpClient() {
		try {
			logger.info("Trying to connect host...");
			readProperties();
			smtpSocket = new Socket(properties.getProperty("logserver.ip"),
					Integer.parseInt(properties.getProperty("logserver.port")));
			os = new DataOutputStream(smtpSocket.getOutputStream());
			is = new DataInputStream(smtpSocket.getInputStream());
			logger.info("Successfull connect!");
		} catch (UnknownHostException e) {
			logger.error("Don't know about host: hostname" + e);
		} catch (IOException e) {
			logger.error("Couldn't get I/O for the connection to: localhost" + e);
		}
	}

	public void sendData(String json) {
		if (smtpSocket != null && os != null && is != null) {
			try {
				os.writeBytes(json);
			} catch (IOException e) {
				logger.error("IOException", e);
			}
		}
	}

	public void closeClient() {
		if (smtpSocket != null && os != null && is != null) {
			try {
				os.close();
				is.close();
				smtpSocket.close();
			} catch (UnknownHostException e) {
				logger.error("Trying to connect to unknown host: ", e);
			} catch (IOException e) {
				logger.error("IOException", e);
			}
		}
	}
}