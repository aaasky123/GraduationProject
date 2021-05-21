package com.wxj.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AddNotificationsFragment extends Fragment {

    private EditText editNotifications;
    private Button publishNotifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editNotifications=view.findViewById(R.id.edit_notifications_content);
        publishNotifications=view.findViewById(R.id.publish_notifications);

        publishNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editNotifications.getText().length()>0){
                    SharedPreferences read2=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                    String number=read2.getString("number","");
                    JSONObject jsonObject=new JSONObject();
                    try{
                        jsonObject.put("createUserId",number);
                        jsonObject.put("notificationsContent",editNotifications.getText());
                        jsonObject.put("createTime",String.valueOf(System.currentTimeMillis()/1000));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String url="http://x.x.x.x:55555/notifications/insertNotifications";
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try{
                                String msg = jsonObject.getString("msg");
                                Log.d("msg", msg);
                                if (msg.equals("发布通知成功")) {
                                    Toast toast=Toast.makeText(getContext(), "发布通知成功", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                }else if(msg.equals("发布通知失败")){
                                    Toast.makeText(getContext(), "发布通知失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }

            }
        });
    }
}