package com.wxj.ui.me;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.documentfile.provider.DocumentFile;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.widget.Button;
import android.widget.EditText;

import com.wxj.R;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static com.wxj.utils.UriUtils.getFileAbsolutePath;

public class PhotoActivity extends AppCompatActivity {

    private String path;
    private String filename;
    private EditText filenameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        filenameEditText = findViewById(R.id.photo_name);
        Button choosePhoto = findViewById(R.id.choosePhoto);
        Button uploadPhoto = findViewById(R.id.uploadPhoto);

        choosePhoto.setOnClickListener(v -> {
            pickPhoto();
        });

        uploadPhoto.setOnClickListener(v -> {
            uploadUserPhoto(path, filenameEditText.getText().toString());
        });
    }

    public void pickPhoto() {
        String[] permissions = new String[]{
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };//所需权限
        if (
                ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED
                        ||
                        ActivityCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED
        )
        //如果没有权限
        {
            ActivityCompat.requestPermissions(this, permissions, 1);//申请权限
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//使用系统的文件选择器
        intent.setType("image/*");//所有类型的文件
        intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String subUri = uri.toString().substring(uri.toString().lastIndexOf("/") + 1, uri.toString().length());
            File dir = getExternalFilesDir(null);
            if (dir != null) {
                if (isNumeric(subUri)) {
                    path = getFileAbsolutePath(this, uri);
                } else {
                    path = DocumentsContract.getDocumentId(uri).split(":")[1];
                }
                DocumentFile documentFile = DocumentFile.fromSingleUri(this, uri);
                filename = documentFile.getName();
                filenameEditText.setText(filename);
            }
        }

    }

    public boolean uploadUserPhoto(String path, String filename) {
        OkHttpClient okhttp = new OkHttpClient();
        File file = new File(path);
        if (path.isEmpty() | !file.exists()) {
            return false;
        } else {
            String number=this.getIntent().getExtras().getString("number");

            final RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", filename, RequestBody.create(new File(path), MediaType.parse("multipart/form-data")))
                    .addFormDataPart("number", number)
                    .build();
            FutureTask<Boolean> task = new FutureTask<>(() ->
            {
                try {
                    ResponseBody responseBody = okhttp.newCall(
                            new okhttp3.Request.Builder().post(body).url("http://x.x.x.x:55555/file/photoUpload").build()
                    ).execute().body();

                    if (responseBody != null)
                        return Boolean.parseBoolean(responseBody.string());
                    return false;
                } catch (IOException e) {
                    return false;
                }
            });
            try {
                new Thread(task).start();
                return task.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;

    }
}