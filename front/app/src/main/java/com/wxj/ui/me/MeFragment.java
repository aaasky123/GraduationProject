package com.wxj.ui.me;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.helloworld.library.BottomMenuFragment;
import com.helloworld.library.MiddleDialogConfig;
import com.wxj.R;
import com.wxj.ui.login.LoginActivity;
import com.wxj.ui.work.FileActivity;
import com.wxj.ui.work.HelperManageActivity;
import com.wxj.ui.work.MarkActivity;
import com.wxj.ui.work.TurnNextActivity;
import com.wxj.ui.work.WorkFlowDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MeFragment extends Fragment {

    private MeViewModel meViewModel;

    private ImageView headPictureBack;
    private ImageView headPicture;
    private TextView user_name;
    private TextView userBelongsTo;
    private Button cancelLogin;

    private LinearLayout userInformation;
    private LinearLayout changePassword;
    private LinearLayout about;
    private LinearLayout registerComapny;
    private LinearLayout joinCompany;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        //meViewModel =
        //        ViewModelProviders.of(this).get(MeViewModel.class);
        //View root = inflater.inflate(R.layout.fragment_me, container, false);
        //final TextView textView = root.findViewById(R.id.text_me);
        //meViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        //    @Override
        //    public void onChanged(@Nullable String s) {
        //        textView.setText(s);
        //    }
        //});
        //return root;
        return inflater.inflate(R.layout.fragment_me,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        headPictureBack=view.findViewById(R.id.head_picture_back);
        headPicture=view.findViewById(R.id.head_picture);
        user_name=view.findViewById(R.id.username1);
        userBelongsTo=view.findViewById(R.id.user_belongs_to);
        cancelLogin=view.findViewById(R.id.btn_cancel_login);
        userInformation=view.findViewById(R.id.host);
        changePassword=view.findViewById(R.id.change_password);
        registerComapny=view.findViewById(R.id.register_company);
        joinCompany=view.findViewById(R.id.join_company);
        about=view.findViewById(R.id.about);
        cancelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        userInformation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View view){
                SharedPreferences read2=getActivity().getSharedPreferences("logindata",Context.MODE_PRIVATE);
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
                    String url="http://x.x.x.x:55555/user/userInformation";
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                String msg = jsonObject.getString("msg");
                                Log.d("msg", msg);
                                if (msg.equals("查询成功")) {

                                    JSONObject detail = jsonObject.getJSONObject("detail");
                                    String realName = detail.getString("真实姓名");
                                    String email = detail.getString("邮件");
                                    String company_id=detail.getString("公司id");
                                    String employee_id=detail.getString("雇员id");
                                    Bundle result=new Bundle();
                                    result.putString("realName",realName);
                                    result.putString("email",email);
                                    result.putString("company_id",company_id);
                                    result.putString("employee_id",employee_id);
                                    getParentFragmentManager().setFragmentResult("user_information_detail",result);

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
                    Navigation.findNavController(view).navigate(R.id.navigation_user_information);
                }
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_change_password);
                //startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });
        registerComapny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_register_company);
            }
        });

        joinCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigation_join_company);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_about);
                //startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });
        SharedPreferences read=getActivity().getSharedPreferences("logindata",Context.MODE_PRIVATE);
        String username = read.getString("number","");
        user_name.setText(username);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("number",read.getString("number",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url="http://x.x.x.x:55555/user/userInformation";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String msg = jsonObject.getString("msg");
                    Log.d("msg", msg);
                    if (msg.equals("查询成功")) {
                        JSONObject detail = jsonObject.getJSONObject("detail");
                        String realName = detail.getString("真实姓名");
                        String email = detail.getString("邮件");
                        String company_id=detail.getString("公司id");
                        String employee_id=detail.getString("雇员id");
                        String photoUrl=detail.getString("头像");
                        if(photoUrl != null||!photoUrl.isEmpty()){
                            String userphotourl="http://x.x.x.x:55555/image/"+photoUrl;
                            Glide.with(getContext()).load(userphotourl).into(headPicture);
                            Glide.with(getContext()).load(userphotourl).bitmapTransform(new BlurTransformation(getActivity(),25),new CenterCrop(getActivity())).into(headPictureBack);
                        }else{
                            Toast.makeText(getContext(),"12345678987654321",Toast.LENGTH_SHORT).show();
                        }
                        //String photoUrl=detail.getString("头像");

                        userBelongsTo.setText(realName);
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
        //String companyId=read.getString("companyId","");
        //Glide.with(getActivity()).load(glideurl).bitmapTransform(new BlurTransformation(getActivity(),25),new CenterCrop(getActivity())).into(headPictureBack);
        //Glide.with(getActivity()).load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimglf4.nosdn0.126.net%2Fimg%2FM0ZOUWlLbG0xcy8wSlJLL2ozT1ZEdGZRSWRWc1lWcWVXV0FndFJhczJaQk9NMGtjMVhvdXRnPT0.jpg%3FimageView%26thumbnail%3D500x0%26quality%3D96%26stripmeta%3D0%26type%3Djpg&refer=http%3A%2F%2Fimglf4.nosdn0.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1623492337&t=203025388211944174fbd30e99a791cb").into(headPicture);
        //Glide.with(getActivity()).load("https://x.x.x.x:55555/uploadFile/b6b4520e-73e2-400c-9017-72953532b716milet.jpeg").into(headPicture);

        headPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUploadPicture();
            }

            public void showUploadPicture(){
                List<String> list = new ArrayList<>();
                list.clear();
                list.add("选择头像上传");
                new BottomMenuFragment((AppCompatActivity) getActivity())
                        .setContentSize(16)
                        .setCancelTextTitle("取消")
                        .setCancelTextSize(16)
                        .setCancelTextColor("#454545")
                        .setCancelTextHeight(45)
                        .setCancelTextMarginTop(20)
                        .setContentColor("#454545")
                        .addMenuItems(list)
                        .setBackgroundColor("#ebebeb")
                        .setCancelPadding(0, 0, 0, 0)
                        .setSizeOneShape(R.drawable.bottom_menu_mid_selector)
                        .setTopShape(R.drawable.bottom_menu_mid_selector)
                        .setMiddleShape(R.drawable.bottom_menu_mid_selector)
                        .setBottomShape(R.drawable.bottom_menu_mid_selector)
                        .setCancelShape(R.drawable.bottom_menu_mid_selector)
                        .setOnItemClickListener(new BottomMenuFragment.OnItemClickListener() {
                            @Override
                            public void onItemClick(TextView menu, int position) {
                                switch (menu.getText().toString()){
                                    case "选择头像上传" :
                                        SharedPreferences read2=getActivity().getSharedPreferences("logindata",Context.MODE_PRIVATE);
                                        String number=read2.getString("number","");
                                        Bundle uploadPhotoData=new Bundle();
                                        uploadPhotoData.putString("number",number);
                                        Intent intent=new Intent(getContext(), PhotoActivity.class);
                                        intent.putExtras(uploadPhotoData);
                                        startActivity(intent);
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });

    }

}
