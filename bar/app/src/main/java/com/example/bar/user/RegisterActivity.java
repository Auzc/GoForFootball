package com.example.bar.user;


import static com.example.bar.MainActivity.userInfos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mRegisterButton;
    private TextView mLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImageView football=findViewById(R.id.football);
        Animation animation = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.rotate);
        football.startAnimation(animation);
        ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mUsernameEditText = findViewById(R.id.et_username);
        mPasswordEditText = findViewById(R.id.et_password);
        mRegisterButton = findViewById(R.id.btn_register);
        mLoginTextView = findViewById(R.id.tv_login);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mUsernameEditText.getTextSize()>=1){
                    userInfos.setUser_name(mUsernameEditText.getText().toString());
                    userInfos.setPassword(mPasswordEditText.getText().toString());
                    userInfos.setSignature("运动每一天");
                    userInfos.setHeight(180);
                    userInfos.setName("球迷");
                    userInfos.setWeight(70);
                    userInfos.setGender("男");
                    userInfos.setLocation("球员");

                    userInfos.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){

                                Intent intent =new Intent();
                                //intent.putExtra("user_objectId", objectId);
                                intent.setClass(RegisterActivity.this, InitLoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this, "注册成功！请重新登录！", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegisterActivity.this,"登录异常\n"+e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }else{

                }
            }
        });

        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                mLoginTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegisterActivity.this, InitLoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
