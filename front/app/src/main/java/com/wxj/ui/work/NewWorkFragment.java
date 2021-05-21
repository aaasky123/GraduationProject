package com.wxj.ui.work;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

public class NewWorkFragment extends Fragment {

    private NewWorkViewModel mViewModel;

    public static NewWorkFragment newInstance() {
        return new NewWorkFragment();
    }

    private EditText editWorkName;
    private EditText editWorkDescription;
    private LinearLayout confidential;
    private TextView vauleOfConfidential;
    private Button createWork;
    private AlertDialog alertDialog;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_work_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editWorkName=view.findViewById(R.id.edit_work_name);
        editWorkDescription=view.findViewById(R.id.edit_first_flow_description);
        confidential=view.findViewById(R.id.ll1);
        vauleOfConfidential=view.findViewById(R.id.value_of_confidential);
        createWork=view.findViewById(R.id.create_work);
        confidential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items={"公开","保密"};
                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(getContext());
                alertBuilder.setTitle("请选择是否公开工作");
                alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vauleOfConfidential.setText(items[which]);
                        alertDialog.dismiss();
                    }
                });
                alertDialog=alertBuilder.create();
                alertDialog.show();
            }
        });

        createWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String workNameStr=editWorkName.getText().toString().trim();
                String workDescriptionStr=editWorkDescription.getText().toString().trim();
                Integer voc=2;
                if(workNameStr.equals("")){
                    Toast.makeText(getContext(),"请填写工作名称",Toast.LENGTH_SHORT).show();
                }else if(workDescriptionStr.equals("")){
                    Toast.makeText(getContext(),"请填写工作描述",Toast.LENGTH_SHORT).show();
                }
                /**
                 * 0公开，1保密
                 * 0工作进行中，1工作已完成
                 */
                if(vauleOfConfidential.getText().toString().equals("公开")){
                    voc=0;
                }else if(vauleOfConfidential.getText().toString().equals("保密")){
                    voc=1;
                }else if(vauleOfConfidential.getText().toString().equals("")){
                    Toast.makeText(getContext(),"请选择保密性",Toast.LENGTH_SHORT).show();
                }
                if(!workNameStr.isEmpty()&&!workDescriptionStr.isEmpty()&&!vauleOfConfidential.getText().toString().trim().isEmpty()){
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                    String createUserId=sharedPreferences.getString("number",null);
                    String createTime =String.valueOf(System.currentTimeMillis()/1000);
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("workName",workNameStr);
                        jsonObject.put("startDescription",workDescriptionStr);
                        jsonObject.put("confidential",voc);
                        jsonObject.put("state",0);
                        jsonObject.put("createUserId",createUserId);
                        jsonObject.put("createTime",createTime);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String url="http://x.x.x.x:55555/work/createWork";
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try{
                                String msg = jsonObject.getString("msg");
                                Log.d("msg", msg);
                                if (msg.equals("新建工作成功")) {
                                    Toast toast=Toast.makeText(getContext(), "新建工作成功", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                }else if(msg.equals("新建工作失败")){
                                    Toast.makeText(getContext(), "新建工作失败", Toast.LENGTH_SHORT).show();
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