package com.example.bar.user;

import static com.example.bar.MainActivity.global_user_objectId;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.MainActivity;
import com.example.bar.R;
import com.example.bar.database.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ChangePasswordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ImageButton btn_back;
        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        EditText old,new1,new2;
        old=findViewById(R.id.old);
        new1=findViewById(R.id.new1);
        new2=findViewById(R.id.new2);
        Button button =findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user =new User();

                BmobQuery<User> bmobQuery = new BmobQuery<User>();
                bmobQuery.getObject(global_user_objectId, new QueryListener<User>() {
                    @Override
                    public void done(User object,BmobException e) {
                        if(e==null){
                            if(old.getText().toString().equals(object.getPassword())){
                                if(new1.getText().toString().equals(new2.getText().toString())){
                                    object.setPassword(new1.getText().toString());
                                    object.update(global_user_objectId, new UpdateListener() {

                                        @Override
                                        public void done(BmobException e) {
                                            if(e==null){
                                                Toast.makeText(ChangePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(ChangePasswordActivity.this, "修改失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    });
                                }else{
                                    Toast.makeText(ChangePasswordActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(ChangePasswordActivity.this, "旧密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ChangePasswordActivity.this, "异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

    }

}