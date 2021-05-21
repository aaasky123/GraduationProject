package com.wxj.ui.work;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.helloworld.library.MiddleDialogConfig;
import com.helloworld.library.utils.DialogEnum;
import com.wxj.R;
import com.wxj.adapter.companyContactAdapter;
import com.wxj.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddHelperActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<User> addHelperArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_helper);
        listView=findViewById(R.id.add_helper_list);
        addHelperArrayList=new ArrayList<>();
        Bundle bundle=this.getIntent().getExtras();
        final Long workId=bundle.getLong("workId");
        final Long workFlowId=bundle.getLong("workFlowId");
        final String number=bundle.getString("number");
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("number",number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String url="http://x.x.x.x:55555/user/queryCompanyAllUser";
        RequestQueue requestQueue = Volley.newRequestQueue(AddHelperActivity.this);
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
                            addHelperArrayList.add(new User(
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
                        AddHelperActivity.this,R.layout.activity_add_helper,addHelperArrayList
                );
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        new MiddleDialogConfig().builder(AddHelperActivity.this)
                                .setTitle("添加"+addHelperArrayList.get(position).getRealname())
                                .setContent("确定添加吗？")
                                .setRightCallBack(new MiddleDialogConfig.RightCallBack() {
                                    @Override
                                    public void rightBtn(String cont) {
                                        JSONObject jsonObject1=new JSONObject();
                                        try{
                                            jsonObject1.put("workId",workId);
                                            jsonObject1.put("workFlowId",workFlowId);
                                            jsonObject1.put("helperUserId",addHelperArrayList.get(position).getNumber());
                                            jsonObject1.put("companyId",addHelperArrayList.get(position).getCompanyId());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        String  url="http://x.x.x.x:55555/helper/addHelper";
                                        RequestQueue requestQueue = Volley.newRequestQueue(AddHelperActivity.this);
                                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject1, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject jsonObject) {
                                                try {
                                                    String msg = jsonObject.getString("msg");
                                                    Log.d("msg", msg);
                                                    if (msg.equals("添加协办人成功")) {
                                                        Toast.makeText(AddHelperActivity.this, "添加协办人成功", Toast.LENGTH_SHORT).show();

                                                    }else {
                                                        Toast.makeText(AddHelperActivity.this, "添加协办人失败", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                Toast.makeText(AddHelperActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddHelperActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}