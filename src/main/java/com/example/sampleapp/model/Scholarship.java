package com.example.sampleapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "scholarships")
public class Scholarship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate deadline;
    private BigDecimal amount;
    private String eligibility;

    public int getId()                              { return id; }
    public void setId(int id)                       { this.id = id; }

    public String getTitle()                        { return title; }
    public void setTitle(String title)              { this.title = title; }

    public String getDescription()                  { return description; }
    public void setDescription(String desc)         { this.description = desc; }

    public LocalDate getDeadline()                  { return deadline; }
    public void setDeadline(LocalDate d)            { this.deadline = d; }

    public BigDecimal getAmount()                   { return amount; }
    public void setAmount(BigDecimal amount)        { this.amount = amount; }

    public String getEligibility()                  { return eligibility; }
    public void setEligibility(String elig)         { this.eligibility = elig; }
}