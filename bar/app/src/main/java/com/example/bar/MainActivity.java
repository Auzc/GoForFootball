package com.example.bar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bar.database.News;
import com.example.bar.database.User;
import com.example.bar.fragment.CommunityFragment;
import com.example.bar.fragment.HomeFragment;
import com.example.bar.fragment.MessagesFragment;
import com.example.bar.fragment.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment;
    private MessagesFragment messagesFragment;
    private CommunityFragment communityFragment;
    private SettingsFragment settingsFragment;

    public  static User global_user =new User();

    public  static User userInfos = new User();
    public  static String global_user_objectId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        News data = new News();
//        data.setTitle("曼联官方：利马跖骨骨折赛季报销，瓦拉内将缺席数周");
//        data.setSource("四条腿的车");
//        data.setTime("2023-04-15 03:32");
//        data.setUrl("https://m.dongqiudi.com/article/3384357.html");
//        data.setPhotourl("https://bdimg6.qunliao.info/fastdfs6/M00/51/7F/720x-/-/-/rBUCgGQ5q8SAYbgLAAKIQhoYGQ8560.jpg?watermark/1/image/aHR0cHM6Ly9pbWcxLmRvbmdxaXVkaS5jb20vZmFzdGRmczIvTTAwLzJBL0UyLzEyMHgtLy0vLS9DaE9xTTFvUy1lV0FQRHEzQUFCQTVVZ3JCVDgxNDIucG5n/dissolve/100/dx/14/dy/10");
//
//        data.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    Toast.makeText(MainActivity.this, "数据保存成功", Toast.LENGTH_SHORT).show();
//                }else{
//
//                }
//            }
//        });

        String temp=getIntent().getStringExtra("user_objectId");
        if(temp==null){

        }else{

            global_user_objectId =temp;
        }







        if(global_user_objectId !=null){

                BmobQuery<User> bmobQuery = new BmobQuery<User>();
                bmobQuery.getObject(global_user_objectId, new QueryListener<User>() {
                    @Override
                    public void done(User object, BmobException e) {
                        if(e==null){
                            global_user =object;
                        }else{

                        }
                    }
                });
        }



        // 初始化底部导航栏和Fragment
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        homeFragment = new HomeFragment();
        messagesFragment = new MessagesFragment();
        settingsFragment = new SettingsFragment();
        communityFragment = new CommunityFragment();
        // 默认显示HomeFragment
        setFragment(homeFragment);

        // 底部导航栏选中项变化的监听器
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.action_messages:
                        setFragment(messagesFragment);
                        return true;
                    case R.id.action_community:
                        setFragment(communityFragment);
                        return true;
                    case R.id.action_settings:
                        setFragment(settingsFragment);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
