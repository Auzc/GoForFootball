package com.example.bar.field;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bar.R;
import com.example.bar.database.FootballField;
import com.example.bar.map.SelectPlaceActivity;
import com.example.bar.toolPhoto.SelectPictureActivity;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddFieldActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private ImageButton btn_back;
    private Button btn_submit;
    private ImageButton button_select_picture,btn_football_field;
    private WebView mWebView;

    private ImageView mImageView;

    private String imageUrl = null;

    public static final int REQUEST_CODE_SELECT_IMAGE = 1;

    public static final int REQUEST_CODE_SELECT_MAP = 2;

    private Float lat=null;
    private Float lng=null;
    private String address=null;
    private TextView textView;
    private RadioGroup radioGroup;

    private String selectedValue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fields);
        editText1 = findViewById(R.id.name_group);
        btn_back = findViewById(R.id.btn_back);
        btn_football_field = findViewById(R.id.btn_football_field);
        textView = findViewById(R.id.address_text);
        editText2 = findViewById(R.id.size_group);
        mImageView = findViewById(R.id.showtp);
        radioGroup = findViewById(R.id.gender_radiogroup);

        button_select_picture = findViewById(R.id.button_select_picture);
        button_select_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFieldActivity.this, SelectPictureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);

            }
        });



        btn_football_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(AddFieldActivity.this, R.anim.rotate_n);
                btn_football_field.startAnimation(animation);
                if(imageUrl == null){
                    Toast.makeText(AddFieldActivity.this, "还没有选择图片，请选择图片！", Toast.LENGTH_SHORT).show();
                }else if(editText1.getText().toString().trim().length() == 0) {
                    Toast.makeText(AddFieldActivity.this, "还没有输入球场名称，请输入球场名称！", Toast.LENGTH_SHORT).show();
                }else if(lat == null) {
                    Toast.makeText(AddFieldActivity.this, "还没有选择球场地址，请选择球场地址！", Toast.LENGTH_SHORT).show();
                }else if(editText2.getText().toString().trim().length() == 0) {
                    Toast.makeText(AddFieldActivity.this, "请输入几人制!", Toast.LENGTH_SHORT).show();
                }else{
                    BmobFile bmobFile = new BmobFile("avatar.png", null, imageUrl.toString());
                    int selectedId = radioGroup.getCheckedRadioButtonId();

                    if (selectedId != -1) {
                        RadioButton selectedRadioButton = findViewById(selectedId);
                        selectedValue = selectedRadioButton.getText().toString();
                    }
                    FootballField data = new FootballField();
                    data.setName(editText1.getText().toString().trim());
                    data.setFile(bmobFile);
                    data.setAddress_lat(lat);
                    data.setAddress_lng(lng);
                    data.setAddress_name(address);
                    data.setField_size(editText2.getText().toString().trim());
                    data.setCharacter(selectedValue);
                    data.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(AddFieldActivity.this, "数据保存成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AddFieldActivity.this, "已有该球场名,请再换一个！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    finish();
                }

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setClass(AddFieldActivity.this, SelectPlaceActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_MAP);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_SELECT_IMAGE){
                imageUrl = data.getStringExtra("image_url");
                Glide.with(this).load(imageUrl).into(mImageView);
            }else if(requestCode == REQUEST_CODE_SELECT_MAP){
                lat = data.getFloatExtra("lat",0);
                lng = data.getFloatExtra("lng",0);
                address = data.getStringExtra("address");
                textView.setText("地址："+address+"\n"+"经度： "+lng+"    纬度： "+lat);
            }
        }


    }

}