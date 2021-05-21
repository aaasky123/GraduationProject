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
import com.wxj.entity.WorkFlowMark;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkFlowMarkAdapter extends ArrayAdapter<WorkFlowMark> {
    ArrayList<WorkFlowMark> workFlowMarks;
    Context context;
    int resource;

    public WorkFlowMarkAdapter(@NonNull Context context, int resource, @NonNull ArrayList<WorkFlowMark> workFlowMarks){
        super(context, resource, workFlowMarks);
        this.workFlowMarks=workFlowMarks;
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_work_flow_mark, null, true);
        }
        WorkFlowMark workFlowMark=getItem(position);
        final TextView createrName=(TextView)convertView.findViewById(R.id.creater_name);
        TextView markContent=(TextView)convertView.findViewById(R.id.mark_content);
        TextView createTime=(TextView)convertView.findViewById(R.id.create_mark_time);
        Long userId=workFlowMark.getCreateUserId();
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
                        createrName.setText(realName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

        markContent.setText(workFlowMark.getMarkContent());
        Long unixTime=new Long(workFlowMark.getMarkTime())*1000L;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String markTime=simpleDateFormat.format(new Date(unixTime));
        createTime.setText(markTime);
        return convertView;
    }
}
