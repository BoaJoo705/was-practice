package org.example.calculator.tobe;

import org.example.calculator.NewArithmeticOperator;
import org.example.calculator.domain.PositiveNumber;

public class DivisionOperator implements NewArithmeticOperator {

    @Override
    public boolean supports(String operator) {
        return "/".equals(operator);
    }

    @Override
    public int calculate(PositiveNumber operand1, PositiveNumber operand2) {
        // 이미 PositiveNumber에서 이미 0보다 같거나 작은건 거르기 때문에 할 필요가 없다.
//        if(operand2.toInt() == 0){
//            throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
//        }
        return operand1.toInt() / operand2.toInt();
    }
}
