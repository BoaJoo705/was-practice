package org.example;

import java.util.Objects;

public class RequestLine {
    private final String method; //GET
    private final String urlPath; //calculate?operand1=11&operator=*&operand2=55
    private String queryString;


    public RequestLine(String method, String urlPath, String queryString) {
        this.method = method;
        this.urlPath = urlPath;
        this.queryString = queryString;
    }
    /**
     * GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
     */
    public RequestLine(String requestLine){
        String[] tokens = requestLine.split(" ");
        this.method = tokens[0]; //GET

//        for(int i=0;i<tokens.length;i++){
//            System.out.println("tokens 값:"+tokens[i]);
//        }

        String[] urlPathTokens = tokens[1].split("\\?"); //tokens[1] -> /calculate?operand1=11&operator=*&operand2=55
        this.urlPath = urlPathTokens[0]; // /calculate


//        for(int i=0;i<urlPathTokens.length;i++){
//            System.out.println("urlPathTokens 값:"+urlPathTokens[i]);
//        }

        if(urlPathTokens.length == 2){
            this.queryString = urlPathTokens[1]; // operand1=11&operator=*&operand2=55
        }
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) && Objects.equals(urlPath, that.urlPath) && Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, urlPath, queryString);
    }
}
