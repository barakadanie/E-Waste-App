package com.baraka.appe_waste.electronics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.baraka.appe_waste.R;

import java.util.ArrayList;
import java.util.List;

public class MobileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    ListView listView;
    List<Pojo> list;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mobile Devices");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView=findViewById(R.id.list_view);
        listShow();
        myAdapter=new MyAdapter(this,list);
        listView.setAdapter(myAdapter);

    }

    private void listShow() {
        list=new ArrayList<>();
        list.add(new Pojo(R.drawable.pager,"Pager",""));
        list.add(new Pojo(R.drawable.pager,"Pager",""));
        list.add(new Pojo(R.drawable.pager,"Pager",""));
        list.add(new Pojo(R.drawable.pager,"Pager",""));
        list.add(new Pojo(R.drawable.pager,"Pager",""));
        list.add(new Pojo(R.drawable.pager,"Pager",""));
        list.add(new Pojo(R.drawable.pager,"Pager",""));
        list.add(new Pojo(R.drawable.pager,"Pager",""));
        list.add(new Pojo(R.drawable.pager,"Pager","A pager (also known as a beeper or bleeper) is a wireless telecommunications device that receives and displays alphanumeric or voice messages."));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}