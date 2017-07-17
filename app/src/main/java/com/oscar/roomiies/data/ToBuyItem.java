package com.oscar.roomiies.data;

/**
 * Created by Oscar on 7/11/2017.
 */

public class ToBuyItem {

    private String itemName;
    private int quantity;

    public ToBuyItem(String itemName, int quantity){
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public ToBuyItem(String itemName){
        this.itemName = itemName;
        this.quantity = 0;
    }

    public String getItemName(){
        return itemName;
    }

    public int getQuantity(){
        return quantity;
    }

    public String toString(){
        if(quantity == 0){
            return itemName;
        }
        else{
            return "x" + quantity + " " + itemName;
        }
    }

}
