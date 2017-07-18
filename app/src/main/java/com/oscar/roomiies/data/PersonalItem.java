package com.oscar.roomiies.data;

/**
 * Created by Oscar on 7/17/2017.
 */

public class PersonalItem {

    private String itemName;
    private String ownerName;

    public PersonalItem(){}

    public PersonalItem(String itemName, String ownerName){ this.itemName = itemName; this.ownerName = ownerName;}

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String toString(){
        return "The " + itemName + " belongs to " + ownerName;
    }

}
