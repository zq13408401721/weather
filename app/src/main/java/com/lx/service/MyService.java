package com.lx.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.location.Geocoder;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.lx.model.Constants;

import java.io.IOException;
import java.util.List;

public class MyService extends Service {

    private static String TAG = MyService.class.getSimpleName();
    MyBinder binder;

    PowerBroadCastReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        Log.d(TAG, "onCreate");
        receiver = new PowerBroadCastReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        registerReceiver();
        //定位
        initLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (binder != null) {
            return binder;
        }
        return null;
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(receiver, filter);
    }

    /**
     * 获取当前定位
     */
    private void initLocation() {
        LocationManager locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null){
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        //通过location获取GeoPoint

        if(location != null){
            Constants.latitude = location.getLatitude()*1E6;
            Constants.longitude = location.getLongitude()*1E6;
            Geocoder geocoder = new Geocoder(getApplicationContext());
            try {
                List<Address> addresses = geocoder.getFromLocation(Constants.latitude,Constants.longitude,1);
                if(addresses != null && addresses.size()>0){
                    Address address = addresses.get(0);
                    Constants.address = address.getCountryName();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    class MyBinder extends Binder{
        public void sendMsg(){
            Log.d(TAG,"sendMsg");
        }
    }
}
