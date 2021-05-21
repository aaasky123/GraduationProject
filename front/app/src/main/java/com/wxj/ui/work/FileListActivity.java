package com.wxj.ui.work;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.helloworld.library.MiddleDialogConfig;
import com.wxj.R;
import com.wxj.adapter.FileAdapter;
import com.wxj.adapter.WorkFlowAdapter;
import com.wxj.entity.File;
import com.wxj.entity.WorkFlow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class FileListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<File> fileArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=FileListActivity.this.getIntent().getExtras();
        Long workId=bundle.getLong("workId");
        Long workFlowId=bundle.getLong("workFlowId");
        setContentView(R.layout.activity_file_list);
        listView=findViewById(R.id.work_flow_file_list);
        fileArrayList=new ArrayList<>();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("workId",workId);
            jsonObject.put("workFlowId",workFlowId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url="http://x.x.x.x:55555/file/query";
        RequestQueue requestQueue = Volley.newRequestQueue(FileListActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String msg = jsonObject.getString("msg");
                    Log.d("msg", msg);
                    if (msg.equals("查询文件成功")) {
                        JSONArray detail=jsonObject.getJSONArray("detail");
                        for(int i=0;i<detail.length();i++){
                            JSONObject onWorkingWorkList=detail.getJSONObject(i);
                            fileArrayList.add(new File(
                                    onWorkingWorkList.getInt("id"),
                                    onWorkingWorkList.getString("fileName"),
                                    onWorkingWorkList.getString("newFileName"),
                                    onWorkingWorkList.getString("path"),
                                    onWorkingWorkList.getLong("workId"),
                                    onWorkingWorkList.getInt("workFlowId")
                            ));
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                FileAdapter adapter=new FileAdapter(
                        FileListActivity.this,R.id.work_flow_file_list,fileArrayList
                );
                listView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(FileListActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new MiddleDialogConfig().builder(FileListActivity.this)
                        .setTitle("下载"+fileArrayList.get(position).getFileName())
                        .setContent("确定下载吗？")
                        .setRightCallBack(new MiddleDialogConfig.RightCallBack() {

                            @Override
                            public void rightBtn(String cont) {
                                downloadFile(fileArrayList.get(position).getNewFileName());
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

    public java.io.File downloadFile(String filename)
    {
        OkHttpClient okhttp = new OkHttpClient();
        if(filename == null || filename.isEmpty())
            return null;
        RequestBody body = new MultipartBody.Builder().addFormDataPart("filename",filename).build();

        FutureTask<java.io.File> task = new FutureTask<>(()->
        {
            ResponseBody responseBody = okhttp.newCall(
                    new okhttp3.Request.Builder().post(body).url("http://x.x.x.x:55555/file/download").build()
            ).execute().body();
            if(responseBody != null)
            {
                if(getExternalFilesDir(null) != null)
                {
                    java.io.File file = new java.io.File(getExternalFilesDir(null).toString() + "/" + filename);
                    try (
                            InputStream inputStream = responseBody.byteStream();
                            FileOutputStream outputStream = new FileOutputStream(file)
                    )
                    {
                        byte[] b = new byte[1024];
                        int n;
                        if((n = inputStream.read(b)) != -1)
                        {
                            outputStream.write(b,0,n);
                            while ((n = inputStream.read(b)) != -1)
                                outputStream.write(b, 0, n);
                            return file;
                        }
                        else
                        {
                            file.delete();
                            return null;
                        }
                    }
                }
            }
            return null;
        });
        try
        {
            new Thread(task).start();
            return task.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}