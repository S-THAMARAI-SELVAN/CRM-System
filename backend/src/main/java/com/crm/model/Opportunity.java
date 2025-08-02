package com.crm.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "opportunities")
public class Opportunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Opportunity name is required")
    @Size(max = 200)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 500)
    @Column(name = "description")
    private String description;

    @NotNull(message = "Amount is required")
    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage")
    private OpportunityStage stage = OpportunityStage.PROSPECTING;

    @Column(name = "probability")
    private Integer probability = 10;

    @Column(name = "expected_close_date")
    private LocalDate expectedCloseDate;

    @Column(name = "actual_close_date")
    private LocalDate actualCloseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private OpportunitySource source;

    @Size(max = 1000)
    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_contact_id")
    private Contact primaryContact;

    public enum OpportunityStage {
        PROSPECTING(10),
        QUALIFICATION(25),
        NEEDS_ANALYSIS(50),
        PROPOSAL(75),
        NEGOTIATION(90),
        CLOSED_WON(100),
        CLOSED_LOST(0);

        private final int defaultProbability;

        OpportunityStage(int defaultProbability) {
            this.defaultProbability = defaultProbability;
        }

        public int getDefaultProbability() {
            return defaultProbability;
        }
    }

    public enum OpportunitySource {
        INBOUND_LEAD, OUTBOUND_PROSPECTING, REFERRAL, EXISTING_CUSTOMER, PARTNER, TRADE_SHOW, OTHER
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (probability == null && stage != null) {
            probability = stage.getDefaultProbability();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (stage == OpportunityStage.CLOSED_WON || stage == OpportunityStage.CLOSED_LOST) {
            if (actualCloseDate == null) {
                actualCloseDate = LocalDate.now();
            }
        }
    }

    // Constructors
    public Opportunity() {}

    public Opportunity(String name, BigDecimal amount, Customer customer) {
        this.name = name;
        this.amount = amount;
        this.customer = customer;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public OpportunityStage getStage() { return stage; }
    public void setStage(OpportunityStage stage) { 
        this.stage = stage;
        if (stage != null) {
            this.probability = stage.getDefaultProbability();
        }
    }

    public Integer getProbability() { return probability; }
    public void setProbability(Integer probability) { this.probability = probability; }

    public LocalDate getExpectedCloseDate() { return expectedCloseDate; }
    public void setExpectedCloseDate(LocalDate expectedCloseDate) { this.expectedCloseDate = expectedCloseDate; }

    public LocalDate getActualCloseDate() { return actualCloseDate; }
    public void setActualCloseDate(LocalDate actualCloseDate) { this.actualCloseDate = actualCloseDate; }

    public OpportunitySource getSource() { return source; }
    public void setSource(OpportunitySource source) { this.source = source; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Contact getPrimaryContact() { return primaryContact; }
    public void setPrimaryContact(Contact primaryContact) { this.primaryContact = primaryContact; }

    public BigDecimal getWeightedAmount() {
        if (amount != null && probability != null) {
            return amount.multiply(BigDecimal.valueOf(probability / 100.0));
        }
        return BigDecimal.ZERO;
    }
}
