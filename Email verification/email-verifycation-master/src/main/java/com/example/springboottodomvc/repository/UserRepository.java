package com.example.springboottodomvc.repository;

import com.example.springboottodomvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    List<User> findByEmail(String email);
}
