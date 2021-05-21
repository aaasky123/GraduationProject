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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class TurnNextActivity extends AppCompatActivity {

    private LinearLayout turnNextLinearLayout;
    private TextView selectedHostName;
    private TextView selectedHostId;
    private EditText turnName;
    private EditText turnDescription;
    private Button turnNextButton;
    public static TurnNextActivity turnNextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_next);
        turnNextActivity=this;
        turnNextLinearLayout=findViewById(R.id.host);
        selectedHostName=findViewById(R.id.next_work_flow_host_name);
        selectedHostId=findViewById(R.id.next_work_flow_host_user_id);
        turnName=findViewById(R.id.edit_turn_name);
        turnDescription=findViewById(R.id.edit_next_flow_description);
        turnNextButton=findViewById(R.id.button_turn_next);
        Bundle bundle=this.getIntent().getExtras();
        final Long workId=bundle.getLong("workId");
        final Long workFlowId=bundle.getLong("workFlowId");
        final String turnUserName=bundle.getString("turnUserName");
        Long turnUserNumber=bundle.getLong("turnUserNumber");
        selectedHostName.setText(turnUserName);
        selectedHostId.setText(turnUserNumber.toString());

        SharedPreferences sharedPreferences=getSharedPreferences("logindata", Context.MODE_PRIVATE);
        final String userId=sharedPreferences.getString("number","");

        turnNextLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1=new Bundle();
                bundle1.putString("number",userId);
                bundle1.putLong("workId",workId);
                bundle1.putLong("workFlowId",workFlowId);
                Intent intent=new Intent(TurnNextActivity.this,TurnUserActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();
            }
        });

        turnNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedHostName.getText() == null|selectedHostName.getText().equals("")){
                    Toast.makeText(TurnNextActivity.this, "请选择移交人", Toast.LENGTH_SHORT).show();
                }else if(turnDescription.getText()==null|turnDescription.getText().equals("")){
                    Toast.makeText(TurnNextActivity.this, "请输入下个流程的工作描述", Toast.LENGTH_SHORT).show();
                }else if(turnName.getText()==null|turnName.getText().equals("")){
                    Toast.makeText(TurnNextActivity.this, "请输入下个流程的名称", Toast.LENGTH_SHORT).show();
                }
                else {
                    JSONObject jsonObject=new JSONObject();
                    try{
                        jsonObject.put("workId",workId);
                        jsonObject.put("workFlowId",workFlowId);
                        jsonObject.put("hostId",selectedHostId.getText());
                        jsonObject.put("turnReason",turnDescription.getText());
                        jsonObject.put("endTime",String.valueOf(System.currentTimeMillis()/1000));
                        jsonObject.put("workFlowName",turnName.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String url="http://x.x.x.x:55555/workFlow/turnWorkFlow";
                    RequestQueue requestQueue = Volley.newRequestQueue(TurnNextActivity.this);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                String msg = jsonObject.getString("msg");
                                Log.d("msg", msg);
                                if (msg.equals("工作移交成功")) {
                                    Toast.makeText(TurnNextActivity.this,"工作移交成功",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(TurnNextActivity.this,"工作移交失败",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(TurnNextActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                    finish();
                }

            }
        });
    }
}