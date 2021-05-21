package com.wxj.ui.work;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.loader.content.CursorLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wxj.R;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.wxj.utils.UriUtils.getFileAbsolutePath;

public class FileActivity extends AppCompatActivity {

    private String path;
    private String filename;
    private EditText filenameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        filenameEditText = findViewById(R.id.file_name);
        ImageView view = findViewById(R.id.file_view);
        Button choose = findViewById(R.id.choosee);
        Button upload = findViewById(R.id.upload);
        //Button download=findViewById(R.id.download);

        choose.setOnClickListener(v -> {
            pickFile();
        });

        upload.setOnClickListener(v -> {
            uploadFile(path, filenameEditText.getText().toString());
        });
    }

    // 打开系统的文件选择器
    public void pickFile() {
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
        intent.setType("*/*");//所有类型的文件
        intent.addCategory(Intent.CATEGORY_OPENABLE);//期望获取的数据可以作为一个File打开
        startActivityForResult(intent, 1);
    }

    // 获取文件的真实路径
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String subUri = uri.toString().substring(uri.toString().lastIndexOf("/") + 1, uri.toString().length());
            /*
            if (DocumentsContract.isDocumentUri(this, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    //Log.d(TAG, uri.toString());
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    //Log.d(TAG, uri.toString());
                    Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(docId));
                    path = getImagePath(contentUri, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //Log.d(TAG, "content: " + uri.toString());
                path = getImagePath(uri, null);
            }
            */


            File dir = getExternalFilesDir(null);
            if (dir != null) {

                //path = dir.toString().substring(0,dir.toString().indexOf("0")+2) +DocumentsContract.getDocumentId(uri).split(":")[1];
                //path = DocumentsContract.getDocumentId(uri).split(":")[1];

                if (isNumeric(subUri)) {
                    path = getFileAbsolutePath(this, uri);
                } else {
                    path = DocumentsContract.getDocumentId(uri).split(":")[1];
                }
                //path = getFileAbsolutePath(this, uri);
                //path=getPath(this,uri);
                DocumentFile documentFile = DocumentFile.fromSingleUri(this, uri);
                filename = documentFile.getName();
                filenameEditText.setText(filename);
            }
        }

    }


    // 使用OkHttp上传文件
    public boolean uploadFile(String path, String filename) {
        OkHttpClient okhttp = new OkHttpClient();
        File file = new File(path);
        if (path.isEmpty() | !file.exists()) {
            return false;
        } else {
            final RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", filename, RequestBody.create(new File(path), MediaType.parse("multipart/form-data")))
                    .addFormDataPart("workId", String.valueOf(this.getIntent().getExtras().getLong("workId")))
                    .addFormDataPart("workFlowId", String.valueOf(this.getIntent().getExtras().getLong("workFlowId")))
                    .build();
            FutureTask<Boolean> task = new FutureTask<>(() ->
            {
                try {
                    ResponseBody responseBody = okhttp.newCall(
                            new okhttp3.Request.Builder().post(body).url("http://x.x.x.x:55555/file/upload").build()
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

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }

            cursor.close();
        }
        return path;
    }

    private String getPath(Context context, Uri uri) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            try {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return path;
    }

    /**
     * 根据图片的Uri获取图片的绝对路径。@uri 图片的uri
     *
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */

    public static String getRealPathFromUri(Context context, Uri uri) {
        if (context == null || uri == null) {
            return null;
        }
        if ("file".equalsIgnoreCase(uri.getScheme())) {
            return getRealPathFromUri_Byfile(context, uri);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getRealPathFromUri_Api11To18(context, uri);
        }
        int sdkVersion = Build.VERSION.SDK_INT;
//        if (sdkVersion < 11) {
//            // SDK < Api11
//            return getRealPathFromUri_BelowApi11(context, uri);
//        }
        if (sdkVersion < 19 && sdkVersion > 11) {
//            SDK > 11 && SDK < 19
            return getRealPathFromUri_Api11To18(context, uri);
//            return getRealPathFromUri_ByXiaomi(context, uri);
        }
//        // SDK > 19
        return getRealPathFromUri_AboveApi19(context, uri);//没用到
    }

    //针对图片URI格式为Uri:: file:///storage/emulated/0/DCIM/Camera/IMG_20170613_132837.jpg
    private static String getRealPathFromUri_Byfile(Context context, Uri uri) {
        String uri2Str = uri.toString();
        String filePath = uri2Str.substring(uri2Str.indexOf(":") + 3);
        return filePath;
    }

    /**
     * 适配api19以上,根据uri获取图片的绝对路径
     */

    @SuppressLint("NewApi")
    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        String filePath = null;
        String wholeID = null;

        wholeID = DocumentsContract.getDocumentId(uri);

        // 使用':'分割
        String id = wholeID.split(":")[1];

        String[] projection = {MediaStore.Images.Media.DATA};
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = {id};

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    /**
     * //适配api11-api18,根据uri获取图片的绝对路径。
     * 针对图片URI格式为Uri:: content://media/external/images/media/1028
     */

    private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        CursorLoader loader = new CursorLoader(context, uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();

        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
     */

    private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }


    @SuppressLint("NewApi")
    public static String getPathFromUri(final Context context, final Uri uri) {
        if (uri == null) {
            return null;
        }
        // 判斷是否為Android 4.4之後的版本
        final boolean after44 = Build.VERSION.SDK_INT >= 19;
        if (after44 && DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是Android 4.4之後的版本，而且屬於文件URI
            final String authority = uri.getAuthority();
            // 判斷Authority是否為本地端檔案所使用的
            if ("com.android.externalstorage.documents".equals(authority)) {
                // 外部儲存空間
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] divide = docId.split(":");
                final String type = divide[0];
                if ("primary".equals(type)) {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/").concat(divide[1]);
                    return path;
                } else {
                    String path = "/storage/".concat(type).concat("/").concat(divide[1]);
                    return path;
                }
            } else if ("com.android.providers.downloads.documents".equals(authority)) {
                // 下載目錄
                final String docId = DocumentsContract.getDocumentId(uri);
                if (docId.startsWith("raw:")) {
                    final String path = docId.replaceFirst("raw:", "");
                    return path;
                }
                final Uri downloadUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));
                String path = queryAbsolutePath(context, downloadUri);
                return path;
            } else if ("com.android.providers.media.documents".equals(authority)) {
                // 圖片、影音檔案
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] divide = docId.split(":");
                final String type = divide[0];
                Uri mediaUri = null;
                if ("image".equals(type)) {
                    mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    mediaUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    mediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    return null;
                }
                mediaUri = ContentUris.withAppendedId(mediaUri, Long.parseLong(divide[1]));
                String path = queryAbsolutePath(context, mediaUri);
                return path;
            }
        } else {
            // 如果是一般的URI
            final String scheme = uri.getScheme();
            String path = null;
            if ("content".equals(scheme)) {
                // 內容URI
                path = queryAbsolutePath(context, uri);
            } else if ("file".equals(scheme)) {
                // 檔案URI
                path = uri.getPath();
            }
            return path;
        }
        return null;
    }

    public static String queryAbsolutePath(final Context context, final Uri uri) {
        final String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                return cursor.getString(index);
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static boolean isImageFile(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (options.outWidth == -1) {
            return false;
        }
        return true;
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