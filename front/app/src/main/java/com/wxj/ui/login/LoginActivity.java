package com.wxj.ui.login;

import android.Manifest;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wxj.MainActivity;
import com.wxj.R;
import com.wxj.ui.forgetPassword.ForgetPasswordActivity;
import com.wxj.ui.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        request_permissions();
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button register=findViewById(R.id.register);
        final Button forgetPassword=findViewById(R.id.forget_password);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        SharedPreferences sharedPreferences1=getSharedPreferences("logindata",Context.MODE_PRIVATE);
        String numberStr=sharedPreferences1.getString("number","");
        String passwordStr=sharedPreferences1.getString("password","");
        if(numberStr.equals("")){

        }else{
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("number",numberStr);
            intent.putExtra("password",passwordStr);
            startActivity(intent);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr=usernameEditText.getText().toString().trim();
                String passwordStr=passwordEditText.getText().toString().trim();
                if(usernameStr.equals("")||passwordStr.equals("")){
                    Toast.makeText(LoginActivity.this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    JSONObject jsonObject=new JSONObject();
                    try{
                        jsonObject.put("number",usernameStr);
                        jsonObject.put("password",passwordStr);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    String url="http://x.x.x.x:55555/user/login";
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                String msg = jsonObject.getString("msg");
                                Log.d("msg", msg);
                                if (msg.equals("登录成功")) {
                                    JSONObject detail = jsonObject.getJSONObject("detail");
                                    String number = detail.getString("number");
                                    String password = detail.getString("password");

                                    sharedPreferences=getSharedPreferences("logindata",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("number",number);
                                    editor.putString("password",password);
                                    editor.commit();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("number", number);
                                    intent.putExtra("password", password);
                                    startActivity(intent);
                                } else if (msg.equals("用户名或密码错误")) {
                                    Toast.makeText(LoginActivity.this, "用户名密码有误", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(LoginActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }
            }
        });

        register.setClickable(true);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        forgetPassword.setClickable(true);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void request_permissions() {
        // 创建一个权限列表，把需要使用而没用授权的的权限存放在这里
        List<String> permissionList = new ArrayList<>();

        // 判断权限是否已经授予，没有就把该权限添加到列表中
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WAKE_LOCK);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.INTERNET);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_WIFI_STATE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_MEDIA_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_MEDIA_LOCATION);
        }

        // 如果列表为空，就是全部权限都获取了，不用再次获取了。不为空就去申请权限
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]), 1002);
        } else {
            Toast.makeText(this, "多个权限你都有了，不用再次申请", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1002:
                // 1002请求码对应的是申请多个权限
                if(grantResults.length>0){
                    // 因为是多个权限，所以需要一个循环获取每个权限的获取情况
                    for(int i=0;i<grantResults.length;i++){
                        // PERMISSION_DENIED 这个值代表是没有授权，我们可以把被拒绝授权的权限显示出来
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                            Toast.makeText(LoginActivity.this, permissions[i] + "权限被拒绝了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
}