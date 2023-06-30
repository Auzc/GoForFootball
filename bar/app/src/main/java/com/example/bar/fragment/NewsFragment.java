package com.example.bar.fragment;

import static com.example.bar.fragment.HomeFragment.mylocation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.bar.R;
import com.example.bar.adapters.FieldListViewAdapter;
import com.example.bar.adapters.Fieldadapter;
import com.example.bar.adapters.NewsAdapter;
import com.example.bar.database.FieldListViewItem;
import com.example.bar.database.FootballField;
import com.example.bar.database.News;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NewsFragment extends Fragment {
    private ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);



        listView = (ListView) view.findViewById(R.id.list_view);

        List<News> listitem = new ArrayList<News>();

        BmobQuery<News> query = new BmobQuery<>();
        query.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if (e == null) {
                    Collections.reverse(list);

                    listView.setAdapter(new NewsAdapter(list,view.getContext()));

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


        return view;

    }

}
