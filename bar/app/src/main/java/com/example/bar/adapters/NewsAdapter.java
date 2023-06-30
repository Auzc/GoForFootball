package com.example.bar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bar.news.NewsActivity;
import com.example.bar.R;
import com.example.bar.database.News;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private List<News> mData;
    private LayoutInflater mInflater;
    private Context context;

    public NewsAdapter(List<News> data, Context context) {
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
            convertView = mInflater.inflate(R.layout.news_item, parent, false);
        }

        News anew = mData.get(position);

        ImageView avatarView = convertView.findViewById(R.id.avatars);
        TextView title = convertView.findViewById(R.id.title);
        TextView time = convertView.findViewById(R.id.time);
        TextView source = convertView.findViewById(R.id.source);
        String photourl = anew.getPhotourl();
        if (photourl != null) {
            Glide.with(context).load(photourl).into(avatarView);
        }
        title.setText(anew.getTitle());
        source.setText(anew.getSource());
        time.setText(anew.getTime());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Item clicked: " + position, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(view.getContext(), NewsActivity.class);
                intent.putExtra("url", anew.getUrl());
                view.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}