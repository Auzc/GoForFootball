package com.example.bar.fragment;


import static com.example.bar.MainActivity.global_user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.bar.field.AddFieldActivity;
import com.example.bar.R;

import com.example.bar.database.FootballField;
import com.example.bar.field.MessageOfFieldActivity;
import com.example.bar.map.MapActivity;
import com.google.android.material.tabs.TabLayout;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TencentMapInitializer;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeFragment extends Fragment  {


    private EditText editText;
    private MapView mapView;
    private TencentMap tencentMap;


    private TencentLocationManager mLocationManager;



    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ImageButton btn_add_field,btn_map,btn_football;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public  static TencentLocation mylocation = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        Bmob.initialize(view.getContext(), "3f7aa9c041871129a0a5b7cecc97dd83");


// 检查是否已经授予定位权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 如果没有授予权限，则向用户请求定位权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // 如果已经授予了定位权限，请执行相应的操作
            // do something...
        }





        TencentMapInitializer.setAgreePrivacy(true);

        mapView = view.findViewById(R.id.mapView);
        btn_add_field = view.findViewById(R.id.btn_add_field);
        btn_add_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), AddFieldActivity.class);
                // 启动目标 Activity
                startActivity(intent1);
            }
        });
        btn_map= view.findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), MapActivity.class);
                // 启动目标 Activity
                startActivity(intent1);
            }
        });


        tencentMap = mapView.getMap();
        TencentLocationManager.setUserAgreePrivacy(true);

        ImageButton myLocationButton = view.findViewById(R.id.my_location_button);
        // 获取定位管理器实例
        mLocationManager = TencentLocationManager.getInstance(getActivity());


        // 发起单次定位请求
        mLocationManager.requestSingleFreshLocation(null, locationListener, Looper.getMainLooper());


        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500); //动画持续时间为500毫秒
        scaleAnimation.setRepeatCount(1); //设置重复次数为1
        scaleAnimation.setRepeatMode(Animation.REVERSE); //设置重复模式为REVERSE，即反向播放

        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLocationButton.startAnimation(scaleAnimation);
                // 发起单次定位请求
                mLocationManager.requestSingleFreshLocation(null, locationListener, Looper.getMainLooper());
            }
        });




        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        // Set icons for each tab
        int[] tabIcons = {R.drawable.news, R.drawable.footballfield};
        for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }


        initMarker();
        btn_football=view.findViewById(R.id.btn_football);
        btn_football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_n);

                btn_football.startAnimation(animation);
                myLocationButton.performClick();

            }
        });
        tencentMap.setOnInfoWindowClickListener(new TencentMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                BmobQuery<FootballField> query = new BmobQuery<>();
                query.findObjects(new FindListener<FootballField>() {
                    @Override
                    public void done(List<FootballField> list, BmobException e) {
                        if (e == null) {
                            for(int i=0;i<list.size();i++){
                                if(list.get(i).getName().toString().equals(marker.getTitle().toString())){
                                    Intent intent =new Intent();
                                    intent.setClass(getActivity(), MessageOfFieldActivity.class);
                                    intent.putExtra("name", list.get(i).getName());
                                    intent.putExtra("avatar_file", list.get(i).getFile().getFileUrl());
                                    intent.putExtra("lat",list.get(i).getAddress_lat());
                                    intent.putExtra("lng",list.get(i).getAddress_lng());
                                    intent.putExtra("addressName",list.get(i).getAddress_name());
                                    intent.putExtra("field_size",list.get(i).getField_size());
                                    intent.putExtra("character",list.get(i).getCharacter());
                                    startActivity(intent);
                                }

                            }

                        } else {
                            // 查询失败
                        }
                    }
                });
                Log.i("TAG","InfoWindow被点击时回调函数");
            }
            //  windowWidth - InfoWindow的宽度
            //windowHigh - InfoWindow的高度
            // x - 点击点在InfoWindow的x坐标点
            //y - 点击点在InfoWindow的y坐标点
            @Override
            public void onInfoWindowClickLocation(int width, int height, int x, int y) {
                Log.i("TAG","当InfoWindow点击时，点击点的回调");
            }
        });



        return view;


    }



    TencentLocationListener locationListener = new TencentLocationListener() {
        @Override
        public void onLocationChanged(TencentLocation location, int error, String reason) {
            if (error == TencentLocation.ERROR_OK) {
                mylocation = location;
                // 定位成功
                String result = "您的位置：\n" +
                        "经度：" + location.getLongitude() + "\n" +
                        "纬度：" + location.getLatitude();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                tencentMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                Resources res = getResources();




                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.me);
                if(global_user.getUser_avatar()!=0){
                    bitmap = BitmapFactory.decodeResource(getResources(), global_user.getUser_avatar());
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, 48, 48, false);
                BitmapDescriptor custom = BitmapDescriptorFactory.fromBitmap(bitmap);
                Marker mCustomMarker = tencentMap.addMarker(new MarkerOptions(latLng)
                        .icon(custom)
                        .alpha(1f)
                        .flat(true)
                        .clockwise(false)
                        .rotation(0));



//                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            } else {
                // 定位失败
                String result = "定位失败：" + reason;
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onStatusUpdate(String name, int status, String desc) {
            // do nothing
        }
    };

    /**
     * MapView的生命周期管理
     */
    public void initMarker(){
        BmobQuery<FootballField> query = new BmobQuery<>();
        query.findObjects(new FindListener<FootballField>() {
            @Override
            public void done(List<FootballField> list, BmobException e) {
                if (e == null) {
                    for(int i=0;i<list.size();i++){
                        LatLng latLng = new LatLng(list.get(i).getAddress_lat(), list.get(i).getAddress_lng());
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.field);
                        bitmap = Bitmap.createScaledBitmap(bitmap, 96, 96, false);
                        BitmapDescriptor custom = BitmapDescriptorFactory.fromBitmap(bitmap);
//                        Marker mCustomMarker = tencentMap.addMarker(new MarkerOptions(latLng)
//                                .icon(custom)
//                                .alpha(1f)
//                                .flat(true)
//                                .clockwise(false)
//                                .rotation(0));
                        MarkerOptions options = new MarkerOptions(latLng);
                        options.infoWindowEnable(false);//默认为true
                        options.title(list.get(i).getName())//标注的InfoWindow的标题

                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap));//设置自定义Marker图标


                        Marker mMarker = tencentMap.addMarker(options);
                        //开启信息窗口
                        mMarker.setInfoWindowEnable(true);
                    }

                } else {
                    // 查询失败
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        // 停止定位
        mLocationManager.removeUpdates(locationListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mapView = null;
    }

    private void setupViewPager(ViewPager viewPager) {
        HomeFragment.Adapter adapter = new HomeFragment.Adapter(getChildFragmentManager());

        adapter.addFragment(new NewsFragment(), R.drawable.news);
        adapter.addFragment(new FieldFragment(), R.drawable.footballfield);

        viewPager.setAdapter(adapter);
    }

    private static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<Integer> fragmentIconList = new ArrayList<>();

        public Adapter(FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragmentList.get(position);
            int iconResId = fragmentIconList.get(position);
            Bundle args = new Bundle();
            args.putInt("icon_res_id", iconResId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment,  int iconResId) {
            fragmentList.add(fragment);

            fragmentIconList.add(iconResId);
        }


    }
    // 处理用户响应
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户已授予定位权限
                // do something...
            } else {
                // 用户拒绝了定位权限
                // do something...
            }
        }
    }

}