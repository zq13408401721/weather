package com.lx.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lx.R;

/**
 * 获取天气数据关闭屏幕的时候显示界面
 */
public class WeatherActivity extends AppCompatActivity {
    private TextView txtMsg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED //锁屏状态下显示
            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD  //解锁
            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON //保持屏幕常亮
            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON  //打开屏幕
        );
        setContentView(R.layout.activity_message);

        initView();
        initData();
    }

    private void initView() {
        txtMsg = findViewById(R.id.txt_msg);
        txtMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, WeatherDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData(){

    }



}
