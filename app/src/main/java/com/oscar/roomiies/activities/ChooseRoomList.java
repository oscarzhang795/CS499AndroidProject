package com.oscar.roomiies.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oscar.roomiies.R;
import com.oscar.roomiies.data.Room;
import com.oscar.roomiies.data.Roomate;
import com.oscar.roomiies.data.User;

import java.util.LinkedList;
import java.util.List;

public class ChooseRoomList extends AppCompatActivity {

    private ListView roomView;
    private List<Room> room;
    ListAdapter testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseroom);

        room = new LinkedList<>();

        roomView = (ListView) findViewById(R.id.item_roomview);
        testAdapter = new ArrayAdapter<Room>(this, android.R.layout.simple_list_item_1, room);

        loadRooms();

        roomView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent r = new Intent(ChooseRoomList.this, MyRoomActivity.class);
                r.putExtra("RoomClicked", room.get(position).toString());
                r.putExtra("Position", "" + position);
                r.putExtra("Room ID", room.get(position).getRoomID());
                startActivity(r);
            }
        });

    }

    private void loadRooms(){
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    if(ds.child("uid").getValue().equals(firebaseAuth.getCurrentUser().getUid())){
                        User user = ds.getValue(User.class);

                        for(Room curRoom : user.getInvolvedRooms()){
                            room.add(curRoom);
                        }
                    }
                }
                roomView.setAdapter(testAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
