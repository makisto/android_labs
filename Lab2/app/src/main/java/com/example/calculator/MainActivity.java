package com.example.calculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text;
    TextView text2;
    TextView text3;
    Double operand = null;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.textView);
        text2 = (TextView)findViewById(R.id.textView2);
        text3 = (TextView)findViewById(R.id.textView3);
    }

    public void onNumberClick(View view)
    {
        Log.v("Hello", "Group");
        if(text.getText().toString().equals("Ошибка"))
        {
            text.setText("");
        }
        switch(view.getId())
        {
            case R.id.button1:
                text.append("1");
                break;
            case R.id.button2:
                text.append("2");
                break;
            case R.id.button3:
                text.append("3");
                break;
            case R.id.button4:
                text.append("4");
                break;
            case R.id.button5:
                text.append("5");
                break;
            case R.id.button6:
                text.append("6");
                break;
            case R.id.button7:
                text.append("7");
                break;
            case R.id.button8:
                text.append("8");
                break;
            case R.id.button9:
                text.append("9");
                break;
            case R.id.button0:
                text.append("0");
                break;
            case R.id.buttonPoint:
                if(flag == 0) {
                    text.append(".");
                    flag = 1;
                }
                break;
        }
    }
    public void onOperButton(View view)
    {
        if(text.getText().toString().equals("Ошибка"))
        {
            text.setText("");
        }
        Button button = (Button)view;
        String op = button.getText().toString();
        String number = text.getText().toString();
        if(number.length() > 0)
        {
                Double num;
                if(text.getText().toString().equals("."))
                {
                    num = 0.0;
                }
                else
                {
                    num = Double.parseDouble(text.getText().toString());
                }
                if(operand == null)
                {
                    operand = num;
                    text3.setText(num.toString());
                    text.setText("");
                    flag = 0;
                }
                else if(op.equals("=")){
                    switch(text2.getText().toString()){
                        case "/":
                            /*try
                            {
                                operand /= num;
                                text3.setText(operand.toString());
                                text.setText("");
                                flag = 0;
                            }
                            catch() {
                                text.setText("Ошибка");
                                text3.setText("");
                                flag = 0;
                            }*/
                            if(num == 0)
                            {
                                text.setText("Ошибка");
                                text3.setText("");
                                //flag = 0;
                            }
                            else {
                                operand /= num;
                                text3.setText(operand.toString());
                                text.setText("");
                                //flag = 0;
                            }
                            break;
                        case "*":
                            operand *= num;
                            text3.setText(operand.toString());
                            text.setText("");
                            //flag = 0;
                            break;
                        case "+":
                            operand += num;
                            text3.setText(operand.toString());
                            text.setText("");
                            //flag = 0;
                            break;
                        case "-":
                            operand -= num;
                            text3.setText(operand.toString());
                            text.setText("");
                            //flag = 0;
                            break;
                        /*case "=":
                            operand = num;
                            text3.setText(operand.toString());
                            text.setText("");
                            flag = 0;
                            break;*/

                    }
                    operand = null;
                    flag = 0;
                }
                text2.setText(op);
        }
    }
    public void onClearButton(View view)
    {
        text.setText("");
        text2.setText("");
        text3.setText("");
        operand = null;
        flag = 0;
    }

}



