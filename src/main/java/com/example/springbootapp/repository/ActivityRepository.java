package com.example.springbootapp.repository;

import com.example.springbootapp.model.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    
    List<Activity> findByCustomerId(Long customerId);
    
    List<Activity> findByContactId(Long contactId);
    
    List<Activity> findByOpportunityId(Long opportunityId);
    
    List<Activity> findByLeadId(Long leadId);
    
    List<Activity> findByStatus(Activity.ActivityStatus status);
    
    List<Activity> findByType(Activity.ActivityType type);
    
    @Query("SELECT a FROM Activity a WHERE " +
           "LOWER(a.subject) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(a.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Activity> findBySearchTerm(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT a FROM Activity a WHERE a.startDate BETWEEN :startDate AND :endDate ORDER BY a.startDate")
    List<Activity> findByStartDateBetween(@Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM Activity a WHERE a.status = 'PLANNED' AND a.startDate < :currentTime")
    List<Activity> findOverdueActivities(@Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT a FROM Activity a WHERE a.status = 'PLANNED' AND a.startDate BETWEEN :startTime AND :endTime")
    List<Activity> findUpcomingActivities(@Param("startTime") LocalDateTime startTime, 
                                         @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT COUNT(a) FROM Activity a WHERE a.status = :status")
    Long countByStatus(@Param("status") Activity.ActivityStatus status);
    
    @Query("SELECT COUNT(a) FROM Activity a WHERE a.type = :type")
    Long countByType(@Param("type") Activity.ActivityType type);
    
    @Query("SELECT a.type, COUNT(a) FROM Activity a WHERE a.completedAt BETWEEN :startDate AND :endDate GROUP BY a.type")
    List<Object[]> getActivityStatsByType(@Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM Activity a WHERE a.customer.id = :customerId ORDER BY a.startDate DESC")
    List<Activity> findByCustomerIdOrderByStartDateDesc(@Param("customerId") Long customerId);
    
    @Query("SELECT a FROM Activity a WHERE a.contact.id = :contactId ORDER BY a.startDate DESC")
    List<Activity> findByContactIdOrderByStartDateDesc(@Param("contactId") Long contactId);
    
    @Query("SELECT a FROM Activity a WHERE a.priority = :priority AND a.status IN ('PLANNED', 'IN_PROGRESS')")
    List<Activity> findByPriorityAndActiveStatus(@Param("priority") Activity.ActivityPriority priority);
}
