package com.wxj.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wxj.R;
import com.wxj.adapter.NotificationsAdapter;
import com.wxj.vo.NotificationVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private ListView notificationsList;
    private ArrayList<NotificationVo> notificationVoArrayList;
    private Button addNotificationsButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notificationsList=view.findViewById(R.id.notifications_list);
        notificationVoArrayList=new ArrayList<>();
        addNotificationsButton=view.findViewById(R.id.add_notification);
        SharedPreferences read2=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        String number=read2.getString("number","");
        if (number.equals("")){
            Toast.makeText(getActivity(), "获取不到用户名", Toast.LENGTH_SHORT).show();
        }else {

            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("userId",number);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String url="http://x.x.x.x:55555/notifications/queryNotifications";
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        String msg = jsonObject.getString("msg");
                        Log.d("msg", msg);
                        if (msg.equals("查找通知成功")) {
                            JSONArray detail=jsonObject.getJSONArray("detail");
                            for(int i=0;i<detail.length();i++){
                                JSONObject NotificationList=detail.getJSONObject(i);
                                notificationVoArrayList.add(new NotificationVo(
                                        NotificationList.getLong("id"),
                                        NotificationList.getLong("createUserId"),
                                        NotificationList.getString("notificationsContent"),
                                        NotificationList.getLong("createTime"),
                                        NotificationList.getString("realname")

                                ));
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    NotificationsAdapter adapter=new NotificationsAdapter(
                            getContext(),R.id.notifications_list,notificationVoArrayList
                    );
                    notificationsList.setAdapter(adapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }

        addNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_add_notifications);
            }
        });
    }
}