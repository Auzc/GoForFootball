package com.example.bar.chat;

import static com.example.bar.MainActivity.global_user;
import static com.example.bar.MainActivity.global_user_objectId;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.MainActivity;
import com.example.bar.R;
import com.example.bar.adapters.ChatAdapter;
import com.example.bar.adapters.NewsAdapter;
import com.example.bar.database.ChatMessage;
import com.example.bar.database.News;
import com.example.bar.database.StoreMessage;
import com.example.bar.database.User;
import com.example.bar.user.InitLoginActivity;
import com.example.bar.user.LoginActivity;
import com.example.bar.user.RegisterActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ChatActivity extends AppCompatActivity {
    private EditText content_text;
    private Button send_button;
    private ImageButton btn_back,btn_store;
    private  String objectld=null;
    private TextView field_name;
    private ArrayList<ChatMessage> mMessages = new ArrayList<>();
    private ListView listview;
    private  int flag=0;


    private String chatroom_name="1";
    private String sender_name,receiver_name;
    private String send_content;

    private void init(){
        field_name=findViewById(R.id.field_name);
        listview=findViewById(R.id.listview);
        content_text=findViewById(R.id.content_text);
        send_button=findViewById(R.id.send_button);
        chatroom_name=getIntent().getStringExtra("chatroom_name");
        field_name.setText(chatroom_name);
        btn_store=findViewById(R.id.btn_store);
        setSendListenter();
        setReceiveListener();
        if(global_user_objectId!=null){

            BmobQuery<StoreMessage> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.findObjects(new FindListener<StoreMessage>() {
                @Override
                public void done(List<StoreMessage> object, BmobException e) {
                    StoreMessage a;
                    if (e == null) {
                        int i;
                        for (i = 0; i < object.size(); i++) {
                            if(object.get(i).getRoom_name().equals(chatroom_name)){
                                flag=1;
                                objectld=object.get(i).getObjectId();
                                break;
                            }
                        }
                        if(flag==0){
                            btn_store.setImageResource(R.drawable.store);
                        }else{
                            btn_store.setImageResource(R.drawable.store2);
                        }

                    } else {
                        Toast.makeText(ChatActivity.this,"异常\n"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });

        }else{

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        btn_back=findViewById(R.id.btn_back1);
        init();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0){
                    new StoreMessage(global_user_objectId,chatroom_name).save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId,BmobException e) {
                            if(e==null){

                            }else{

                            }
                        }
                    });
                    btn_store.setImageResource(R.drawable.store2);
                    flag=1;

                }else{
                    StoreMessage a = new StoreMessage();
                    a.setObjectId(objectld);
                    a.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){

                            }else{

                            }
                        }
                    });
                    btn_store.setImageResource(R.drawable.store);
                    flag=0;

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
    private void setSendListenter(){
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                send_content =content_text.getText().toString().trim();
                ChatMessage message=new ChatMessage(chatroom_name,global_user_objectId,send_content,getTime());
                message.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast.makeText(ChatActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                            content_text.setText("");
                        }else{
                            Toast.makeText(ChatActivity.this,"发送失败！\n"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
//                try{
//
//                    Connection conn = MySQLConnection.getConnection();
//
//
//                    if (conn == null) {
//                        System.err.println("Connection object is null.");
//                    } else {
//                        PreparedStatement stmt = conn.prepareStatement("INSERT INTO ChatMessage (chatroom_name, user_id, messageContent, time) VALUES (?, ?, ?, ?)");
//                        stmt.setString(1, message.getChatroom_name());
//                        stmt.setString(2, message.getUser_id());
//                        stmt.setString(3, message.getMessage_content());
//                        stmt.setString(4, message.getTime());
//
//
//                        stmt.executeUpdate();
//
//                        stmt.close();
//                        conn.close();
//                        // 如果成功插入了一行，则输出一条成功消息
//
//                    }
//
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }


            }
        });

    }
    private void setReceiveListener(){

        List<ChatMessage> listitem = new ArrayList<ChatMessage>();

        BmobQuery<ChatMessage> query = new BmobQuery<>();
        query.findObjects(new FindListener<ChatMessage>() {
            @Override
            public void done(List<ChatMessage> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if(list.get(i).getChatroom_name().equals(chatroom_name)){
                            listitem.add(list.get(i));
                        }
                    }
                    if(listitem.isEmpty()){
                        return;
                    }
                    listview.setAdapter(new ChatAdapter(listitem,ChatActivity.this));
                } else {
                    // 查询失败

                }
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在这里处理点击事件
                view.performClick();
            }
        });
        //判断adapter是否为null
        if (listview.getAdapter() != null) {
            //将ListView滚动到最后一个item
            listview.post(new Runnable() {
                @Override
                public void run() {
                    listview.smoothScrollToPosition(listview.getAdapter().getCount() - 1);
                }
            });
        }
    }



}
