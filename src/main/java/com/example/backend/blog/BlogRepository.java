package com.example.backend.blog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    // Additional query methods can be defined here if needed
    
}
