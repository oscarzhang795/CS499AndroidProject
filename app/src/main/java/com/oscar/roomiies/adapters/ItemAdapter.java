package com.oscar.roomiies.adapters;

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
import com.oscar.roomiies.data.ToBuyItem;

import java.util.List;

/**
 * Created by Oscar on 7/10/2017.
 */

public class ItemAdapter extends ArrayAdapter<ToBuyItem>{
    private Context context;
    private int layoutResource;
    private List<ToBuyItem> itemList;

    public ItemAdapter(@NonNull Context context,
                       @LayoutRes int resource,
                       @NonNull List<ToBuyItem> objects){
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.itemList = objects;
    }

    private class ViewHolder{
        TextView itemName;
        CheckBox checkBox;
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
            holder.itemName = (TextView) convertView.findViewById(R.id.roomateName);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox1);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemName.setText(itemList.get(positon).toString());
        if(itemList.get(positon).isBought() == true){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

        return convertView;

    }


}
