package ru.mail.sergey_balotnikov.culcapp;

public class CalculatorClass {

    private MathematicalExpression mathematicalExpression;

    public MathematicalExpression getMathematicalExpression() {
        return mathematicalExpression;
    }

    public CalculatorClass(){
        mathematicalExpression=new MathematicalExpression();
    }
    public void dropResult(){
        mathematicalExpression.setOperand("");
        mathematicalExpression.setFirstArgument(0);
        mathematicalExpression.setSecondArgument(0);
    }

}
