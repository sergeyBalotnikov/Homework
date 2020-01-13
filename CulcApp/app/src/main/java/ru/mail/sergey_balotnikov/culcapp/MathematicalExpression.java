package ru.mail.sergey_balotnikov.culcapp;

public class MathematicalExpression {

    private double firstArgument;
    private double secondArgument;
    private String operand;

    public MathematicalExpression() {
        firstArgument=0.0;
        secondArgument=0.0;
        operand="";
    }
    public String getResult(){
        double result = 0;
        switch (operand){
            case "+":
                result=firstArgument+secondArgument;
                break;
            case "-":
                result=firstArgument-secondArgument;
                break;
            case "*":
                result=firstArgument*secondArgument;
                break;
            case "/":
                result=firstArgument==0?0:firstArgument/secondArgument;
                break;
        }
        return String.valueOf(result);
    }

    public double getFirstArgument() {
        return firstArgument;
    }

    public double getSecondArgument() {
        return secondArgument;
    }

    public String getOperand() {
        return operand;
    }

    public void setFirstArgument(double firstArgument) {
        this.firstArgument = firstArgument;
    }

    public void setSecondArgument(double secondArgument) {
        this.secondArgument = secondArgument;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }
}
