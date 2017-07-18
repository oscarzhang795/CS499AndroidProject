package com.oscar.roomiies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oscar.roomiies.R;
import com.oscar.roomiies.data.Announcement;

public class MyRoomActivity extends AppCompatActivity {

    TextView roomTitle;
    TextView roomID;
    String position;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room);

        roomTitle = (TextView) findViewById(R.id.roomname_textview);
        roomID = (TextView) findViewById(R.id.roomid_textview) ;

        Intent r = getIntent();
        Bundle b = r.getExtras();

        if(b!=null){
            String roomName = (String) b.get("RoomClicked");
            id = (String) b.get("Room ID");
            position = "" + b.getString("Position");
            roomTitle.setText(roomName);
            roomID.setText("Room ID: " + id);
        }
    }

    public void GroceryRotationButton(View view){
        Intent r = new Intent(this, GroceryRotationActivity.class);
        r.putExtra("Position", position);
        r.putExtra("Room ID", id);
        startActivity(r);
    }

    public void shoppingListButton (View view){
        Intent r = new Intent(this, ToBuyListActivity.class);
        r.putExtra("Position", position);
        r.putExtra("Room ID", id);
        startActivity(r);
    }

    public void personalItemButton(View view){
        Intent r = new Intent(this, PersonalBuyActivity.class);
        r.putExtra("Position", position);
        r.putExtra("Room ID", id);
        startActivity(r);
    }

    public void announcementButton(View view){
        Intent r = new Intent(this, AnnouncementActivity.class);
        r.putExtra("Position", position);
        r.putExtra("Room ID", id);
        startActivity(r);
    }


}
