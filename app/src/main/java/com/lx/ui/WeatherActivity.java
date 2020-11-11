package com.lx.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lx.R;
import com.lx.http.HttpManager;
import com.lx.model.Constants;
import com.lx.model.data.WeatherData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;

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

    }

    private void initData(){
        //有效的经纬度
        if(Constants.latitude > 0 && Constants.longitude > 0 && !TextUtils.isEmpty(Constants.address)){
            Map<String,String> map = new HashMap<>();
            map.put("city",Constants.address);
            map.put("location",Constants.latitude+","+Constants.longitude);
            Response<WeatherData> result = HttpManager.getInstance().getTpApi().queryWeather(map);
            if(result != null){

            }
        }
    }

}
