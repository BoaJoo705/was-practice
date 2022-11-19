package org.example;

import org.example.calculator.HttpRequest;
import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientRequestHandler implements Runnable{

    private  static final Logger logger = LoggerFactory.getLogger(ClientRequestHandler.class);

    private final Socket clientSocket;

    public ClientRequestHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        /**
         * Step2 - 사용자 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리
         * 하도록 한다.
         * 사용자가 몰리면 많은 쓰레드를 새로 생성하게 된다. 쓰레드는 독립적인 스택메모리를 할당받고 메모리를 할당받는 작업은 비용이 많이 든다.
         * 메모리 할당작업이 많이 발생하여 좋지 않다.쓰레드가 많아지게 되면 메모리 사용량이 많아 지게 되고 서버가 다운될 가능성도 있다.
         */

        logger.info("[ClientRequestHandler] new Client {} started.",Thread.currentThread().getName()); //new Client pool-1-thread-1 started.

        try(InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()){
            System.out.println("찍히나??");
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)); // utf-8로 String 을 인코딩함
            DataOutputStream dos = new DataOutputStream(out);


            HttpRequest httpRequest = new HttpRequest(br);
            // GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
            if(httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) { // get요청이고, path가 /calculate면
                QueryStrings queryStrings = httpRequest.getQueryStrings();  //

                int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                String operator = queryStrings.getValue("operator");
                int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
                byte[] body = String.valueOf(result).getBytes();

                HttpResponse response = new HttpResponse(dos);
                response.response200Header("application/json", body.length);
                response.responseBody(body);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
