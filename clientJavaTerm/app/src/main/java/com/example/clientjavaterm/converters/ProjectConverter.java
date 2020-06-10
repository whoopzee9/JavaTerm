package com.example.clientjavaterm.converters;

import com.example.clientjavaterm.entity.Department;
import com.example.clientjavaterm.entity.Project;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProjectConverter implements BaseConverter<Project> {
    @Override
    public Class getConverterClass() {
        return Project.class;
    }

    @Override
    public Project deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Long id = object.get("id").getAsLong();
        String name = object.get("name").getAsString();
        Float cost = object.get("cost").getAsFloat();
        Department department = context.deserialize(object.get("departments"), Department.class);
        Date beg = null;
        Date end = null;
        Date endReal = null;
        try {
            java.util.Date utilDate = new SimpleDateFormat( "yyyy-MM-dd" ).parse(object.get("dateBeg").getAsString());
            beg = new Date(utilDate.getTime());
            utilDate = new SimpleDateFormat( "yyyy-MM-dd" ).parse(object.get("dateEnd").getAsString());
            end = new Date(utilDate.getTime());
            utilDate = new SimpleDateFormat( "yyyy-MM-dd" ).parse(object.get("dateEnd").getAsString());
            endReal = new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Project(id, name, cost, department, beg, end, endReal);
    }

    @Override
    public JsonElement serialize(Project src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("id", src.getId());
        object.addProperty("name", src.getName());
        object.addProperty("cost", src.getCost());
        object.add("departments", context.serialize(src.getDepartment()));
        object.addProperty("dateBeg", src.getDateBeg().getTime());
        object.addProperty("dateEnd", src.getDateEnd().getTime());
        object.addProperty("dateEndReal", src.getDateEndReal().getTime());
        return object;
    }
}