package com.lx.service;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lx.ui.WeatherActivity;

public class PowerBroadCastReceiver extends BroadcastReceiver {
    private static String TAG = PowerBroadCastReceiver.class.getSimpleName();

    String SCREEN_ON = "android.intent.action.SCREEN_ON";  //屏幕亮
    String SCREEN_OFF = "android.intent.action.SCREEN_OFF"; //屏幕关闭

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d(TAG,Thread.currentThread().getName());
        if(SCREEN_ON.equals(intent.getAction())){
            Log.d(TAG,"屏幕亮了");
        }else if(SCREEN_OFF.equals(intent.getAction())){
            Log.d(TAG,"屏幕关闭了");
            /*new Thread(new Runnable() {
                @Override
                public void run() {*/
                    try {
                        Thread.sleep(2000);
                        //管理锁屏的一个服务
                        KeyguardManager manager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                        String str = manager.inKeyguardRestrictedInputMode() ? "锁屏" : "屏幕亮着";
                        Log.i(TAG,str);
                        if(manager.inKeyguardRestrictedInputMode()){
                            //锁屏了
                            Intent intent1 = new Intent(context, WeatherActivity.class);
                            //在广播中启动activity的context，可能不是activity对象 使用new_task
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(intent1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

               // }
            //}).start();
        }else if(Intent.ACTION_USER_PRESENT.equals(intent.getAction())){
            Log.d(TAG,"解锁成功");
        }
    }

    private void sendMsg(){

    }
}
