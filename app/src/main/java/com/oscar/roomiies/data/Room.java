package com.oscar.roomiies.data;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Oscar on 7/15/2017.
 */

public class Room{

    private String roomName;
    private List<Roomate> roomates;
    private List<ToBuyItem> itemsToBuyList;
    private List<PersonalItem> personalItemList;
    private List<Announcement> announcementList;
    private String roomID;

    public Room(){

    }

    public Room(String roomName, Roomate roomate){
        this.roomName = roomName;

        roomates = new LinkedList<>();
        itemsToBuyList = new LinkedList<>();
        personalItemList = new LinkedList<>();
        announcementList = new LinkedList<>();

        this.roomID = UUID.randomUUID().toString();
        roomates.add(roomate);

    }



    public boolean contains(Roomate roomate){
        for(Roomate curRoomate : roomates){
            if(curRoomate.getFullName().equals(roomate.getFullName())){
                return true;
            }
        }
        return false;
    }


    /**********************************************
     * Add Methods
     **********************************************/

    public void addRoomate(Roomate roomate){
        roomates.add(roomate);
    }

    public void addItemToBuy(ToBuyItem item){
        itemsToBuyList.add(item);
    }

    public void addPersonalItem(PersonalItem personalItem){
        personalItemList.add(personalItem);
    }

    public void addAnnouncement(Announcement announcement){
        announcementList.add(announcement);
    }



    /*********************************************
     * Getters and Setters
     *********************************************/
    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Roomate> getRoomates() {
        return roomates;
    }

    public void setRoomates(List<Roomate> roomates) {
        this.roomates = roomates;
    }

    public List<ToBuyItem> getItemsToBuyList() {
        return itemsToBuyList;
    }

    public void setItemsToBuyList(List<ToBuyItem> itemsToBuyList) {
        this.itemsToBuyList = itemsToBuyList;
    }

    public List<PersonalItem> getPersonalItemList() {
        return personalItemList;
    }

    public void setPersonalItemList(List<PersonalItem> personalItemList) {
        this.personalItemList = personalItemList;
    }

    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }

    public void initializeItemsToBuyList(){
        itemsToBuyList = new LinkedList<>();
    }

    public void initializePersonalItemList() {personalItemList = new LinkedList<>();}

    public void initializeAnnouncementList() {announcementList = new LinkedList<>();}

    //To String method
    public String toString(){
        return roomName;
    }



}
