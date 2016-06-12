package com.dyggyty.manipulation.reader.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Format Double In/Out JSON values
 */
public class DoubleGsonTypeAdapter extends TypeAdapter<Double> {

    @Override
    public void write(JsonWriter out, Double value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            if (value % 1 > 0) {
                out.value(value);
            } else {
                out.value(value.intValue());
            }
        }
    }

    @Override
    public Double read(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        switch (peek) {
            case NUMBER:
                return in.nextDouble();
            case NULL:
                in.nextNull();
                return null;
            case STRING:
                return Double.parseDouble(in.nextString());
            default:
                throw new IllegalStateException("Expected Double or NUMBER but was " + peek);
        }
    }

}
