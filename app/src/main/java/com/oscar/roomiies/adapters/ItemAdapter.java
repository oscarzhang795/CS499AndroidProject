package com.oscar.roomiies.adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oscar.roomiies.R;
import com.oscar.roomiies.data.ToBuyItem;
import com.oscar.roomiies.data.User;

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
    public View getView(final int positon, @Nullable View convertView, @NonNull ViewGroup parent){
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

        Log.i("POSITION 1", positon +"");

        holder.itemName.setText(itemList.get(positon).toString());
        if(itemList.get(positon).isBought() == true){
            holder.checkBox.setChecked(true);
        }
        else{
            holder.checkBox.setChecked(false);
        }

        final ItemAdapter.ViewHolder tempHolder = holder;
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            User existingUser = ds.getValue(User.class);
                            for(int i=0 ; i< existingUser.getInvolvedRooms().size(); i++) {
                                for(int j=0; j< existingUser.getInvolvedRooms().get(i).getItemsToBuyList().size(); j++){
                                    Log.i("POSITION", positon +"");
                                    Log.i("ITEM LIST SIZE", itemList.size() + "");
                                    if(itemList.get(positon).equals(existingUser.getInvolvedRooms().get(i).getItemsToBuyList().get(j))){
                                        if(existingUser.getInvolvedRooms().get(i).getItemsToBuyList().get(j).isBought() != tempHolder.checkBox.isChecked()) {
                                            existingUser.getInvolvedRooms().get(i).getItemsToBuyList().get(j).setBought(tempHolder.checkBox.isChecked());
                                            databaseReference.child(existingUser.getUid()).setValue(existingUser);
                                        }
                                    }
                                }

                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        return convertView;

    }


}
