package com.wxj.ui.work;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.documentfile.provider.DocumentFile;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.helloworld.library.BottomMenuFragment;
import com.helloworld.library.MiddleDialogConfig;
import com.wxj.R;
import com.wxj.adapter.WorkFlowAdapter;
import com.wxj.adapter.WorkFlowMarkAdapter;
import com.wxj.entity.Work;
import com.wxj.entity.WorkFlow;
import com.wxj.entity.WorkFlowMark;
import com.wxj.ui.register.RegisterActivity;
import com.wxj.ui.registerResult.RegisterResultActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class WorkFlowDetailActivity extends AppCompatActivity {


    private TextView workFlowInformation;
    private ListView listView;
    private ArrayList<WorkFlowMark> workNoteArrayList;
    private List<String> list = new ArrayList<>();
    private LinearLayout file;
    private LinearLayout operation;
    private int a;
    private int b;
    private int c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_flow_detail);

        file=findViewById(R.id.work_flow_file_resource);
        operation=findViewById(R.id.operation);
        Bundle bundle=this.getIntent().getExtras();
        final Long workId=bundle.getLong("workId");
        final Long workFlowId=bundle.getLong("workFlowId");
        final int state=bundle.getInt("state");
        listView=findViewById(R.id.work_flow_note_list);
        workFlowInformation=findViewById(R.id.work_flow_information);
        workNoteArrayList=new ArrayList<>();
        SharedPreferences sharedPreferences=getSharedPreferences("logindata", Context.MODE_PRIVATE);
        final String permissionUserId=sharedPreferences.getString("number","");
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("workId",workId);
            jsonObject.put("workFlowId",workFlowId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url="http://x.x.x.x:55555/workFlow/queryHostId";
        RequestQueue requestQueue = Volley.newRequestQueue(WorkFlowDetailActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String msg = jsonObject.getString("msg");
                    Log.d("msg", msg);
                    if (msg.equals("???????????????id??????")) {
                        String number = jsonObject.getString("detail");
                        if(!number.equals(permissionUserId)){//?????????????????????
                            a=1;
                        }else {
                            a=0;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WorkFlowDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);





        JSONObject jsonObject2=new JSONObject();
        try {
            jsonObject2.put("userId",permissionUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url2="http://x.x.x.x:55555/permission/queryPermission";
        RequestQueue requestQueue2 = Volley.newRequestQueue(WorkFlowDetailActivity.this);
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, url2, jsonObject2, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject2) {
                try {
                    String msg = jsonObject2.getString("msg");
                    Log.d("msg", msg);
                    if (msg.equals("??????????????????")) {
                        String permissionValue = jsonObject2.getString("detail");
                        if(permissionValue.equals(0)|permissionValue.equals(1)){//???????????????
                            b =1;
                        }else{
                            b=0;
                        }
                        System.out.println(b);
                        System.out.println(b);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WorkFlowDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue2.add(jsonObjectRequest2);

        String url3="http://x.x.x.x:55555/workFlow/queryInformation";
        RequestQueue requestQueue3 = Volley.newRequestQueue(WorkFlowDetailActivity.this);
        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.POST, url3, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject3) {
                try {
                    String msg = jsonObject3.getString("msg");
                    Log.d("msg", msg);
                    if (msg.equals("??????????????????????????????")) {
                        String workFlowDescription=jsonObject3.getString("detail");
                        workFlowInformation.setText(workFlowDescription);
                    }else{
                        Toast.makeText(WorkFlowDetailActivity.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WorkFlowDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue3.add(jsonObjectRequest3);


        String url4="http://x.x.x.x:55555/workFlowMark/queryWorkFlowMark";
        RequestQueue requestQueue4 = Volley.newRequestQueue(WorkFlowDetailActivity.this);
        JsonObjectRequest jsonObjectRequest4 = new JsonObjectRequest(Request.Method.POST, url4, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject4) {
                try {
                    String msg = jsonObject4.getString("msg");
                    Log.d("msg", msg);
                    if (msg.equals("??????????????????")) {
                        JSONArray detail=jsonObject4.getJSONArray("detail");
                        for(int i=0;i<detail.length();i++){
                            JSONObject onWorkingWorkList=detail.getJSONObject(i);
                            workNoteArrayList.add(new WorkFlowMark(
                                    onWorkingWorkList.getLong("id"),
                                    onWorkingWorkList.getLong("workId"),
                                    onWorkingWorkList.getInt("workFlowId"),
                                    onWorkingWorkList.getLong("createUserId"),
                                    onWorkingWorkList.getString("markContent"),
                                    onWorkingWorkList.getLong("markTime")

                            ));
                        }
                    }else{
                        Toast.makeText(WorkFlowDetailActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                WorkFlowMarkAdapter adapter=new WorkFlowMarkAdapter(
                        WorkFlowDetailActivity.this, R.id.work_flow_note_list, workNoteArrayList);
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WorkFlowDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue4.add(jsonObjectRequest4);

        operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();

            }


            public void showBottomDialog() {
                list.clear();
                list.add("???????????????");
                list.add("???????????????");
                list.add("??????");
                list.add("??????");
                list.add("????????????");
                list.add("????????????");
                if(state==1){
                    list.remove("???????????????");
                    list.remove("???????????????");
                    list.remove("??????");
                    list.remove("??????");
                    list.remove("????????????");
                    list.remove("????????????");
                } else if(a==1){
                    list.remove("???????????????");
                    list.remove("???????????????");
                    list.remove("??????");
                    list.remove("??????");
                }else if(b==1|b==0){
                    list.remove("????????????");
                }
                new BottomMenuFragment(WorkFlowDetailActivity.this)
                        .setContentSize(16)
                        .setCancelTextTitle("??????")
                        .setCancelTextSize(16)
                        .setCancelTextColor("#454545")
                        .setCancelTextHeight(45)
                        .setCancelTextMarginTop(20)
                        .setContentColor("#454545")
                        .addMenuItems(list)
                        .setBackgroundColor("#ebebeb")
                        .setCancelPadding(0, 0, 0, 0)
                        .setSizeOneShape(R.drawable.bottom_menu_mid_selector)
                        .setTopShape(R.drawable.bottom_menu_mid_selector)
                        .setMiddleShape(R.drawable.bottom_menu_mid_selector)
                        .setBottomShape(R.drawable.bottom_menu_mid_selector)
                        .setCancelShape(R.drawable.bottom_menu_mid_selector)
                        .setOnItemClickListener(new BottomMenuFragment.OnItemClickListener() {
                            @Override
                            public void onItemClick(TextView menu, int position) {
                                switch (menu.getText().toString()){
                                    case "???????????????" :
                                        Bundle turnNextData=new Bundle();
                                        turnNextData.putLong("workId",workId);
                                        turnNextData.putLong("workFlowId",workFlowId);
                                        Intent intent=new Intent(WorkFlowDetailActivity.this,TurnNextActivity.class);
                                        intent.putExtras(turnNextData);
                                        startActivity(intent);
                                        break;
                                    case "???????????????" :
                                        Bundle helpManageData=new Bundle();
                                        helpManageData.putLong("workId",workId);
                                        helpManageData.putLong("workFlowId",workFlowId);
                                        helpManageData.putString("number",permissionUserId);
                                        Intent intent2=new Intent(WorkFlowDetailActivity.this,HelperManageActivity.class);
                                        intent2.putExtras(helpManageData);
                                        startActivity(intent2);
                                        break;
                                    case "??????" :
                                        new MiddleDialogConfig().builder(WorkFlowDetailActivity.this)
                                                .setTitle("??????")
                                                .setContent("??????????????????????????????????????????")
                                                .setRight("??????")
                                                .setRightCallBack(new MiddleDialogConfig.RightCallBack() {
                                                    @Override
                                                    public void rightBtn(String cont) {
                                                        String endTime =String.valueOf(System.currentTimeMillis()/1000);
                                                        JSONObject jsonObject=new JSONObject();
                                                        try {
                                                            jsonObject.put("workId",workId);
                                                            jsonObject.put("workFLowId",workFlowId);
                                                            jsonObject.put("endTime",endTime);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        String url="http://x.x.x.x:55555/work/finishWork";
                                                        RequestQueue requestQueue = Volley.newRequestQueue(WorkFlowDetailActivity.this);
                                                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject jsonObject) {
                                                                try {
                                                                    String msg = jsonObject.getString("msg");
                                                                    Log.d("msg", msg);
                                                                    if (msg.equals("??????????????????")) {
                                                                        Toast.makeText(WorkFlowDetailActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                                                    }else{
                                                                        Toast.makeText(WorkFlowDetailActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError volleyError) {
                                                                Toast.makeText(WorkFlowDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        requestQueue.add(jsonObjectRequest);
                                                    }
                                                }).show();
                                        //Bundle finishData=new Bundle();
                                        //finishData.putLong("workId",workId);
                                        //finishData.putLong("workFlowId",workFlowId);
                                        //Intent intent3=new Intent(WorkFlowDetailActivity.this,FinishActivity.class);
                                        //intent3.putExtras(finishData);
                                        //startActivity(intent3);
                                        break;
                                    case "??????" :
                                        new MiddleDialogConfig().builder(WorkFlowDetailActivity.this)
                                                .setTitle("??????")
                                                .setContent("???????????????????????????????????????????????????")
                                                .setRight("??????")
                                                .setRightCallBack(new MiddleDialogConfig.RightCallBack() {
                                                    @Override
                                                    public void rightBtn(String cont) {
                                                        String endTime =String.valueOf(System.currentTimeMillis()/1000);
                                                        JSONObject jsonObject=new JSONObject();
                                                        try {
                                                            jsonObject.put("workId",workId);
                                                            jsonObject.put("workFLowId",workFlowId);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        String url="http://x.x.x.x:55555/workFlow/revokeWorkFlow";
                                                        RequestQueue requestQueue = Volley.newRequestQueue(WorkFlowDetailActivity.this);
                                                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject jsonObject) {
                                                                try {
                                                                    String msg = jsonObject.getString("msg");
                                                                    Log.d("msg", msg);
                                                                    if (msg.equals("??????????????????")) {
                                                                        Toast.makeText(WorkFlowDetailActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                                                    }else{
                                                                        Toast.makeText(WorkFlowDetailActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError volleyError) {
                                                                Toast.makeText(WorkFlowDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        requestQueue.add(jsonObjectRequest);
                                                    }
                                                }).show();
                                        //Bundle revokeData=new Bundle();
                                        //revokeData.putLong("workId",workId);
                                        //revokeData.putLong("workFlowId",workFlowId);
                                        //Intent intent4=new Intent(WorkFlowDetailActivity.this,RevokeActivity.class);
                                        //intent4.putExtras(revokeData);
                                        //startActivity(intent4);
                                        break;
                                    case "????????????" :
                                        Bundle markData=new Bundle();
                                        markData.putLong("workId",workId);
                                        markData.putLong("workFlowId",workFlowId);
                                        Intent intent5=new Intent(WorkFlowDetailActivity.this,MarkActivity.class);
                                        intent5.putExtras(markData);
                                        startActivity(intent5);
                                        break;
                                    case "????????????" :
                                        Bundle fileData=new Bundle();
                                        fileData.putLong("workId",workId);
                                        fileData.putLong("workFlowId",workFlowId);
                                        Intent intent6=new Intent(WorkFlowDetailActivity.this,FileActivity.class);
                                        intent6.putExtras(fileData);
                                        startActivity(intent6);
                                        break;

                                }
                            }
                        })
                        .show();
            }
        });

        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle fileData=new Bundle();
                fileData.putLong("workId",workId);
                fileData.putLong("workFlowId",workFlowId);
                Intent intent7=new Intent(WorkFlowDetailActivity.this,FileListActivity.class);
                intent7.putExtras(fileData);
                startActivity(intent7);
            }
        });


    }



}