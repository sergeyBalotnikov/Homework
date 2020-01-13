package ru.mail.sergey_balotnikov.culcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView firstArgument;
    private TextView secondArgument;
    private TextView operand;
    private CalculatorClass calculator;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnMultiply,
    btnSplit, btnPlus, btnMinus, btnEquals, btnDot;
    private LinearLayout expression;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result="";
        calculator = new CalculatorClass();
        btn0 = findViewById(R.id.btn_0);
        btn0.setOnClickListener(this);
        btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btn_3);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.btn_4);
        btn4.setOnClickListener(this);
        btn5 = findViewById(R.id.btn_5);
        btn5.setOnClickListener(this);
        btn6 = findViewById(R.id.btn_6);
        btn6.setOnClickListener(this);
        btn7 = findViewById(R.id.btn_7);
        btn7.setOnClickListener(this);
        btn8 = findViewById(R.id.btn_8);
        btn8.setOnClickListener(this);
        btn9 = findViewById(R.id.btn_9);
        btn9.setOnClickListener(this);
        btnMinus = findViewById(R.id.btn_minus);
        btnMinus.setOnClickListener(this);
        btnPlus = findViewById(R.id.btn_plus);
        btnPlus.setOnClickListener(this);
        btnMultiply = findViewById(R.id.btn_multiply);
        btnMultiply.setOnClickListener(this);
        btnSplit = findViewById(R.id.btn_split);
        btnSplit.setOnClickListener(this);
        btnEquals = findViewById(R.id.btn_equals);
        btnEquals.setOnClickListener(this);
        btnDot = findViewById(R.id.btn_dot);
        btnDot.setOnClickListener(this);
        firstArgument = findViewById(R.id.tv_first_argument);
        secondArgument = findViewById(R.id.tv_second_argument);
        operand = findViewById(R.id.tv_operand);
        expression=findViewById(R.id.layoutExpression);
        expression.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation));
        switch (view.getId()){
            case R.id.layoutExpression:
                firstArgument.setText("");
                secondArgument.setText("");
                operand.setText("");
                calculator.dropResult();
                break;
            case R.id.btn_minus:
                if(firstArgument.getText().toString().equals("")
                        ||firstArgument.getText().toString().equals("-")){
                        this.firstArgument.setText("-");
                } else {
                    operandButtonPress("-");
                }
                break;
            case R.id.btn_plus:
                operandButtonPress("+");
                break;
            case R.id.btn_split:
                operandButtonPress("/");
                break;
            case R.id.btn_multiply:
                operandButtonPress("*");
                break;

            case R.id.btn_equals:
                operandButtonPress("=");
                break;

            case R.id.btn_0:
                numberButtonPress("0");
                break;
            case R.id.btn_1:
                numberButtonPress("1");
                break;
            case R.id.btn_2:
                numberButtonPress("2");
                break;
            case R.id.btn_3:
                numberButtonPress("3");
                break;
            case R.id.btn_4:
                numberButtonPress("4");
                break;
            case R.id.btn_5:
                numberButtonPress("5");
                break;
            case R.id.btn_6:
                numberButtonPress("6");
                break;
            case R.id.btn_7:
                numberButtonPress("7");
                break;
            case R.id.btn_8:
                numberButtonPress("8");
                break;
            case R.id.btn_9:
                numberButtonPress("9");
                break;
            case R.id.btn_dot:
                numberButtonPress(".");
                break;
        }
    }
    public void numberButtonPress(String s){

        if(this.operand.getText().toString().equals("")){
            if(s.equals(".")){
                if(firstArgument.getText().toString().contains(".")){
                    return;
                }
                if(firstArgument.getText().toString().equals("")){
                    firstArgument.setText("0.");
                    return;
                }
                if(firstArgument.getText().toString().equals("-")){
                    firstArgument.setText("-0.");
                    return;
                }

            }
            if(firstArgument.getText().toString().equals(result)){
                firstArgument.setText(s);
                return;
            }
            firstArgument.append(s);
            calculator.getMathematicalExpression().setFirstArgument(
                    Double.parseDouble(firstArgument.getText().toString()));

        } else {
            if(s.equals(".")){
                if(secondArgument.getText().toString().contains(".")){
                    return;
                }
                if(secondArgument.getText().toString().equals("")){
                    secondArgument.setText("0.");
                    return;
                }
                if(secondArgument.getText().toString().equals("-")){
                    secondArgument.setText("-0.");
                    return;
                }

            }
            secondArgument.append(s);
            calculator.getMathematicalExpression().setSecondArgument(
                    Double.parseDouble(secondArgument.getText().toString()));
        }
    }
    public void operandButtonPress(String s){
        String operand = calculator.getMathematicalExpression().getOperand();
        if(!operand.equals("")) {
            if (calculator.getMathematicalExpression().getSecondArgument() == 0
                    &&!s.equals("=")) {
                calculator.getMathematicalExpression().setOperand(s);
                this.operand.setText(s);
            } else if (!s.equals("=")) {
                this.firstArgument.setText(calculator.getMathematicalExpression().getResult());
                this.operand.setText(s);
                this.secondArgument.setText("");
                calculator.dropResult();
                calculator.getMathematicalExpression().setFirstArgument(
                        Double.parseDouble(this.firstArgument.getText().toString()));
                calculator.getMathematicalExpression().setOperand(s);
            } else {
                this.firstArgument.setText(calculator.getMathematicalExpression().getResult());
                result = firstArgument.getText().toString();
                System.out.println("result is " + result);
                this.operand.setText("");
                this.secondArgument.setText("");
                calculator.dropResult();
            }
        }else {
                calculator.getMathematicalExpression().setOperand(s);
                this.operand.setText(s);
            }


    }
}
