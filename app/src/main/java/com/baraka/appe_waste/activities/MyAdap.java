package com.baraka.appe_waste.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.baraka.appe_waste.R;

import java.util.List;
import java.util.Map;

public class MyAdap extends BaseExpandableListAdapter {
   Context context;
   List<String>names;
   Map<String,List<String>>childs;
public MyAdap(Context context,List<String>names,Map<String,List<String>>childs)
{
   this.context=context;
   this.names=names;
   this.childs=childs;
}
   @Override
   public int getGroupCount() {
      return names.size();
   }

   @Override
   public int getChildrenCount(int groupPosition) {
      return childs.get(names.get(groupPosition)).size();
   }

   @Override
   public Object getGroup(int groupPosition) {
      return names.get(groupPosition);
   }

   @Override
   public Object getChild(int groupPosition, int childPosition) {
      return childs.get(names.get(groupPosition)).get(childPosition);
   }

   @Override
   public long getGroupId(int groupPosition) {
      return groupPosition;
   }

   @Override
   public long getChildId(int groupPosition, int childPosition) {
      return childPosition;
   }

   @Override
   public boolean hasStableIds() {
      return false;
   }

   @Override
   public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
      String lang=(String) getGroup(groupPosition);
      if (convertView==null)
      {
         LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
         convertView=inflater.inflate(R.layout.parent_list,null);

      }
      TextView txtparent=convertView.findViewById(R.id.parentlist);
      txtparent.setText(lang);
      return convertView;
   }

   @Override
   public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
      String dir=(String)getChild(groupPosition,childPosition);
      if (convertView==null)
      {
         LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
         convertView=inflater.inflate(R.layout.child_list,null);

      }
      TextView txtchild=convertView.findViewById(R.id.childlist);
      txtchild.setText(dir);
      return convertView;
   }

   @Override
   public boolean isChildSelectable(int groupPosition, int childPosition) {
      return true;
   }
}
