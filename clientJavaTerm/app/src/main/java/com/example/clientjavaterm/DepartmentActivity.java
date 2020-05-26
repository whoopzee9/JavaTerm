package com.example.clientjavaterm;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientjavaterm.converters.DepartmentConverter;
import com.example.clientjavaterm.entity.Departments;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DepartmentActivity extends AppCompatActivity {

    private EditText ETId;
    private EditText ETName;
    private Button BPrev;
    private Button BNext;
    private TextView TVPage;
    private Button BNew;
    private Button BUpdate;
    private Button BAdd;
    private Button BDelete;
    private Button BFind;
    private EditText ETFindNameOrId;
    private Spinner spinner;
    private List<Departments> array;
    private String token;
    private String urlString;
    private int spinnerItem;
    private int currentRecord;
    private int arrayLength;
    private RequestHandler handler;
    private DepartmentConverter converter;
    private Gson departmentGson;
    private ArrayList<String> rolesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            System.out.println("null!");
            token = arguments.getString("token");
            urlString = arguments.getString("url");
            rolesList = arguments.getStringArrayList("roles");
        }

        converter = new DepartmentConverter();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(converter.getConverterClass(), converter);
        departmentGson = builder.create();

        handler = new RequestHandler(urlString);
        handler.setToken(token);

        setContentView(R.layout.activity_department);

        ETId = findViewById(R.id.ETDEId);
        ETName = findViewById(R.id.ETDepartmentName);
        BPrev = findViewById(R.id.BDEPrev);
        BNext = findViewById(R.id.BDENext);
        TVPage = findViewById(R.id.TVDEPages);
        BNew = findViewById(R.id.BDENew);
        BUpdate = findViewById(R.id.BDEUpdate);
        BAdd = findViewById(R.id.BDEAdd);
        BDelete = findViewById(R.id.BDEDelete);
        BFind = findViewById(R.id.BDEFind);
        ETFindNameOrId = findViewById(R.id.ETDEFindLastNameOrId);
        spinner = findViewById(R.id.SpDESpinner);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.DepartmentFind, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ETId.setFocusable(false);
        ETId.setLongClickable(false);
        ETId.setCursorVisible(false);

        if (!rolesList.contains("ROLE_ADMIN")) {
            BUpdate.setVisibility(View.GONE);
            BAdd.setVisibility(View.GONE);
            BDelete.setVisibility(View.GONE);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                switch (selectedItemPosition) {
                    case 0: {
                        ETFindNameOrId.setVisibility(View.GONE);
                        spinnerItem = 0;
                        break;
                    }
                    case 1: {
                        ETFindNameOrId.setVisibility(View.GONE);
                        spinnerItem = 1;
                        break;
                    }
                    case 2: {
                        ETFindNameOrId.setVisibility(View.VISIBLE);
                        ETFindNameOrId.setHint("id");
                        spinnerItem = 2;
                        break;
                    }
                    case 3: {
                        ETFindNameOrId.setVisibility(View.VISIBLE);
                        ETFindNameOrId.setHint("name");
                        spinnerItem = 3;
                        break;
                    }
                }
                ETFindNameOrId.setText("");
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        BFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BFindClickListener();
            }
        });

        BAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BAddClickListener();
            }
        });

        BDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDeleteClickListener();
            }
        });

        BUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUpdateClickListener();
            }
        });

        BPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRecord > 0) {
                    currentRecord--;
                    setFieldsWithCurrentDepartment(currentRecord);
                }
            }
        });

        BNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRecord < arrayLength - 1) {
                    currentRecord++;
                    setFieldsWithCurrentDepartment(currentRecord);
                }
            }
        });

        BNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void BFindClickListener() {
        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<List<Departments>>(){}.getType();
                List<Departments> list = new ArrayList<>();
                try {
                    list = departmentGson.fromJson(result, type);
                } catch (JsonIOException | JsonSyntaxException ex) {
                    Departments empl = departmentGson.fromJson(result, Departments.class);
                    list.add(empl);
                }
                if (list.isEmpty()) {
                    createToast("Nothing found!");
                } else {
                    array = list;
                    currentRecord = 0;
                    arrayLength = array.size();
                    setFieldsWithCurrentDepartment(0);
                }
            }

            @Override
            public void onFail(String message) {
                createToast("No such Departments!");
            }
        };

        String url = "";
        String text = ETFindNameOrId.getText().toString();
        switch (spinnerItem) {
            case 1: {
                url = "departments/all";
                break;
            }
            case 2: {
                url = "departments/getById/" + text;
                break;
            }
            case 3: {
                url = "departments/getByName/" + text;
                break;
            }
        }
        if (spinnerItem != 0) {
            handler.setUrlResource(url);
            handler.setHttpMethod("GET");
            handler.execute(callBack, null);
        }
    }

    private void BDeleteClickListener() {
        String id = ETId.getText().toString();

        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                arrayLength--;
                array.remove(currentRecord);
                if (arrayLength != 0) {
                    if (currentRecord < arrayLength) {
                        setFieldsWithCurrentDepartment(currentRecord);
                    } else {
                        currentRecord--;
                        setFieldsWithCurrentDepartment(currentRecord);
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            clearFields();
                            TVPage.setText("0/0");
                        }
                    });
                }
            }

            @Override
            public void onFail(String message) {
                createToast("No such Departments!");
            }
        };

        if (id.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Enter Id",  Toast.LENGTH_LONG);
            toast.show();
        } else {
            String url = "departments/deleteById/" + id;
            handler.setUrlResource(url);
            handler.setHttpMethod("DELETE");
            handler.execute(callBack, null);
        }
    }

    private void BAddClickListener() {
        String name = ETName.getText().toString();

        //TODO добавить проверку на существование

        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                final Departments dep = departmentGson.fromJson(result, Departments.class);
                runOnUiThread(new Runnable() {
                    public void run() {
                        ETId.setText(dep.getId().toString());
                    }
                });
                createToast("Adding completed successfully!");
            }

            @Override
            public void onFail(String message) {
                createToast("Adding failed!");
            }
        };

        if (name.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Not enough information!",  Toast.LENGTH_LONG);
            toast.show();
        } else {
            Departments dep = new Departments(null, name);
            String json = departmentGson.toJson(dep);
            String url = "departments/add";
            handler.setUrlResource(url);
            handler.setHttpMethod("POST");
            handler.execute(callBack, json);
        }
    }

    private void BUpdateClickListener() {
        String id = ETId.getText().toString();
        String name = ETName.getText().toString();

        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                Departments empl = departmentGson.fromJson(result, Departments.class);
                Departments curr = array.get(currentRecord);
                curr.setId(empl.getId());
                curr.setName(empl.getName());
                createToast("Update completed successfully!");
            }

            @Override
            public void onFail(String message) {
                createToast("Updating failed!");
            }
        };

        if (id.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "No id!",  Toast.LENGTH_LONG);
            toast.show();
        } else {
            if (name.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Not enough information!",  Toast.LENGTH_LONG);
                toast.show();
            } else {
                Departments object = new Departments(Long.parseLong(id), name);
                String json = departmentGson.toJson(object);
                String url = "departments/update";
                handler.setUrlResource(url);
                handler.setHttpMethod("PUT");
                handler.execute(callBack, json);
            }
        }
    }

    private void createToast(final String text) {
        runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(getApplicationContext(), text,  Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void clearFields() {
        ETId.setText("");
        ETName.setText("");
    }

    private void setFieldsWithCurrentDepartment(final int num) {
        runOnUiThread(new Runnable() {
            public void run() {
                Departments object = array.get(num);
                ETId.setText(object.getId().toString());
                ETName.setText(object.getName());
                TVPage.setText((num + 1) + "/" + arrayLength);
            }
        });
    }
}
