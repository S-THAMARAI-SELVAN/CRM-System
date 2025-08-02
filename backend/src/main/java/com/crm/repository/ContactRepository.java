package com.crm.repository;

import com.crm.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    
    Optional<Contact> findByEmail(String email);
    
    List<Contact> findByCustomerId(Long customerId);
    
    List<Contact> findByStatus(Contact.ContactStatus status);
    
    @Query("SELECT c FROM Contact c WHERE c.customer.id = :customerId AND c.isPrimary = true")
    Optional<Contact> findPrimaryContactByCustomerId(@Param("customerId") Long customerId);
    
    @Query("SELECT c FROM Contact c WHERE " +
           "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.jobTitle) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.customer.companyName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Contact> findBySearchTerm(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT c FROM Contact c WHERE c.customer.id = :customerId AND " +
           "(LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Contact> findByCustomerIdAndSearchTerm(@Param("customerId") Long customerId, 
                                               @Param("search") String search);
    
    @Query("SELECT COUNT(c) FROM Contact c WHERE c.status = :status")
    Long countByStatus(@Param("status") Contact.ContactStatus status);
    
    @Query("SELECT c FROM Contact c LEFT JOIN FETCH c.activities WHERE c.id = :id")
    Optional<Contact> findByIdWithActivities(@Param("id") Long id);
}
