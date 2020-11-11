package com.lx.http;

import android.util.Log;


import com.lx.model.MyApi;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * http协议
 * 负责网络请求
 */
public class HttpManager {

    private static HttpManager instance;

    public static HttpManager getInstance(){
        if(instance == null){
            synchronized (HttpManager.class){
                if(instance == null){
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }
    /**
     * 网络请求接口
     */
    MyApi myApi;


    private static Retrofit getRetrofit(String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        return client;
    }

    /**
     * 日志的拦截器打印报文信息
     */
    static class LoggingInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.i("interceptor", String.format("Sending request %s on %s%n%s",request.url(),chain.connection(),request.headers()));

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.i("Received:", String.format("Received response for %s in %.1fms%n%s",response.request().url(),(t2-t1)/1e6d,response.headers()));
            if(response.header("session_id") != null){
                //Constant.session_id = response.header("session_id");
            }
            return response;
        }
    }

    /**
     * 添加头拦截器
     */
    static class HeaderInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            //获取请求对象
            Request.Builder builder = chain.request().newBuilder();
            //阿里云市场提供的全国免费天气预报接口
            builder.addHeader("Authorization","APPCODE 964e16aa1ae944e9828e87b8b9fbd30a");
            Request request = builder.build();
            Response response = chain.proceed(request);
            return response;
        }
    }



    /**
     * 获取网络接口请求的接口类
     * @return
     */
    public MyApi getTpApi(){
        if(myApi == null){
            myApi = getRetrofit(MyApi.BASE_URL).create(MyApi.class);
        }
        return myApi;
    }

}
