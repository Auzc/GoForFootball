package com.example.bar.map;

import static com.example.bar.MainActivity.global_user;
import static com.example.bar.fragment.HomeFragment.mylocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.bar.R;
import com.example.bar.adapters.FieldListViewAdapter;
import com.example.bar.adapters.Fieldadapter;
import com.example.bar.database.FieldListViewItem;
import com.example.bar.database.FootballField;
import com.example.bar.field.MessageOfFieldActivity;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MapActivity extends AppCompatActivity implements
        TencentLocationListener {
    private MapView mapView;
    private TencentMap tencentMap;
    private ImageButton btn_back,button;

    private TencentLocationManager mLocationManager;

    private  ArrayList<Marker> list1 = new ArrayList<>();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
// 检查是否已经授予定位权限
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 如果没有授予权限，则向用户请求定位权限
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // 如果已经授予了定位权限，请执行相应的操作
            // do something...
        }

        mapView = findViewById(R.id.mapView);

        tencentMap = mapView.getMap();
        TencentLocationManager.setUserAgreePrivacy(true);

        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        ImageButton myLocationButton = findViewById(R.id.my_location_button);
        // 获取定位管理器实例
        mLocationManager = TencentLocationManager.getInstance(this);

        // 发起单次定位请求
        mLocationManager.requestSingleFreshLocation(null, MapActivity.this, Looper.getMainLooper());
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500); //动画持续时间为500毫秒
        scaleAnimation.setRepeatCount(1); //设置重复次数为1
        scaleAnimation.setRepeatMode(Animation.REVERSE); //设置重复模式为REVERSE，即反向播放
// 设置定位按钮点击事件
        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLocationButton.startAnimation(scaleAnimation);
                // 发起单次定位请求
                mLocationManager.requestSingleFreshLocation(null, MapActivity.this, Looper.getMainLooper());
            }
        });


        initMarker();
        //设置点击事件
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
                                    intent.setClass(MapActivity.this, MessageOfFieldActivity.class);
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

            @Override
            public void onInfoWindowClickLocation(int width, int height, int x, int y) {
                Log.i("TAG","当InfoWindow点击时，点击点的回调");
            }
        });
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MapActivity.this, R.anim.rotate_n);
                button.startAnimation(animation);
                myLocationButton.performClick();

            }
        });
    }

    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason) {
        if (error == TencentLocation.ERROR_OK) {
            // 定位成功
            String result = "您的位置：\n" +
                    "经度：" + location.getLongitude() + "\n" +
                    "纬度：" + location.getLatitude();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            tencentMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
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


            //Toast.makeText(MapActivity.this, result, Toast.LENGTH_SHORT).show();
        } else {
            // 定位失败
            String result = "定位失败：" + reason;
            Toast.makeText(MapActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusUpdate(String name, int status, String desc) {
        // do nothing
    }



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
                        list1.add(mMarker);
                    }

                } else {
                    // 查询失败
                }
            }
        });
    }

    /**
     * mapview的生命周期管理
     */
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        // 停止定位
        mLocationManager.removeUpdates(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mapView.onRestart();
    }




}
