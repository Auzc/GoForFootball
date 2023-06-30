package com.example.bar.fragment;

import static com.example.bar.MainActivity.global_user_objectId;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.bar.R;
import com.example.bar.adapters.ChatRoomAdapter;
import com.example.bar.database.FootballField;
import com.example.bar.database.StoreMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ChatRoomFragment extends Fragment {
    private ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatroom, container, false);

        listView = (ListView) view.findViewById(R.id.list_view);
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
                        listView.setAdapter(new ChatRoomAdapter(listitem,view.getContext()));
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


        return view;

    }


}