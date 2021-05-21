package com.wxj.ui.userinformation;

import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.wxj.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInformationFragment extends Fragment {

    private UserInformationViewModel userInformationViewModel;
    private TextView realname;
    private TextView email;
    private TextView company;
    private TextView employeeId;
    private Button editInformation;

    public UserInformationFragment() {
    }


    public static UserInformationFragment newInstance() {
        return new UserInformationFragment();
    }

    //@Override
    //public void onCreate(@Nullable Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
    //}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getParentFragmentManager().setFragmentResultListener("user_information_detail", this, new FragmentResultListener() {
        //    @Override
        //    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
        //        // We use a String here, but any type that can be put in
        //        String userRealName = bundle.getString("realName");
        //        String userEmail = bundle.getString("email");
        //        String userCompanyId = bundle.getString("company_id");
        //        String userEmployeeId = bundle.getString("employee_id");
        //        // Do something with the result
        //        realname=getView().findViewById(R.id.realName);
        //        email=getView().findViewById(R.id.email);
        //        company=getView().findViewById(R.id.company);
        //        employeeId=getView().findViewById(R.id.employee);
        //        realname.setText(userRealName);
        //        email.setText(userEmail);
        //        company.setText(userCompanyId);
        //        employeeId.setText(userEmployeeId);
        //    }
        //});
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.user_information_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        realname=view.findViewById(R.id.realName);
        email=view.findViewById(R.id.email);
        company=view.findViewById(R.id.company);
        employeeId=view.findViewById(R.id.employee);
        SharedPreferences read=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put("number",read.getString("number",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url="http://x.x.x.x:55555/user/userInformation";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String msg = jsonObject.getString("msg");
                    Log.d("msg", msg);
                    if (msg.equals("查询成功")) {
                        JSONObject detail = jsonObject.getJSONObject("detail");
                        String realName = detail.getString("真实姓名");
                        String email2 = detail.getString("邮件");
                        String company_id=detail.getString("公司id");
                        String employee_id=detail.getString("雇员id");
                        realname.setText(realName);
                        email.setText(email2);
                        company.setText(company_id);
                        employeeId.setText(employee_id);
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
        editInformation=view.findViewById(R.id.changeInformation);
        editInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.navigation_change_user_information);
            }
        });
    }
}