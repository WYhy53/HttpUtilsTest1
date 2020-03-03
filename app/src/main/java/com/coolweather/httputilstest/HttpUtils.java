package com.coolweather.httputilstest;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.net.URL;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpUtils  {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static final BlockingDeque<Runnable> sPoolWorkQueue =
            new LinkedBlockingDeque<>(128);

    private static HttpUtils httpUtils = new HttpUtils();

    private HttpUtils() {

    }

    public static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }
    public void get(URL url){
        AsyncTask1 asyncTask1=new AsyncTask1();
        asyncTask1.dosomething(url);
        asyncTask1.customThreadPool();
    }

//一个封装了的内部类
    static class AsyncTask1 implements Runnable{
        public void run(){

        }

        //handler
    final Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Log.d("this","请求成功！更新UI");
                    //具体UI操作
                    break;
            }
        }
    };
//具体网络请求
    protected void dosomething (final URL url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        InputStreamReader streamReader = new InputStreamReader(inputStream);
                        BufferedReader reader = new BufferedReader(streamReader);
                        StringBuilder builder = new StringBuilder();
                        String s = "";
                        while ((s = reader.readLine()) != null) {
                            builder.append(s);
                        }
                        Message msg = Message.obtain();
                        msg.what = 1;
                        handler.sendMessage(msg);
                        streamReader.close();
                        reader.close();
                        //  return builder.toString();
                        Log.d("thiswwwwwwwwwwwwwwwwww", builder.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //线程池
        class MyThreadPool extends ThreadPoolExecutor {
            public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                TimeUnit unit, BlockingDeque<Runnable> workQueue) {
                super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
            }

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
                Log.d("google_lenve_fb", "beforeExecute: 开始执行任务！");
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                Log.d("google_lenve_fb", "afterExecute: 任务执行结束！");
            }

            @Override
            protected void terminated() {
                super.terminated();
                Log.d("google_lenve_fb", "terminated: 线程池关闭！");
            }
        }
    final public void customThreadPool() {
        MyThreadPool myThreadPool = new MyThreadPool(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
                TimeUnit.SECONDS, sPoolWorkQueue);
        AsyncTask1 task1=new AsyncTask1();
        new Thread(task1).start();
        myThreadPool.execute(task1);
    }
    }
}
