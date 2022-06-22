package com.baraka.appe_waste.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.baraka.appe_waste.R;
import com.baraka.appe_waste.electronics.MyAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectionsActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    List<String>names;
    Map<String, List<String>> childs;
    MyAdap adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        expandableListView=findViewById(R.id.expanded_list);
        getData();
        adp=new MyAdap(this,names,childs);
        expandableListView.setAdapter(adp);
    }

    private void getData() {
        names=new ArrayList<>();
        names.add("Nairobi");
        childs=new HashMap<>();
        List<String>Nairobi=new ArrayList<>();
        Nairobi.add("Collection Offices located inside Nairobi CBD, 2floor room NO.25");
        childs.put(names.get(0),Nairobi);
    }
}