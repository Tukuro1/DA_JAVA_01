package com.example.demo.Controller;

import com.example.demo.Model.Blog;
import com.example.demo.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/blogs")
public class AdminBlogController {
    @Autowired
    private  BlogService blogService;

    @GetMapping
    public String listBlogs(Model model) {
        List<Blog> blogs = blogService.getAllBlogs();
        model.addAttribute("blogs", blogs);
        return "admin/blogs/blogs-list"; // Ensure this path is correct
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "admin/blogs/add-blog"; // Ensure this path is correct
    }

    @PostMapping("/add")
    public String addBlog(@Valid @ModelAttribute Blog blog, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/blogs/add-blog"; // Ensure this path is correct
        }
        blogService.addBlog(blog);
        return "redirect:/admin/blogs";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Blog blog = blogService.getBlogById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));
        model.addAttribute("blog", blog);
        return "/admin/blogs/update-blog"; // Ensure this path is correct
    }

    @PostMapping("/update/{id}")
    public String updateBlog(@PathVariable("id") Long id, @Valid Blog blog,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            blog.setId(id);
            return "/admin/blogs/update-blog";
        }
        blogService.updateBlog(blog);
        model.addAttribute("blogs", blogService.getAllBlogs());
        return "redirect:/admin/blogs";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlog(@PathVariable("id") Long id) {
        blogService.getBlogById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog Id:" + id));
        blogService.deleteBlogById(id);
        return "redirect:/admin/blogs";
    }
}
