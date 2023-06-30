package com.example.bar.toolPhoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bar.R;

import java.util.ArrayList;
import java.util.List;

public class SelectPictureActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_IMAGE = 1;
    private RecyclerView recyclerView;
    private ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        button = findViewById(R.id.button_cancels);
        List<String> imageUrlList = new ArrayList<>();

        imageUrlList.add("http://www.aolinty.com/Public/Uploads/Products/20161126/58393f4f4d62a.jpg");
        imageUrlList.add("http://www.aolinty.com/Public/Uploads/Products/20161126/58393bde2b395.jpg");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageAdapter adapter = new ImageAdapter(imageUrlList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Send the selected image URL back to Activity A
                String imageUrl = imageUrlList.get(position);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("image_url", imageUrl);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    // Adapter for the RecyclerView
    private  class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

        private List<String> mImageUrlList;
        private OnItemClickListener mListener;

        public ImageAdapter(List<String> imageUrlList) {
            mImageUrlList = imageUrlList;
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String imageUrl = mImageUrlList.get(position);

            // Use a network library (such as Glide or Picasso) to load the image
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return mImageUrlList.size();
        }

        // ViewHolder for the RecyclerView
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView imageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.image_view);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(getAdapterPosition());
                }
            }
        }
    }

    // Listener interface for list item clicks
    private interface OnItemClickListener {
        void onItemClick(int position);
    }
}