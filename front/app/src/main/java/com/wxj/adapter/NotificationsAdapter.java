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
import com.wxj.vo.NotificationVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationsAdapter extends ArrayAdapter<NotificationVo> {
    ArrayList<NotificationVo> notificationVos;
    Context context;
    int resource;

    public NotificationsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<NotificationVo> notificationVos){
        super(context, resource,notificationVos);
        this.context=context;
        this.resource=resource;
        this.notificationVos=notificationVos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_work_flow_mark, null, true);
        }
        NotificationVo notificationVo=getItem(position);
        final TextView createrName=(TextView)convertView.findViewById(R.id.creater_name);
        TextView notificationsContent=(TextView)convertView.findViewById(R.id.mark_content);
        TextView createTime=(TextView)convertView.findViewById(R.id.create_mark_time);
        Long userId=notificationVo.getCreateUserId();

        createrName.setText(notificationVo.getRealname());
        notificationsContent.setText(notificationVo.getNotificationsContent());
        Long unixTime=new Long(notificationVo.getCreateTime())*1000L;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String markTime=simpleDateFormat.format(new Date(unixTime));
        createTime.setText(markTime);
        return convertView;
    }
}
