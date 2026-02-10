package com.example;

public class ShoppingCart {

    boolean addItem(Item item){
        if(item == null){
            throw new NullPointerException("Requires non-null item");
        }

        return true;
    }

}
