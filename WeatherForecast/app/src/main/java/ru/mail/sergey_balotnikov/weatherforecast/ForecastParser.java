package ru.mail.sergey_balotnikov.weatherforecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ForecastParser {

    private String data;

    public ForecastParser(String data) {
        this.data = data;
    }

    public List<Forecast> getParseWhether() throws JSONException {
        List<Forecast> whetherList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonObjectsList = jsonObject.getJSONArray("list");
        for (int i = 0; i<jsonObjectsList.length(); i++){
            JSONObject jsonObjectStep = (JSONObject)jsonObjectsList.get(i);
            double temperature = jsonObjectStep.getJSONObject("main").getDouble("temp");
            String description = jsonObjectStep.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description");
            String iconId = jsonObjectStep.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("icon");
            long time = jsonObjectStep.getLong("dt");
            whetherList.add(new Forecast(description, temperature,  time, iconId));
        }
        return whetherList;
    }

}
