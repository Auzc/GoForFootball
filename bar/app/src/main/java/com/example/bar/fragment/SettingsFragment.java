package com.example.bar.fragment;

import static android.app.Activity.RESULT_OK;
import static com.example.bar.MainActivity.global_user;
import static com.example.bar.MainActivity.global_user_objectId;


import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.bar.user.AdviceActivity;
import com.example.bar.R;

import com.example.bar.toolPhoto.SelectUserPictureActivity;
import com.example.bar.user.SettingsActivity;
import com.example.bar.user.LoginActivity;
import com.example.bar.user.EditMessageActivity;


public class SettingsFragment extends Fragment {

    private ImageView userProfileImage;
    private Button button_user_nickname,advice;
    private TextView hello,signature,location;

    private Button wdsc;
    public static final int REQUEST_CODE_SELECT_IMAGES = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        userProfileImage = view.findViewById(R.id.user_profile_image);
        button_user_nickname = view.findViewById(R.id.button_user_nickname);
        hello =  view.findViewById(R.id.hello);
        wdsc = view.findViewById(R.id.watting01);
        wdsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        Bitmap bitmap;
        TextView user_id=view.findViewById(R.id.user_id);
        signature=view.findViewById(R.id.signature);
        location=view.findViewById(R.id.location);
        if(global_user_objectId !=null){
            button_user_nickname.setText(global_user.getUser_name());
            user_id.setText(global_user_objectId);
            if(global_user.getName()!=null)
                hello.setText("👋你好！"+ global_user.getName());
            else
                hello.setText("👋你好！");
            if(global_user.getUser_avatar()!=0){
                userProfileImage.setImageResource(global_user.getUser_avatar());
            }else{
                userProfileImage.setImageResource(R.drawable.avatar01);
            }
            if(global_user.getLocation()!=null){
                location.setText(global_user.getLocation());
            }
            if(global_user.getSignature()!=null){
                signature.setText(global_user.getSignature());
            }

        }else{
            userProfileImage.setImageResource(R.drawable.avatar01);
        }
        advice=view.findViewById(R.id.advice);
        advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();

                intent.setClass(getActivity(), AdviceActivity.class);
                startActivity(intent);
            }
        });


        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), SelectUserPictureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGES);

            }
        });

        button_user_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(global_user_objectId ==null){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        Button button_message_edit = view.findViewById(R.id.button_message_edit);
        button_message_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), EditMessageActivity.class);
                startActivity(intent);
            }
        });
        ImageView action_settings = view.findViewById(R.id.action_settings);
        action_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_n);

                action_settings.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        //动画开始时执行的代码
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), SettingsActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_SELECT_IMAGES){
                int image = data.getIntExtra("image_int",0);
                userProfileImage.setImageResource(image);
//                Toast.makeText(getActivity(), ""+image, Toast.LENGTH_SHORT).show();

            }
        }


    }


}
