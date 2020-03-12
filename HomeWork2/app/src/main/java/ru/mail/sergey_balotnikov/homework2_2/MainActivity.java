package ru.mail.sergey_balotnikov.homework2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.mail.sergey_balotnikov.homework2_2.task1.ContactTask;
import ru.mail.sergey_balotnikov.homework2_2.task2.CustomViewTask;
import ru.mail.sergey_balotnikov.homework2_2.task3.MyWebView;
public class MainActivity extends AppCompatActivity {

    private Button run_task_1;
    private Button run_task_2;
    private Button run_task_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        run_task_1 = findViewById(R.id.btn_1task);
        run_task_2 = findViewById(R.id.btn_2task);
        run_task_3 = findViewById(R.id.btn_3task);

        run_task_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ContactTask.class));
            }
        });
        run_task_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, CustomViewTask.class));
            }
        });
        run_task_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, MyWebView.class));
            }
        });


    }
}
