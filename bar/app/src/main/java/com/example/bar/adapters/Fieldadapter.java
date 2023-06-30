package com.example.bar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bar.field.MessageOfFieldActivity;
import com.example.bar.R;
import com.example.bar.database.FootballField;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

public class Fieldadapter extends BaseAdapter {
    private List<FootballField> mData;
    private LayoutInflater mInflater;
    private Context context;

    public Fieldadapter(List<FootballField> data, Context context) {
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
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
        }

        FootballField field = mData.get(position);

        ImageView avatarView = convertView.findViewById(R.id.avatars);
        TextView nameView = convertView.findViewById(R.id.texts);
        TextView size_field = convertView.findViewById(R.id.size_field);

        BmobFile avatarFile = field.getFile();
        if (avatarFile != null) {
            Glide.with(context).load(avatarFile.getFileUrl()).into(avatarView);
        }
        nameView.setText(field.getName());
        size_field.setText(field.getField_size()+"人制标准足球场    "+field.getCharacter());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Item clicked: " + position, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(view.getContext(), MessageOfFieldActivity.class);
                intent.putExtra("name", field.getName());
                intent.putExtra("avatar_file", avatarFile.getFileUrl());
                intent.putExtra("lat",field.getAddress_lat());
                intent.putExtra("lng",field.getAddress_lng());
                intent.putExtra("addressName",field.getAddress_name());
                intent.putExtra("field_size",field.getField_size());
                intent.putExtra("character",field.getCharacter());
                view.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
