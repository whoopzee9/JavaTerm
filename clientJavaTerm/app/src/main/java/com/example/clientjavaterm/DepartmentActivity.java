package com.example.clientjavaterm;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_TEXT;

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

        array = new ArrayList<>();

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
                        ETFindNameOrId.setInputType(TYPE_CLASS_NUMBER);
                        ETFindNameOrId.setHint("id");
                        spinnerItem = 2;
                        break;
                    }
                    case 3: {
                        ETFindNameOrId.setVisibility(View.VISIBLE);
                        ETFindNameOrId.setInputType(TYPE_CLASS_TEXT);
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
                clearFocuses();
            }
        });

        BAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BAddClickListener();
                clearFocuses();
            }
        });

        BDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDeleteClickListener();
                clearFocuses();
            }
        });

        BUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUpdateClickListener();
                clearFocuses();
            }
        });

        BPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRecord > 0) {
                    currentRecord--;
                }
                setFieldsWithCurrentDepartment(currentRecord);
                clearFocuses();
            }
        });

        BNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRecord < arrayLength - 1) {
                    currentRecord++;
                }
                setFieldsWithCurrentDepartment(currentRecord);
                clearFocuses();
            }
        });

        BNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
                clearFocuses();
            }
        });
    }

    private void BFindClickListener() {
        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                ResultConverter<Departments> converter = new ResultConverter<>(departmentGson);
                Type listType = new TypeToken<List<Departments>>(){}.getType();
                Type type = new TypeToken<Departments>(){}.getType();
                List<Departments> list = converter.getListFromResult(result, listType, type);
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
            public void onFail(String message, int code) {
                handlerForBadRequest(code);
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
                createToast("Deleted completed successfully!");
            }

            @Override
            public void onFail(String message, int code) {
                handlerForBadRequest(code);
            }
        };

        if (id.isEmpty()) {
            createToast("Enter id!");
        } else {
            String url = "departments/deleteById/" + id;
            handler.setUrlResource(url);
            handler.setHttpMethod("DELETE");
            handler.execute(callBack, null);
        }
    }

    private void BAddClickListener() {
        final String name = ETName.getText().toString();

        if (!ETId.getText().toString().isEmpty()) {
            createToast("Can't add. Please try to click 'New' for new one, or 'Update'");
            return;
        }

        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                ResultConverter<Departments> converter = new ResultConverter<>(departmentGson);
                Type listType = new TypeToken<List<Departments>>(){}.getType();
                Type type = new TypeToken<Departments>(){}.getType();
                List<Departments> list = converter.getListFromResult(result, listType, type);
                Departments departments = new Departments(null, name);

                if (name.isEmpty()) {
                    createToast("Not enough information!");
                    return;
                }

                if (!list.contains(departments)) {
                    CallBack<String> callBack = new CallBack<String>() {
                        @Override
                        public void onSuccess(String result) {
                            final Departments dep = departmentGson.fromJson(result, Departments.class);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    ETId.setText(dep.getId().toString());
                                    arrayLength++;
                                    //System.out.println(dep);
                                    currentRecord = arrayLength - 1;
                                    array.add(dep);
                                    setFieldsWithCurrentDepartment(currentRecord);
                                }
                            });
                            createToast("Adding completed successfully!");
                        }

                        @Override
                        public void onFail(String message, int code) {
                            handlerForBadRequest(code); //Adding failed
                        }
                    };

                    String json = departmentGson.toJson(departments);
                    String url = "departments/add";
                    handler.setUrlResource(url);
                    handler.setHttpMethod("POST");
                    handler.execute(callBack, json);
                } else {
                    createToast("Department already exist!");
                }

            }

            @Override
            public void onFail(String message, int code) {
                handlerForBadRequest(code); //Adding failed
            }
        };

        handler.setUrlResource("departments/all");
        handler.setHttpMethod("GET");
        handler.execute(callBack, null);
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
            public void onFail(String message, int code) {
                handlerForBadRequest(code); //Updating failed
            }
        };

        if (id.isEmpty()) {
            createToast("No id!");
        } else {
            if (name.isEmpty()) {
                createToast("Not enough information!");
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

    private void handlerForBadRequest(int code) {
        String mess;
        switch (code) {
            case HttpURLConnection.HTTP_NOT_FOUND: {
                mess = "No such departments!";
                break;
            }
            case HttpURLConnection.HTTP_FORBIDDEN: {
                mess = "Your token is expired!";
                break;
            }
            case HttpURLConnection.HTTP_BAD_METHOD: {
                mess = "You can't delete department!";
                break;
            }
            default:
                mess = "Connection failed or token is expired!";
        }
        createToast(mess);
    }

    private void clearFields() {
        ETId.setText("");
        ETName.setText("");
    }

    private void clearFocuses() {
        ETId.clearFocus();
        ETName.clearFocus();
        ETFindNameOrId.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(BAdd.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
