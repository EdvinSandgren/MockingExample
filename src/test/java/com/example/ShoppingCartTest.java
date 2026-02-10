package com.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ShoppingCartTest {
    private ShoppingCart testCart;
    private final Item testItem = new Item() {
        final BigDecimal price = new BigDecimal("1.00");
    };

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

    @Test
    public void removeItemNoItemTest() {
        Assertions.assertThat(testCart.removeItem(testItem)).isFalse();
    }

    @Test
    public void calculatePriceTest() {
        testCart.addItem(testItem);
        Assertions.assertThat(testCart.calculatePrice().equals(BigDecimal.ONE)).isTrue();
    }

}