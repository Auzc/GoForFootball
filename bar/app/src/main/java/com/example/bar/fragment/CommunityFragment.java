package com.example.bar.fragment;

import static com.example.bar.MainActivity.global_user;
import static com.example.bar.MainActivity.global_user_objectId;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bar.adapters.NewsAdapter;
import com.example.bar.R;
import com.example.bar.adapters.PostAdapter;
import com.example.bar.database.News;
import com.example.bar.database.Post;
import com.example.bar.post.AddPostActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CommunityFragment extends Fragment{


    private ImageButton add_new;
    private ProgressBar mProgressBar;
    private ListView listView;
    List<Post> listitem = new ArrayList<Post>();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        add_new= view.findViewById(R.id.add_new);
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(global_user_objectId!=null&&global_user!=null){
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_n);

                    add_new.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            //动画开始时执行的代码
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), AddPostActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            //动画重复时执行的代码
                        }
                    });

                }else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }


            }
        });


        listView =  view.findViewById(R.id.listview);

        mProgressBar = view.findViewById(R.id.progressBar);


// Get data from the cloud
        getDataFromCloud();

        listView.setAdapter(new PostAdapter(listitem,view.getContext()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在这里处理点击事件
                view.performClick();
            }
        });



//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                onResume();
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });



        //mSwipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }




    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getDataFromCloud() {

        // 显示进度条
        mProgressBar.setVisibility(View.VISIBLE);
        // ... code to get data from cloud ...

        BmobQuery<Post> query = new BmobQuery<>();
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e == null) {
                    // 反转列表
                    Collections.reverse(list);
                    listitem.clear();
                    listitem.addAll(list);
                } else {
                    // 查询失败
                }
            }
        });

//// 隐藏进度条
//        mProgressBar.setVisibility(View.GONE);


    }

}