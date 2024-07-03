package com.example.demo.repository.Service;

import com.example.demo.Model.CartItem;
import com.example.demo.Model.Order;
import com.example.demo.Model.OrderDetail;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService;

    public List<Order> getOrders (){
        return orderRepository.findAll();
    }

    @Transactional
    public Order createOrder(String customerName,String address,String phone,String note, List<CartItem> cartItems, String paymentMethod) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setAddress(address);
        order.setPhone(phone);
        order.setNote(note);
        order.setPaymentMethod(paymentMethod);
        order = orderRepository.save(order);

        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }

        // Optionally clear the cart after order placement
        cartService.clearCart();

        return order;
    }
}
