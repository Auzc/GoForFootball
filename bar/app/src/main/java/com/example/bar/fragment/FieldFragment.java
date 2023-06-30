package com.example.bar.fragment;

import static com.example.bar.fragment.HomeFragment.mylocation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.bar.adapters.FieldListViewAdapter;
import com.example.bar.adapters.Fieldadapter;
import com.example.bar.R;
import com.example.bar.database.FieldListViewItem;
import com.example.bar.database.FootballField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FieldFragment extends Fragment {

    private ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field, container, false);


        listView = (ListView) view.findViewById(R.id.list_view);

        List<FieldListViewItem> listitem = new ArrayList<FieldListViewItem>();

        BmobQuery<FootballField> query = new BmobQuery<>();
        query.findObjects(new FindListener<FootballField>() {
            @Override
            public void done(List<FootballField> list, BmobException e) {
                if (e == null) {
                    if(mylocation!=null){
                        for(int i=0;i<list.size();i++){
                            FieldListViewItem fieldListViewItem=new FieldListViewItem();
                            fieldListViewItem.setFootballField(list.get(i));
                            fieldListViewItem.setDistance(distance(mylocation.getLatitude(),mylocation.getLongitude(),list.get(i).getAddress_lat(),list.get(i).getAddress_lng()));
                            listitem.add(fieldListViewItem);

                        }
                        Collections.sort(listitem, new Comparator<FieldListViewItem>() {
                            @Override
                            public int compare(FieldListViewItem item1, FieldListViewItem item2) {
                                return Double.compare(item1.getDistance(), item2.getDistance());
                            }
                        });

                        listView.setAdapter(new FieldListViewAdapter(listitem,view.getContext()));
                    }else{
                        listView.setAdapter(new Fieldadapter(list,view.getContext()));
                    }

                } else {
                    // 查询失败
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在这里处理点击事件
                view.performClick();
            }
        });


        return view;

    }
    public double  distance(Double lat1, Double lon1, float lat2, float lon2) {

        Double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        dist = Math.round(dist * 100.0) / 100.0;
        return dist;
    }

}
