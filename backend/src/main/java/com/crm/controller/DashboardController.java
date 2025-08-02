package com.crm.controller;

import com.crm.model.Customer;
import com.crm.model.Lead;
import com.crm.model.Opportunity;
import com.crm.repository.CustomerRepository;
import com.crm.repository.LeadRepository;
import com.crm.repository.OpportunityRepository;
import com.crm.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Customer stats
        stats.put("totalCustomers", customerRepository.count());
        stats.put("activeCustomers", customerRepository.countByStatus(Customer.CustomerStatus.ACTIVE));
        stats.put("prospectCustomers", customerRepository.countByStatus(Customer.CustomerStatus.PROSPECT));
        
        // Lead stats
        stats.put("totalLeads", leadRepository.count());
        stats.put("newLeads", leadRepository.countByStatus(Lead.LeadStatus.NEW));
        stats.put("qualifiedLeads", leadRepository.countByStatus(Lead.LeadStatus.QUALIFIED));
        stats.put("convertedLeads", leadRepository.countByStatus(Lead.LeadStatus.CONVERTED));
        
        // Opportunity stats
        stats.put("totalOpportunities", opportunityRepository.count());
        BigDecimal totalRevenue = opportunityRepository.sumAmountByStage(Opportunity.OpportunityStage.CLOSED_WON);
        stats.put("totalRevenue", totalRevenue != null ? totalRevenue : BigDecimal.ZERO);
        
        BigDecimal pipelineValue = opportunityRepository.calculateWeightedPipeline();
        stats.put("pipelineValue", pipelineValue != null ? pipelineValue : BigDecimal.ZERO);
        
        BigDecimal avgDealSize = opportunityRepository.getAverageWonDealSize();
        stats.put("averageDealSize", avgDealSize != null ? avgDealSize : BigDecimal.ZERO);
        
        // Activity stats
        stats.put("totalActivities", activityRepository.count());
        
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/lead-sources")
    public ResponseEntity<Map<String, Long>> getLeadSourceStats() {
        Map<String, Long> sourceStats = new HashMap<>();
        
        for (Lead.LeadSource source : Lead.LeadSource.values()) {
            Long count = leadRepository.countBySource(source);
            sourceStats.put(source.name(), count);
        }
        
        return ResponseEntity.ok(sourceStats);
    }

    @GetMapping("/opportunity-stages")
    public ResponseEntity<Map<String, Object>> getOpportunityStageStats() {
        Map<String, Object> stageStats = new HashMap<>();
        
        for (Opportunity.OpportunityStage stage : Opportunity.OpportunityStage.values()) {
            Map<String, Object> stageData = new HashMap<>();
            stageData.put("count", opportunityRepository.countByStage(stage));
            BigDecimal amount = opportunityRepository.sumAmountByStage(stage);
            stageData.put("amount", amount != null ? amount : BigDecimal.ZERO);
            stageStats.put(stage.name(), stageData);
        }
        
        return ResponseEntity.ok(stageStats);
    }

    @GetMapping("/recent-activities")
    public ResponseEntity<Object> getRecentActivities() {
        // This would return recent activities - simplified for now
        return ResponseEntity.ok("Recent activities endpoint - to be implemented");
    }
}
