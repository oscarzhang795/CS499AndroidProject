package com.oscar.roomiies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.oscar.roomiies.adapters.RoommateAdapter;
import com.oscar.roomiies.data.User;

import java.util.LinkedList;
import java.util.List;

public class GroceryRotationActivity extends AppCompatActivity {

    private List<Roomate> roomates;
    private List<Room> tempRooms;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private int postion;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        checkBox = (CheckBox) findViewById(R.id.checkbox1);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        tempRooms = new LinkedList<>();
        roomates = new LinkedList<>();

        loadList();



    }

    private void setCheckBoxListener(){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){

                            if(ds.child("uid").getValue().equals(firebaseAuth.getCurrentUser().getUid())){
                                User user = ds.getValue(User.class);

                                for(Room curRoom : user.getInvolvedRooms()){
                                    tempRooms.add(curRoom);
                                }
                            }
                        }
                    }

                    Intent r = getIntent();
                    Bundle b = r.getExtras();
                    int position = Integer.parseInt(b.getString("Position"));
                    Room room = tempRooms.get(position);

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void loadList(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    if(ds.child("uid").getValue().equals(firebaseAuth.getCurrentUser().getUid())){
                        User user = ds.getValue(User.class);

                        for(Room curRoom : user.getInvolvedRooms()){
                            tempRooms.add(curRoom);
                        }
                    }
                }

                Intent r = getIntent();
                Bundle b = r.getExtras();
                int position = Integer.parseInt(b.getString("Position"));
                Room room = tempRooms.get(position);


                ListView groceryListView = (ListView) findViewById(R.id.roomateList);
                groceryListView.setAdapter(new RoommateAdapter(GroceryRotationActivity.this, R.layout.activity_grocery, (List) room.getRoomates()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
