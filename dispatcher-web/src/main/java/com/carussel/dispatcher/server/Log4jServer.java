package com.carussel.dispatcher.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carussel.dispatcher.repository.ApacheAccessLogRepository;
import com.carussel.dispatcher.repository.ApacheErrorLogRepository;

@Service
public class Log4jServer implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(Log4jServer.class);

  private final int port = 4715;

  private ServerSocket serverSocket = null;

  private boolean isAcceptConnections = true;

  private Log4jServerClient log4jServerClient;

  @Autowired
  ApacheAccessLogRepository apacheAccessLogRepository;

  @Autowired
  ApacheErrorLogRepository apacheErrorLogRepository;

  private static int clients = 0;

  void init() {

    try {
      serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  @Override
  public void run() {
    init();

    while (isAcceptConnections) {
      try {

        Socket socket = serverSocket.accept();
        logger.info("ServerSocket accept.");
        log4jServerClient = new Log4jServerClient();
        log4jServerClient.setApacheLogRepository(apacheAccessLogRepository);
        log4jServerClient.setApacheErrorLogRepository(apacheErrorLogRepository);
        log4jServerClient.setSocket(socket);
        new Thread(log4jServerClient).start();
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    }

    while (clients == 0) {
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        logger.error(e.getMessage());
      }
    }
    deInit();
  };

  void deInit() {
    if (serverSocket != null) {
      try {

        serverSocket.close();
        logger.info("ServerSocket closed.");
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    }
  }

  public synchronized boolean isAcceptConnections() {
    return isAcceptConnections;
  }

  public synchronized void setAcceptConnections(boolean isAcceptConnections) {
    this.isAcceptConnections = isAcceptConnections;
  }
}