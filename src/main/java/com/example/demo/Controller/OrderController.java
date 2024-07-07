package com.example.demo.Controller;


import com.example.demo.Model.CartItem;
import com.example.demo.Model.PaymentMethod;
import com.example.demo.Service.CartService;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String getOrder(Model model ){
        model.addAttribute("orders", orderService.getOrders());
        return "order-list";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("subtotal", cartService.getCartSubtotal());
        return "/cart/checkout";
    }

    @PostMapping("/submit")
    public String submitOrder(@RequestParam String customerName,
                              @RequestParam String email,
                              @RequestParam String address,
                              @RequestParam String phone,
                              @RequestParam String note,
                              @RequestParam String paymentMethod,
                              Principal principal) {
        System.out.println(principal.getName());

        List<CartItem> cartItems = cartService.getCartItems();
        String nameListProduct = "";
        String titleMail = "Chào " + customerName + "\n Cảm ơn đã đã mua hàng của chúng tôi";
        for (CartItem c : cartItems) {
            nameListProduct += c.getNameProduct() + ", ";
        }
        if (cartItems.isEmpty()) {
            // Redirect if cart is empty
            return "redirect:/cart";
        }
        try {
            // Attempt to create an order
            orderService.createOrder(customerName, address, phone, note, cartItems, paymentMethod);
            // Send an email confirmation
            emailService.sendEmail(titleMail, nameListProduct, email);
        } catch (Exception e) {
            // Log the exception (optional, for debugging purposes)
            // e.g., logger.error("Error creating order or sending email", e);

            // Redirect to cart page if there is an error
            return "redirect:/cart";
        }
        // Redirect to a success page or confirmation page if needed
        return "redirect:/order-confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Your order has been successfully placed.");
        return "cart/order-confirmation";
    }
}
