package com.wxj.ui.work;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wxj.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MarkActivity extends AppCompatActivity {

    private EditText editMark;
    private Button addMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        Bundle bundle=this.getIntent().getExtras();
        final Long workId=bundle.getLong("workId");
        final Long workFlowId=bundle.getLong("workFlowId");
        editMark=findViewById(R.id.edit_Mark);
        addMark=findViewById(R.id.add_mark);
        SharedPreferences sharedPreferences=getSharedPreferences("logindata", Context.MODE_PRIVATE);
        final String number=sharedPreferences.getString("number","");
        addMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editMark.getText()==null|editMark.getText().equals("")){
                    Toast.makeText(MarkActivity.this,"批注为空，请输入",Toast.LENGTH_SHORT).show();
                }else{
                    JSONObject jsonObject=new JSONObject();
                    try{
                        jsonObject.put("workId",workId);
                        jsonObject.put("workFlowId",workFlowId);
                        jsonObject.put("markContent",editMark.getText());
                        jsonObject.put("markTime",String.valueOf(System.currentTimeMillis()/1000));
                        jsonObject.put("createUserId",number);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String url="http://x.x.x.x:55555/workFlowMark/addWorkFlowMark";
                    RequestQueue requestQueue = Volley.newRequestQueue(MarkActivity.this);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                String msg= jsonObject.getString("msg");
                                if(msg.equals("增加批注成功")){
                                    Toast.makeText(MarkActivity.this,"增加批注成功",Toast.LENGTH_SHORT).show();
                                }else if(msg.equals("增加批注失败")){
                                    Toast.makeText(MarkActivity.this,"增加批注失败",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(MarkActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                    finish();
                }
            }
        });
    }
}