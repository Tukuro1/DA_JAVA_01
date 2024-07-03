package com.example.demo.repository;
import com.example.demo.Model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>{
}
