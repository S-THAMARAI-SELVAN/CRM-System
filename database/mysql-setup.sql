-- CRM Database Setup for MySQL
-- Run this script in your MySQL server

-- Create database
CREATE DATABASE IF NOT EXISTS crm_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE crm_system;

-- Create customers table
CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL,
    industry VARCHAR(50),
    company_size VARCHAR(20),
    website VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(200),
    city VARCHAR(50),
    state VARCHAR(50),
    postal_code VARCHAR(20),
    country VARCHAR(50),
    status ENUM('ACTIVE', 'INACTIVE', 'PROSPECT') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_company_name (company_name),
    INDEX idx_email (email),
    INDEX idx_status (status),
    INDEX idx_industry (industry)
);

-- Create contacts table
CREATE TABLE contacts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    job_title VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    mobile VARCHAR(20),
    linkedin_url VARCHAR(100),
    status ENUM('ACTIVE', 'INACTIVE', 'DO_NOT_CONTACT') DEFAULT 'ACTIVE',
    is_primary BOOLEAN DEFAULT FALSE,
    customer_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    INDEX idx_customer_id (customer_id),
    INDEX idx_email (email),
    INDEX idx_name (first_name, last_name),
    INDEX idx_status (status)
);

-- Create leads table
CREATE TABLE leads (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    company VARCHAR(100),
    job_title VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    status ENUM('NEW', 'CONTACTED', 'QUALIFIED', 'UNQUALIFIED', 'CONVERTED', 'LOST') DEFAULT 'NEW',
    source ENUM('WEBSITE', 'SOCIAL_MEDIA', 'EMAIL_CAMPAIGN', 'REFERRAL', 'COLD_CALL', 'TRADE_SHOW', 'ADVERTISEMENT', 'OTHER'),
    score INT DEFAULT 0,
    estimated_value DECIMAL(10,2),
    notes TEXT,
    converted_at TIMESTAMP NULL,
    converted_customer_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (converted_customer_id) REFERENCES customers(id) ON DELETE SET NULL,
    INDEX idx_status (status),
    INDEX idx_source (source),
    INDEX idx_score (score),
    INDEX idx_email (email),
    INDEX idx_company (company)
);

-- Create opportunities table
CREATE TABLE opportunities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    amount DECIMAL(12,2) NOT NULL,
    stage ENUM('PROSPECTING', 'QUALIFICATION', 'NEEDS_ANALYSIS', 'PROPOSAL', 'NEGOTIATION', 'CLOSED_WON', 'CLOSED_LOST') DEFAULT 'PROSPECTING',
    probability INT DEFAULT 10,
    expected_close_date DATE,
    actual_close_date DATE,
    source ENUM('INBOUND_LEAD', 'OUTBOUND_PROSPECTING', 'REFERRAL', 'EXISTING_CUSTOMER', 'PARTNER', 'TRADE_SHOW', 'OTHER'),
    notes TEXT,
    customer_id BIGINT NOT NULL,
    primary_contact_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (primary_contact_id) REFERENCES contacts(id) ON DELETE SET NULL,
    INDEX idx_customer_id (customer_id),
    INDEX idx_stage (stage),
    INDEX idx_amount (amount),
    INDEX idx_expected_close_date (expected_close_date),
    INDEX idx_primary_contact_id (primary_contact_id)
);

-- Create activities table
CREATE TABLE activities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject VARCHAR(200) NOT NULL,
    description TEXT,
    type ENUM('CALL', 'EMAIL', 'MEETING', 'TASK', 'NOTE', 'DEMO', 'PROPOSAL', 'FOLLOW_UP') NOT NULL,
    status ENUM('PLANNED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED', 'OVERDUE') DEFAULT 'PLANNED',
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'URGENT') DEFAULT 'MEDIUM',
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    completed_at TIMESTAMP NULL,
    outcome TEXT,
    customer_id BIGINT,
    contact_id BIGINT,
    opportunity_id BIGINT,
    lead_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (contact_id) REFERENCES contacts(id) ON DELETE CASCADE,
    FOREIGN KEY (opportunity_id) REFERENCES opportunities(id) ON DELETE CASCADE,
    FOREIGN KEY (lead_id) REFERENCES leads(id) ON DELETE CASCADE,
    INDEX idx_customer_id (customer_id),
    INDEX idx_contact_id (contact_id),
    INDEX idx_opportunity_id (opportunity_id),
    INDEX idx_lead_id (lead_id),
    INDEX idx_type (type),
    INDEX idx_status (status),
    INDEX idx_start_date (start_date),
    INDEX idx_priority (priority)
);

-- Insert sample data
INSERT INTO customers (company_name, industry, company_size, website, phone, email, address, city, state, postal_code, country, status) VALUES
('TechCorp Solutions', 'Technology', 'Medium', 'https://techcorp.com', '+1-555-0101', 'info@techcorp.com', '123 Tech Street', 'San Francisco', 'CA', '94105', 'USA', 'ACTIVE'),
('Global Manufacturing Inc', 'Manufacturing', 'Large', 'https://globalmanuf.com', '+1-555-0102', 'contact@globalmanuf.com', '456 Industrial Ave', 'Detroit', 'MI', '48201', 'USA', 'ACTIVE'),
('StartupXYZ', 'Software', 'Small', 'https://startupxyz.com', '+1-555-0103', 'hello@startupxyz.com', '789 Innovation Blvd', 'Austin', 'TX', '73301', 'USA', 'PROSPECT'),
('Healthcare Partners', 'Healthcare', 'Medium', 'https://healthpartners.com', '+1-555-0104', 'info@healthpartners.com', '321 Medical Center Dr', 'Boston', 'MA', '02101', 'USA', 'ACTIVE'),
('Retail Giants LLC', 'Retail', 'Large', 'https://retailgiants.com', '+1-555-0105', 'sales@retailgiants.com', '654 Commerce St', 'New York', 'NY', '10001', 'USA', 'ACTIVE');

INSERT INTO contacts (first_name, last_name, job_title, email, phone, mobile, status, is_primary, customer_id) VALUES
('John', 'Smith', 'CTO', 'john.smith@techcorp.com', '+1-555-0201', '+1-555-0301', 'ACTIVE', TRUE, 1),
('Sarah', 'Johnson', 'VP of Operations', 'sarah.johnson@globalmanuf.com', '+1-555-0202', '+1-555-0302', 'ACTIVE', TRUE, 2),
('Mike', 'Davis', 'CEO', 'mike.davis@startupxyz.com', '+1-555-0203', '+1-555-0303', 'ACTIVE', TRUE, 3),
('Emily', 'Brown', 'Director of IT', 'emily.brown@healthpartners.com', '+1-555-0204', '+1-555-0304', 'ACTIVE', TRUE, 4),
('David', 'Wilson', 'Procurement Manager', 'david.wilson@retailgiants.com', '+1-555-0205', '+1-555-0305', 'ACTIVE', TRUE, 5);

INSERT INTO leads (first_name, last_name, company, job_title, email, phone, status, source, score, estimated_value, notes) VALUES
('Alice', 'Cooper', 'Future Tech Inc', 'CIO', 'alice.cooper@futuretech.com', '+1-555-0401', 'QUALIFIED', 'WEBSITE', 85, 50000.00, 'Interested in enterprise solution'),
('Bob', 'Martinez', 'Innovation Labs', 'VP Technology', 'bob.martinez@innovlabs.com', '+1-555-0402', 'CONTACTED', 'REFERRAL', 70, 75000.00, 'Referred by existing customer'),
('Carol', 'Taylor', 'NextGen Systems', 'IT Director', 'carol.taylor@nextgen.com', '+1-555-0403', 'NEW', 'SOCIAL_MEDIA', 60, 30000.00, 'Downloaded whitepaper'),
('Daniel', 'Anderson', 'Smart Solutions', 'CEO', 'daniel.anderson@smartsol.com', '+1-555-0404', 'QUALIFIED', 'EMAIL_CAMPAIGN', 90, 100000.00, 'High-value prospect'),
('Eva', 'Thompson', 'Digital Dynamics', 'CTO', 'eva.thompson@digitaldyn.com', '+1-555-0405', 'CONTACTED', 'TRADE_SHOW', 75, 60000.00, 'Met at tech conference');

INSERT INTO opportunities (name, description, amount, stage, probability, expected_close_date, source, notes, customer_id) VALUES
('TechCorp Enterprise License', 'Annual enterprise software license renewal', 150000.00, 'NEGOTIATION', 90, '2024-03-15', 'EXISTING_CUSTOMER', 'Renewal with 20% increase', 1),
('Global Manufacturing Integration', 'System integration project for manufacturing processes', 250000.00, 'PROPOSAL', 75, '2024-04-30', 'INBOUND_LEAD', 'Large integration project', 2),
('StartupXYZ Initial Setup', 'Initial CRM setup for growing startup', 25000.00, 'QUALIFICATION', 50, '2024-02-28', 'OUTBOUND_PROSPECTING', 'Small but growing company', 3),
('Healthcare Compliance Solution', 'HIPAA compliance software implementation', 80000.00, 'NEEDS_ANALYSIS', 60, '2024-05-15', 'REFERRAL', 'Compliance requirements', 4),
('Retail Analytics Platform', 'Customer analytics and reporting platform', 120000.00, 'PROSPECTING', 25, '2024-06-30', 'PARTNER', 'Early stage opportunity', 5);

INSERT INTO activities (subject, description, type, status, priority, start_date, customer_id, contact_id) VALUES
('Follow-up call with John Smith', 'Discuss contract renewal terms', 'CALL', 'COMPLETED', 'HIGH', '2024-01-15 10:00:00', 1, 1),
('Product demo for Global Manufacturing', 'Demonstrate integration capabilities', 'DEMO', 'PLANNED', 'HIGH', '2024-02-01 14:00:00', 2, 2),
('Send proposal to StartupXYZ', 'Email detailed proposal document', 'EMAIL', 'PLANNED', 'MEDIUM', '2024-01-25 09:00:00', 3, 3),
('Meeting with Healthcare Partners', 'Discuss compliance requirements', 'MEETING', 'PLANNED', 'HIGH', '2024-02-05 11:00:00', 4, 4),
('Retail Giants quarterly review', 'Review account status and opportunities', 'MEETING', 'COMPLETED', 'MEDIUM', '2024-01-10 15:00:00', 5, 5);
