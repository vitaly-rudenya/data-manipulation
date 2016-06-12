package com.dyggyty.manipulation.reader.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonContainer {

    private static final Gson GSON;

    static {
        BooleanGsonTypeAdapter booleanTypeAdapter = new BooleanGsonTypeAdapter();
        DoubleGsonTypeAdapter doubleTypeAdapter = new DoubleGsonTypeAdapter();

        GSON = new GsonBuilder()
                .registerTypeAdapter(Boolean.class, booleanTypeAdapter)
                .registerTypeAdapter(boolean.class, booleanTypeAdapter)
                .registerTypeAdapter(Double.class, doubleTypeAdapter)
                .registerTypeAdapter(double.class, doubleTypeAdapter)
                .create();
    }

    public static Gson getGSON() {
        return GSON;
    }
}
