package com.oscar.roomiies.data;

/**
 * Created by Oscar on 7/9/2017.
 */

public class Roomate {

    private String firstName;
    private String lastName;
    private String uid;

    public Roomate(){

    }

    public Roomate(String firstName, String lastName, String uid){

        this.firstName = firstName;
        this.lastName = lastName;
        this.uid = uid;
    }

    public String toString(){
        return firstName + " " + lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public  String getUid(){
        return uid;
    }

}
