package ru.mail.sergey_balotnikov.homework1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.textclassifier.TextClassifierEvent;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

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
        layout = findViewById(R.id.activity_main);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layout.setBackgroundResource(R.drawable.flag);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            layout.setBackgroundResource(R.drawable.flag);
        } else if (screenSize!=0){
            layout.setBackgroundResource(R.color.colorWhite);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration configuration){
        super.onConfigurationChanged(configuration);
        orientation = configuration.orientation;
        if(configuration.screenHeightDp==640){
            screenSize = configuration.screenHeightDp;
        }
        setBackgroundImage(configuration.orientation);
    }
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.setContentView(R.layout.activity_main);
    }

}
