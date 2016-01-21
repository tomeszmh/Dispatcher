package com.carussel.dispatcher.api;

import java.util.Date;

public class BaseErrorLog {

	private Date logDate;

	private String errorLevel;

	private String ipAddress;

	private String message;

	private String host;
	
	private String module;
	
	private String system;

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(String errorLevel) {
		this.errorLevel = errorLevel;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@Override
	public String toString() {
		return "BaseErrorLog [logDate=" + logDate + ", errorLevel=" + errorLevel + ", ipAddress=" + ipAddress
				+ ", message=" + message + ", host=" + host + ", module=" + module + ", system=" + system + "]";
	}
}
