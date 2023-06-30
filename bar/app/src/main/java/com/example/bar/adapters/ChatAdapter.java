package com.example.bar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bar.R;
import com.example.bar.database.ChatMessage;
import com.example.bar.database.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class ChatAdapter extends BaseAdapter {
    private List<ChatMessage> mData;
    private LayoutInflater mInflater;
    private Context context;
    User auser=new User();

    public ChatAdapter(List<ChatMessage> data, Context context) {
        mData = data;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.chat_item, parent, false);
        }

        ChatMessage anew = mData.get(position);

        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.getObject(anew.getUser_id(), new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if(e==null){
                    auser =object;
                }else{

                }
            }
        });

        ImageView avatarView = convertView.findViewById(R.id.avatar);
        TextView nickname = convertView.findViewById(R.id.nickname);
        TextView time = convertView.findViewById(R.id.time);
        TextView content = convertView.findViewById(R.id.content);


        if (auser.getUser_avatar()!= 0) {
            avatarView.setImageResource(auser.getUser_avatar());
        }
        nickname.setText(auser.getName());
        content.setText(anew.getMessage_content());
        time.setText(anew.getTime());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }
}