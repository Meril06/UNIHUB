package com.example.sampleapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private String username;

    private String password;
    private String email;
    private String department;
    private int year;

    // ── Getters & Setters ──
    public int getId()                  { return id; }
    public void setId(int id)           { this.id = id; }

    public String getName()             { return name; }
    public void setName(String name)    { this.name = name; }

    public String getUsername()                 { return username; }
    public void setUsername(String username)    { this.username = username; }

    public String getPassword()                 { return password; }
    public void setPassword(String password)    { this.password = password; }

    public String getEmail()                    { return email; }
    public void setEmail(String email)          { this.email = email; }

    public String getDepartment()               { return department; }
    public void setDepartment(String dept)      { this.department = dept; }

    public int getYear()                        { return year; }
    public void setYear(int year)               { this.year = year; }
}