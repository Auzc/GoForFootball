package com.example.bar.user;

import static com.example.bar.MainActivity.global_user;
import static com.example.bar.MainActivity.global_user_objectId;
import static com.example.bar.fragment.SettingsFragment.REQUEST_CODE_SELECT_IMAGES;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.MainActivity;
import com.example.bar.R;
import com.example.bar.database.User;
import com.example.bar.toolPhoto.SelectUserPictureActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class EditMessageActivity extends AppCompatActivity {
    NumberPicker heightPicker,weight_picker,age_picker;
    TextView user_name,user_id;
    EditText name_edit_text,signature_input;
    RadioButton male_radiobutton,female_radiobutton;
    Spinner position_spinner;
    Button save_button;
    ImageButton btn_back,ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_message);
        //getSupportActionBar().hide();//隐藏掉整个ActionBar
        user_name=findViewById(R.id.user_name);
        user_id=findViewById(R.id.user_id);
        name_edit_text=findViewById(R.id.name_edit_text);
        signature_input=findViewById(R.id.signature_input);
        male_radiobutton=findViewById(R.id.male_radiobutton);
        female_radiobutton=findViewById(R.id.female_radiobutton);
        position_spinner=findViewById(R.id.position_spinner);
        save_button=findViewById(R.id.save_button);
        btn_back = findViewById(R.id.btn_back);
        ImageView user_profile_image=findViewById(R.id.user_profile_image);
        if(global_user_objectId !=null){
            if(global_user.getUser_avatar()!=0){
                user_profile_image.setImageResource(global_user.getUser_avatar());
            }else{
                user_profile_image.setImageResource(R.drawable.avatar01);
            }

        }else{
            user_profile_image.setImageResource(R.drawable.avatar01);
        }
        user_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(EditMessageActivity.this, SelectUserPictureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGES);
            }
        });
        set_num();
        if(global_user_objectId !=null){
            user_name.setText(global_user.getUser_name());
            user_id.setText(global_user_objectId);
            if(global_user.getName()!=null){
                name_edit_text.setText(global_user.getName());
            }

            if(global_user.getHeight()>0){
                heightPicker.setValue(global_user.getHeight());
            }

            if(global_user.getWeight()>0){
                weight_picker.setValue(global_user.getWeight());
            }
            if(global_user.getAge()>0){
                age_picker.setValue(global_user.getAge());
            }

            if(global_user.getGender().equals("女")){
                female_radiobutton.setChecked(true);
                male_radiobutton.setChecked(false);
            }else{
                female_radiobutton.setChecked(false);
                male_radiobutton.setChecked(true);
            }
            if(global_user.getSignature()!=null){
                signature_input.setText(global_user.getSignature());
            }

        }

        heightPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // 处理用户选择的数值变化
                // 这里可以将数值显示在TextView上或者做其他操作
            }
        });
        weight_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });
        age_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });


        Spinner positionSpinner = findViewById(R.id.position_spinner);
        String selectedPosition = positionSpinner.getSelectedItem().toString();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok = findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User auser =new User();
                auser.setName(name_edit_text.getText().toString());
                if(female_radiobutton.isChecked()){
                    auser.setGender("女");
                }
                else{
                    auser.setGender("男");
                }
                auser.setLocation(position_spinner.getSelectedItem().toString());
                auser.setAge(age_picker.getValue());
                auser.setWeight(weight_picker.getValue());
                auser.setHeight(heightPicker.getValue());
                auser.setSignature(signature_input.getText().toString());
                auser.update(global_user_objectId, new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(EditMessageActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent();
                            intent.putExtra("user_objectId", global_user_objectId);
                            intent.setClass(EditMessageActivity.this, MainActivity.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(EditMessageActivity.this, "保存失败", Toast.LENGTH_SHORT).show();

                        }
                    }

                });
            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User auser =new User();
                auser.setName(name_edit_text.getText().toString());
                if(female_radiobutton.isChecked()){
                    auser.setGender("女");
                }
                else{
                    auser.setGender("男");
                }
                auser.setLocation(position_spinner.getSelectedItem().toString());
                auser.setAge(age_picker.getValue());
                auser.setWeight(weight_picker.getValue());
                auser.setHeight(heightPicker.getHeight());
                auser.setSignature(signature_input.getText().toString());
                auser.update(global_user_objectId, new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(EditMessageActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent();
                            intent.putExtra("user_objectId", global_user_objectId);
                            intent.setClass(EditMessageActivity.this, MainActivity.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(EditMessageActivity.this, "保存失败", Toast.LENGTH_SHORT).show();

                        }
                    }

                });
            }
        });


    }
    private String[] getHeightDisplayValues() {
        String[] displayValues = new String[131];
        for (int i = 0; i < displayValues.length; i++) {
            displayValues[i] = String.valueOf(100 + i);
        }
        return displayValues;
    }
    private String[] getWeightDisplayValues() {
        String[] displayValues = new String[66];
        for (int i = 0; i < displayValues.length; i++) {
            displayValues[i] = String.valueOf(35 + i);
        }
        return displayValues;
    }
    private String[] getAgeDisplayValues() {
        String[] displayValues = new String[76];
        for (int i = 0; i < displayValues.length; i++) {
            displayValues[i] = String.valueOf(5 + i);
        }
        return displayValues;
    }
    private void close(){

    }
    private void set_num(){
        heightPicker = findViewById(R.id.height_picker);
        weight_picker =findViewById(R.id.weight_picker);
        age_picker =findViewById(R.id.age_picker);
// 设置最小值和最大值
        heightPicker.setMinValue(100);
        heightPicker.setMaxValue(230);
        weight_picker.setMaxValue(100);
        weight_picker.setMinValue(35);
        age_picker.setMinValue(5);
        age_picker.setMaxValue(80);
// 设置初始值为150
        heightPicker.setValue(180);
        weight_picker.setValue(70);
        age_picker.setValue(20);
// 设置选择器的显示范围
// 如果不设置的话，默认是显示3个数值
// 这里设置为显示5个数值，可以根据实际情况调整
        heightPicker.setDisplayedValues(getHeightDisplayValues());
        weight_picker.setDisplayedValues(getWeightDisplayValues());
        age_picker.setDisplayedValues(getAgeDisplayValues());

    }

}
