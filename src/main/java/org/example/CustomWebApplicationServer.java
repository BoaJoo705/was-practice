package org.example;

import org.example.calculator.HttpRequest;
import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

// 커스텀한 톰캣을 만드는 과정이다.
public class CustomWebApplicationServer {
    private final int port;

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client.");

            while ((clientSocket = serverSocket.accept()) != null) { //클라이언트가 연결되었다는 의미
                logger.info("[CustomWebApplicationServer] client connected!");

                // 쓰레드를 새로 생성해서 클라이언트 요청을 생성

                new Thread(new ClientRequestHandler(clientSocket)).start();

            }

        }
    }

}
