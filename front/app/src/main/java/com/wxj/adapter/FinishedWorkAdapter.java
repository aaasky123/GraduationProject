package com.wxj.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wxj.R;
import com.wxj.entity.Work;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FinishedWorkAdapter extends ArrayAdapter<Work> {
    ArrayList<Work> works;
    Context context;
    int resource;

    public FinishedWorkAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Work> works){
        super(context, resource,works);
        this.works=works;
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_finished_worklist, null, true);
        }
        Work work=getItem(position);
        TextView valueOfSecret=(TextView)convertView.findViewById(R.id.finished_value_of_secret);
        TextView workId=(TextView)convertView.findViewById(R.id.finished_work_id);
        TextView workName=(TextView)convertView.findViewById(R.id.finished_work_name);
        final TextView createUserName=(TextView)convertView.findViewById(R.id.finished_work_create_name);
        TextView startTime=(TextView)convertView.findViewById(R.id.finished_work_create_time);
        TextView endTime=(TextView)convertView.findViewById(R.id.finished_work_end_time);
        //valueOfSecret.setText(work.getConfidential());
        if(work.getConfidential().equals(0)){
            valueOfSecret.setText("公开");
        }else if(work.getConfidential().equals(1)){
            valueOfSecret.setText("保密");
        }
        workId.setText(String.valueOf(work.getWorkId()));
        workName.setText(work.getWorkName());
        //createUserName.setText(work.getCreateUserId());
        Long userId=work.getCreateUserId();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("number",userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String url="http://x.x.x.x:55555/user/userInformation";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String msg = jsonObject.getString("msg");
                    Log.d("msg", msg);
                    if (msg.equals("查询成功")) {
                        JSONObject detail=jsonObject.getJSONObject("detail");
                        String realName = detail.getString("真实姓名");
                        createUserName.setText(realName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        Long unixTime=new Long(work.getCreateTime())*1000L;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime=simpleDateFormat.format(new Date(unixTime));
        Long unixEndTime=new Long(work.getEndTime())*1000L;
        String endTimeFormat=simpleDateFormat.format(new Date(unixEndTime));
        startTime.setText(createTime);
        endTime.setText(endTimeFormat);
        return convertView;
    }
}
