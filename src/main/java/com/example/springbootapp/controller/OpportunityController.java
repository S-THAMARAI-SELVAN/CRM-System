package com.example.springbootapp.controller;

import com.example.springbootapp.model.Opportunity;
import com.example.springbootapp.repository.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/opportunities")
@CrossOrigin(origins = "http://localhost:3000")
public class OpportunityController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @GetMapping
    public ResponseEntity<Page<Opportunity>> getAllOpportunities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String search) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Opportunity> opportunities;
        if (search != null && !search.trim().isEmpty()) {
            opportunities = opportunityRepository.findBySearchTerm(search.trim(), pageable);
        } else {
            opportunities = opportunityRepository.findAll(pageable);
        }
        
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Opportunity> getOpportunityById(@PathVariable Long id) {
        Optional<Opportunity> opportunity = opportunityRepository.findById(id);
        return opportunity.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Opportunity>> getOpportunitiesByCustomer(@PathVariable Long customerId) {
        List<Opportunity> opportunities = opportunityRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
        return ResponseEntity.ok(opportunities);
    }

    @PostMapping
    public ResponseEntity<Opportunity> createOpportunity(@Valid @RequestBody Opportunity opportunity) {
        try {
            Opportunity savedOpportunity = opportunityRepository.save(opportunity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOpportunity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Opportunity> updateOpportunity(@PathVariable Long id, 
                                                       @Valid @RequestBody Opportunity opportunityDetails) {
        Optional<Opportunity> optionalOpportunity = opportunityRepository.findById(id);
        
        if (optionalOpportunity.isPresent()) {
            Opportunity opportunity = optionalOpportunity.get();
            opportunity.setName(opportunityDetails.getName());
            opportunity.setDescription(opportunityDetails.getDescription());
            opportunity.setAmount(opportunityDetails.getAmount());
            opportunity.setStage(opportunityDetails.getStage());
            opportunity.setProbability(opportunityDetails.getProbability());
            opportunity.setExpectedCloseDate(opportunityDetails.getExpectedCloseDate());
            opportunity.setSource(opportunityDetails.getSource());
            opportunity.setNotes(opportunityDetails.getNotes());
            opportunity.setPrimaryContact(opportunityDetails.getPrimaryContact());
            
            Opportunity updatedOpportunity = opportunityRepository.save(opportunity);
            return ResponseEntity.ok(updatedOpportunity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable Long id) {
        if (opportunityRepository.existsById(id)) {
            opportunityRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-stage/{stage}")
    public ResponseEntity<List<Opportunity>> getOpportunitiesByStage(@PathVariable Opportunity.OpportunityStage stage) {
        List<Opportunity> opportunities = opportunityRepository.findByStageOrderByAmountDesc(stage);
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/pipeline-value")
    public ResponseEntity<BigDecimal> getPipelineValue() {
        BigDecimal pipelineValue = opportunityRepository.calculateWeightedPipeline();
        return ResponseEntity.ok(pipelineValue != null ? pipelineValue : BigDecimal.ZERO);
    }

    @GetMapping("/stats")
    public ResponseEntity<OpportunityStats> getOpportunityStats() {
        OpportunityStats stats = new OpportunityStats();
        stats.setTotalOpportunities(opportunityRepository.count());
        stats.setTotalValue(opportunityRepository.sumAmountByStage(Opportunity.OpportunityStage.CLOSED_WON));
        stats.setPipelineValue(opportunityRepository.calculateWeightedPipeline());
        stats.setAverageDealSize(opportunityRepository.getAverageWonDealSize());
        
        List<Object[]> stageStats = opportunityRepository.getOpportunityStatsByStage();
        stats.setStageBreakdown(stageStats);
        
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Opportunity>> getOverdueOpportunities() {
        List<Opportunity> overdueOpportunities = opportunityRepository.findOverdueOpportunities(LocalDate.now());
        return ResponseEntity.ok(overdueOpportunities);
    }

    public static class OpportunityStats {
        private Long totalOpportunities;
        private BigDecimal totalValue;
        private BigDecimal pipelineValue;
        private BigDecimal averageDealSize;
        private List<Object[]> stageBreakdown;

        // Getters and setters
        public Long getTotalOpportunities() { return totalOpportunities; }
        public void setTotalOpportunities(Long totalOpportunities) { this.totalOpportunities = totalOpportunities; }
        public BigDecimal getTotalValue() { return totalValue; }
        public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }
        public BigDecimal getPipelineValue() { return pipelineValue; }
        public void setPipelineValue(BigDecimal pipelineValue) { this.pipelineValue = pipelineValue; }
        public BigDecimal getAverageDealSize() { return averageDealSize; }
        public void setAverageDealSize(BigDecimal averageDealSize) { this.averageDealSize = averageDealSize; }
        public List<Object[]> getStageBreakdown() { return stageBreakdown; }
        public void setStageBreakdown(List<Object[]> stageBreakdown) { this.stageBreakdown = stageBreakdown; }
    }
}
