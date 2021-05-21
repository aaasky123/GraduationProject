package com.wxj.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wxj.MainActivity;
import com.wxj.R;
import com.wxj.ui.login.LoginActivity;
import com.wxj.ui.registerResult.RegisterResultActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText realNameText=findViewById(R.id.editTextTextPersonName);
        final EditText emailText=findViewById(R.id.editTextTextEmailAddress2);
        final EditText passwordText=findViewById(R.id.editTextTextPassword);
        Button regiseterButton=findViewById(R.id.button_register);

        regiseterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String realNameStr=realNameText.getText().toString().trim();
                String emailStr=emailText.getText().toString().trim();
                String passwordStr=passwordText.getText().toString().trim();
                if(realNameStr.equals("")||emailStr.equals("")||passwordStr.equals("")){
                    Toast.makeText(RegisterActivity.this, "请完整填写注册信息", Toast.LENGTH_SHORT).show();
                }else{
                    JSONObject jsonObject=new JSONObject();
                    try{
                        jsonObject.put("realname",realNameStr);
                        jsonObject.put("email",emailStr);
                        jsonObject.put("password",passwordStr);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    String url="http://x.x.x.x:55555/user/register";
                    RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                String msg = jsonObject.getString("msg");
                                Log.d("msg", msg);
                                if (msg.equals("注册成功")) {
                                    String number = jsonObject.getString("detail");
                                    System.out.println(number);
                                    //String number = detail.toString();
                                    Intent intent=new Intent(RegisterActivity.this, RegisterResultActivity.class);
                                    intent.putExtra("number",number);
                                    startActivity(intent);
                                    finish();
                                } else if (msg.equals("注册失败")) {
                                    Toast.makeText(RegisterActivity.this, "注册失败，若有问题请反馈", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(RegisterActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }
            }
        });
    }
}