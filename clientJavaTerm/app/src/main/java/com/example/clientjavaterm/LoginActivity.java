package com.example.clientjavaterm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText ETLogin;
    private EditText ETPassword;
    private EditText ETRepeatPassword;
    private Switch SwRegister;
    private Button BLogin;
    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ETLogin = findViewById(R.id.etLogin);
        ETPassword = findViewById(R.id.etPassword);
        ETRepeatPassword = findViewById(R.id.etRepeatPassword);
        SwRegister = findViewById(R.id.swRegister);
        BLogin = findViewById(R.id.bLogin);
        BLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ETLogin.getText().toString();
                String password = ETPassword.getText().toString();
                //baseUrl = "http://localhost:8080/"; //10.0.2.2
                baseUrl = "http://192.168.1.51:8080/"; //10.0.2.2
                //String baseUrl = "http://10.0.2.2:5432/"; //emulator
                //baseUrl = "http://192.168.1.33:8080/"; //phone
                //baseUrl = "http://127.0.0.1:5432/"; //phone

                JSONObject object = new JSONObject();
                try {
                    object.put("userName", username);
                    object.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final RequestHandler requestHandler = new RequestHandler(baseUrl);

                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                requestHandler.setUrlResource("auth/signIn");
                requestHandler.setHttpMethod("POST");
                requestHandler.execute(new CallBack<String>() {
                    @Override
                    public void onSuccess(String result) {
                        startMainActivityAndClose(result);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            }
                        });
                    }
                    @Override
                    public void onFail(String message) {
                        ifFailure();
                    }
                }, object.toString());
            }
        });
    }

    private void ifFailure() {
        runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(getApplicationContext(),
                        "Invalid username or password!",  Toast.LENGTH_LONG);
                toast.show();
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        });
    }

    private void startMainActivityAndClose(String result) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(BLogin.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            Intent intent = new Intent(this, MainActivity.class);
            JSONObject jsonReturnObject = new JSONObject(result);
            intent.putExtra("token", jsonReturnObject.get("token").toString());
            intent.putExtra("url", baseUrl);
            JSONArray rolesArray = jsonReturnObject.getJSONArray("roles");
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < rolesArray.length(); i++) {
                list.add(rolesArray.get(i).toString());
            }
            intent.putStringArrayListExtra("roles", list);
            startActivity(intent);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
