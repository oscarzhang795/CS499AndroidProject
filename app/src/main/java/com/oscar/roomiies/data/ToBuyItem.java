package com.oscar.roomiies.data;

/**
 * Created by Oscar on 7/11/2017.
 */

public class ToBuyItem {

    private String itemName;
    private int quantity;
    private boolean isBought;

    /****************************************
    * Constructors
    *****************************************/
    public ToBuyItem(){}

    public ToBuyItem(String itemName, int quantity){
        this.itemName = itemName;
        this.quantity = quantity;
        this.isBought = false;
    }

    public ToBuyItem(String itemName){
        this.itemName = itemName;
        this.quantity = 0;
        this.isBought = false;
    }


    /************************************************
     * Getters and Setters
     ************************************************/
    public String getItemName(){
        return itemName;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    //toString method
    public String toString(){
        if(quantity == 0){
            return itemName;
        }
        else{
            return "x" + quantity + " " + itemName;
        }
    }

}
