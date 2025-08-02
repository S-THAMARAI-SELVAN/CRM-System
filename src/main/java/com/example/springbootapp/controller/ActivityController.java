package com.example.springbootapp.controller;

import com.example.springbootapp.model.Activity;
import com.example.springbootapp.repository.ActivityRepository;
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
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:3000")
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping
    public ResponseEntity<Page<Activity>> getAllActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String search) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Activity> activities;
        if (search != null && !search.trim().isEmpty()) {
            activities = activityRepository.findBySearchTerm(search.trim(), pageable);
        } else {
            activities = activityRepository.findAll(pageable);
        }
        
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        return activity.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Activity>> getActivitiesByCustomer(@PathVariable Long customerId) {
        List<Activity> activities = activityRepository.findByCustomerIdOrderByStartDateDesc(customerId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<List<Activity>> getActivitiesByContact(@PathVariable Long contactId) {
        List<Activity> activities = activityRepository.findByContactIdOrderByStartDateDesc(contactId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/opportunity/{opportunityId}")
    public ResponseEntity<List<Activity>> getActivitiesByOpportunity(@PathVariable Long opportunityId) {
        List<Activity> activities = activityRepository.findByOpportunityId(opportunityId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<Activity>> getActivitiesByLead(@PathVariable Long leadId) {
        List<Activity> activities = activityRepository.findByLeadId(leadId);
        return ResponseEntity.ok(activities);
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@Valid @RequestBody Activity activity) {
        try {
            Activity savedActivity = activityRepository.save(activity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, 
                                                 @Valid @RequestBody Activity activityDetails) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        
        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            activity.setSubject(activityDetails.getSubject());
            activity.setDescription(activityDetails.getDescription());
            activity.setType(activityDetails.getType());
            activity.setStatus(activityDetails.getStatus());
            activity.setPriority(activityDetails.getPriority());
            activity.setStartDate(activityDetails.getStartDate());
            activity.setEndDate(activityDetails.getEndDate());
            activity.setOutcome(activityDetails.getOutcome());
            
            Activity updatedActivity = activityRepository.save(activity);
            return ResponseEntity.ok(updatedActivity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<Activity>> getActivitiesByStatus(@PathVariable Activity.ActivityStatus status) {
        List<Activity> activities = activityRepository.findByStatus(status);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/by-type/{type}")
    public ResponseEntity<List<Activity>> getActivitiesByType(@PathVariable Activity.ActivityType type) {
        List<Activity> activities = activityRepository.findByType(type);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Activity>> getOverdueActivities() {
        List<Activity> overdueActivities = activityRepository.findOverdueActivities(LocalDateTime.now());
        return ResponseEntity.ok(overdueActivities);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Activity>> getUpcomingActivities(
            @RequestParam(defaultValue = "24") int hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = now.plusHours(hours);
        List<Activity> upcomingActivities = activityRepository.findUpcomingActivities(now, endTime);
        return ResponseEntity.ok(upcomingActivities);
    }

    @GetMapping("/stats")
    public ResponseEntity<ActivityStats> getActivityStats() {
        ActivityStats stats = new ActivityStats();
        stats.setTotalActivities(activityRepository.count());
        stats.setPlannedActivities(activityRepository.countByStatus(Activity.ActivityStatus.PLANNED));
        stats.setCompletedActivities(activityRepository.countByStatus(Activity.ActivityStatus.COMPLETED));
        stats.setOverdueActivities((long) activityRepository.findOverdueActivities(LocalDateTime.now()).size());
        
        return ResponseEntity.ok(stats);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Activity> completeActivity(@PathVariable Long id, 
                                                   @RequestParam(required = false) String outcome) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        
        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            activity.setStatus(Activity.ActivityStatus.COMPLETED);
            activity.setCompletedAt(LocalDateTime.now());
            if (outcome != null) {
                activity.setOutcome(outcome);
            }
            
            Activity updatedActivity = activityRepository.save(activity);
            return ResponseEntity.ok(updatedActivity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public static class ActivityStats {
        private Long totalActivities;
        private Long plannedActivities;
        private Long completedActivities;
        private Long overdueActivities;

        // Getters and setters
        public Long getTotalActivities() { return totalActivities; }
        public void setTotalActivities(Long totalActivities) { this.totalActivities = totalActivities; }
        public Long getPlannedActivities() { return plannedActivities; }
        public void setPlannedActivities(Long plannedActivities) { this.plannedActivities = plannedActivities; }
        public Long getCompletedActivities() { return completedActivities; }
        public void setCompletedActivities(Long completedActivities) { this.completedActivities = completedActivities; }
        public Long getOverdueActivities() { return overdueActivities; }
        public void setOverdueActivities(Long overdueActivities) { this.overdueActivities = overdueActivities; }
    }
}
