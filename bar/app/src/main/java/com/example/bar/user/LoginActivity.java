package com.example.bar.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.bar.MainActivity;
import com.example.bar.R;
import com.example.bar.database.User;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    private TextView mRegisterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageView football=findViewById(R.id.football);
        Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.rotate);
        football.startAnimation(animation);
        Bmob.initialize(this, "8f956b6b382653af09c008d2ed40889b");
        ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mUsernameEditText = findViewById(R.id.et_username);
        mPasswordEditText = findViewById(R.id.et_password);
        mLoginButton = findViewById(R.id.btn_login);
        mRegisterTextView = findViewById(R.id.tv_register);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user =new User();
                final String Account = mUsernameEditText.getText().toString().trim();
                final String Password = mPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(Account)) {
                    Toast.makeText(LoginActivity.this, "请填写用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(LoginActivity.this, Account+"请填写密码", Toast.LENGTH_SHORT).show();
                } else {
                    BmobQuery<User> categoryBmobQuery = new BmobQuery<>();
                    categoryBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> object, BmobException e) {
                            if (e == null) {
                                int i;
                                for (i = 0; i < object.size(); i++) {
                                    if(object.get(i).getUser_name().equals(Account)){
                                        if (object.get(i).getPassword().equals(Password)) {
                                            //密码正确，跳转（Home是登陆后跳转的页面）
                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("user_objectId", object.get(i).getObjectId());
                                            startActivity(intent);
                                            break;
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }

                                }
                                if(i>=object.size()){
                                    Toast.makeText(LoginActivity.this, "账户不存在", Toast.LENGTH_SHORT).show();

                                }


                            } else {
                                Toast.makeText(LoginActivity.this,"登录异常\n"+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

            }
        });

        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
