package com.example.springboottodomvc.repository;

import com.example.springboottodomvc.model.VericationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VericationTokenRepository extends JpaRepository<VericationToken,String> {
    List<VericationToken> findByUserEmail(String email);
    List<VericationToken> findByToken(String token);
}
