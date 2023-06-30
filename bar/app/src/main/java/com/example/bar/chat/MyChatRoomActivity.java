package com.example.bar.chat;

import static com.example.bar.MainActivity.global_user_objectId;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bar.R;
import com.example.bar.adapters.ChatRoomAdapter;
import com.example.bar.database.FootballField;
import com.example.bar.database.StoreMessage;
import com.example.bar.fragment.ChatRoomFragment;
import com.example.bar.fragment.FieldFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyChatRoomActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_chatroom);

        ImageButton btn_back;
        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        listView = findViewById(R.id.list_view);
        if(global_user_objectId!=null){
            Map<String, Integer> map = new HashMap<>();

            BmobQuery<StoreMessage> query1 = new BmobQuery<>();
            query1.findObjects(new FindListener<StoreMessage>() {
                @Override
                public void done(List<StoreMessage> list, BmobException e) {
                    if (e == null) {
                        for(int i=0;i<list.size();i++){
                            if(list.get(i).getUser_id().equals(global_user_objectId)){
                                map.put(list.get(i).getRoom_name(), 1);
                            }
                        }
                    } else {
                        // 查询失败
                    }
                }
            });
            List<FootballField> listitem=new ArrayList<>();
            BmobQuery<FootballField> query = new BmobQuery<>();
            query.findObjects(new FindListener<FootballField>() {
                @Override
                public void done(List<FootballField> list, BmobException e) {
                    if (e == null) {
                        for(int i=0;i<list.size();i++){
                            Integer value = map.get(list.get(i).getName());
                            if(value!=null){
                                listitem.add(list.get(i));
                            }

                        }
                        listView.setAdapter(new ChatRoomAdapter(listitem,MyChatRoomActivity.this));
                    } else {
                        // 查询失败
                    }
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // 在这里处理点击事件
                    view.performClick();
                }
            });
        }
    }



}


