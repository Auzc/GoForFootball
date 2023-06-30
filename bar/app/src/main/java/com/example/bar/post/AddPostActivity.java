package com.example.bar.post;

import static com.example.bar.MainActivity.global_user;
import static com.example.bar.MainActivity.global_user_objectId;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.R;
import com.example.bar.database.Post;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddPostActivity extends AppCompatActivity {

    private ImageButton btn_back,ok;
    private EditText titleEditText,contentEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        btn_back=findViewById(R.id.btn_back);
        //titleEditText=findViewById(R.id.titleEditText);
        contentEditText=findViewById(R.id.contentEditText);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok = findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contentEditText.getText()!=null){
                    Post anew=new Post();

                    anew.setTime(getTime());
                    anew.setAuthor_id(global_user_objectId);
                    anew.setAuthor_image(global_user.getUser_avatar());
                    anew.setAuthor_name(global_user.getUser_name());
                    anew.setContent(contentEditText.getText().toString());
                    anew.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(AddPostActivity.this,"发送成功" , Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(AddPostActivity.this, "发送异常", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }





            }
        });
    }
    public String getTime(){
        SimpleDateFormat d_f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        d_f.setTimeZone(TimeZone.getTimeZone("GMT+08"));  //设置时区，+08是北京时间
        String date = d_f.format(new Date());

        System.out.println("获取的时间为："  +date);
        return date;

    }

}
