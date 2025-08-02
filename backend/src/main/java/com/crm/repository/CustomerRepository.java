package com.crm.repository;

import com.crm.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByEmail(String email);
    
    List<Customer> findByStatus(Customer.CustomerStatus status);
    
    @Query("SELECT c FROM Customer c WHERE " +
           "LOWER(c.companyName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.industry) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.city) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Customer> findBySearchTerm(@Param("search") String search, Pageable pageable);
    
    List<Customer> findByIndustry(String industry);
    
    List<Customer> findByCity(String city);
    
    @Query("SELECT DISTINCT c.industry FROM Customer c WHERE c.industry IS NOT NULL ORDER BY c.industry")
    List<String> findDistinctIndustries();
    
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.status = :status")
    Long countByStatus(@Param("status") Customer.CustomerStatus status);
    
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.contacts WHERE c.id = :id")
    Optional<Customer> findByIdWithContacts(@Param("id") Long id);
    
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.opportunities WHERE c.id = :id")
    Optional<Customer> findByIdWithOpportunities(@Param("id") Long id);
}
