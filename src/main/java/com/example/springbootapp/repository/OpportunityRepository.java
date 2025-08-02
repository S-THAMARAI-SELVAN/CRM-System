package com.example.springbootapp.repository;

import com.example.springbootapp.model.Opportunity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    
    List<Opportunity> findByCustomerId(Long customerId);
    
    List<Opportunity> findByStage(Opportunity.OpportunityStage stage);
    
    @Query("SELECT o FROM Opportunity o WHERE " +
           "LOWER(o.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(o.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(o.customer.companyName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Opportunity> findBySearchTerm(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT o FROM Opportunity o WHERE o.stage = :stage ORDER BY o.amount DESC")
    List<Opportunity> findByStageOrderByAmountDesc(@Param("stage") Opportunity.OpportunityStage stage);
    
    @Query("SELECT o FROM Opportunity o WHERE o.expectedCloseDate BETWEEN :startDate AND :endDate")
    List<Opportunity> findByExpectedCloseDateBetween(@Param("startDate") LocalDate startDate, 
                                                    @Param("endDate") LocalDate endDate);
    
    @Query("SELECT o FROM Opportunity o WHERE o.actualCloseDate BETWEEN :startDate AND :endDate AND o.stage = 'CLOSED_WON'")
    List<Opportunity> findClosedWonBetween(@Param("startDate") LocalDate startDate, 
                                          @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(o) FROM Opportunity o WHERE o.stage = :stage")
    Long countByStage(@Param("stage") Opportunity.OpportunityStage stage);
    
    @Query("SELECT SUM(o.amount) FROM Opportunity o WHERE o.stage = :stage")
    BigDecimal sumAmountByStage(@Param("stage") Opportunity.OpportunityStage stage);
    
    @Query("SELECT SUM(o.amount * o.probability / 100.0) FROM Opportunity o WHERE o.stage NOT IN ('CLOSED_WON', 'CLOSED_LOST')")
    BigDecimal calculateWeightedPipeline();
    
    @Query("SELECT o.stage, COUNT(o), SUM(o.amount) FROM Opportunity o GROUP BY o.stage")
    List<Object[]> getOpportunityStatsByStage();
    
    @Query("SELECT o FROM Opportunity o WHERE o.expectedCloseDate < :date AND o.stage NOT IN ('CLOSED_WON', 'CLOSED_LOST')")
    List<Opportunity> findOverdueOpportunities(@Param("date") LocalDate date);
    
    @Query("SELECT AVG(o.amount) FROM Opportunity o WHERE o.stage = 'CLOSED_WON'")
    BigDecimal getAverageWonDealSize();
    
    @Query("SELECT o FROM Opportunity o WHERE o.customer.id = :customerId ORDER BY o.createdAt DESC")
    List<Opportunity> findByCustomerIdOrderByCreatedAtDesc(@Param("customerId") Long customerId);
}
