package com.example.clientjavaterm.converters;

import com.example.clientjavaterm.entity.Department;
import com.example.clientjavaterm.entity.DepartmentEmployee;
import com.example.clientjavaterm.entity.Employee;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;

public class DepartmentsEmployeesConverter implements BaseConverter<DepartmentEmployee> {
    @Override
    public Class getConverterClass() {
        return DepartmentEmployee.class;
    }

    @Override
    public DepartmentEmployee deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Long id = object.get("id").getAsLong();
        Department department = context.deserialize(object.get("departments"), Department.class);
        Employee employee = context.deserialize(object.get("employees"), Employee.class);

        return new DepartmentEmployee(id,department, employee);
    }

    @Override
    public JsonElement serialize(DepartmentEmployee src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("id", src.getId());
        object.add("departments", context.serialize(src.getDepartment()));
        object.add("employees", context.serialize(src.getEmployee()));
        return object;
    }
}
