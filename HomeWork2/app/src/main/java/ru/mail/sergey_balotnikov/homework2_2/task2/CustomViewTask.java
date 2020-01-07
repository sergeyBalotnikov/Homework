package ru.mail.sergey_balotnikov.homework2_2.task2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.mail.sergey_balotnikov.homework2_2.R;

public class CustomViewTask extends AppCompatActivity{

    private CustomView myCustomView;
    private Switch useToastOrSnack;
    public static final String FILE_NAME = "LogFile";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.task_2_title);

        myCustomView = findViewById(R.id.cv_myCustomView);
        useToastOrSnack = findViewById(R.id.sw_toast_or_snack);
        useToastOrSnack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                useToastOrSnack.setText(b?getResources().getText(R.string.use_snack_bar)
                        :getResources().getText(R.string.use_toast));
            }
        });
        myCustomView.setCallBack(new CustomCallBack() {
            @Override
            public void makeToast(int[] XYColor) {
                if(!useToastOrSnack.isChecked()){
                Toast.makeText(CustomViewTask.this, "Coordinates:[x:"+XYColor[0]+", y:"+XYColor[1]+"]", Toast.LENGTH_SHORT).show();
                System.out.println("jlsjnglwjrbgwhf;go");
            } else {
                    View view = findViewById(R.id.custom_view_layout);
                    Snackbar snackbar = Snackbar.make(view, "Coordinates:[x:"+XYColor[0]+", y:"+XYColor[1]+"]"
                            , Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    TextView textView = snackbarView.findViewById(R.id.snackbar_text);
                    textView.setTextColor(XYColor[2]);
                    snackbar.show();
                }
            }
            @Override
            public void writeFile(int [] XYColor, String color){
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                            openFileOutput(FILE_NAME, MODE_APPEND)));
                    Locale locale = new Locale.Builder().setLanguage("ru").setScript("Cyrl").build();
                    String day = new SimpleDateFormat("dd.MM.yyyy", locale)
                            .format(Calendar.getInstance().getTime())+"\n"
                            +new SimpleDateFormat("hh:mm:ss", locale)
                            .format(Calendar.getInstance().getTime())+"\n";
                    String coordsTab = "Coordinates:[x:"+XYColor[0]+", y:"+XYColor[1]+"]"+"color:"+color+"\n\n";
                    bufferedWriter.write(day+coordsTab);
                    bufferedWriter.close();
                    Log.d("=====!=====", "Файл записан");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
