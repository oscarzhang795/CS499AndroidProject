package com.oscar.roomiies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oscar.roomiies.R;

public class MyRoomActivity extends AppCompatActivity {

    TextView roomTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room);

        roomTitle = (TextView) findViewById(R.id.roomname_textview);

        Intent r = getIntent();
        Bundle b = r.getExtras();

        if(b!=null){
            String roomName = (String) b.get("RoomClicked");
            roomTitle.setText(roomName);
        }
    }

    public void GroceryRotationButton(View view){
        Intent r = new Intent(this, GroceryRotationActivity.class);
        startActivity(r);
    }

    public void shoppingListButton (View view){
        Intent r = new Intent(this, ToBuyListActivity.class);
        startActivity(r);
    }

    public void personalItemButton(View view){
        Intent r = new Intent(this, GroceryRotationActivity.class);
        startActivity(r);
    }

    public void announcementButton(View view){
        Intent r = new Intent(this, GroceryRotationActivity.class);
        startActivity(r);
    }


}
