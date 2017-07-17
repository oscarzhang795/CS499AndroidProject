package com.oscar.roomiies.data;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.oscar.roomiies.R;

import java.util.List;

/**
 * Created by Oscar on 7/10/2017.
 */

public class RoommateAdapter extends ArrayAdapter<Roomate>{
    private Context context;
    private int layoutResource;
    private List<Roomate> roomateList;

    public RoommateAdapter(@NonNull Context context,
                            @LayoutRes int resource,
                            @NonNull List<Roomate> objects){
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.roomateList = objects;
    }

    private class ViewHolder{
        TextView roomateName;
        CheckBox name;
    }

    @NonNull
    @Override
    public View getView(int positon, @Nullable View convertView, @NonNull ViewGroup parent){
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater vi = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listview_checkbox, null);

            holder = new ViewHolder();
            holder.roomateName = (TextView) convertView.findViewById(R.id.roomateName);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkbox1);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.roomateName.setText(roomateList.get(positon).toString());

        return convertView;

    }


}
