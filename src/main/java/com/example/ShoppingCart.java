package com.example;

import java.math.BigDecimal;
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

    public boolean removeItem(Item testItem) {
        if(cart.contains(testItem)){
            cart.remove(testItem);
            return true;
        }
        return false;
    }

    public BigDecimal calculatePrice() {
        return BigDecimal.ONE;
    }
}
