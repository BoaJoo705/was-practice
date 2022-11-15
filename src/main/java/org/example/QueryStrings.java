package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryStrings {
    private List<QueryString> queryStrings = new ArrayList<>();

    //   operand1=11   operator=*     operand2=55
    public QueryStrings(String queryStringLine){
        String[] queryStringTokens = queryStringLine.split("&");
        Arrays.stream(queryStringTokens)
                .forEach(queryString ->{
            String[] values = queryString.split("=");
            if(values.length != 2){
                throw new IllegalArgumentException("잘못된 QueryString 포맷을 가진 문자열입니다.");
            }
            queryStrings.add(new QueryString(values[0],values[1]));
        });
    }

    public String getValue(String key) {// key가 존재한다면 value 를 주세요
        return this.queryStrings.stream()
                .filter(queryString -> queryString.exists(key)) //key가 존재하니?
                .map(QueryString::getValue)
                .findFirst()
                .orElse(null);
    }
}
