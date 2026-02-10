package com.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShoppingCartTest {
    ShoppingCart testCart;
    Item testItem = new Item() {};

    @BeforeEach
    void beforeEach() {
        testCart = new ShoppingCart();
    }

    @Test
    public void addItemTest() {
        Assertions.assertThat(testCart.addItem(testItem)).isTrue();
    }

    @Test
    public void addItemNullTest() {
        Assertions.assertThatThrownBy(() -> testCart.addItem(null)).hasMessage("Requires non-null item");
    }

}