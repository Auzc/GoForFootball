package com.example.bar.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bar.MainActivity;
import com.example.bar.R;
import com.example.bar.database.User;
//import com.tencent.qcloud.tuicore.TUILogin;
//import com.tencent.qcloud.tuicore.TUIThemeManager;
//import com.tencent.qcloud.tuicore.interfaces.TUICallback;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class InitLoginActivity extends AppCompatActivity {

//    public static final int SDKAPPIDS = 1400800264;
    private static  String USERID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_login);
        ImageView football=findViewById(R.id.football);
        Animation animation = AnimationUtils.loadAnimation(InitLoginActivity.this, R.anim.rotate);
        football.startAnimation(animation);



       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        Bmob.initialize(InitLoginActivity.this, "8f956b6b382653af09c008d2ed40889b");
        Button loginbutton = findViewById(R.id.btn_logins);
        EditText username = findViewById(R.id.et_usernames);
        EditText  password = findViewById(R.id.et_passwords);
        TextView register = findViewById(R.id.tv_registers);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitLoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        Context context = InitLoginActivity.this;
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String USERID = username.getText().toString().trim();
                final String PASSWORDS = password.getText().toString().trim();
                if (TextUtils.isEmpty(USERID)) {
                    Toast.makeText(InitLoginActivity.this, "请填写用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(PASSWORDS)) {
                    Toast.makeText(InitLoginActivity.this, USERID+"请填写密码", Toast.LENGTH_SHORT).show();
                } else{
                    BmobQuery<User> categoryBmobQuery = new BmobQuery<>();
                    categoryBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> object, BmobException e) {
                            if (e == null) {
                                int i;
                                for (i = 0; i < object.size(); i++) {
                                    if(object.get(i).getUser_name().equals(USERID)){
                                        if (object.get(i).getPassword().equals(PASSWORDS)) {
                                            //密码正确，跳转（Home是登陆后跳转的页面）

                                            //IM_login
//                                            String USERSIGS = GenerateTestUserSig.genTestUserSig(USERID);
//                                            TUILogin.login(context, SDKAPPIDS, USERID, USERSIGS, new TUICallback() {
//
//                                                @Override
//                                                public void onError(final int code, final String desc) {
//                                                }
//
//                                                @Override
//                                                public void onSuccess() {
//                                                }
//                                            });
//
//                                            TUIThemeManager.getInstance().changeLanguage(context,"zh");
                                            Intent intent = new Intent(InitLoginActivity.this, MainActivity.class);
                                            intent.putExtra("user_objectId", object.get(i).getObjectId());
                                            startActivity(intent);
                                            //Intent intent = new Intent(initLogin.this, MainActivity.class);
                                            //intent.putExtra("user_objectId", object.get(i).getObjectId());
                                            //startActivity(intent);
                                        } else {
                                            Toast.makeText(InitLoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                        }
                                        break;
                                    }

                                }
                                if(i>=object.size()){
                                    Toast.makeText(InitLoginActivity.this, "账户不存在,请注册账号！", Toast.LENGTH_SHORT).show();

                                }


                            } else {
                                Toast.makeText(InitLoginActivity.this,"登录异常\n"+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }


            }
        });


    }
}