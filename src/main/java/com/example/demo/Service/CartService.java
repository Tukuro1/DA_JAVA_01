package com.example.demo.Service;

import com.example.demo.Model.CartItem;
import com.example.demo.Model.Cart;
import com.example.demo.Model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    @Autowired
    private ProductRepository productRepository;

    private Cart cart = new Cart();

    public List<CartItem> getCartItems() {
        return cart.getItems();
    }

    public void addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        cart.addItem(product, quantity);
    }

    public void removeFromCart(Long productId) {
        cart.removeItem(productId);
    }

    public void clearCart() {
        cart.clear();
    }
    public double getCartSubtotal() {
        return cart.getSubtotal();
    }
}