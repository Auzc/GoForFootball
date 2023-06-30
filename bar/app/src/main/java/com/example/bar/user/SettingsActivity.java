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

public class SettingsActivity extends AppCompatActivity {
    private Button share,advice,about,account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //getSupportActionBar().hide();//隐藏掉整个ActionBar
        ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button un_login = findViewById(R.id.un_login);
        un_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                global_user_objectId =null;
                intent.setClass(SettingsActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        account=findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingsActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
        share=findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareText = "Check out this cool app: https://example.com/myapp";
                intent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        });
        advice=findViewById(R.id.advice);
        advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingsActivity.this, AdviceActivity.class);
                startActivity(intent);
            }
        });
        about=findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingsActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });


    }
}
