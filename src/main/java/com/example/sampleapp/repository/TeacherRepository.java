package com.example.sampleapp.repository;
 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sampleapp.model.Teacher;
 
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);
}