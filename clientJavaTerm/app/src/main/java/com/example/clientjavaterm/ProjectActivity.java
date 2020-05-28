package com.example.clientjavaterm;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientjavaterm.converters.DepartmentConverter;
import com.example.clientjavaterm.converters.ProjectConverter;
import com.example.clientjavaterm.entity.Departments;
import com.example.clientjavaterm.entity.Projects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ProjectActivity extends AppCompatActivity {

    private EditText ETId;
    private EditText ETName;
    private EditText ETCost;
    private Spinner spinnerDepartment;
    private EditText ETDateBeg;
    private EditText ETDateEnd;
    private EditText ETDateEndReal;
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
    private List<Projects> array;
    private String token;
    private String urlString;
    private int spinnerItem;
    private int currentRecord;
    private int arrayLength;
    private Departments currentDepartment;
    private CustomSpinnerAdapter<Departments> departmentAdapter;
    private int currentDepartmentIndex;
    private RequestHandler handler;
    private ProjectConverter converter;
    private Gson projectGson;
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

        converter = new ProjectConverter();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(converter.getConverterClass(), converter);
        projectGson = builder.create();

        handler = new RequestHandler(urlString);
        handler.setToken(token);

        setContentView(R.layout.activity_project);

        ETId = findViewById(R.id.ETProjectId);
        ETName = findViewById(R.id.ETProjectName);
        ETCost = findViewById(R.id.ETProjectCost);
        spinnerDepartment = findViewById(R.id.SpProjectDepartmentSpinner);
        ETDateBeg = findViewById(R.id.ETProjectDateBeg);
        ETDateEnd = findViewById(R.id.ETProjectDateEnd);
        ETDateEndReal = findViewById(R.id.ETProjectDateEndReal);
        BPrev = findViewById(R.id.BProjectPrev);
        BNext = findViewById(R.id.BProjectNext);
        TVPage = findViewById(R.id.TVProjectPages);
        BNew = findViewById(R.id.BProjectNew);
        BUpdate = findViewById(R.id.BProjectUpdate);
        BAdd = findViewById(R.id.BProjectAdd);
        BDelete = findViewById(R.id.BProjectDelete);
        BFind = findViewById(R.id.BProjectFind);
        ETFindNameOrId = findViewById(R.id.ETProjectFindLastNameOrId);
        spinner = findViewById(R.id.SpProjectSpinner);
        ArrayAdapter<?> spAdapter =
                ArrayAdapter.createFromResource(this, R.array.ProjectFind, android.R.layout.simple_spinner_item);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spAdapter);

        if (!rolesList.contains("ROLE_ADMIN")) {
            BUpdate.setVisibility(View.GONE);
            BAdd.setVisibility(View.GONE);
            BDelete.setVisibility(View.GONE);
        }

        ETId.setFocusable(false);
        ETId.setLongClickable(false);
        ETId.setCursorVisible(false);
        ETDateBeg.setFocusable(false);
        ETDateBeg.setLongClickable(false);
        ETDateBeg.setCursorVisible(false);
        ETDateEnd.setFocusable(false);
        ETDateEnd.setLongClickable(false);
        ETDateEnd.setCursorVisible(false);
        ETDateEndReal.setFocusable(false);
        ETDateEndReal.setLongClickable(false);
        ETDateEndReal.setCursorVisible(false);


        departmentAdapter = new CustomSpinnerAdapter<>(this, android.R.layout.simple_spinner_item);

        spinnerDepartment.setAdapter(departmentAdapter);

        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

        updateDepartmentSpinner();

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
                    case 4: {
                        ETFindNameOrId.setVisibility(View.VISIBLE);
                        ETFindNameOrId.setHint("name");
                        spinnerItem = 4;
                        break;
                    }
                    case 5: {
                        ETFindNameOrId.setVisibility(View.VISIBLE);
                        ETFindNameOrId.setHint("id");
                        spinnerItem = 5;
                        break;
                    }
                }
                ETFindNameOrId.setText("");
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy" , Locale.ROOT);
                String formatted = format1.format(cal.getTime());
                ETDateBeg.setText(formatted);
            }
        };

        ETDateBeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ProjectActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        final DatePickerDialog.OnDateSetListener mDateEndSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy" , Locale.ROOT);
                String formatted = format1.format(cal.getTime());
                ETDateEnd.setText(formatted);
            }
        };

        ETDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ProjectActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDateEndSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        final DatePickerDialog.OnDateSetListener mDateEndRealSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy" , Locale.ROOT);
                String formatted = format1.format(cal.getTime());
                ETDateEndReal.setText(formatted);
            }
        };

        ETDateEndReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ProjectActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDateEndRealSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
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
                    setFieldsWithCurrentProject(currentRecord);
                }
            }
        });

        BNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRecord < arrayLength - 1) {
                    currentRecord++;
                    setFieldsWithCurrentProject(currentRecord);
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

    private void updateDepartmentSpinner() {
        departmentAdapter.clear();
        CallBack<String> callBack = new CallBack<String>() {
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
                                /*for (Departments dep : list) {
                                    finalList.add(dep.toString());
                                }*/
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
        handler.setHttpMethod("GET");
        handler.setUrlResource("departments/all");
        handler.execute(callBack, null);
    }

    private void BFindClickListener() {
        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<List<Projects>>(){}.getType();
                List<Projects> list = new ArrayList<>();
                try {
                    list = projectGson.fromJson(result, type);
                } catch (JsonIOException | JsonSyntaxException ex) {
                    Projects empl = projectGson.fromJson(result, Projects.class);
                    list.add(empl);
                }
                if (list.isEmpty()) {
                    createToast("Nothing found!");
                } else {
                    array = list;
                    currentRecord = 0;
                    arrayLength = array.size();
                    setFieldsWithCurrentProject(0);
                }
            }

            @Override
            public void onFail(String message, int code) {
                handlerForBadRequest(code, "projects");
            }
        };

        String text = ETFindNameOrId.getText().toString();
        String url = "";
        System.out.println("spinner item: " + spinnerItem);
        switch (spinnerItem) {
            case 1: {
                url = "projects/all";
                break;
            }
            case 2: {
                url = "projects/getById/" + text;
                break;
            }
            case 3: {
                url = "projects/getByName/" + text;
                break;
            }
            case 4: {
                url = "projects/getByDepartmentName/" + text;
                break;
            }
            case 5: {
                url = "projects/getByDepartmentId/" + text;
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
                        setFieldsWithCurrentProject(currentRecord);
                    } else {
                        currentRecord--;
                        setFieldsWithCurrentProject(currentRecord);
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
                handlerForBadRequest(code,"projects");
            }
        };

        if (id.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Enter Id",  Toast.LENGTH_LONG);
            toast.show();
        } else {
            String url = "projects/deleteById/" + id;
            handler.setUrlResource(url);
            handler.setHttpMethod("DELETE");
            handler.execute(callBack, null);
        }
    }

    private void BAddClickListener() {
        final String name = ETName.getText().toString();
        final String cost = ETCost.getText().toString();
        final String dateBeg = ETDateBeg.getText().toString();
        final String dateEnd = ETDateEnd.getText().toString();
        final String dateEndReal = ETDateEndReal.getText().toString();

        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<List<Projects>>(){}.getType();
                List<Projects> list = new ArrayList<>();
                try {
                    list = projectGson.fromJson(result, type);
                } catch (JsonIOException | JsonSyntaxException ex) {
                    Projects proj = projectGson.fromJson(result, Projects.class);
                    list.add(proj);
                }

                if (!list.contains(array.get(currentRecord))) {
                    CallBack<String> call = new CallBack<String>() {
                        @Override
                        public void onSuccess(String result) {
                            final Projects proj = projectGson.fromJson(result, Projects.class);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    ETId.setText(proj.getId().toString());
                                }
                            });
                            createToast("Adding completed successfully!");
                        }

                        @Override
                        public void onFail(String message, int code) {
                            handlerForBadRequest(code, "projects"); //adding failed
                        }
                    };

                    if (name.isEmpty() || cost.isEmpty() || dateBeg.isEmpty() || dateEnd.isEmpty() ||
                            dateEndReal.isEmpty() || currentDepartment == null) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Not enough information!",  Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        Float newCost = Float.parseFloat(cost);
                        Date beg = null;
                        Date end = null;
                        Date endReal = null;
                        try {
                            java.util.Date utilDate = new SimpleDateFormat( "dd.MM.yyyy" ).parse(dateBeg);
                            beg = new Date(utilDate.getTime());
                            utilDate = new SimpleDateFormat( "dd.MM.yyyy" ).parse(dateEnd);
                            end = new Date(utilDate.getTime());
                            utilDate = new SimpleDateFormat( "dd.MM.yyyy" ).parse(dateEndReal);
                            endReal = new Date(utilDate.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Projects object = new Projects(null, name, newCost, currentDepartment, beg, end, endReal);
                        String json = projectGson.toJson(object);
                        System.out.println(json);
                        //System.out.println(object);
                        //System.out.println(object.toString());
                        String url = "projects/add";
                        handler.setUrlResource(url);
                        handler.setHttpMethod("POST");
                        handler.execute(call, json);
                    }
                } else {
                    createToast("Project already exist!");
                }

            }

            @Override
            public void onFail(String message, int code) {
                handlerForBadRequest(code, "departments"); //Adding failed
            }
        };

        handler.setUrlResource("departments/all");
        handler.setHttpMethod("GET");
        handler.execute(callBack, null);
    }

    private void BUpdateClickListener() {
        String id = ETId.getText().toString();
        String name = ETName.getText().toString();
        String cost = ETCost.getText().toString();
        String dateBeg = ETDateBeg.getText().toString();
        String dateEnd = ETDateEnd.getText().toString();
        String dateEndReal = ETDateEndReal.getText().toString();

        CallBack<String> callBack = new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                Projects proj = projectGson.fromJson(result, Projects.class);
                Projects curr = array.get(currentRecord);
                curr.setId(proj.getId());
                curr.setName(proj.getName());
                curr.setCost(proj.getCost());
                curr.setDepartments(proj.getDepartments());
                curr.setDateBeg(proj.getDateBeg());
                curr.setDateEnd(proj.getDateEnd());
                curr.setDateEndReal(proj.getDateEndReal());
                createToast("Update completed successfully!");
            }

            @Override
            public void onFail(String message, int code) {
                handlerForBadRequest(code, "projects"); //Updating failed
            }
        };

        if (id.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "No id!",  Toast.LENGTH_LONG);
            toast.show();
        } else {
            if (name.isEmpty() || cost.isEmpty() || dateBeg.isEmpty() || dateEnd.isEmpty() ||
                    dateEndReal.isEmpty() || currentDepartment == null) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Not enough information!",  Toast.LENGTH_LONG);
                toast.show();
            } else {
                Float newCost = Float.parseFloat(cost);
                Date beg = null;
                Date end = null;
                Date endReal = null;
                try {
                    java.util.Date utilDate = new SimpleDateFormat( "dd.MM.yyyy" ).parse(dateBeg);
                    beg = new Date(utilDate.getTime());
                    utilDate = new SimpleDateFormat( "dd.MM.yyyy" ).parse(dateEnd);
                    end = new Date(utilDate.getTime());
                    utilDate = new SimpleDateFormat( "dd.MM.yyyy" ).parse(dateEndReal);
                    endReal = new Date(utilDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Projects object = new Projects(Long.parseLong(id), name, newCost, currentDepartment, beg, end, endReal);
                String json = projectGson.toJson(object);
                String url = "projects/update";
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
                mess = "No such "+ name + "!";
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
        ETName.setText("");
        ETCost.setText("");
        ETDateBeg.setText("");
        ETDateEnd.setText("");
        ETDateEndReal.setText("");
    }

    private void setFieldsWithCurrentProject(final int num) {
        runOnUiThread(new Runnable() {
            public void run() {
                departmentAdapter.setFlag(true);
                Projects object = array.get(num);
                ETId.setText(object.getId().toString());
                ETName.setText(object.getName());
                ETCost.setText(object.getCost().toString());
                List<Departments> list = departmentAdapter.getList();
                int ind = -1;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId().equals(object.getDepartments().getId())) {
                        ind = i;
                    }
                }
                spinnerDepartment.setSelection(ind);
                ETDateBeg.setText(object.getDateBeg().toString());
                ETDateEnd.setText(object.getDateEnd().toString());
                ETDateEndReal.setText(object.getDateEndReal().toString());
                TVPage.setText((num + 1) + "/" + arrayLength);
            }
        });
    }
}
