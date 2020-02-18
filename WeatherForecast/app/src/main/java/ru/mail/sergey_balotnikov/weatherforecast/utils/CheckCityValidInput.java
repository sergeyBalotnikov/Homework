package ru.mail.sergey_balotnikov.weatherforecast.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.mail.sergey_balotnikov.weatherforecast.BuildConfig;

public class CheckCityValidInput {
    private CheckCityValidInput(){}
    public static boolean isCityValid(String city){

        OkHttpClient client = new OkHttpClient();
        final String url = String.format(
                Consts.GET_WEATHER_BY_CITY_NAME,
                city,
                "",
                BuildConfig.API_KEY);
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            String responseCode = new JSONObject(response.body().string()).get("cod").toString();
            if(responseCode.equals("400")||responseCode.equals("404")){
                return false;
            } else {
                return true;            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return false;
    }
}
