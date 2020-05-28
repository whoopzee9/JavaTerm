package com.example.clientjavaterm;

import android.os.AsyncTask;
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

import com.example.clientjavaterm.converters.DepartmentConverter;
import com.example.clientjavaterm.converters.DepartmentsEmployeesConverter;
import com.example.clientjavaterm.converters.EmployeeConverter;
import com.example.clientjavaterm.converters.ProjectConverter;
import com.example.clientjavaterm.entity.Departments;
import com.example.clientjavaterm.entity.DepartmentsEmployees;
import com.example.clientjavaterm.entity.Employees;
import com.example.clientjavaterm.entity.Projects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DepartmentsEmployeesActivity extends AppCompatActivity {

    private EditText ETId;
    private Spinner spinnerDepartment;
    private Spinner spinnerEmployee;
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
    private List<DepartmentsEmployees> array;
    private String token;
    private String urlString;
    private int spinnerItem;
    private int currentRecord;
    private int arrayLength;
    private Departments currentDepartment;
    private CustomSpinnerAdapter<Departments> departmentAdapter;
    private int currentDepartmentIndex;
    private Employees currentEmployee;
    private CustomSpinnerAdapter<Employees> employeeAdapter;
    private int currentEmployeeIndex;
    private RequestHandler handler;
    private DepartmentsEmployeesConverter converter;
    private Gson DEGson;
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

        converter = new DepartmentsEmployeesConverter();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(converter.getConverterClass(), converter);
        DEGson = builder.create();

        handler = new RequestHandler(urlString);
        handler.setToken(token);

        setContentView(R.layout.activity_departments_employees);

        ETId = findViewById(R.id.ETDEId);
        spinnerDepartment = findViewById(R.id.SpDEDepartmentSpinner);
        spinnerEmployee = findViewById(R.id.SpDEEmployeeSpinner);
        BPrev = findViewById(R.id.BDEPrev);
        BNext = findViewById(R.id.BDENext);
        TVPage = findViewById(R.id.TVDEPages);
        BNew = findViewById(R.id.BDENew);
        BUpdate = findViewById(R.id.BDEUpdate);
        BAdd = findViewById(R.id.BDEAdd);
        BDelete = findViewById(R.id.BDEDelete);
        BFind = findViewById(R.id.BDEFind);
        ETFindLastNameOrId = findViewById(R.id.ETDEFindLastNameOrId);
        ETFindFirstName = findViewById(R.id.ETDEFindFirstName);
        ETFindPatherName = findViewById(R.id.ETDEFindPatherName);
        spinner = findViewById(R.id.SpDESpinner);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.DEFind, android.R.layout.simple_spinner_item);
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
                    case 4: {
                        ETFindLastNameOrId.setVisibility(View.VISIBLE);
                        ETFindFirstName.setVisibility(View.GONE);
                        ETFindPatherName.setVisibility(View.GONE);
                        ETFindLastNameOrId.setHint("id");
                        spinnerItem = 4;
                        break;
                    }
                    case 5: {
                        ETFindLastNameOrId.setVisibility(View.VISIBLE);
                        ETFindFirstName.setVisibility(View.GONE);
                        ETFindPatherName.setVisibility(View.GONE);
                        ETFindLastNameOrId.setHint("name");
                        spinnerItem = 5;
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

        departmentAdapter = new CustomSpinnerAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerDepartment.setAdapter(departmentAdapter);

        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departmentAdapter.setFlag(true);
                currentDepartment = (Departments) parent.getItemAtPosition(position);
                currentDepartmentIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerDepartment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() ==  MotionEvent.ACTION_DOWN) {
                    updateDepartmentSpinner();
                }
                return false;
            }
        });

        employeeAdapter = new CustomSpinnerAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerEmployee.setAdapter(employeeAdapter);

        spinnerEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employeeAdapter.setFlag(true);
                currentEmployee = (Employees) parent.getItemAtPosition(position);
                currentEmployeeIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerEmployee.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() ==  MotionEvent.ACTION_DOWN) {
                    updateEmployeeSpinner();
                }
                return false;
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
                    setFieldsWithCurrentDE(currentRecord);
                }
            }
        });

        BNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRecord < arrayLength - 1) {
                    currentRecord++;
                    setFieldsWithCurrentDE(currentRecord);
                }
            }
        });

        BNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        updateDepartmentSpinner();
        updateEmployeeSpinner();
    }

    private void updateDepartmentSpinner() {
        departmentAdapter.clear();
        final CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                DepartmentConverter lConverter = new DepartmentConverter();
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(lConverter.getConverterClass(), lConverter);
                Gson departmentGson = builder.create();
                Type type = new TypeToken<List<Departments>>(){}.getType();
                List<Departments> list = new ArrayList<>();
                try {
                    list = departmentGson.fromJson(result, type);
                } catch (JsonIOException | JsonSyntaxException ex) {
                    Departments dep = departmentGson.fromJson(result, Departments.class);
                    list.add(dep);
                }
                if (!list.isEmpty()) {

                    final List<Departments> finalList = list;
                    System.out.println("departments: " + finalList.size());

                    runOnUiThread(new Runnable() {
                        public void run() {
                            departmentAdapter.addAll(finalList);
                            departmentAdapter.notifyDataSetChanged();
                        }
                    });
                    //setFieldsWithCurrentProject(0);
                    //createToast("Nothing found!");
                }
            }

            @Override
            public void onFail(String message, int code) {
                handlerForBadRequest(code, "departments");
            }
        };
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                handler.setHttpMethod("GET");
                handler.setUrlResource("departments/all");
                handler.execute(callBack, null);
            }
        });
//        handler.setHttpMethod("GET");
//        handler.setUrlResource("departments/all");
//        handler.execute(callBack, null);
    }

    private void updateEmployeeSpinner() {
        employeeAdapter.clear();
        final CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                EmployeeConverter lConverter = new EmployeeConverter();
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(lConverter.getConverterClass(), lConverter);
                Gson employeeGson = builder.create();
                Type type = new TypeToken<List<Employees>>(){}.getType();
                List<Employees> list = new ArrayList<>();
                try {
                    list = employeeGson.fromJson(result, type);
                } catch (JsonIOException | JsonSyntaxException ex) {
                    Employees dep = employeeGson.fromJson(result, Employees.class);
                    list.add(dep);
                }
                if (!list.isEmpty()) {

                    final List<Employees> finalList = list;
                    System.out.println("employees: " + finalList.size());

                    runOnUiThread(new Runnable() {
                        public void run() {
                            employeeAdapter.addAll(finalList);
                            employeeAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }

            @Override
            public void onFail(String message, int code) {
                handlerForBadRequest(code, "employees");
            }
        };
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                handler.setHttpMethod("GET");
                handler.setUrlResource("employees/all");
                handler.execute(callBack, null);
            }
        });
//        handler.setHttpMethod("GET");
//        handler.setUrlResource("employees/all");
//        handler.execute(callBack, null);
    }

    private void BFindClickListener() {
        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<List<DepartmentsEmployees>>(){}.getType();
                List<DepartmentsEmployees> list = new ArrayList<>();
                try {
                    list = DEGson.fromJson(result, type);
                } catch (JsonIOException | JsonSyntaxException ex) {
                    DepartmentsEmployees de = DEGson.fromJson(result, DepartmentsEmployees.class);
                    list.add(de);
                }
                if (list.isEmpty()) {
                    createToast("Nothing found!");
                } else {
                    array = list;
                    currentRecord = 0;
                    arrayLength = array.size();
                    setFieldsWithCurrentDE(0);
                }
            }

            @Override
            public void onFail(String message, int code) {
                handlerForBadRequest(code, "records");
            }
        };

        String url = "";
        String text = ETFindLastNameOrId.getText().toString();
        String first = ETFindFirstName.getText().toString();
        String pather = ETFindPatherName.getText().toString();
        switch (spinnerItem) {
            case 1: {
                url = "departmentsEmployees/all";
                break;
            }
            case 2: {
                url = "departmentsEmployees/getByEmployeeId/" + text;
                break;
            }
            case 3: {
                url = "departmentsEmployees/getByEmployeeName/" + text + "/" + first + "/" + pather;
                break;
            }
            case 4: {
                url = "departmentsEmployees/getByDepartmentId/" + text;
                break;
            }
            case 5: {
                url = "departmentsEmployees/getByDepartmentName/" + text + "/" + first + "/" + pather;
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
                        setFieldsWithCurrentDE(currentRecord);
                    } else {
                        currentRecord--;
                        setFieldsWithCurrentDE(currentRecord);
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
            public void onFail(String message, int code) {
                handlerForBadRequest(code, "records");
            }
        };

        if (id.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Enter Id",  Toast.LENGTH_LONG);
            toast.show();
        } else {
            String url = "departmentsEmployees/deleteById/" + id;
            handler.setUrlResource(url);
            handler.setHttpMethod("DELETE");
            handler.execute(callBack, null);
        }
    }

    private void BAddClickListener() {

        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<List<DepartmentsEmployees>>(){}.getType();
                List<DepartmentsEmployees> list = new ArrayList<>();
                try {
                    list = DEGson.fromJson(result, type);
                } catch (JsonIOException | JsonSyntaxException ex) {
                    DepartmentsEmployees de = DEGson.fromJson(result, DepartmentsEmployees.class);
                    list.add(de);
                }
                if (!list.contains(array.get(currentRecord))) {
                    CallBack<String> call = new CallBack<String>() {
                        @Override
                        public void onSuccess(String result) {
                            final DepartmentsEmployees proj = DEGson.fromJson(result, DepartmentsEmployees.class);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    ETId.setText(proj.getId().toString());
                                }
                            });
                            createToast("Adding completed successfully!");
                        }

                        @Override
                        public void onFail(String message, int code) {
                            handlerForBadRequest(code, "records"); //Adding failed
                        }
                    };

                    if (currentDepartment == null || currentEmployee == null) {
                        createToast("Not enough information!");
                    } else {
                        DepartmentsEmployees object = new DepartmentsEmployees(null, currentDepartment, currentEmployee);
                        String json = DEGson.toJson(object);
                        System.out.println(json);
                        String url = "departmentsEmployees/add";
                        handler.setUrlResource(url);
                        handler.setHttpMethod("POST");
                        handler.execute(call, json);
                    }
                } else {
                    createToast("Record already exist!");
                }

            }

            @Override
            public void onFail(String message, int code) {
                handlerForBadRequest(code, "records"); //Adding failed
            }
        };

        handler.setUrlResource("departmentsEmployees/all");
        handler.setHttpMethod("GET");
        handler.execute(callBack, null);
    }

    private void BUpdateClickListener() {
        String id = ETId.getText().toString();

        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                DepartmentsEmployees de = DEGson.fromJson(result, DepartmentsEmployees.class);
                DepartmentsEmployees curr = array.get(currentRecord);
                curr.setId(de.getId());
                curr.setDepartments(de.getDepartments());
                curr.setEmployees(de.getEmployees());

                createToast("Update completed successfully!");
            }

            @Override
            public void onFail(String message, int code) {
                handlerForBadRequest(code, "connection"); //Updating failed
            }
        };

        if (id.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "No id!",  Toast.LENGTH_LONG);
            toast.show();
        } else {
            if (currentDepartment == null || currentEmployee == null) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Not enough information!",  Toast.LENGTH_LONG);
                toast.show();
            } else {
                DepartmentsEmployees object = new DepartmentsEmployees(Long.parseLong(id), currentDepartment, currentEmployee);
                String json = DEGson.toJson(object);
                String url = "departmentsEmployees/update";
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

    private void handlerForBadRequest(int code, String name) {
        String mess;
        switch (code) {
            case HttpURLConnection.HTTP_NOT_FOUND: {
                mess = "No such " + name + "!";
                break;
            }
            case HttpURLConnection.HTTP_FORBIDDEN: {
                mess = "Your token is expired!";
                break;
            }
            /*case HttpURLConnection.HTTP_BAD_METHOD: {
                mess = "You can't delete employee!";
                break;
            }*/
            default:
                mess = "Connection failed!";
        }
        createToast(mess);
    }

    private void clearFields() {
        ETId.setText("");
        //TODO сделать сброс для спиннеров
    }

    private void setFieldsWithCurrentDE(final int num) {
        runOnUiThread(new Runnable() {
            public void run() {
                DepartmentsEmployees object = array.get(num);
                ETId.setText(object.getId().toString());

                List<Departments> list1 = departmentAdapter.getList();
                int ind = -1;
                for (int i = 0; i < list1.size(); i++) {
                    if (list1.get(i).getId().equals(object.getDepartments().getId())) {
                        ind = i;
                    }
                }
                spinnerDepartment.setSelection(ind);

                List<Employees> list2 = employeeAdapter.getList();
                ind = -1;
                for (int i = 0; i < list2.size(); i++) {
                    if (list2.get(i).getId().equals(object.getEmployees().getId())) {
                        ind = i;
                    }
                }
                spinnerEmployee.setSelection(ind);

                TVPage.setText((num + 1) + "/" + arrayLength);
            }
        });
    }
}
