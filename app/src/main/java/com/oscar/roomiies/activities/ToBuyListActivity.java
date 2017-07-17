package com.oscar.roomiies.activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.oscar.roomiies.R;
import com.oscar.roomiies.data.ItemAdapter;
import com.oscar.roomiies.data.ToBuyItem;

import java.util.LinkedList;
import java.util.List;

public class ToBuyListActivity extends AppCompatActivity {

    private ListView itemListView;
    private List<ToBuyItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_buy_list);

        itemListView = (ListView) findViewById(R.id.item_listview);
        itemList = new LinkedList<>();
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
                    itemList.add(new ToBuyItem(itemName.getText().toString()));
                }
                else{
                    itemList.add(new ToBuyItem(itemName.getText().toString(), numberPicker.getValue()));
                }
                dialog.dismiss();

            }
        });

        itemListView.setAdapter(new ItemAdapter(this, R.layout.activity_to_buy_list, itemList));


    }
}
