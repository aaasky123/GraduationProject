package com.wxj.ui.work;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.helloworld.library.MiddleDialogConfig;
import com.wxj.R;
import com.wxj.adapter.HelperVOAdapter;
import com.wxj.adapter.companyContactAdapter;
import com.wxj.entity.User;
import com.wxj.vo.HelperVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HelperManageActivity extends AppCompatActivity {

    private Button addHelperButton;
    private ListView listView;
    private ArrayList<HelperVo> helperArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_manage);
        addHelperButton=findViewById(R.id.add_helper);
        listView=findViewById(R.id.helper_list);
        helperArrayList=new ArrayList<>();
        final Bundle bundle=this.getIntent().getExtras();
        final Long workId=bundle.getLong("workId");
        final Long workFlowId=bundle.getLong("workFlowId");
        final String number=bundle.getString("number");
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("workId",workId);
            jsonObject.put("workFlowId",workFlowId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String url="http://x.x.x.x:55555/helper/queryHelper";
        RequestQueue requestQueue = Volley.newRequestQueue(HelperManageActivity.this);
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
                            JSONObject helperObject=detail.getJSONObject(i);
                            helperArrayList.add(new HelperVo(
                                    helperObject.getInt("id"),
                                    helperObject.getLong("协办人id"),
                                    helperObject.getLong("工作id"),
                                    helperObject.getLong("工作流程id"),
                                    helperObject.getInt("公司id"),
                                    helperObject.getString("真实姓名")
                            ));
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HelperVOAdapter adapter=new HelperVOAdapter(
                        HelperManageActivity.this,R.layout.activity_helper_manage,helperArrayList
                );
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                        new MiddleDialogConfig().builder(HelperManageActivity.this)
                                .setTitle("删除"+helperArrayList.get(position).getRealname())
                                .setContent("确定删除吗？")
                                .setRightCallBack(new MiddleDialogConfig.RightCallBack(){
                                    @Override
                                    public void rightBtn(String cont) {
                                        JSONObject jsonObject1=new JSONObject();
                                        try{
                                            jsonObject1.put("id",helperArrayList.get(position).getId());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        String url="http://x.x.x.x:55555/helper/deleteHelper";
                                        RequestQueue requestQueue1 = Volley.newRequestQueue(HelperManageActivity.this);
                                        JsonObjectRequest jsonObjectRequest1=new JsonObjectRequest(Request.Method.POST, url, jsonObject1, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject jsonObject1) {
                                                try{
                                                    String msg = jsonObject1.getString("msg");
                                                    Log.d("msg", msg);
                                                    if (msg.equals("删除协办人成功")) {
                                                        Toast.makeText(HelperManageActivity.this, "删除协办人成功", Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        Toast.makeText(HelperManageActivity.this, "删除协办人失败", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                Toast.makeText(HelperManageActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        requestQueue1.add(jsonObjectRequest1);
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
                Toast.makeText(HelperManageActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

        addHelperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1=new Bundle();
                bundle1.putLong("workId",workId);
                bundle1.putLong("workFlowId",workFlowId);
                bundle1.putString("number",number);
                Intent intent=new Intent(HelperManageActivity.this,AddHelperActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }
}