package com.oscar.roomiies.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oscar.roomiies.R;
import com.oscar.roomiies.adapters.ItemAdapter;
import com.oscar.roomiies.data.Room;
import com.oscar.roomiies.data.Roomate;
import com.oscar.roomiies.data.ToBuyItem;
import com.oscar.roomiies.data.User;

import java.util.LinkedList;
import java.util.List;

public class ToBuyListActivity extends AppCompatActivity {

    private ListView itemListView;
    private List<ToBuyItem> itemList;
    private Room currentRoom;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_buy_list);

        itemListView = (ListView) findViewById(R.id.item_listview);
        itemList = new LinkedList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        loadList();
    }

    public void addNewItem(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_additem, null);

        final NumberPicker numberPicker = (NumberPicker) mView.findViewById(R.id.numberScroll);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(true);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        Button clearButton = (Button) mView.findViewById(R.id.cancelButton);
        Button addButton = (Button) mView.findViewById(R.id.addButton);

        clearButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dialog.dismiss();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText itemName = (EditText) mView.findViewById(R.id.itemTextView);

                if(itemName.getText().toString().isEmpty()){
                    Toast.makeText(ToBuyListActivity.this, "Please Enter An Item Name", Toast.LENGTH_SHORT).show();
                }
                else if(numberPicker.getValue() == 0) {
                    ToBuyItem item = new ToBuyItem(itemName.getText().toString());
                    updateDatabase(item);
                }
                else{
                    ToBuyItem item = new ToBuyItem(itemName.getText().toString(), numberPicker.getValue());
                    updateDatabase(item);
                }

                dialog.dismiss();

            }
        });

        itemListView.setAdapter(new ItemAdapter(this, R.layout.activity_to_buy_list, itemList));


    }


    private void updateDatabase(final ToBuyItem item){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intent r = getIntent();
                Bundle b = r.getExtras();
                String id = (String) b.get("Room ID");


                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    User existingUser = ds.getValue(User.class);

                    int counter = 0;
                    for(Room room : existingUser.getInvolvedRooms()){
                        if(room.getRoomID().equals(id)){
                            if(existingUser.getInvolvedRooms().get(counter).getItemsToBuyList() == null){
                                //List<ToBuyItem> tempList = new LinkedList<ToBuyItem>();
                                //tempList.add(item);
                                existingUser.getInvolvedRooms().get(counter).initializeItemsToBuyList();
                                existingUser.getInvolvedRooms().get(counter).getItemsToBuyList().add(item);
                                //existingUser.getInvolvedRooms().get(counter).getItemsToBuyList().add(item);
                            }
                            else{
                                existingUser.getInvolvedRooms().get(counter).getItemsToBuyList().add(item);
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

    private void loadList(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                List<Room> tempRooms = new LinkedList<>();


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

                if(tempRooms.get(position).getItemsToBuyList() != null) {
                    itemListView.setAdapter(new ItemAdapter(ToBuyListActivity.this, R.layout.activity_to_buy_list, tempRooms.get(position).getItemsToBuyList()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
