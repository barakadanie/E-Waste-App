package com.baraka.appe_waste.electronics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baraka.appe_waste.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    Context context;
    List<Pojo> listPojos;
    public  MyAdapter(Context context, List<Pojo>listPojos)
    {
        this.context=context;
        this.listPojos=listPojos;
    }
    @Override
    public int getCount() {
        return listPojos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        ImageView image= convertView.findViewById(R.id.image);
        TextView title= convertView.findViewById(R.id.title);
        TextView description= convertView.findViewById(R.id.description);
        title.setText(listPojos.get(position).getTitle());
        description.setText(listPojos.get(position).getDescription());
        image.setImageResource(listPojos.get(position).getImage());
        
        return convertView;
    }
}
