package com.wxj.ui.register;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class RegisterCompanyFragment extends Fragment {

    private EditText inputCompanyId;
    private EditText inputCompanyName;
    private Button registerCompany;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_company, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputCompanyId=view.findViewById(R.id.input_company_id);
        inputCompanyName=view.findViewById(R.id.input_company_name);
        registerCompany=view.findViewById(R.id.button_register_comany);

        registerCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                String number=sharedPreferences.getString("number","");
                if (number.equals("")){
                    Toast.makeText(getActivity(), "获取不到用户名", Toast.LENGTH_SHORT).show();
                }else{
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("adminId",number);
                        jsonObject.put("companyId",inputCompanyId.getText().toString().trim());
                        jsonObject.put("companyName",inputCompanyName.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String url="http://x.x.x.x:55555/company/register";
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                String msg = jsonObject.getString("msg");
                                Log.d("msg", msg);
                                if (msg.equals("注册公司成功")) {
                                    Toast.makeText(getContext(),"注册公司成功",Toast.LENGTH_SHORT).show();
                                }else  {
                                    Toast.makeText(getContext(),"注册公司失败",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }
            }
        });
    }
}