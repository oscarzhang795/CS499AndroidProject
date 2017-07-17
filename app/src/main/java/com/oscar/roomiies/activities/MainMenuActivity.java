package com.oscar.roomiies.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oscar.roomiies.R;
import com.oscar.roomiies.data.Room;
import com.oscar.roomiies.data.Roomate;

import java.util.HashMap;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String name;

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
        databaseReference = database.getReference();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter room name");

        final EditText input_field = new EditText(this);

        builder.setView(input_field);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = input_field.getText().toString();

                Roomate rm = new Roomate("Oscar", "Zhang", firebaseAuth.getCurrentUser().getUid());

                databaseReference.child("Rooms").push().setValue(new Room(name, rm));

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
        String key = database.getReference("Rooms").push().getKey();
        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
    }

    public void signOutButton(View view){
        FirebaseAuth.getInstance().signOut();
        Intent r = new Intent(this, LoginActivity.class);
        startActivity(r);
    }
}
