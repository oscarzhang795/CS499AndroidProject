package com.oscar.roomiies.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.oscar.roomiies.R;
import com.oscar.roomiies.data.Room;
import com.oscar.roomiies.data.Roomate;
import com.oscar.roomiies.data.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void myRoomButton(View view){
        Intent r = new Intent(this, ChooseRoomList.class);
        startActivity(r);
    }

    public void createRoomButton(View view){
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter room name");

        final EditText input_field = new EditText(this);

        builder.setView(input_field);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = input_field.getText().toString();

                //final Roomate newRoomate = new Roomate(firebaseAuth.getCurrentUser().getDisplayName());
                //final Room newRoom = new Room(name, newRoomate);
                //final User user = new User(firebaseAuth.getCurrentUser().getDisplayName(), firebaseAuth.getCurrentUser().getEmail(), newRoom);
                //    databaseReference.child(user.getUid()).setValue(user);


                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        Roomate newRoomate = new Roomate(firebaseAuth.getCurrentUser().getDisplayName());
                        Room newRoom = new Room(name, newRoomate);
                        User user = new User(firebaseAuth.getCurrentUser().getDisplayName(), firebaseAuth.getCurrentUser().getEmail(), newRoom);
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                User existingUser = ds.getValue(User.class);

                                Log.i("BOOL VALUE", !ds.hasChild("uid")+"");
                                if(!ds.child("uid").getValue().equals(mAuth.getCurrentUser().getUid())){
                                    Log.i("mAuthID", !ds.hasChild(mAuth.getCurrentUser().getUid()) + "");
                                    Log.i("gsagasgas", "guess it works");
                                    databaseReference.child(user.getUid()).setValue(user);
                                }

                                else if(existingUser.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    Log.i("Email Match", existingUser.getEmail().equals(mAuth.getCurrentUser().getEmail()) + "");
                                    existingUser.getInvolvedRooms().add(newRoom);
                                    databaseReference.child(existingUser.getUid()).setValue(existingUser);
                                }
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

    public void joinRoomButton(View view){
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Room ID");

        final EditText input_field = new EditText(this);


        builder.setView(input_field);


        builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String roomID = input_field.getText().toString();
                Log.i("ID FIELD: ", input_field.getText().toString());
                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User tempUser = null;
                        Room tempRoom = null;
                        boolean flag = false;
                        boolean isRoomFound = false;
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            User existingUser = ds.getValue(User.class);

                            for(Room room : existingUser.getInvolvedRooms()){
                                Log.i("Roomids", room.getRoomID());
                                if(room.getRoomID().equals(roomID)){
                                    isRoomFound = true;
                                    tempRoom = room;
                                }
                            }

                            if(ds.child("uid").getValue().equals(firebaseAuth.getCurrentUser().getUid())){
                                tempUser = ds.getValue(User.class);
                                flag = true;
                            }

                            if(flag == true && isRoomFound == true){
                                tempRoom.addRoomate(new Roomate(firebaseAuth.getCurrentUser().getDisplayName()));
                                tempUser.getInvolvedRooms().add(tempRoom);
                                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(tempUser);
                                Toast.makeText(MainMenuActivity.this, "Room successfully joined", Toast.LENGTH_SHORT).show();
                                updateRoomates(roomID, new Roomate(firebaseAuth.getCurrentUser().getDisplayName()));
                            }
                            else if(flag == false && isRoomFound == true){
                                tempRoom.addRoomate(new Roomate(firebaseAuth.getCurrentUser().getDisplayName()));
                                User newUser = new User(firebaseAuth.getCurrentUser().getDisplayName(),
                                        firebaseAuth.getCurrentUser().getEmail(), tempRoom);
                                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(newUser);
                                updateRoomates(roomID, new Roomate(firebaseAuth.getCurrentUser().getDisplayName()));
                                Toast.makeText(MainMenuActivity.this, "Room successfully joined", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MainMenuActivity.this, "Room does not exist", Toast.LENGTH_SHORT).show();
                            }

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

    private void updateRoomates(final String roomID, final Roomate roomate){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String id = roomID;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    User existingUser = ds.getValue(User.class);
                    int counter = 0;
                    for(Room room : existingUser.getInvolvedRooms()){
                        Log.i("Roomids", room.getRoomID());
                        if(room.getRoomID().equals(id)){
                            if(!existingUser.getInvolvedRooms().get(counter).contains(roomate)) {
                                existingUser.getInvolvedRooms().get(counter).addRoomate(roomate);
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

    public void signOutButton(View view){
        FirebaseAuth.getInstance().signOut();
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Intent r = new Intent(this, LoginActivity.class);
        startActivity(r);
    }
}
