package com.carussel.dispatcher.api;

import java.util.Date;

public class BaseAccessLog {
	private Long id;

	private String host;

	private String clientIp;

	private String url;

	private Date logdate;

	private String method;

	private String protocol;

	private Integer statusCode;

	private Long size;

	private String referer;

	private String userAgent;

	private String hostName;

	private String module;

	private String system;

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getLogdate() {
		return logdate;
	}

	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
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
		return "ApacheLog [id=" + id + ", host=" + host + ", clientIp=" + clientIp + ", url=" + url + ", logdate="
				+ logdate + ", method=" + method + ", protocol=" + protocol + ", statusCode=" + statusCode + ", size="
				+ size + ", referer=" + referer + ", userAgent=" + userAgent + ", hostName=" + hostName + "]";
	}
}
