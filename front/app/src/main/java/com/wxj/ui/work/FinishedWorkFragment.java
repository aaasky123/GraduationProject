package com.wxj.ui.work;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.wxj.R;
import com.wxj.adapter.FinishedWorkAdapter;
import com.wxj.adapter.OnWorkingAdapter;
import com.wxj.entity.Work;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FinishedWorkFragment extends Fragment {

    private FinishedWorkViewModel mViewModel;

    public static FinishedWorkFragment newInstance() {
        return new FinishedWorkFragment();
    }

    private ListView listView;
    private ArrayList<Work> finishedWorkArrayList;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.finished_work_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=view.findViewById(R.id.finished_work_list);
        finishedWorkArrayList=new ArrayList<>();
        SharedPreferences read2=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        String number=read2.getString("number","");
        if (number.equals("")){
            Toast.makeText(getActivity(), "获取不到用户名", Toast.LENGTH_SHORT).show();
        }else {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("number", number);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String url = "http://x.x.x.x:55555/work/queryFinishedWork";
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        String msg = jsonObject.getString("msg");
                        Log.d("msg", msg);
                        if (msg.equals("查询成功")) {
                            JSONArray detail = jsonObject.getJSONArray("detail");
                            for (int i = 0; i < detail.length(); i++) {
                                JSONObject onWorkingWorkList = detail.getJSONObject(i);
                                finishedWorkArrayList.add(new Work(
                                        onWorkingWorkList.getLong("workId"),
                                        onWorkingWorkList.getString("workName"),
                                        onWorkingWorkList.getLong("createUserId"),
                                        onWorkingWorkList.getString("createTime"),
                                        onWorkingWorkList.getString("endTime"),
                                        onWorkingWorkList.getInt("companyId"),
                                        onWorkingWorkList.getInt("state"),
                                        onWorkingWorkList.getString("startDescription"),
                                        onWorkingWorkList.getString("endDescription"),
                                        onWorkingWorkList.getInt("confidential")

                                ));
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    FinishedWorkAdapter adapter = new FinishedWorkAdapter(
                            getContext(), R.layout.finished_work_fragment, finishedWorkArrayList
                    );
                    listView.setAdapter(adapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long workID=finishedWorkArrayList.get(position).getWorkId();
                Bundle args=new Bundle();
                args.putLong("workId",workID);
                args.putInt("viewpagerId",1);
                Intent intent=new Intent(getContext(), WorkFlowActivity.class);
                intent.putExtras(args);
                startActivity(intent);

            }
        });
    }
}