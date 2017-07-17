package com.oscar.roomiies.data;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oscar on 7/15/2017.
 */

public class Room{

    private String roomName;
    private List<Roomate> roomates;
    private List<ToBuyItem> itemsToBuy;
    //private String roomID;

    public Room(){

    }

    public Room(String roomName, Roomate roomate){
        this.roomName = roomName;

        roomates = new LinkedList<>();
        itemsToBuy = new LinkedList<>();

        roomates.add(roomate);

    }

    public void addRoomate(String firstName, String lastName){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        roomates.add(new Roomate(firstName, lastName, firebaseAuth.getCurrentUser().getUid()) );
    }

    public void addItemsToBuy(String itemName, int quantity){
        itemsToBuy.add(new ToBuyItem(itemName, quantity));
    }

    public void addItemsToBuy(String itemName){
        itemsToBuy.add(new ToBuyItem(itemName));
    }

    public String getRoomName(){
        return roomName;
    }

    public List<Roomate> getRoomates(){
        return roomates;
    }

    public List<ToBuyItem> getItemsToBuy(){
        return itemsToBuy;
    }

    public String toString(){
        return roomName;
    }


}
