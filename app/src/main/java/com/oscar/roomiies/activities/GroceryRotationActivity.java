package com.oscar.roomiies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.oscar.roomiies.R;
import com.oscar.roomiies.data.Roomate;
import com.oscar.roomiies.data.RoommateAdapter;

import java.util.LinkedList;
import java.util.List;

public class GroceryRotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        List<Roomate> haha = new LinkedList<>();
        //haha.add(new Roomate("Oscar"));
        //haha.add(new Roomate("Hong"));
        //ListView hue = (ListView) findViewById(R.id.roomateList);
        //hue.setAdapter(new RoommateAdapter(this, R.layout.activity_grocery, (List)haha));



    }
}
