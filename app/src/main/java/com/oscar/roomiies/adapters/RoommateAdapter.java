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
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oscar.roomiies.R;
import com.oscar.roomiies.data.Room;
import com.oscar.roomiies.data.Roomate;
import com.oscar.roomiies.data.User;

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
    public View getView(final int positon, @Nullable View convertView, @NonNull ViewGroup parent){
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
        if(roomateList.get(positon).isHasDoneGrocery() == true){
           holder.name.setChecked(true);
        }
        else{
            holder.name.setChecked(false);
        }

        final RoommateAdapter.ViewHolder tempHolder = holder;
        holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                                for(int j=0; j< existingUser.getInvolvedRooms().get(i).getRoomates().size(); j++){
                                    if(roomateList.get(positon).equals(existingUser.getInvolvedRooms().get(i).getRoomates().get(j))){
                                        if(existingUser.getInvolvedRooms().get(i).getRoomates().get(j).isHasDoneGrocery() != tempHolder.name.isChecked()) {
                                            existingUser.getInvolvedRooms().get(i).getRoomates().get(j).setHasDoneGrocery(tempHolder.name.isChecked());
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
