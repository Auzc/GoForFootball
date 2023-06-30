package com.example.bar.field;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.bar.R;
import com.example.bar.chat.ChatActivity;
import com.example.bar.toolPhoto.SelectPictureActivity;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TencentMapInitializer;
import com.tencent.tencentmap.mapsdk.maps.TextureMapView;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MessageOfFieldActivity extends AppCompatActivity {

    private ImageButton btn_back,btn_football_field;
    private ViewPager mViewPager;
    private ArrayList<String> mImageList = new ArrayList<>();
    private TextureMapView mapView;
    private TencentMap tencentMap;
    private String name;
    private String avatarFile;
    private Float lat;
    private Float lng;
    private String addressName;
    private String field_size;
    private String character;

    private ImageView imageView_in;
    private TextView textView_inNa;
    private TextView address0,address;
    private TextView textView_isopen;
    private TextView textView_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_message);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        avatarFile = intent.getStringExtra("avatar_file");
        lat = intent.getFloatExtra("lat",0);
        lng = intent.getFloatExtra("lng",0);
        addressName = intent.getStringExtra("addressName");
        field_size = intent.getStringExtra("field_size");
        character = intent.getStringExtra("character");


        imageView_in = findViewById(R.id.showtp);
        if (avatarFile != null) {
            //Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            Glide.with(MessageOfFieldActivity.this).load(avatarFile).into(imageView_in);
        }
        textView_inNa = findViewById(R.id.Names);
        textView_inNa.setText(name);
        address0 = findViewById(R.id.address0);
        address0.setText("地址："+addressName);
//        address = findViewById(R.id.address);
//        address.setText("经度： "+lng+"\n纬度： "+lat);
        textView_isopen = findViewById(R.id.isopen);
        textView_isopen.setText("对外开放  "+character);
        textView_num = findViewById(R.id.size_field);
        textView_num.setText(field_size+"人制标准足球场");

        TencentMapInitializer.setAgreePrivacy(true);

        mapView = findViewById(R.id.mapView);
        LatLng position = new LatLng(lat,lng);


        tencentMap = mapView.getMap();
        tencentMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 14));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stadium);
        bitmap = Bitmap.createScaledBitmap(bitmap, 96, 96, false);
        BitmapDescriptor custom = BitmapDescriptorFactory.fromBitmap(bitmap);
        Marker mCustomMarker = tencentMap.addMarker(new MarkerOptions(position)
                .icon(custom)
                .alpha(1f)
                .flat(true)
                .clockwise(false)
                .rotation(0));

        btn_football_field=findViewById(R.id.btn_football_field);
        btn_football_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MessageOfFieldActivity.this, R.anim.rotate_n);

                btn_football_field.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        //动画开始时执行的代码
                        Intent intent = new Intent();

                        intent.setClass(MessageOfFieldActivity.this, ChatActivity.class);
                        intent.putExtra("chatroom_name", name);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //动画结束时执行的代码
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        //动画重复时执行的代码
                    }
                });
            }
        });


        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mViewPager = findViewById(R.id.view_pager);

        mImageList.add("http://www.aolinty.com/Public/Uploads/Products/20161126/58393f4f4d62a.jpg");
        mImageList.add("http://www.aolinty.com/Public/Uploads/Products/20161126/58393bde2b395.jpg");

        mImageList.add("http://www.aolinty.com/Public/Uploads/Products/20161126/58393bde2b395.jpg");
        // 设置ViewPager适配器
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = new ImageView(MessageOfFieldActivity.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(MessageOfFieldActivity.this)
                        .load(mImageList.get(position))
                        .into(imageView);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((ImageView)object);
            }

            @Override
            public int getCount() {
                return mImageList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        };
        mViewPager.setAdapter(pagerAdapter);

        // 设置ViewPager自动轮播
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentItem = mViewPager.getCurrentItem();
                if (currentItem == mImageList.size() - 1) {
                    mViewPager.setCurrentItem(0);
                } else {
                    mViewPager.setCurrentItem(currentItem + 1);
                }
                mViewPager.postDelayed(this, 5000); // 延时5秒再次执行
            }
        }, 5000); // 延时5秒执行第一次
    }
}