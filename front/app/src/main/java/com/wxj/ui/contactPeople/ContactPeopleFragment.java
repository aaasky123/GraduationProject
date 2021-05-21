package com.wxj.ui.contactPeople;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.helloworld.library.MiddleDialogConfig;
import com.wxj.R;
import com.wxj.adapter.CommonTurnUserListAdapter;
import com.wxj.adapter.companyContactAdapter;
import com.wxj.entity.CommonTurnUser;
import com.wxj.entity.User;
import com.wxj.ui.contactsList.ContactsListFragment;
import com.wxj.ui.work.AddHelperActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactPeopleFragment extends Fragment {

    private ContactPeopleViewModel contactPeopleViewModel;
    private Button addContactButton;
    private BaseAdapter baseAdapter;
    private ListView listView;
    private ArrayList<User> commonTurnUserArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contactpeople,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addContactButton=view.findViewById(R.id.button);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_contact_list);
            }
        });

        listView=view.findViewById(R.id.common_turn_user_list);
        commonTurnUserArrayList=new ArrayList<>();
        SharedPreferences read2=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
        String number=read2.getString("number","");
        if (number.equals("")){
            Toast.makeText(getActivity(), "获取不到用户名", Toast.LENGTH_SHORT).show();
        }else{
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("number",number);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String url="http://x.x.x.x:55555/contactPeople/queryCommonTurnUser";
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        String msg = jsonObject.getString("msg");
                        Log.d("msg", msg);
                        if (msg.equals("查询成功")) {
                            JSONArray detail=jsonObject.getJSONArray("detail");
                            for(int i=0;i<detail.length();i++){
                                JSONObject commonTurnUserList=detail.getJSONObject(i);
                                commonTurnUserArrayList.add(new User(
                                        commonTurnUserList.getString("realname"),
                                        commonTurnUserList.getInt("employeeId"),
                                        commonTurnUserList.getLong("number"),
                                        commonTurnUserList.getInt("companyId")

                                ));
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    companyContactAdapter adapter=new companyContactAdapter(
                            getContext(),R.layout.fragment_contactpeople,commonTurnUserArrayList
                    );
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            new MiddleDialogConfig().builder(getContext())
                                    .setTitle("删除"+commonTurnUserArrayList.get(position).getRealname())
                                    .setContent("确定删除吗？")
                                    .setRightCallBack(new MiddleDialogConfig.RightCallBack() {
                                        @Override
                                        public void rightBtn(String cont) {
                                            Long mateNumber=commonTurnUserArrayList.get(position).getNumber();
                                            Integer companyId=commonTurnUserArrayList.get(position).getCompanyId();
                                            SharedPreferences read2=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                                            String loginUserNumber=read2.getString("number","");
                                            final JSONObject jsonObject1=new JSONObject();
                                            try {
                                                jsonObject1.put("turnUserId",mateNumber);
                                                jsonObject1.put("userId",loginUserNumber);
                                                jsonObject1.put("companyId",companyId);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            final String url="http://x.x.x.x:55555/contactPeople/delCommonTurnUser";
                                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject1, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject jsonObject) {
                                                    try {
                                                        String msg= jsonObject1.getString("msg");
                                                        if(msg.equals("删除成功")){
                                                            Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                                                        }else if(msg.equals("删除失败")){
                                                            Toast.makeText(getActivity(),"删除失败",Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError volleyError) {
                                                    Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            requestQueue.add(jsonObjectRequest);
                                        }
                                    })
                                    .setLeftCallBack(new MiddleDialogConfig.LeftCallBack() {
                                        @Override
                                        public void leftBtn(String cont) {
                                        }
                                    }).show();
                        }
                    });


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }
}