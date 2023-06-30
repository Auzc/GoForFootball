package com.example.bar.user;

import static com.example.bar.MainActivity.global_user_objectId;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.MainActivity;
import com.example.bar.R;
import com.example.bar.database.Advice;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AdviceActivity extends AppCompatActivity {
    EditText emailEditText,contentEditText;
    Button sendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ImageButton btn_back;
        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        emailEditText=findViewById(R.id.emailEditText);
        contentEditText=findViewById(R.id.contentEditText);
        sendButton=findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(global_user_objectId==null){
                    Toast.makeText(AdviceActivity.this, "请先登录。", Toast.LENGTH_SHORT).show();
                }else{
                    if(emailEditText.getText().toString()==null||contentEditText.getTextSize()==0){
                        Toast.makeText(AdviceActivity.this, "请输入完整信息。", Toast.LENGTH_SHORT).show();
                    }else{
                        Advice a = new Advice();
                        a.setContent(contentEditText.getText().toString());
                        a.setEmail(emailEditText.getText().toString());
                        a.setUser_id(global_user_objectId);
                        a.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if(e==null){
                                    Toast.makeText(AdviceActivity.this, "发送成功！感谢您的反馈！", Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent();
                                    intent.setClass(AdviceActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(AdviceActivity.this,"发送失败！\n"+e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }

            }
        });
    }

}
