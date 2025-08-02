package com.crm.repository;

import com.crm.model.Lead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
    
    Optional<Lead> findByEmail(String email);
    
    List<Lead> findByStatus(Lead.LeadStatus status);
    
    List<Lead> findBySource(Lead.LeadSource source);
    
    @Query("SELECT l FROM Lead l WHERE " +
           "LOWER(l.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(l.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(l.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(l.company) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(l.jobTitle) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Lead> findBySearchTerm(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT l FROM Lead l WHERE l.status = :status ORDER BY l.score DESC")
    List<Lead> findByStatusOrderByScoreDesc(@Param("status") Lead.LeadStatus status);
    
    @Query("SELECT l FROM Lead l WHERE l.score >= :minScore ORDER BY l.score DESC")
    List<Lead> findByScoreGreaterThanEqualOrderByScoreDesc(@Param("minScore") Integer minScore);
    
    @Query("SELECT COUNT(l) FROM Lead l WHERE l.status = :status")
    Long countByStatus(@Param("status") Lead.LeadStatus status);
    
    @Query("SELECT COUNT(l) FROM Lead l WHERE l.source = :source")
    Long countBySource(@Param("source") Lead.LeadSource source);
    
    @Query("SELECT l FROM Lead l WHERE l.createdAt BETWEEN :startDate AND :endDate")
    List<Lead> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                     @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT l FROM Lead l WHERE l.status = 'CONVERTED' AND l.convertedAt BETWEEN :startDate AND :endDate")
    List<Lead> findConvertedLeadsBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT AVG(l.score) FROM Lead l WHERE l.status = :status")
    Double getAverageScoreByStatus(@Param("status") Lead.LeadStatus status);
}
