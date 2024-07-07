package com.example.demo.Model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(Product product, int quantity) {
        for (CartItem cartItem : items) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(Long productId) {
        items.removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getSubtotal() {
        return items.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();
    }
}
