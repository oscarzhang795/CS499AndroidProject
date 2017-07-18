package com.oscar.roomiies.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oscar.roomiies.R;
import com.oscar.roomiies.data.Announcement;
import com.oscar.roomiies.data.PersonalItem;
import com.oscar.roomiies.data.Room;
import com.oscar.roomiies.data.User;

import java.util.LinkedList;
import java.util.List;

public class AnnouncementActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListView listView;
    private List<PersonalItem> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        listView = (ListView) findViewById(R.id.announcement_listview);
        loadDatabase();




        //listView.setAdapter(arrayAdapter);
    }

    private void loadDatabase(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Room> tempRooms = new LinkedList<Room>();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    if(ds.child("uid").getValue().equals(firebaseAuth.getCurrentUser().getUid())){
                        User user = ds.getValue(User.class);

                        for(Room curRoom : user.getInvolvedRooms()){
                            tempRooms.add(curRoom);
                        }
                    }
                }
                Log.i("SIZE: ", tempRooms.size() + "");
                Intent r = getIntent();
                Bundle b = r.getExtras();
                int position = Integer.parseInt(b.getString("Position"));
                Log.i("POSITION: ", position + "");

                if(tempRooms.get(position).getAnnouncementList() != null) {
                    listView.setAdapter(new ArrayAdapter<Announcement>(AnnouncementActivity.this, android.R.layout.simple_list_item_1, tempRooms.get(position).getAnnouncementList()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void addAnnouncementItemButton(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter announcement");
        final EditText input_field = new EditText(this);
        builder.setView(input_field);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                Log.i("WHAT IS MY NAME: ", firebaseAuth.getCurrentUser().getDisplayName());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent r = getIntent();
                        Bundle b = r.getExtras();
                        String roomID = b.getString("Room ID");




                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            User existingUser = ds.getValue(User.class);

                            for(Room room : existingUser.getInvolvedRooms()){
                                int counter = 0;
                                if(room.getRoomID().equals(roomID)){
                                    if(room.getAnnouncementList() == null){
                                        Announcement announcement = new Announcement(input_field.getText().toString(), firebaseAuth.getCurrentUser().getDisplayName());
                                        existingUser.getInvolvedRooms().get(counter).initializeAnnouncementList();
                                        existingUser.getInvolvedRooms().get(counter).getAnnouncementList().add(announcement);
                                    }
                                    else{
                                        Announcement announcement = new Announcement(input_field.getText().toString(), firebaseAuth.getCurrentUser().getDisplayName());
                                        existingUser.getInvolvedRooms().get(counter).getAnnouncementList().add(announcement);
                                    }
                                }
                                counter++;
                            }
                            databaseReference.child(existingUser.getUid()).setValue(existingUser);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
