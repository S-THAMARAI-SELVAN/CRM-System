package com.example.springbootapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Subject is required")
    @Size(max = 200)
    @Column(name = "subject", nullable = false)
    private String subject;

    @Size(max = 1000)
    @Column(name = "description")
    private String description;

    @NotNull(message = "Activity type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ActivityType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ActivityStatus status = ActivityStatus.PLANNED;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private ActivityPriority priority = ActivityPriority.MEDIUM;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Size(max = 1000)
    @Column(name = "outcome")
    private String outcome;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opportunity_id")
    private Opportunity opportunity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id")
    private Lead lead;

    public enum ActivityType {
        CALL, EMAIL, MEETING, TASK, NOTE, DEMO, PROPOSAL, FOLLOW_UP
    }

    public enum ActivityStatus {
        PLANNED, IN_PROGRESS, COMPLETED, CANCELLED, OVERDUE
    }

    public enum ActivityPriority {
        LOW, MEDIUM, HIGH, URGENT
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (status == ActivityStatus.COMPLETED && completedAt == null) {
            completedAt = LocalDateTime.now();
        }
    }

    // Constructors
    public Activity() {}

    public Activity(String subject, ActivityType type, LocalDateTime startDate) {
        this.subject = subject;
        this.type = type;
        this.startDate = startDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ActivityType getType() { return type; }
    public void setType(ActivityType type) { this.type = type; }

    public ActivityStatus getStatus() { return status; }
    public void setStatus(ActivityStatus status) { this.status = status; }

    public ActivityPriority getPriority() { return priority; }
    public void setPriority(ActivityPriority priority) { this.priority = priority; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) { this.outcome = outcome; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Contact getContact() { return contact; }
    public void setContact(Contact contact) { this.contact = contact; }

    public Opportunity getOpportunity() { return opportunity; }
    public void setOpportunity(Opportunity opportunity) { this.opportunity = opportunity; }

    public Lead getLead() { return lead; }
    public void setLead(Lead lead) { this.lead = lead; }

    public boolean isOverdue() {
        return status != ActivityStatus.COMPLETED && 
               status != ActivityStatus.CANCELLED && 
               startDate != null && 
               startDate.isBefore(LocalDateTime.now());
    }
}
