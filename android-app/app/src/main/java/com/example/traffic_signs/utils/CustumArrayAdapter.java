package com.example.traffic_signs.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.traffic_signs.MainActivity;
import com.example.traffic_signs.R;
import com.example.traffic_signs.Text_Content_Activity;

import java.util.ArrayList;
import java.util.List;

public class CustumArrayAdapter extends ArrayAdapter<ListItemClass> {
    private LayoutInflater inflater;
    private List<ListItemClass> listItem = new ArrayList<>();
    private Context context;

    public CustumArrayAdapter(@NonNull Context context, int resource,List<ListItemClass> listItem, LayoutInflater inflater) {
        super(context, resource, listItem);
        this.inflater = inflater;
        this.listItem = listItem;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        ListItemClass listItemMain = listItem.get(position);

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_view_item_1,null,false);
            viewHolder.image = convertView.findViewById(R.id.imItem);
            viewHolder.name = convertView.findViewById(R.id.tvName);
            viewHolder.secName = convertView.findViewById(R.id.tvSecName);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(listItemMain.getNameS());
        viewHolder.secName.setText(listItemMain.getSecond_name());
        viewHolder.image.setImageResource(listItemMain.image_id);


        return convertView;
    }
    private class ViewHolder{
        TextView name;
        TextView secName;
        ImageView image;
    }


}
