package com.example.demo.Controller;

import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService; // Đảm bảo bạn đã inject CategoryService
// Display a list of all products
    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/products/product-list";
    }
    // Display details of a single product
    @GetMapping("/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id).orElseThrow(() -> new IllegalStateException("Product not found")));
        return "products/single-product";
    }
}
