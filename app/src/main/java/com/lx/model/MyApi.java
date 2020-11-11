package com.lx.model;

import com.lx.model.data.CityData;
import com.lx.model.data.WeatherData;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MyApi {

    String BASE_URL = "https://jisutqybmf.market.alicloudapi.com/";

    /**
     * 获取添加的城市数据
     * @return
     */
    @GET("weather/city")
    Response<CityData> queryCity();

    /**
     * 获取城市对应的天气数据
     * @param map<city 城市名,citycode 城市的code,cityid 城市id,ip IP,location 经纬度39.001,116.456 > city cityid citycode三选一
     * @return
     */
    @GET("weather/query")
    Response<WeatherData> queryWeather(@QueryMap Map<String,String> map);


}
