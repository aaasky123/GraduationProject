package com.wxj.ui.userinformation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ChangeUserInformationFragment extends Fragment {

    private EditText editRealName;
    private EditText editEmail;
    private Button saveInformation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_user_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("user_information_detail", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle){
                // We use a String here, but any type that can be put in
                String userRealName = bundle.getString("realName");
                String userEmail = bundle.getString("email");
                String userCompanyId = bundle.getString("company_id");
                String userEmployeeId = bundle.getString("employee_id");
                // Do something with the result
                editRealName=getView().findViewById(R.id.editRealName);
                editEmail=getView().findViewById(R.id.editUserEmail);
                editRealName.setText(userRealName);
                editEmail.setText(userEmail);
            }
        });
        saveInformation=view.findViewById(R.id.saveInformation);
        saveInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences read=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("number",read.getString("number",""));
                    jsonObject.put("realname",editRealName.getText());
                    jsonObject.put("email",editEmail.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url="http://x.x.x.x:55555/user/updateInformation";
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String msg = jsonObject.getString("msg");
                            Log.d("msg", msg);
                            if (msg.equals("更改个人信息成功")) {
                                Toast.makeText(getContext(), "更改个人信息成功", Toast.LENGTH_SHORT).show();
                                getActivity().getFragmentManager().popBackStack();
                                //JSONObject detail = jsonObject.getJSONObject("detail");
                                //String realName = detail.getString("真实姓名");
                                //String email2 = detail.getString("邮件");
                                //String company_id=detail.getString("公司id");
                                //String employee_id=detail.getString("雇员id");
                                //realname.setText(realName);
                                //email.setText(email2);
                                //company.setText(company_id);
                                //employeeId.setText(employee_id);
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
        });
    }
}