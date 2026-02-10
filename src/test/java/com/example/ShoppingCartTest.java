package com.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShoppingCartTest {
    ShoppingCart testCart;

    @BeforeEach
    void beforeEach() {
        testCart = new ShoppingCart();
    }

    @Test
    public void addItemTest() {
        Item testItem = new Item() {};
        Assertions.assertThat(testCart.addItem(testItem)).isTrue();
    }

}