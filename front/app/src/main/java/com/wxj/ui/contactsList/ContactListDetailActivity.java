package com.wxj.ui.contactsList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxj.R;

public class ContactListDetailActivity extends AppCompatActivity {
    private TextView contactDetailName;
    private TextView contactDetailEmployeeId;
    private TextView contactDetailEmail;
    private ImageView contactDetailPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list_detail);
        contactDetailName=findViewById(R.id.textView3);
        contactDetailEmail=findViewById(R.id.textView6);
        contactDetailEmployeeId=findViewById(R.id.textView9);
        contactDetailPicture=findViewById(R.id.contact_list_user_detail_picture);
        Intent intent=getIntent();
        String name=intent.getStringExtra("realName");
        String email=intent.getStringExtra("email");
        Integer employeeId=intent.getIntExtra("employeeId",0);
        contactDetailName.setText(name);
        contactDetailEmail.setText(email);
        contactDetailEmployeeId.setText(employeeId);
    }
}