package com.wxj.ui.changePassword;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wxj.R;
import com.wxj.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordFragment extends Fragment {

    private ChangePasswordViewModel mViewModel;

    EditText oldPassword;
    EditText newPassword;
    Button changePasswordButtton;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.change_password_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oldPassword=view.findViewById(R.id.input_old_password);
        newPassword=view.findViewById(R.id.input_new_password);
        changePasswordButtton=view.findViewById(R.id.change_password_button);
        changePasswordButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm=(InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null)
                    imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                String oldPasswordStr=oldPassword.getText().toString().trim();
                String newPasswordStr=newPassword.getText().toString().trim();
                if(oldPasswordStr.equals("")||newPasswordStr.equals("")){
                    Toast.makeText(getActivity(),"请填完信息",Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences read3=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                    String spNumber=read3.getString("number","");
                    String spPassword=read3.getString("password","");
                    if (oldPasswordStr.equals(spPassword)){
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("number",spNumber);
                            jsonObject.put("oldPassword",spPassword);
                            jsonObject.put("newPassword",newPasswordStr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String url="http://x.x.x.x:55555/user/changePassword";
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    String msg = jsonObject.getString("msg");
                                    Log.d("msg", msg);
                                    if (msg.equals("修改成功")) {
                                        Toast.makeText(getActivity(),"修改密码成功",Toast.LENGTH_SHORT).show();
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
                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }else{
                        Toast.makeText(getActivity(),"旧密码输入错误",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        // TODO: Use the ViewModel
    }

}