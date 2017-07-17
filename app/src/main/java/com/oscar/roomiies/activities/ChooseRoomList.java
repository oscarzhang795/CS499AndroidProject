package com.oscar.roomiies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oscar.roomiies.R;
import com.oscar.roomiies.data.Room;
import com.oscar.roomiies.data.Roomate;

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

        loadRooms();

        roomView = (ListView) findViewById(R.id.item_roomview);
        testAdapter = new ArrayAdapter<Room>(this, android.R.layout.simple_list_item_1, room);

        roomView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent r = new Intent(ChooseRoomList.this, MyRoomActivity.class);
                r.putExtra("RoomClicked", room.get(position).toString());
                r.putExtra("Position", position);
                startActivity(r);
            }
        });

    }

    private void loadRooms(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Rooms");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Room dsRoom = ds.getValue(Room.class);
                    room.add(dsRoom);
                }
                roomView.setAdapter(testAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
