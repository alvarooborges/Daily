package br.com.kodeway.daily.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Map;

public final class MapHelper {

    private static final Gson gson = new GsonBuilder().create();

    public static Map<String, Long> fromDatabase(String databaseOutput) {
        Type typeOfHashMap = new TypeToken<Map<String, Long>>() {
        }.getType();

        return gson.fromJson(databaseOutput, typeOfHashMap);
    }

    public static String toDatabase(Map<String, Long> databaseInput) {
        return gson.toJson(databaseInput);
    }

}
