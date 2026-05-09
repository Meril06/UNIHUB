package com.example.sampleapp.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String subject;
    private LocalDate date;
    private String status; // "Present" or "Absent"

    public int getId()                      { return id; }
    public void setId(int id)               { this.id = id; }

    public Student getStudent()             { return student; }
    public void setStudent(Student s)       { this.student = s; }

    public String getSubject()              { return subject; }
    public void setSubject(String subject)  { this.subject = subject; }

    public LocalDate getDate()              { return date; }
    public void setDate(LocalDate date)     { this.date = date; }

    public String getStatus()               { return status; }
    public void setStatus(String status)    { this.status = status; }
}