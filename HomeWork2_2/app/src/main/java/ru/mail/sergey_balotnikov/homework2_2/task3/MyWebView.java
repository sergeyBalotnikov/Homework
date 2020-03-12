package ru.mail.sergey_balotnikov.homework2_2.task3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.mail.sergey_balotnikov.homework2_2.R;

public class MyWebView extends AppCompatActivity{

    private ImageButton back;
    private TextView uri;
    private Button previous;
    private Button openInBrowser;
    private WebView webPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        getSupportActionBar().hide();

        back=findViewById(R.id.ib_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        finish();
                    }
                });

        previous = findViewById(R.id.btn_previos);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webPage.canGoBack()){
                    webPage.goBack();
                } else {
                    Toast.makeText(MyWebView.this, "Can't go back. This is start page", Toast.LENGTH_SHORT).show();
                }
            }
        });

        openInBrowser = findViewById(R.id.btn_open_in_browser);
        openInBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String page = webPage.getUrl();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(page)));
            }
        });

        uri = findViewById(R.id.tv_page);
        webPage = findViewById(R.id.wv_web);
        webPage.getSettings().setJavaScriptEnabled(true);
        webPage.loadUrl("https://google.com");
        MyWebViewClient client = new MyWebViewClient();
        client.setUriChanged(new UriChanged() {
            @Override
            public void setChangedAddress(String s) {
                String [] title = s.split("/");
                uri.setText(title[2]);
            }
        });
        webPage.setWebViewClient(client);

    }
}



