package com.example.sampleapp.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sampleapp.model.Scholarship;
 
public interface ScholarshipRepository extends JpaRepository<Scholarship, Integer> {
}