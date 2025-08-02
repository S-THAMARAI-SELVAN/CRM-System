package com.example.springbootapp.controller;

import com.example.springbootapp.model.Lead;
import com.example.springbootapp.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin(origins = "http://localhost:3000")
public class LeadController {

    @Autowired
    private LeadRepository leadRepository;

    @GetMapping
    public ResponseEntity<Page<Lead>> getAllLeads(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String search) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Lead> leads;
        if (search != null && !search.trim().isEmpty()) {
            leads = leadRepository.findBySearchTerm(search.trim(), pageable);
        } else {
            leads = leadRepository.findAll(pageable);
        }
        
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lead> getLeadById(@PathVariable Long id) {
        Optional<Lead> lead = leadRepository.findById(id);
        return lead.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lead> createLead(@Valid @RequestBody Lead lead) {
        try {
            Lead savedLead = leadRepository.save(lead);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLead);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lead> updateLead(@PathVariable Long id, 
                                         @Valid @RequestBody Lead leadDetails) {
        Optional<Lead> optionalLead = leadRepository.findById(id);
        
        if (optionalLead.isPresent()) {
            Lead lead = optionalLead.get();
            lead.setFirstName(leadDetails.getFirstName());
            lead.setLastName(leadDetails.getLastName());
            lead.setCompany(leadDetails.getCompany());
            lead.setJobTitle(leadDetails.getJobTitle());
            lead.setEmail(leadDetails.getEmail());
            lead.setPhone(leadDetails.getPhone());
            lead.setStatus(leadDetails.getStatus());
            lead.setSource(leadDetails.getSource());
            lead.setScore(leadDetails.getScore());
            lead.setEstimatedValue(leadDetails.getEstimatedValue());
            lead.setNotes(leadDetails.getNotes());
            
            // Set conversion timestamp if status changed to CONVERTED
            if (leadDetails.getStatus() == Lead.LeadStatus.CONVERTED && 
                lead.getConvertedAt() == null) {
                lead.setConvertedAt(LocalDateTime.now());
            }
            
            Lead updatedLead = leadRepository.save(lead);
            return ResponseEntity.ok(updatedLead);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
        if (leadRepository.existsById(id)) {
            leadRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<Lead>> getLeadsByStatus(@PathVariable Lead.LeadStatus status) {
        List<Lead> leads = leadRepository.findByStatusOrderByScoreDesc(status);
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/by-source/{source}")
    public ResponseEntity<List<Lead>> getLeadsBySource(@PathVariable Lead.LeadSource source) {
        List<Lead> leads = leadRepository.findBySource(source);
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/high-score")
    public ResponseEntity<List<Lead>> getHighScoreLeads(@RequestParam(defaultValue = "70") Integer minScore) {
        List<Lead> leads = leadRepository.findByScoreGreaterThanEqualOrderByScoreDesc(minScore);
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/stats")
    public ResponseEntity<LeadStats> getLeadStats() {
        LeadStats stats = new LeadStats();
        stats.setTotalLeads(leadRepository.count());
        stats.setNewLeads(leadRepository.countByStatus(Lead.LeadStatus.NEW));
        stats.setContactedLeads(leadRepository.countByStatus(Lead.LeadStatus.CONTACTED));
        stats.setQualifiedLeads(leadRepository.countByStatus(Lead.LeadStatus.QUALIFIED));
        stats.setConvertedLeads(leadRepository.countByStatus(Lead.LeadStatus.CONVERTED));
        stats.setLostLeads(leadRepository.countByStatus(Lead.LeadStatus.LOST));
        stats.setAverageScore(leadRepository.getAverageScoreByStatus(Lead.LeadStatus.QUALIFIED));
        return ResponseEntity.ok(stats);
    }

    @PutMapping("/{id}/convert")
    public ResponseEntity<Lead> convertLead(@PathVariable Long id) {
        Optional<Lead> optionalLead = leadRepository.findById(id);
        
        if (optionalLead.isPresent()) {
            Lead lead = optionalLead.get();
            lead.setStatus(Lead.LeadStatus.CONVERTED);
            lead.setConvertedAt(LocalDateTime.now());
            
            Lead updatedLead = leadRepository.save(lead);
            return ResponseEntity.ok(updatedLead);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public static class LeadStats {
        private Long totalLeads;
        private Long newLeads;
        private Long contactedLeads;
        private Long qualifiedLeads;
        private Long convertedLeads;
        private Long lostLeads;
        private Double averageScore;

        // Getters and setters
        public Long getTotalLeads() { return totalLeads; }
        public void setTotalLeads(Long totalLeads) { this.totalLeads = totalLeads; }
        public Long getNewLeads() { return newLeads; }
        public void setNewLeads(Long newLeads) { this.newLeads = newLeads; }
        public Long getContactedLeads() { return contactedLeads; }
        public void setContactedLeads(Long contactedLeads) { this.contactedLeads = contactedLeads; }
        public Long getQualifiedLeads() { return qualifiedLeads; }
        public void setQualifiedLeads(Long qualifiedLeads) { this.qualifiedLeads = qualifiedLeads; }
        public Long getConvertedLeads() { return convertedLeads; }
        public void setConvertedLeads(Long convertedLeads) { this.convertedLeads = convertedLeads; }
        public Long getLostLeads() { return lostLeads; }
        public void setLostLeads(Long lostLeads) { this.lostLeads = lostLeads; }
        public Double getAverageScore() { return averageScore; }
        public void setAverageScore(Double averageScore) { this.averageScore = averageScore; }
    }
}
