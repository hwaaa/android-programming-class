package hwa.helloworld.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edtText;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button plusBtn, minusBtn, multipleBtn, divideBtn, resultBtn, clearBtn;

    static int plus(int num1, int num2) {
        return num1 + num2;
    }

    static int minus(int num1, int num2) {
        return num1 - num2;
    }

    static int multiple(int num1, int num2) {
        return num1 * num2;
    }

    static int divide(int num1, int num2) {
        return num1 / num2;
    }

    public void number(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                edtText.append("0");
            case R.id.btn1:
                edtText.append("1");
            case R.id.btn2:
                edtText.append("2");
            case R.id.btn3:
                edtText.append("3");
            case R.id.btn4:
                edtText.append("4");
            case R.id.btn5:
                edtText.append("5");
            case R.id.btn6:
                edtText.append("6");
            case R.id.btn7:
                edtText.append("7");
            case R.id.btn8:
                edtText.append("8");
            case R.id.btn9:
                edtText.append("9");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = (Button)findViewById(R.id.btn0);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);

        plusBtn = (Button)findViewById(R.id.plusBtn);
        minusBtn = (Button)findViewById(R.id.minusBtn);
        multipleBtn = (Button)findViewById(R.id.multipleBtn);
        divideBtn = (Button)findViewById(R.id.divideBtn);
        resultBtn = (Button)findViewById(R.id.resultBtn);
        clearBtn = (Button)findViewById(R.id.clearBtn);


        int num1[] = {0, };
        int num2[] = {0, };

        String ch;
        final int check = 0;

        plusBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String ch;
                ch = plusBtn.getText().toString();
                int check = 1;
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String ch;
                ch = minusBtn.getText().toString();
                int check = 2;
            }
        });

        multipleBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String ch;
                ch = multipleBtn.getText().toString();
                int check = 3;
            }
        });

        divideBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String ch;
                ch = divideBtn.getText().toString();
                int check = 4;
            }
        });

        resultBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (check) {
                    case 1:
                        //plus(num, num2);
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                edtText.setText("");
            }
        });
    }
}

// NumberListener numberListener = 숫자 저장하는 리스너
// operatorListener 연산자 저장하는 리스너
// 익명클래스 -> 변수 이슈

// string num1 = "", num2 = "", operator = "";