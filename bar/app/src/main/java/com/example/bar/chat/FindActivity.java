package com.example.bar.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.R;
import com.example.bar.adapters.ChatRoomAdapter;
import com.example.bar.database.FootballField;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FindActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener {
    ListView listView;
    private SearchView mSearchView;
    private ChatRoomAdapter myadpter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        listView = findViewById(R.id.list_view);
        ImageButton btn_back;
        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BmobQuery<FootballField> query = new BmobQuery<>();
        query.findObjects(new FindListener<FootballField>() {
            @Override
            public void done(List<FootballField> list, BmobException e) {
                if (e == null) {
                    myadpter=new ChatRoomAdapter(list,FindActivity.this);
                    listView.setAdapter(myadpter);
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
        // 获取 SearchView 对象
        mSearchView = findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 在用户提交查询时执行操作
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 当搜索文本发生更改时执行操作
                if (myadpter != null) {
                    myadpter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        myadpter.getFilter().filter(newText);
        return true;
    }
}
