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

                /**
                 * Step1 - 사용자 요청을 메인 Thread 가 처리하도록 한다.
                 */

                try(InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()){
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)); // utf-8로 String 을 인코딩함
                    DataOutputStream dos = new DataOutputStream(out);

//                    String line;
//                    while((line = br.readLine()) != ""){
//                        System.out.println(line);
//                    }

                    HttpRequest httpRequest = new HttpRequest(br);
                    // GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
                    if(httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")){ // get요청이고, path가 /calculate면
                        QueryStrings queryStrings = httpRequest.getQueryStrings();  //

                        int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                        String operator = queryStrings.getValue("operator");
                        int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                        int result = Calculator.calculate(new PositiveNumber(operand1),operator,new PositiveNumber(operand2));
                        byte[] body = String.valueOf(result).getBytes();

                        HttpResponse response = new HttpResponse(dos);
                        response.response200Header("application/json",body.length);
                        response.responseBody(body);
                    }
                }

            }

        }
    }

}
