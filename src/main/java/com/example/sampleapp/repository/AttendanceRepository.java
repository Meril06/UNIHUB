package com.example.sampleapp.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sampleapp.model.Attendance;
import com.example.sampleapp.model.Student;
 
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByStudent(Student student);
}