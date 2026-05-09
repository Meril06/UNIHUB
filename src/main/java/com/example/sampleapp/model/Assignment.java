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
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String title;
    private String subject;
    private LocalDate deadline;
    private String status; // "Pending" or "Completed"

    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }

    public Student getStudent()                 { return student; }
    public void setStudent(Student s)           { this.student = s; }

    public String getTitle()                    { return title; }
    public void setTitle(String title)          { this.title = title; }

    public String getSubject()                  { return subject; }
    public void setSubject(String subject)      { this.subject = subject; }

    public LocalDate getDeadline()              { return deadline; }
    public void setDeadline(LocalDate d)        { this.deadline = d; }

    public String getStatus()                   { return status; }
    public void setStatus(String status)        { this.status = status; }
}