package com.wxj.ui.forgetPassword;

import androidx.appcompat.app.AppCompatActivity;

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
import com.wxj.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        final EditText inputNumber=findViewById(R.id.input_number);
        final EditText inputName=findViewById(R.id.input_name);
        final EditText inputEmail=findViewById(R.id.input_email);
        final EditText inputNewPassword=findViewById(R.id.input_forget_new_password);
        Button updateForgetPassword=findViewById(R.id.button_change_forget_password);

        updateForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject=new JSONObject();
                try{
                    jsonObject.put("number",inputNumber.getText());
                    jsonObject.put("email",inputEmail.getText());
                    jsonObject.put("realname",inputName.getText());
                    jsonObject.put("password",inputNewPassword.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url="http://x.x.x.x:55555/user/updatePassword";
                RequestQueue requestQueue = Volley.newRequestQueue(ForgetPasswordActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String msg = jsonObject.getString("msg");
                            Log.d("msg", msg);
                            if (msg.equals("修改密码成功")) {
                                Toast.makeText(ForgetPasswordActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(ForgetPasswordActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}