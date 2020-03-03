package com.coolweather.httputilstest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpUtils httpUtils = HttpUtils.getHttpUtils();
        try {
            URL url1=new URL("https://www.baidu.com");
            URL url2=new URL("https://www.csdn.net/");
            URL url3=new URL("https://www.jianshu.com/");
            URL url4=new URL("https://www.zhihu.com/");
            httpUtils.get(url1);
            httpUtils.get(url2);
            httpUtils.get(url3);
            httpUtils.get(url4);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

