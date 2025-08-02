package com.example.springbootapp.controller;

import com.example.springbootapp.model.Contact;
import com.example.springbootapp.repository.ContactRepository;
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
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public ResponseEntity<Page<Contact>> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Contact> contacts;
        if (search != null && !search.trim().isEmpty()) {
            contacts = contactRepository.findBySearchTerm(search.trim(), pageable);
        } else {
            contacts = contactRepository.findAll(pageable);
        }
        
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/with-activities")
    public ResponseEntity<Contact> getContactWithActivities(@PathVariable Long id) {
        Optional<Contact> contact = contactRepository.findByIdWithActivities(id);
        return contact.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Contact>> getContactsByCustomer(@PathVariable Long customerId) {
        List<Contact> contacts = contactRepository.findByCustomerId(customerId);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/customer/{customerId}/primary")
    public ResponseEntity<Contact> getPrimaryContactByCustomer(@PathVariable Long customerId) {
        Optional<Contact> contact = contactRepository.findPrimaryContactByCustomerId(customerId);
        return contact.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        try {
            Contact savedContact = contactRepository.save(contact);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, 
                                               @Valid @RequestBody Contact contactDetails) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            contact.setFirstName(contactDetails.getFirstName());
            contact.setLastName(contactDetails.getLastName());
            contact.setJobTitle(contactDetails.getJobTitle());
            contact.setEmail(contactDetails.getEmail());
            contact.setPhone(contactDetails.getPhone());
            contact.setMobile(contactDetails.getMobile());
            contact.setLinkedinUrl(contactDetails.getLinkedinUrl());
            contact.setStatus(contactDetails.getStatus());
            contact.setIsPrimary(contactDetails.getIsPrimary());
            
            Contact updatedContact = contactRepository.save(contact);
            return ResponseEntity.ok(updatedContact);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<Contact>> getContactsByStatus(@PathVariable Contact.ContactStatus status) {
        List<Contact> contacts = contactRepository.findByStatus(status);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/customer/{customerId}/search")
    public ResponseEntity<List<Contact>> searchContactsByCustomer(
            @PathVariable Long customerId,
            @RequestParam String search) {
        List<Contact> contacts = contactRepository.findByCustomerIdAndSearchTerm(customerId, search);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/stats")
    public ResponseEntity<ContactStats> getContactStats() {
        ContactStats stats = new ContactStats();
        stats.setTotalContacts(contactRepository.count());
        stats.setActiveContacts(contactRepository.countByStatus(Contact.ContactStatus.ACTIVE));
        stats.setInactiveContacts(contactRepository.countByStatus(Contact.ContactStatus.INACTIVE));
        stats.setDoNotContactContacts(contactRepository.countByStatus(Contact.ContactStatus.DO_NOT_CONTACT));
        return ResponseEntity.ok(stats);
    }

    public static class ContactStats {
        private Long totalContacts;
        private Long activeContacts;
        private Long inactiveContacts;
        private Long doNotContactContacts;

        // Getters and setters
        public Long getTotalContacts() { return totalContacts; }
        public void setTotalContacts(Long totalContacts) { this.totalContacts = totalContacts; }
        public Long getActiveContacts() { return activeContacts; }
        public void setActiveContacts(Long activeContacts) { this.activeContacts = activeContacts; }
        public Long getInactiveContacts() { return inactiveContacts; }
        public void setInactiveContacts(Long inactiveContacts) { this.inactiveContacts = inactiveContacts; }
        public Long getDoNotContactContacts() { return doNotContactContacts; }
        public void setDoNotContactContacts(Long doNotContactContacts) { this.doNotContactContacts = doNotContactContacts; }
    }
}
