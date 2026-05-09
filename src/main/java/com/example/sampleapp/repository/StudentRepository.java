package com.example.sampleapp.repository;
 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sampleapp.model.Student;
 
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);
}