package com.example.bar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bar.R;
import com.example.bar.chat.ChatActivity;
import com.example.bar.database.FootballField;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

public class ChatRoomAdapter extends BaseAdapter implements Filterable {
    private List<FootballField> mData;
    private LayoutInflater mInflater;
    private Context context;
    private List<FootballField> originalList;
    private List<FootballField> filteredList;
    private LayoutInflater inflater;
    private ItemFilter filter = new ItemFilter();
    public ChatRoomAdapter(List<FootballField> data, Context context) {
        mData = data;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public ChatRoomAdapter() {

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


        BmobFile avatarFile = field.getFile();
        if (avatarFile != null) {
            Glide.with(context).load(avatarFile.getFileUrl()).into(avatarView);
        }
        nameView.setText(field.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Item clicked: " + position, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(view.getContext(), ChatActivity.class);
                intent.putExtra("chatroom_name", field.getName());
                view.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    public ChatRoomAdapter(Context context, List<FootballField> dataList) {
        this.originalList = dataList;
        this.filteredList = dataList;
        this.inflater = LayoutInflater.from(context);
    }




    @Override
    public Filter getFilter() {
        return filter;
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<FootballField> tempList = new ArrayList<>();
            for (FootballField data : originalList) {
                if (data.getName().contains(constraint.toString())) {
                    tempList.add(data);
                }
            }
            results.values = tempList;
            results.count = tempList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<FootballField>) results.values;
            notifyDataSetChanged();
        }
    }
}