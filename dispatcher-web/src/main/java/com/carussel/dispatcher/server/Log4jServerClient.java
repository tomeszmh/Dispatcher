package com.carussel.dispatcher.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.carussel.dispatcher.api.BaseAccessLog;
import com.carussel.dispatcher.api.BaseErrorLog;
import com.carussel.dispatcher.entity.AccessLog;
import com.carussel.dispatcher.entity.ErrorLog;
import com.carussel.dispatcher.repository.ApacheAccessLogRepository;
import com.carussel.dispatcher.repository.ApacheErrorLogRepository;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Log4jServerClient implements Runnable {
	Logger logger = Logger.getLogger(Log4jServerClient.class);

	private Socket socket = null;

	private static int clients = 0;

	private ApacheAccessLogRepository apacheAccessLogRepository;

	private ApacheErrorLogRepository apacheErrorLogRepository;

	@Override
	public void run() {
		incClients();
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = "";
			DateFormat format = null;
			while ((line = in.readLine()) != null) {
				ObjectMapper mapper = new ObjectMapper();
				String moduleName = parse(line);

				if (moduleName != null && moduleName.equals("access")) {
					mapper.setDateFormat(format);
					format = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
					mapper.setDateFormat(format);
					BaseAccessLog baseAccessLog = mapper.readValue(line, BaseAccessLog.class);
					apacheAccessLogRepository.save(new AccessLog(baseAccessLog));
				} else if (moduleName != null && moduleName.equals("error")) {
					format = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.ENGLISH);
					mapper.setDateFormat(format);
					BaseErrorLog baseErrorLog = mapper.readValue(line, BaseErrorLog.class);
					apacheErrorLogRepository.save(new ErrorLog(baseErrorLog));
				}
			}
		} catch (IOException e) {
			if (!e.getMessage().equals("Connection reset")) {
				logger.error("IOException ", e);
			}
			logger.error("IO Exception", e);
		}
		decClients();
	}

	public String parse(String json) {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		JsonNode rootNode = null;
		Iterator<Map.Entry<String, JsonNode>> fieldsIterator = null;

		try {
			rootNode = mapper.readTree(json);
			fieldsIterator = rootNode.fields();
			while (fieldsIterator.hasNext()) {

				Map.Entry<String, JsonNode> field = fieldsIterator.next();
				if (field.getKey().equals("module"))
					return field.getValue().asText();
			}
		} catch (JsonProcessingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public synchronized static boolean isClients() {
		return clients >= 0;
	}

	public synchronized static void incClients() {
		clients++;
	}

	public synchronized static void decClients() {
		clients--;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ApacheAccessLogRepository getApacheLogRepository() {
		return apacheAccessLogRepository;
	}

	public void setApacheLogRepository(ApacheAccessLogRepository apacheLogRepository) {
		this.apacheAccessLogRepository = apacheLogRepository;
	}

	public ApacheErrorLogRepository getApacheErrorLogRepository() {
		return apacheErrorLogRepository;
	}

	public void setApacheErrorLogRepository(ApacheErrorLogRepository apacheErrorLogRepository) {
		this.apacheErrorLogRepository = apacheErrorLogRepository;
	}

}
