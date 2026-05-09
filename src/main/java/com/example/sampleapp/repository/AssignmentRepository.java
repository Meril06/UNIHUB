package com.example.sampleapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sampleapp.model.Assignment;
import com.example.sampleapp.model.Student;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    List<Assignment> findByStudent(Student student);
    List<Assignment> findByStudentId(int studentId);
}