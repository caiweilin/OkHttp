package com.example.networktest;

import android.app.DownloadManager;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView=(WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.baidu.com");
        Button sendRequest=(Button)findViewById(R.id.send_request);
        responseText=(TextView)findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            sendRequestWithOkHttp();
        }
    }
    private void sendRequestWithOkHttp(){
            //开启线程来发起网络请求
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        OkHttpClient client=new OkHttpClient();
                        Request request=new Request.Builder()
                                .url("https://www.baidu.com")
                                .build();
                        Response response=client.newCall(request).execute();
                        String responseData=response.body().string();
                        showResponse(responseData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();

    }

        private void showResponse(final String response) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //在这里进行UI操作，将结果显示到界面上
                    responseText.setText(response);

                }
            });
        }
}
