package com.example.bar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bar.chat.FindActivity;
import com.example.bar.R;
import com.example.bar.chat.MyChatRoomActivity;

public class FindFragment extends Fragment implements SearchView.OnQueryTextListener{
    private ListView listView;
    private Button button_find,my;
    private SearchView mSearchView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FieldFragment fieldFragment = new FieldFragment();
        fragmentTransaction.add(R.id.field_fragment_container, fieldFragment);
        fragmentTransaction.commit();
        // 获取 SearchView 对象并设置监听器
        mSearchView = view.findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(this);

        button_find=view.findViewById(R.id.button_find);
        button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(getActivity(), FindActivity.class));
            }
        });
        my=view.findViewById(R.id.my);
        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), com.example.bar.chat.MyChatRoomActivity.class));
            }
        });
        return view;

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        // 处理搜索框提交事件
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // 处理搜索框文本变化事件
        //mAdapter.getFilter().filter(newText);
        return true;
    }
}
