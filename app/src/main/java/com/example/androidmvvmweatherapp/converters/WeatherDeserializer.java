package com.example.androidmvvmweatherapp.converters;

import com.example.androidmvvmweatherapp.model.Weather;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class WeatherDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT,
                              JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray weatherJsonArray = jsonObject.get("weather").getAsJsonArray();
        JsonObject weatherJsonObject = weatherJsonArray.get(0).getAsJsonObject();

        Weather weather = new Weather();

        weather.setDescription(weatherJsonObject.get("description").getAsString());

        String icon = weatherJsonObject.get("icon").getAsString();
        weather.setImageUrl(String.format("https://openweathermap.org/img/w/%s.png", icon));

        JsonObject mainObject = jsonObject.get("main").getAsJsonObject();
        weather.setTemperature(mainObject.get("temp").getAsInt());

        return weather;
    }
}