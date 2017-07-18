package com.oscar.roomiies.data;

import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oscar on 7/18/2017.
 */

public class User {

    private String fullName;
    private String email;
    private String uid;
    private List<Room> involvedRooms;



    public User(){}

    public User(String fullName, String email, Room room){
        this.fullName = fullName;
        this.email = email;
        involvedRooms = new LinkedList<>();
        involvedRooms.add(room);

        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
    }

    public void addToInvolvedRooms(Room room){
        involvedRooms.add(room);
    }

    /************************************************
     * Getters and Setters
     ************************************************/
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Room> getInvolvedRooms() {
        return involvedRooms;
    }

    public void setInvolvedRooms(List<Room> involvedRooms) {
        this.involvedRooms = involvedRooms;
    }
}
