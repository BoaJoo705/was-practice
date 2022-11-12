package org.example.calculator.domain;

import org.example.calculator.MultiplicationOperator;
import org.example.calculator.NewArithmeticOperator;
import org.example.calculator.SubtractionOperator;
import org.example.calculator.tobe.AdditionOperator;
import org.example.calculator.tobe.DivisionOperator;

import java.util.List;

public class Calculator {
    private  static final List<NewArithmeticOperator> arithmeticOperators = List.of(new AdditionOperator(),new SubtractionOperator(),new MultiplicationOperator(),new DivisionOperator()); // 4개의 구현체를 인터페이스로 받는다.

    public static int calculate(PositiveNumber operand1,String operator, PositiveNumber operand2) {
        // 1번째 소스구현
//        if("+".equals(operator)){
//            return operand1 + operand2;
//        }else if("-".equals(operator)){
//            return operand1 - operand2;
//        }else if("*".equals(operator)){
//            return operand1 * operand2;
//        }else if("/".equals(operator)){
//            return operand1 / operand2;
//        }
        //2번째 리팩토링
//        return ArithmeticOperator.calculate(operand1,operator,operand2) ;
        return arithmeticOperators.stream()
                .filter(arithmeticOperator -> arithmeticOperator.supports(operator)) // operator 에 맞는 구현체를 찾은 후에
                .map(arithmeticOperator -> arithmeticOperator.calculate(operand1,operand2)) // 해당 구현체에 맞는 인자를 보낸다. int 를 받기위해 map 사용
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다."));
    }
}
