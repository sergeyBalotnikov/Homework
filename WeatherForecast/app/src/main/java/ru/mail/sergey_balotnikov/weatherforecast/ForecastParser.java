package ru.mail.sergey_balotnikov.weatherforecast;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ForecastParser {
    private static final String LOG_TAG = "SVB";
    private String data;

    public ForecastParser(String data) {
        this.data = data;
    }

    public List<Forecast> getParseWhether() throws JSONException {
        List<Forecast> whetherList = new ArrayList<>();
        Log.d(LOG_TAG, data);
        JSONObject jsonObject = new JSONObject(data);
        Log.d(LOG_TAG, jsonObject.get("cod").toString());
        JSONArray jsonObjectsList = jsonObject.getJSONArray("list");
        Log.d(LOG_TAG, jsonObjectsList.length()+"");
        for (int i = 0; i<jsonObjectsList.length(); i++){
            JSONObject jsonObjectStep = (JSONObject)jsonObjectsList.get(i);
            Log.d(LOG_TAG, jsonObjectsList.get(i).toString());
            double temperature = jsonObjectStep.getJSONObject("main").getDouble("temp");
            Log.d(LOG_TAG, temperature+"");
            String description = jsonObjectStep.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description");
            Log.d(LOG_TAG, description);

            String iconId = jsonObjectStep.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("icon");
            Log.d(LOG_TAG, iconId);
            long time = jsonObjectStep.getLong("dt");
            Log.d(LOG_TAG, time+"");
            whetherList.add(new Forecast(description, temperature,  time, iconId));
        }
        Log.d(LOG_TAG, "whetherList.size()"+whetherList.size());
        return whetherList;
    }

}
