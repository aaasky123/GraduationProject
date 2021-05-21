package com.wxj.ui.registerResult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wxj.R;

public class RegisterResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_result);
        TextView showRegisterNumber=findViewById(R.id.users_login_number);
        Button toLogin=findViewById(R.id.back_to_login);

        Intent intent =getIntent();
        showRegisterNumber.setText(intent.getStringExtra("number"));
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}