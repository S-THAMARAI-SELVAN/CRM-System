package com.crm.controller;

import com.crm.model.Customer;
import com.crm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<Page<Customer>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "companyName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Customer> customers;
        if (search != null && !search.trim().isEmpty()) {
            customers = customerRepository.findBySearchTerm(search.trim(), pageable);
        } else {
            customers = customerRepository.findAll(pageable);
        }
        
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/with-contacts")
    public ResponseEntity<Customer> getCustomerWithContacts(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findByIdWithContacts(id);
        return customer.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/with-opportunities")
    public ResponseEntity<Customer> getCustomerWithOpportunities(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findByIdWithOpportunities(id);
        return customer.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerRepository.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, 
                                                  @Valid @RequestBody Customer customerDetails) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setCompanyName(customerDetails.getCompanyName());
            customer.setIndustry(customerDetails.getIndustry());
            customer.setCompanySize(customerDetails.getCompanySize());
            customer.setWebsite(customerDetails.getWebsite());
            customer.setPhone(customerDetails.getPhone());
            customer.setEmail(customerDetails.getEmail());
            customer.setAddress(customerDetails.getAddress());
            customer.setCity(customerDetails.getCity());
            customer.setState(customerDetails.getState());
            customer.setPostalCode(customerDetails.getPostalCode());
            customer.setCountry(customerDetails.getCountry());
            customer.setStatus(customerDetails.getStatus());
            
            Customer updatedCustomer = customerRepository.save(customer);
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<Customer>> getCustomersByStatus(@PathVariable Customer.CustomerStatus status) {
        List<Customer> customers = customerRepository.findByStatus(status);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/industries")
    public ResponseEntity<List<String>> getDistinctIndustries() {
        List<String> industries = customerRepository.findDistinctIndustries();
        return ResponseEntity.ok(industries);
    }

    @GetMapping("/stats")
    public ResponseEntity<CustomerStats> getCustomerStats() {
        CustomerStats stats = new CustomerStats();
        stats.setTotalCustomers(customerRepository.count());
        stats.setActiveCustomers(customerRepository.countByStatus(Customer.CustomerStatus.ACTIVE));
        stats.setProspectCustomers(customerRepository.countByStatus(Customer.CustomerStatus.PROSPECT));
        stats.setInactiveCustomers(customerRepository.countByStatus(Customer.CustomerStatus.INACTIVE));
        return ResponseEntity.ok(stats);
    }

    public static class CustomerStats {
        private Long totalCustomers;
        private Long activeCustomers;
        private Long prospectCustomers;
        private Long inactiveCustomers;

        // Getters and setters
        public Long getTotalCustomers() { return totalCustomers; }
        public void setTotalCustomers(Long totalCustomers) { this.totalCustomers = totalCustomers; }
        public Long getActiveCustomers() { return activeCustomers; }
        public void setActiveCustomers(Long activeCustomers) { this.activeCustomers = activeCustomers; }
        public Long getProspectCustomers() { return prospectCustomers; }
        public void setProspectCustomers(Long prospectCustomers) { this.prospectCustomers = prospectCustomers; }
        public Long getInactiveCustomers() { return inactiveCustomers; }
        public void setInactiveCustomers(Long inactiveCustomers) { this.inactiveCustomers = inactiveCustomers; }
    }
}
