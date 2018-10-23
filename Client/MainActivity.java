package com.hit.ftask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        browser = findViewById(R.id.webby);
        browser.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        browser.loadUrl("file:///android_asset/UI.html");
    }

    @Override
    public void onBackPressed() {
        browser.loadUrl("javascript:mainObj.getMainPage()");
    }
}
