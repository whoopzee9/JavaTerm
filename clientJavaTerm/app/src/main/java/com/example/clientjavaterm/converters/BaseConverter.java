package com.example.clientjavaterm.converters;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface BaseConverter<T> extends JsonSerializer<T>, JsonDeserializer<T> {
    Class getConverterClass();
}
