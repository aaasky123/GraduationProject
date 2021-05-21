package com.wxj.ui.contactsList;

import androidx.annotation.ContentView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.helloworld.library.MiddleDialogConfig;
import com.wxj.R;
import com.wxj.adapter.companyContactAdapter;
import com.wxj.entity.User;
import com.wxj.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactsListFragment extends Fragment {

    private ContactsListViewModel mViewModel;
    private BaseAdapter baseAdapter;
    private ListView listView;
    private ArrayList<User> userArrayList;

    public static ContactsListFragment newInstance() {
        return new ContactsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { return inflater.inflate(R.layout.contacts_list_fragment, container, false); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=view.findViewById(R.id.company_contacts_list);
        userArrayList=new ArrayList<>();
        SharedPreferences read2=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        String number=read2.getString("number","");
        if (number.equals("")){
            Toast.makeText(getActivity(), "获取不到用户名", Toast.LENGTH_SHORT).show();
        }else{
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("number",number);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String url="http://x.x.x.x:55555/user/queryCompanyAllUser";
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        String msg = jsonObject.getString("msg");
                        Log.d("msg", msg);
                        if (msg.equals("查询成功")) {
                            JSONArray detail=jsonObject.getJSONArray("detail");
                            System.out.println(detail);
                            for(int i=0;i<detail.length();i++){
                                JSONObject companyContactObject=detail.getJSONObject(i);
                                userArrayList.add(new User(
                                        companyContactObject.getString("realname"),
                                        companyContactObject.getInt("employeeId"),
                                        companyContactObject.getLong("number"),
                                        companyContactObject.getInt("companyId")
                                ));
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    companyContactAdapter adapter=new companyContactAdapter(
                            getContext(),R.layout.contacts_list_fragment,userArrayList
                    );
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            new MiddleDialogConfig().builder(getContext())
                                    .setTitle("添加"+userArrayList.get(position).getRealname())
                                    .setContent("确定添加吗？")
                                    .setRightCallBack(new MiddleDialogConfig.RightCallBack() {
                                        @Override
                                        public void rightBtn(String cont) {
                                            Long mateNumber=userArrayList.get(position).getNumber();
                                            Integer companyId=userArrayList.get(position).getCompanyId();
                                            SharedPreferences read2=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                                            String loginUserNumber=read2.getString("number","");
                                            final JSONObject jsonObject1=new JSONObject();
                                            try {
                                                jsonObject1.put("turnUserId",mateNumber);
                                                jsonObject1.put("userId",loginUserNumber);
                                                jsonObject1.put("companyId",companyId);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            final String url="http://x.x.x.x:55555/contactPeople/addCommonTurnUser";
                                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject1, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject jsonObject) {
                                                    try {
                                                        String msg= jsonObject1.getString("msg");
                                                        if(msg.equals("添加常用移交人成功")){
                                                            Toast.makeText(getActivity(),"添加常用移交人成功",Toast.LENGTH_SHORT).show();
                                                        }else if(msg.equals("添加常用移交人失败")){
                                                            Toast.makeText(getActivity(),"添加常用移交人失败",Toast.LENGTH_SHORT).show();
                                                        }else if(msg.equals("已添加此常用移交人")){
                                                            Toast.makeText(getActivity(),"已添加此常用移交人",Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError volleyError) {
                                                    Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            requestQueue.add(jsonObjectRequest);
                                        }
                                    })
                                    .setLeftCallBack(new MiddleDialogConfig.LeftCallBack() {
                                        @Override
                                        public void leftBtn(String cont) {
                                        }
                                    }).show();
                            
                        }
                    });

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
}