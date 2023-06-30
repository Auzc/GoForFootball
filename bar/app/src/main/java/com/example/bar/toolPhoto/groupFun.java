package com.example.bar.toolPhoto;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.example.bar.R;
import com.example.bar.database.FootballField;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class groupFun {

    private Context context;
    public void intiGroupClass(Context tt){
        context =tt;
    }
    public void initBomb(){
        Bmob.initialize(context, "8f956b6b382653af09c008d2ed40889b");
    }
    //输入图片名和图片的路径，保存图片
    public void savePhoto(String groupName,String pathnames){
        // 从文件中创建 BmobFile 对象
        BmobFile file = new BmobFile(new File(pathnames));

        // 上传图片到 Bomb 服务器
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    // 上传成功，将 BmobFile 对象保存到数据库中
                    FootballField gg = new FootballField();
                    gg.setName(groupName);
                    gg.setFile(file);
                    gg.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(context, "数据保存成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "数据保存失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void saveItem(String na){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cr7);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        BmobFile bmobFile = new BmobFile("avatar.png", null, "data:image/png;base64," + base64);
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    // 文件上传成功，可以将文件的URL保存到Bomb云数据库中
                    String url = bmobFile.getFileUrl();

                    // 创建一个MyData对象并保存到Bomb云数据库中
                    FootballField data = new FootballField();
                    data.setName(na);
                    data.setFile(bmobFile);
                    data.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(context, "数据保存成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "数据保存失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public  void show(){
        BmobQuery<FootballField> query = new BmobQuery<>();
        query.findObjects(new FindListener<FootballField>() {
            @Override
            public void done(List<FootballField> list, BmobException e) {
                if (e == null) {


                } else {
                    // 查询失败
                }
            }
        });
    }

}
