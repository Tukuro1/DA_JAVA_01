package com.example.demo.Controller;

import com.example.demo.Model.Product;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
    @GetMapping
    public String index(Model model)
    {
        model.addAttribute("products", productService.getAllProducts());
        return "/admin/products/product-list";
    }
    // For adding a new product
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories()); //Load categories
        return "/admin/products/add-product";
    }
    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
             return "redirect:/admin/products/add";
        }
        productService.addProduct(product);
        return "redirect:/admin/products";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model)
    {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories()); //Load categories
        return "/admin/products/update-product";
    }
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            product.setId(id); // set id to keep it in the form in case of errors54 BÀI 3XÂY DỰNG CHỨC NĂNG HIỂN THỊ/ THÊM/XÓA/SỬA
            return "/admin/products/update-product";
        }
        productService.updateProduct(product);
        return "redirect:/admin/products";
    }
    // Handle request to delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }
}
