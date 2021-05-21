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
import android.widget.LinearLayout;
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
import com.wxj.entity.Work;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TurnUserActivity extends AppCompatActivity {

    private Button buttonSelectFromCommonTurnUser;
    private ListView listView;
    private ArrayList<User> companyMateArrayList;
    public static TurnUserActivity turnUserActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        turnUserActivity=this;
        setContentView(R.layout.activity_turn_user);
        listView=findViewById(R.id.company_mate);
        buttonSelectFromCommonTurnUser=findViewById(R.id.buttonTurnUser);
        companyMateArrayList=new ArrayList<>();

        Bundle bundle=this.getIntent().getExtras();
        final String number=bundle.getString("number");
        final Long workId=bundle.getLong("workId");
        final Long workFlowId=bundle.getLong("workFlowId");
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("number",number);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        final String url="http://x.x.x.x:55555/user/queryCompanyAllUser";
        RequestQueue requestQueue = Volley.newRequestQueue(TurnUserActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String msg = jsonObject.getString("msg");
                    Log.d("msg", msg);
                    if (msg.equals("查询成功")) {
                        JSONArray detail = jsonObject.getJSONArray("detail");
                        System.out.println(detail);
                        for (int i = 0; i < detail.length(); i++) {
                            JSONObject companyContactObject = detail.getJSONObject(i);
                            companyMateArrayList.add(new User(
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
                companyContactAdapter adapter = new companyContactAdapter(
                        TurnUserActivity.this, R.layout.activity_turn_user, companyMateArrayList
                );
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String turnUserName=companyMateArrayList.get(position).getRealname();
                        Long turnUserNumber=companyMateArrayList.get(position).getNumber();
                        Bundle bundle1=new Bundle();
                        bundle1.putString("turnUserName",turnUserName);
                        bundle1.putLong("turnUserNumber",turnUserNumber);
                        bundle1.putLong("workId",workId);
                        bundle1.putLong("workFlowId",workFlowId);
                        Intent intent=new Intent(TurnUserActivity.this,TurnNextActivity.class);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                        finish();
                    }
                });



            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(TurnUserActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

        buttonSelectFromCommonTurnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1=new Bundle();
                bundle1.putString("number",number);
                bundle1.putLong("workId",workId);
                bundle1.putLong("workFlowId",workFlowId);
                Intent intent=new Intent(TurnUserActivity.this,CommonTurnUserActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(TurnNextActivity.turnNextActivity!=null){
            Intent intent=new Intent(TurnUserActivity.this,TurnNextActivity.class);
            Bundle bundle=this.getIntent().getExtras();
            Long workId=bundle.getLong("workId");
            Long workFlowId=bundle.getLong("workFlowId");
            Bundle bundle1=new Bundle();
            bundle1.putLong("workId",workId);
            bundle1.putLong("workFlowId",workFlowId);
            bundle1.putString("turnUserName","");
            intent.putExtras(bundle1);
            startActivity(intent);
            finish();
        }
    }
}