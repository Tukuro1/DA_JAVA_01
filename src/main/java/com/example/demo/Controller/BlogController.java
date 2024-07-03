package com.example.demo.Controller;

import com.example.demo.Model.Blog;
import com.example.demo.repository.Service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
@RequiredArgsConstructor
public class BlogController {
    @Autowired
    private final BlogService blogService;

    // Hiển thị danh sách danh mục
    @GetMapping("/blogs")
    public String listBogs(Model model) {
        List<Blog> blogs = blogService.getAllBlogs();
        model.addAttribute("blogs", blogs);
        return "/blogs/blogs-list";
    }
}
