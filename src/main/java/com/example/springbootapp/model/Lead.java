package com.example.springbootapp.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Size(max = 100)
    @Column(name = "company")
    private String company;

    @Size(max = 100)
    @Column(name = "job_title")
    private String jobTitle;

    @Email
    @Size(max = 100)
    @Column(name = "email")
    private String email;

    @Size(max = 20)
    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LeadStatus status = LeadStatus.NEW;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private LeadSource source;

    @Column(name = "score")
    private Integer score = 0;

    @Column(name = "estimated_value", precision = 10, scale = 2)
    private BigDecimal estimatedValue;

    @Size(max = 500)
    @Column(name = "notes")
    private String notes;

    @Column(name = "converted_at")
    private LocalDateTime convertedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "converted_customer_id")
    private Customer convertedCustomer;

    public enum LeadStatus {
        NEW, CONTACTED, QUALIFIED, UNQUALIFIED, CONVERTED, LOST
    }

    public enum LeadSource {
        WEBSITE, SOCIAL_MEDIA, EMAIL_CAMPAIGN, REFERRAL, COLD_CALL, TRADE_SHOW, ADVERTISEMENT, OTHER
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Lead() {}

    public Lead(String firstName, String lastName, String email, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LeadStatus getStatus() { return status; }
    public void setStatus(LeadStatus status) { this.status = status; }

    public LeadSource getSource() { return source; }
    public void setSource(LeadSource source) { this.source = source; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public BigDecimal getEstimatedValue() { return estimatedValue; }
    public void setEstimatedValue(BigDecimal estimatedValue) { this.estimatedValue = estimatedValue; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getConvertedAt() { return convertedAt; }
    public void setConvertedAt(LocalDateTime convertedAt) { this.convertedAt = convertedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Customer getConvertedCustomer() { return convertedCustomer; }
    public void setConvertedCustomer(Customer convertedCustomer) { this.convertedCustomer = convertedCustomer; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
