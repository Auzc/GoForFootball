package com.example.bar.toolPhoto;

import static com.example.bar.MainActivity.global_user_objectId;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bar.R;
import com.example.bar.database.User;

import java.util.ArrayList;
import java.util.List;
import static com.example.bar.MainActivity.global_user;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class SelectUserPictureActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_IMAGE = 1;
    private RecyclerView recyclerView;
    private ImageButton button;
    private List<Integer> imageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        button = findViewById(R.id.button_cancels);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageList = new ArrayList<>();

        imageList.add(R.drawable.avatar01);
        imageList.add(R.drawable.avatar02);
        imageList.add(R.drawable.avatar03);
        imageList.add(R.drawable.avatar04);
        imageList.add(R.drawable.avatar05);
        imageList.add(R.drawable.avatar06);
        imageList.add(R.drawable.avatar07);
        imageList.add(R.drawable.avatar08);
        imageList.add(R.drawable.avatar10);
        imageList.add(R.drawable.avatar11);
        imageList.add(R.drawable.avatar12);
        imageList.add(R.drawable.avatar13);
        imageList.add(R.drawable.avatar14);
        imageList.add(R.drawable.avatar15);
        imageList.add(R.drawable.avatar16);
        imageList.add(R.drawable.avatar17);
        imageList.add(R.drawable.avatar18);
        imageList.add(R.drawable.avatar19);
        imageList.add(R.drawable.avatar20);
        imageList.add(R.drawable.avatar25);
        imageList.add(R.drawable.avatar27);
        imageList.add(R.drawable.avatar31);
        imageList.add(R.drawable.avatar32);
        imageList.add(R.drawable.avatar33);
        imageList.add(R.drawable.avatar34);
        imageList.add(R.drawable.avatar35);
        imageList.add(R.drawable.avatar36);
        imageList.add(R.drawable.avatar37);
        imageList.add(R.drawable.avatar38);
        imageList.add(R.drawable.avatar39);
        imageList.add(R.drawable.avatar40);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageAdapter adapter = new ImageAdapter(imageList);
        recyclerView.setAdapter(adapter);


    }
    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private List<Integer> mImageResources;


        public ImageAdapter(List<Integer> imageResources) {
            mImageResources = imageResources;

        }


        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_item, parent, false);
            return new ImageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Integer resource = mImageResources.get(position);
            holder.imageView.setImageResource(resource);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer image = imageList.get(position);
                    int user_avatar=image;
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("image_int", image);
                    setResult(RESULT_OK, resultIntent);
                    if(global_user_objectId !=null){
                        User auser =new User();

                        auser.setUser_avatar(user_avatar);

                        auser.update(global_user_objectId, new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(SelectUserPictureActivity.this, "头像修改成功", Toast.LENGTH_SHORT).show();
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

                                }else{
                                    Toast.makeText(SelectUserPictureActivity.this, "头像修改失败", Toast.LENGTH_SHORT).show();

                                }
                            }

                        });
                    }
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mImageResources.size();
        }

        class ImageViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image_view);
            }

        }
    }




}