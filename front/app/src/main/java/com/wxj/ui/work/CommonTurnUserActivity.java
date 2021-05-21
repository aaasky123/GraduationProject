package com.wxj.ui.work;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wxj.R;
import com.wxj.adapter.companyContactAdapter;
import com.wxj.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Stack;

public class CommonTurnUserActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<User> commonTurnUserArrayList;
    private static Stack<Activity> activityStack = new Stack<Activity>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_turn_user);
        listView=findViewById(R.id.common_turn_user);
        Bundle bundle=this.getIntent().getExtras();
        String number=bundle.getString("number");
        final Long workId=bundle.getLong("workId");
        final Long workFlowId=bundle.getLong("workFlowId");
        commonTurnUserArrayList=new ArrayList<>();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("number",number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String url="http://x.x.x.x:55555/contactPeople/queryCommonTurnUser";
        RequestQueue requestQueue = Volley.newRequestQueue(CommonTurnUserActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String msg = jsonObject.getString("msg");
                    Log.d("msg", msg);
                    if (msg.equals("查询成功")) {
                        JSONArray detail=jsonObject.getJSONArray("detail");
                        for(int i=0;i<detail.length();i++){
                            JSONObject commonTurnUserList=detail.getJSONObject(i);
                            commonTurnUserArrayList.add(new User(
                                    commonTurnUserList.getString("realname"),
                                    commonTurnUserList.getInt("employeeId"),
                                    commonTurnUserList.getLong("number"),
                                    commonTurnUserList.getInt("companyId")

                            ));
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                companyContactAdapter adapter=new companyContactAdapter(
                        CommonTurnUserActivity.this,R.layout.activity_common_turn_user,commonTurnUserArrayList
                );
                listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String turnUserName=commonTurnUserArrayList.get(position).getRealname();
                        Long turnUserNumber=commonTurnUserArrayList.get(position).getNumber();
                        Bundle bundle1=new Bundle();
                        bundle1.putString("turnUserName",turnUserName);
                        bundle1.putLong("turnUserNumber",turnUserNumber);
                        bundle1.putLong("workId",workId);
                        bundle1.putLong("workFlowId",workFlowId);
                        Intent intent=new Intent(CommonTurnUserActivity.this,TurnNextActivity.class);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                        finish();
                        if(TurnUserActivity.turnUserActivity!=null){
                            TurnUserActivity.turnUserActivity.finish();
                        }
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(CommonTurnUserActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }


}