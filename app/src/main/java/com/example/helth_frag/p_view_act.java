package com.example.helth_frag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class p_view_act extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pview);
        getSupportActionBar().hide();

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        String titlepdf =getIntent().getStringExtra("titlename");
        String urlPDF =getIntent().getStringExtra("pdf");

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(titlepdf);
        pd.setMessage("Openning...!");

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url ="";
        try {
            url = URLEncoder.encode(urlPDF,"UTF-8");
        }catch (Exception ex){}

        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);




    }
}