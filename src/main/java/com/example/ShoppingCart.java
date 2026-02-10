package com.example;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<Item> cart = new ArrayList<>();

    boolean addItem(Item item){
        if(item == null){
            throw new NullPointerException("Requires non-null item");
        }
        cart.add(item);
        return true;
    }

    public List<Item> getCart() {
        return cart;
    }
}
