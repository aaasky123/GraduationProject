package com.wxj.ui.work;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.wxj.R;
import com.wxj.adapter.OnWorkingAdapter;
import com.wxj.adapter.WorkFlowAdapter;
import com.wxj.entity.Work;
import com.wxj.entity.WorkFlow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WorkFlowActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<WorkFlow> workFlowArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_flow);
        final Bundle bundle=WorkFlowActivity.this.getIntent().getExtras();
        final Long workId=bundle.getLong("workId");
        int viewpagerId=bundle.getInt("viewpagerId");
        listView=findViewById(R.id.work_flow_list);
        workFlowArrayList=new ArrayList<>();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("workId",workId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String url="http://x.x.x.x:55555/workFlow/queryWorkFlow";
        RequestQueue requestQueue = Volley.newRequestQueue(WorkFlowActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String msg = jsonObject.getString("msg");
                    Log.d("msg", msg);
                    if (msg.equals("查询成功")) {
                        JSONArray detail=jsonObject.getJSONArray("detail");
                        for(int i=0;i<detail.length();i++){
                            JSONObject onWorkingWorkList=detail.getJSONObject(i);
                            workFlowArrayList.add(new WorkFlow(
                                    onWorkingWorkList.getLong("workId"),
                                    onWorkingWorkList.getLong("workFlowId"),
                                    onWorkingWorkList.getInt("companyId"),
                                    onWorkingWorkList.getLong("hostId"),
                                    onWorkingWorkList.getString("turnReason"),
                                    onWorkingWorkList.getString("startTime"),
                                    onWorkingWorkList.getString("endTime"),
                                    onWorkingWorkList.getInt("state"),
                                    onWorkingWorkList.getString("workFlowName")
                            ));
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                WorkFlowAdapter adapter=new WorkFlowAdapter(
                        WorkFlowActivity.this,R.layout.activity_work_flow,workFlowArrayList
                );
                listView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WorkFlowActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long workFlowId=workFlowArrayList.get(position).getWorkFlowId();
                Integer state=workFlowArrayList.get(position).getState();
                Bundle detailQueryData=new Bundle();
                detailQueryData.putLong("workId",workId);
                detailQueryData.putLong("workFlowId",workFlowId);
                detailQueryData.putInt("state",state);
                Intent intent=new Intent(WorkFlowActivity.this,WorkFlowDetailActivity.class);
                intent.putExtras(detailQueryData);
                startActivity(intent);
            }
        });
    }
}