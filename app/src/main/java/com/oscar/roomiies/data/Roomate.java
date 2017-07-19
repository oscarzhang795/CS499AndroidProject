package com.oscar.roomiies.data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oscar on 7/9/2017.
 */

public class Roomate {

    private String fullName;
    private boolean hasDoneGrocery;

    public Roomate(){}

    public Roomate(String fullName){
        this.fullName = fullName;
        this.hasDoneGrocery = false;
    }

    public boolean equals(Roomate roomate){
        if(this.fullName.equals(roomate.getFullName()) && this.hasDoneGrocery == roomate.hasDoneGrocery){
            return true;
        }
        return false;
    }

    /*****************************************************
    * Getters and Setters
    ******************************************************/
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isHasDoneGrocery() {
        return hasDoneGrocery;
    }

    public void setHasDoneGrocery(boolean hasDoneGrocery) {
        this.hasDoneGrocery = hasDoneGrocery;
    }

    public String toString(){
        return fullName;
    }
}
