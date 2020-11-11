package com.lx;

import android.app.Application;
import android.content.Intent;

import com.lx.service.MyService;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, MyService.class);
        startService(intent);//启动服务
    }
}
