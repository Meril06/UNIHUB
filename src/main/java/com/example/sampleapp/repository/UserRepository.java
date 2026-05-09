package com.example.sampleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sampleapp.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}