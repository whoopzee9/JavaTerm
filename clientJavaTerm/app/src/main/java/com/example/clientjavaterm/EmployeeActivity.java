package com.example.clientjavaterm;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientjavaterm.converters.EmployeeConverter;
import com.example.clientjavaterm.entity.Employees;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    private EditText ETId;
    private EditText ETLastName;
    private EditText ETFirstName;
    private EditText ETPatherName;
    private EditText ETPosition;
    private EditText ETSalary;
    private Button BPrev;
    private Button BNext;
    private TextView TVPage;
    private Button BNew;
    private Button BUpdate;
    private Button BAdd;
    private Button BDelete;
    private Button BFind;
    private EditText ETFindLastNameOrId;
    private EditText ETFindFirstName;
    private EditText ETFindPatherName;
    private Spinner spinner;
    private List<Employees> array;
    private String token;
    private String urlString;
    private int spinnerItem;
    private int currentRecord;
    private int arrayLength;
    private RequestHandler handler;
    private EmployeeConverter converter;
    private Gson employeeGson;
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

        converter = new EmployeeConverter();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(converter.getConverterClass(), converter);
        employeeGson = builder.create();

        handler = new RequestHandler(urlString);
        handler.setToken(token);

        setContentView(R.layout.activity_employee);

        ETId = findViewById(R.id.ETEmployeeId);
        ETLastName = findViewById(R.id.ETEmployeeLastName);
        ETFirstName = findViewById(R.id.ETEmployeeFirstName);
        ETPatherName = findViewById(R.id.ETEmployeePatherName);
        ETPosition = findViewById(R.id.ETEmployeePosition);
        ETSalary = findViewById(R.id.ETEmployeeSalary);
        BPrev = findViewById(R.id.BEmployeePrev);
        BNext = findViewById(R.id.BEmployeeNext);
        TVPage = findViewById(R.id.TVEmployeePages);
        BNew = findViewById(R.id.BEmployeeNew);
        BUpdate = findViewById(R.id.BEmployeeUpdate);
        BAdd = findViewById(R.id.BEmployeeAdd);
        BDelete = findViewById(R.id.BEmployeeDelete);
        BFind = findViewById(R.id.BEmployeeFind);
        ETFindLastNameOrId = findViewById(R.id.ETEmployeeFindLastNameOrId);
        ETFindFirstName = findViewById(R.id.ETEmployeeFindFirstName);
        ETFindPatherName = findViewById(R.id.ETEmployeeFindPatherName);
        spinner = findViewById(R.id.SpEmployeeSpinner);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.EmployeeFind, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (!rolesList.contains("ROLE_ADMIN")) {
            BUpdate.setVisibility(View.GONE);
            BAdd.setVisibility(View.GONE);
            BDelete.setVisibility(View.GONE);
        }

        ETId.setFocusable(false);
        ETId.setLongClickable(false);
        ETId.setCursorVisible(false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                switch (selectedItemPosition) {
                    case 0: {
                        ETFindLastNameOrId.setVisibility(View.GONE);
                        ETFindFirstName.setVisibility(View.GONE);
                        ETFindPatherName.setVisibility(View.GONE);
                        spinnerItem = 0;
                        break;
                    }
                    case 1: {
                        ETFindLastNameOrId.setVisibility(View.GONE);
                        ETFindFirstName.setVisibility(View.GONE);
                        ETFindPatherName.setVisibility(View.GONE);
                        spinnerItem = 1;
                        break;
                    }
                    case 2: {
                        ETFindLastNameOrId.setVisibility(View.VISIBLE);
                        ETFindFirstName.setVisibility(View.GONE);
                        ETFindPatherName.setVisibility(View.GONE);
                        ETFindLastNameOrId.setHint("id");
                        spinnerItem = 2;
                        break;
                    }
                    case 3: {
                        ETFindLastNameOrId.setVisibility(View.VISIBLE);
                        ETFindFirstName.setVisibility(View.VISIBLE);
                        ETFindPatherName.setVisibility(View.VISIBLE);
                        ETFindLastNameOrId.setHint("last name");
                        spinnerItem = 3;
                        break;
                    }
                }
                ETFindLastNameOrId.setText("");
                ETFindFirstName.setText("");
                ETFindPatherName.setText("");
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
                    setFieldsWithCurrentEmployee(currentRecord);
                }
            }
        });

        BNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRecord < arrayLength - 1) {
                    currentRecord++;
                    setFieldsWithCurrentEmployee(currentRecord);
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
                Type type = new TypeToken<List<Employees>>(){}.getType();
                List<Employees> list = new ArrayList<>();
                try {
                    list = employeeGson.fromJson(result, type);
                } catch (JsonIOException | JsonSyntaxException ex) {
                    Employees empl = employeeGson.fromJson(result, Employees.class);
                    list.add(empl);
                }
                if (list.isEmpty()) {
                    createToast("Nothing found!");
                } else {
                    array = list;
                    currentRecord = 0;
                    arrayLength = array.size();
                    setFieldsWithCurrentEmployee(0);
                }
            }

            @Override
            public void onFail(String message) {
                createToast("No such employees!");
            }
        };

        String url = "";
        switch (spinnerItem) {
            case 1: {
                url = "employees/all";
                break;
            }
            case 2: {
                String id = ETFindLastNameOrId.getText().toString();
                url = "employees/getById/" + id;
                break;
            }
            case 3: {
                String last = ETFindLastNameOrId.getText().toString();
                String first = ETFindFirstName.getText().toString();
                String pather = ETFindPatherName.getText().toString();
                url = "employees/getByName/" + last + "/" + first + "/" + pather;
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
                        setFieldsWithCurrentEmployee(currentRecord);
                    } else {
                        currentRecord--;
                        setFieldsWithCurrentEmployee(currentRecord);
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
                createToast("No such employees!");
            }
        };

        if (id.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Enter Id",  Toast.LENGTH_LONG);
            toast.show();
        } else {
            String url = "employees/deleteById/" + id;
            handler.setUrlResource(url);
            handler.setHttpMethod("DELETE");
            handler.execute(callBack, null);
        }
    }

    private void BAddClickListener() {
        String last = ETLastName.getText().toString();
        String first = ETFirstName.getText().toString();
        String pather = ETPatherName.getText().toString();
        String position = ETPosition.getText().toString();
        String salary = ETSalary.getText().toString();

        //TODO добавить проверку на существование

        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                final Employees empl = employeeGson.fromJson(result, Employees.class);
                runOnUiThread(new Runnable() {
                    public void run() {
                        ETId.setText(empl.getId().toString());
                    }
                });
                createToast("Adding completed successfully!");
            }

            @Override
            public void onFail(String message) {
                createToast("Adding failed!");
            }
        };

        if (last.isEmpty() || first.isEmpty() || pather.isEmpty() || position.isEmpty() || salary.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Not enough information!",  Toast.LENGTH_LONG);
            toast.show();
        } else {
            Employees empl = new Employees(null, first, last, pather, position, Float.parseFloat(salary));
            String json = employeeGson.toJson(empl);
            String url = "employees/add";
            handler.setUrlResource(url);
            handler.setHttpMethod("POST");
            handler.execute(callBack, json);
        }
    }

    private void BUpdateClickListener() {
        String id = ETId.getText().toString();
        String last = ETLastName.getText().toString();
        String first = ETFirstName.getText().toString();
        String pather = ETPatherName.getText().toString();
        String position = ETPosition.getText().toString();
        String salary = ETSalary.getText().toString();

        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                Employees empl = employeeGson.fromJson(result, Employees.class);
                Employees curr = array.get(currentRecord);
                curr.setId(empl.getId());
                curr.setFirstName(empl.getFirstName());
                curr.setLastName(empl.getLastName());
                curr.setPatherName(empl.getPatherName());
                curr.setPosition(empl.getPosition());
                curr.setSalary(empl.getSalary());
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
            if (last.isEmpty() || first.isEmpty() || pather.isEmpty() || position.isEmpty() || salary.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Not enough information!",  Toast.LENGTH_LONG);
                toast.show();
            } else {
                Employees object = new Employees(Long.parseLong(id), first, last, pather, position, Float.parseFloat(salary));
                String json = employeeGson.toJson(object);
                String url = "employees/update";
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
        ETLastName.setText("");
        ETFirstName.setText("");
        ETPatherName.setText("");
        ETPosition.setText("");
        ETSalary.setText("");
    }

    private void setFieldsWithCurrentEmployee(final int num) {
        runOnUiThread(new Runnable() {
            public void run() {
                Employees object = array.get(num);
                ETId.setText(object.getId().toString());
                ETLastName.setText(object.getLastName());
                ETFirstName.setText(object.getFirstName());
                ETPatherName.setText(object.getPatherName());
                ETPosition.setText(object.getPosition());
                ETSalary.setText(object.getSalary().toString());
                TVPage.setText((num + 1) + "/" + arrayLength);
            }
        });
    }
}