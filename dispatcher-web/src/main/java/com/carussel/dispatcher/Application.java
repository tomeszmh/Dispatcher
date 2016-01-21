package com.carussel.dispatcher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.carussel.dispatcher.server.Log4jServer;

@SpringBootApplication

public class Application {

	private Log4jServer log4jServer;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	void init() {
		log4jServer = (Log4jServer) applicationContext.getBean("log4jServer");
		new Thread(log4jServer).start();
	}

	@PreDestroy
	void deInit() {
		log4jServer.setAcceptConnections(false);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}