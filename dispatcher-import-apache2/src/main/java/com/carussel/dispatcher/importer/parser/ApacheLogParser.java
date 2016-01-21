package com.carussel.dispatcher.importer.parser;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carussel.dispatcher.api.BaseAccessLog;
import com.carussel.dispatcher.api.BaseErrorLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApacheLogParser {

	private static final Logger logger = LoggerFactory.getLogger(ApacheLogParser.class);

	private static final String accessPattern = "^(\\S*) (\\S*).*\\[(.*)\\]\\s\"(\\S*)\\s(\\S*)\\s([^\"]*)\"\\s(\\S*)\\s(\\S*)\\s\"([^\"]*)\"\\s\"([^\"]*)\"";
	private static final String errorPattern = "^\\[([^\\]]+)\\] \\[([^\\]]+)\\] \\[([^\\]]+)\\] (.*)$";
	private static final String errorPatternWithoutIP = "^\\[([^\\]]+)\\] \\[([^\\]]+)\\] (.*)$";

	public BaseAccessLog parseAccessLog(String line, BaseAccessLog logRecord) {
		Pattern r = Pattern.compile(accessPattern);
		Matcher m = r.matcher(line);

		if (m.find()) {
			logRecord.setHost(m.group(1));
			logRecord.setClientIp(m.group(2));

			SimpleDateFormat accesslogDateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");

			try {
				accesslogDateFormat.parse(m.group(3));
				logRecord.setLogdate(new Date(accesslogDateFormat.parse(m.group(3)).getTime()));
				logRecord.setMethod(m.group(4));
				logRecord.setUrl(m.group(5));
				logRecord.setProtocol(m.group(6));
				logRecord.setStatusCode(Integer.parseInt(m.group(7)));
				logRecord.setSize(Long.parseLong(m.group(8)));
				logRecord.setReferer(m.group(9));
				logRecord.setUserAgent(m.group(10));

				logRecord.setModule("access");
				logRecord.setSystem("apache2");
			} catch (ParseException e) {
				logRecord = null;
				logger.error("bad time format:" + e.getMessage());
			}
		} else {
			logRecord = null;
			logger.error("Not compatible access log record: " + line);
		}
		return logRecord;
	}

	public BaseErrorLog parseErrorLog(String line, BaseErrorLog logRecord) {
		Pattern r = Pattern.compile(errorPattern);
		Matcher m = r.matcher(line);
		Pattern p2 = Pattern.compile(errorPatternWithoutIP);
		Matcher m2 = p2.matcher(line);

		SimpleDateFormat accesslogDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");

		if (m.find()) {
			String date = m.group(1);

			try {
				logRecord.setLogDate(new Date(accesslogDateFormat.parse(date).getTime()));
			} catch (ParseException e) {
				logger.error("Can't parse date object.", e);
			}

			logRecord.setIpAddress(m.group(3));
			logRecord.setMessage(m.group(4));
			logRecord.setErrorLevel(m.group(2));
		} else if (m2.find()) {
			String date = m2.group(1);

			try {

				logRecord.setLogDate(new Date(accesslogDateFormat.parse(date).getTime()));
				logRecord.setErrorLevel(m2.group(2));

			} catch (ParseException e) {
				logger.error("Can't parse date object.", e);
			}

			logRecord.setMessage(m2.group(3));
		} else {

			logger.error("Can't parse error line to regexp: " + line);
			return null;
		}

		logRecord.setModule("error");
		logRecord.setSystem("apache2");

		return logRecord;
	}

	public String toErrorJSONObject(BaseErrorLog log) {
		Gson gson = new GsonBuilder().setDateFormat("EEE MMM dd HH:mm:ss yyyy").create();
		return gson.toJson(log);
	}

	public String toAccessJSONObject(BaseAccessLog log) {

		Gson gson = new GsonBuilder().setDateFormat("dd/MMM/yyyy:HH:mm:ss Z").create();
		return gson.toJson(log);
	}
}
