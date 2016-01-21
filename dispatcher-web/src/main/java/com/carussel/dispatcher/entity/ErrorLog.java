package com.carussel.dispatcher.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.carussel.dispatcher.api.BaseErrorLog;

@Entity
@Table(name = "error_log", indexes = { @Index(columnList = "system", name = "idx_error_log_system"),
		@Index(columnList = "module", name = "idx_error_log_module"),
		@Index(columnList = "log_date", name = "idx_error_log_log_date"),
		@Index(columnList = "message", name = "idx_error_message") })
@SequenceGenerator(sequenceName = "apache_log_sequence", name = "apache_log_sequence_generator", allocationSize = 1, initialValue = 1)
public class ErrorLog {
	@Id
	@GeneratedValue(generator = "apache_log_sequence_generator", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "host_name")
	private String hostName;

	@Column(name = "module")
	private String module;

	@Column(name = "system")
	private String system;

	@Column(name = "message", length = 4096)
	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "log_date")
	private Date logDate;

	@Column(name = "error_level")
	private String errorLevel;

	@Column(name = "ip_address")
	private String ipAddress;

	public ErrorLog(String errorLevel, String message, Long count) {
		super();
		this.message = message;
		this.errorLevel = errorLevel;
		this.count = count;
	}

	private Long count;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
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

	public ErrorLog() {

	}

	public ErrorLog(BaseErrorLog baseErrorLog) {

		this.hostName = baseErrorLog.getHost();
		this.module = baseErrorLog.getModule();
		this.system = baseErrorLog.getSystem();
		this.message = baseErrorLog.getMessage();
		this.logDate = baseErrorLog.getLogDate();
		this.errorLevel = baseErrorLog.getErrorLevel();
		this.ipAddress = baseErrorLog.getIpAddress();
	}

	@Override
	public String toString() {
		return "ErrorLog [id=" + id + ", hostName=" + hostName + ", module=" + module + ", system=" + system
				+ ", message=" + message + ", logDate=" + logDate + ", errorLevel=" + errorLevel + ", ipAddress="
				+ ipAddress + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

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

}
