package com.carussel.dispatcher.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.carussel.dispatcher.api.BaseAccessLog;

@NamedNativeQuery(name = "findTopAccessLogs", query = "select * from access_log")
@Entity
@Table(name = "access_log", indexes = { @Index(columnList = "system", name = "idx_error_log_system"),
		@Index(columnList = "module", name = "idx_error_log_module"),
		@Index(columnList = "log_date", name = "idx_error_log_log_date") })
@SequenceGenerator(sequenceName = "apache_log_sequence", name = "apache_log_sequence_generator", allocationSize = 1, initialValue = 1)
public class AccessLog {

	@Id
	@GeneratedValue(generator = "apache_log_sequence_generator", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "host")
	private String host;

	@Column(name = "client_ip")
	private String clientIp;

	@Column(length = 4096)
	private String url;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "log_date")
	private Date logdate;

	@Column(name = "method")
	private String method;

	@Column(name = "protocol")
	private String protocol;

	@Column(name = "status_code")
	private Integer statusCode;

	@Column(name = "size")
	private Long size;

	@Column(length = 4096)
	private String referer;

	@Column(name = "user_agent", length = 1024)
	private String userAgent;

	@Column(name = "host_name")
	private String hostName;

	@Column(name = "module")
	private String module;

	@Column(name = "system")
	private String system;

	private Long count;

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

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
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

	public AccessLog(BaseAccessLog baseAccessLog) {
		this.host = baseAccessLog.getHost();
		this.clientIp = baseAccessLog.getClientIp();
		this.url = baseAccessLog.getUrl();
		this.logdate = baseAccessLog.getLogdate();
		this.method = baseAccessLog.getMethod();
		this.protocol = baseAccessLog.getProtocol();
		this.statusCode = baseAccessLog.getStatusCode();
		this.size = baseAccessLog.getSize();
		this.referer = baseAccessLog.getReferer();
		this.userAgent = baseAccessLog.getUserAgent();
		this.hostName = baseAccessLog.getHostName();
		this.module = baseAccessLog.getModule();
		this.system = baseAccessLog.getSystem();
	}

	public AccessLog(String host) {
		this.host = host;
	}

	public AccessLog() {

	}

	public AccessLog(String host, Long id) {
		this.host = host;
		this.id = id;
	}

	public AccessLog(int statusCode, String method,String url,Long count) {
		this.count = count;
		this.statusCode = statusCode;
		this.url = url;
		this.method = method;
	}

	@Override
	public String toString() {
		return "AccessLog [id=" + id + ", host=" + host + ", clientIp=" + clientIp + ", url=" + url + ", logdate="
				+ logdate + ", method=" + method + ", protocol=" + protocol + ", statusCode=" + statusCode + ", size="
				+ size + ", referer=" + referer + ", userAgent=" + userAgent + ", hostName=" + hostName + ", module="
				+ module + ", system=" + system + ", count=" + count + "]";
	}

	
}