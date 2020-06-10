package com.example.clientjavaterm.converters;

import com.example.clientjavaterm.entity.Department;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;

public class DepartmentConverter implements BaseConverter<Department> {
    @Override
    public Class getConverterClass() {
        return Department.class;
    }

    @Override
    public Department deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Long id = object.get("id").getAsLong();
        String name = object.get("name").getAsString();
        return new Department(id, name);
    }

    @Override
    public JsonElement serialize(Department src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("id", src.getId());
        object.addProperty("name", src.getName());
        return object;
    }
}