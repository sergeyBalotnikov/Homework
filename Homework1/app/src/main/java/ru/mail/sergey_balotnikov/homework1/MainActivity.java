package ru.mail.sergey_balotnikov.homework1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private int orientation;
    private int screenSize=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setBackgroundImage(orientation);
    }

    private void setBackgroundImage(final int orientation)
    {
        LinearLayout layout;
        layout = findViewById(R.id.l_flag);

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layout.setBackgroundResource(R.drawable.flag_landscape);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            layout.setBackgroundResource(R.drawable.flag_portrait);
        } else if (screenSize!=0){
            layout.setBackgroundResource(R.color.colorWhite);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration configuration){
        super.onConfigurationChanged(configuration);

        orientation = configuration.orientation;
        setBackgroundImage(configuration.orientation);
        if(configuration.screenHeightDp==480){
            screenSize = configuration.screenHeightDp;
        } else if(configuration.screenWidthDp==480){
            screenSize = configuration.screenWidthDp;
        }
        Intent intent = getIntent();
    }

}
