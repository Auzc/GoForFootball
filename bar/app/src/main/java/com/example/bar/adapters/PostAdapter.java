package com.example.bar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bar.R;
import com.example.bar.database.Post;

import java.util.List;
public class PostAdapter extends BaseAdapter {
    private List<Post> posts;
    private LayoutInflater mInflater;
    private Context context;

    public PostAdapter(List<Post> data, Context context) {
        posts = data;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.post_item, parent, false);
        }

        Post post = posts.get(position);

        ImageView image = convertView.findViewById(R.id.image);
        TextView name = convertView.findViewById(R.id.name);
        TextView content = convertView.findViewById(R.id.content);
        TextView time = convertView.findViewById(R.id.time);
        if(post.getAuthor_image()!=0){
            image.setImageResource(post.getAuthor_image());
        }
        name.setText(post.getAuthor_name());
        content.setText(post.getContent());
        time.setText(post.getTime());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //Toast.makeText(context, "Item clicked: " + position, Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent();
//                intent.setClass(view.getContext(), NewsActivity.class);
//                view.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
