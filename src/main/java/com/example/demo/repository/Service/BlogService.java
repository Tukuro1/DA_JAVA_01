package com.example.demo.repository.Service;

import com.example.demo.Model.Blog;
import com.example.demo.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogService {
    private final BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public void addBlog(Blog blog) {
        blogRepository.save(blog);
    }

    public void updateBlog(@NotNull Blog blog) {
        Blog existingBlog = blogRepository.findById(blog.getId())
                .orElseThrow(() -> new IllegalStateException("Blog with ID " +
                        blog.getId() + " does not exist."));
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContent(blog.getContent());
        blogRepository.save(existingBlog);
    }
    public void deleteBlogById(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new IllegalStateException("Blog with ID " + id + " does not exist.");
        }
        blogRepository.deleteById(id);
    }
}
