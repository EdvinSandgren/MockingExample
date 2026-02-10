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

    @Test
    public void getCartTest() {
        testCart.addItem(testItem);
        Assertions.assertThat(testCart.getCart().contains(testItem)).isTrue();
    }

    @Test
    public void removeItemTest() {
        testCart.addItem(testItem);
        Assertions.assertThat(testCart.removeItem(testItem)).isTrue();
    }

    @Test
    public void removeItemVerifyTest() {
        testCart.addItem(testItem);
        testCart.removeItem(testItem);
        Assertions.assertThat(testCart.getCart().contains(testItem)).isFalse();
    }
}