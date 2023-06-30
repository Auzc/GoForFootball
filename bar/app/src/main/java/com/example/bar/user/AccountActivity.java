package com.example.bar.user;

import static com.example.bar.MainActivity.global_user_objectId;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.MainActivity;
import com.example.bar.R;

public class AccountActivity extends AppCompatActivity {
    private Button changepassword,changeaccount,acoount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Button un_login = findViewById(R.id.un_login);
        un_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();

                global_user_objectId =null;
                intent.setClass(AccountActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        ImageButton btn_back;
        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        changepassword=findViewById(R.id.changepassword);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(AccountActivity.this, ChangePasswordActivity.class));
            }
        });
        changeaccount=findViewById(R.id.changeaccount);
        changeaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(AccountActivity.this, LoginActivity.class));
            }
        });
        acoount = findViewById(R.id.acoount);
        acoount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AccountActivity.this, EditMessageActivity.class);
                startActivity(intent);
            }
        });

    }

}