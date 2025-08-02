-- Sample data for CRM system

-- Insert sample customers
INSERT INTO customers (company_name, industry, company_size, website, phone, email, address, city, state, postal_code, country, status, created_at, updated_at) VALUES
('TechCorp Solutions', 'Technology', 'Medium', 'https://techcorp.com', '+1-555-0101', 'info@techcorp.com', '123 Tech Street', 'San Francisco', 'CA', '94105', 'USA', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Global Manufacturing Inc', 'Manufacturing', 'Large', 'https://globalmanuf.com', '+1-555-0102', 'contact@globalmanuf.com', '456 Industrial Ave', 'Detroit', 'MI', '48201', 'USA', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('StartupXYZ', 'Software', 'Small', 'https://startupxyz.com', '+1-555-0103', 'hello@startupxyz.com', '789 Innovation Blvd', 'Austin', 'TX', '73301', 'USA', 'PROSPECT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Healthcare Partners', 'Healthcare', 'Medium', 'https://healthpartners.com', '+1-555-0104', 'info@healthpartners.com', '321 Medical Center Dr', 'Boston', 'MA', '02101', 'USA', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Retail Giants LLC', 'Retail', 'Large', 'https://retailgiants.com', '+1-555-0105', 'sales@retailgiants.com', '654 Commerce St', 'New York', 'NY', '10001', 'USA', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample contacts
INSERT INTO contacts (first_name, last_name, job_title, email, phone, mobile, status, is_primary, customer_id, created_at, updated_at) VALUES
('John', 'Smith', 'CTO', 'john.smith@techcorp.com', '+1-555-0201', '+1-555-0301', 'ACTIVE', true, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sarah', 'Johnson', 'VP of Operations', 'sarah.johnson@globalmanuf.com', '+1-555-0202', '+1-555-0302', 'ACTIVE', true, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Mike', 'Davis', 'CEO', 'mike.davis@startupxyz.com', '+1-555-0203', '+1-555-0303', 'ACTIVE', true, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Emily', 'Brown', 'Director of IT', 'emily.brown@healthpartners.com', '+1-555-0204', '+1-555-0304', 'ACTIVE', true, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('David', 'Wilson', 'Procurement Manager', 'david.wilson@retailgiants.com', '+1-555-0205', '+1-555-0305', 'ACTIVE', true, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample leads
INSERT INTO leads (first_name, last_name, company, job_title, email, phone, status, source, score, estimated_value, notes, created_at, updated_at) VALUES
('Alice', 'Cooper', 'Future Tech Inc', 'CIO', 'alice.cooper@futuretech.com', '+1-555-0401', 'QUALIFIED', 'WEBSITE', 85, 50000.00, 'Interested in enterprise solution', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bob', 'Martinez', 'Innovation Labs', 'VP Technology', 'bob.martinez@innovlabs.com', '+1-555-0402', 'CONTACTED', 'REFERRAL', 70, 75000.00, 'Referred by existing customer', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Carol', 'Taylor', 'NextGen Systems', 'IT Director', 'carol.taylor@nextgen.com', '+1-555-0403', 'NEW', 'SOCIAL_MEDIA', 60, 30000.00, 'Downloaded whitepaper', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Daniel', 'Anderson', 'Smart Solutions', 'CEO', 'daniel.anderson@smartsol.com', '+1-555-0404', 'QUALIFIED', 'EMAIL_CAMPAIGN', 90, 100000.00, 'High-value prospect', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Eva', 'Thompson', 'Digital Dynamics', 'CTO', 'eva.thompson@digitaldyn.com', '+1-555-0405', 'CONTACTED', 'TRADE_SHOW', 75, 60000.00, 'Met at tech conference', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample opportunities
INSERT INTO opportunities (name, description, amount, stage, probability, expected_close_date, source, notes, customer_id, created_at, updated_at) VALUES
('TechCorp Enterprise License', 'Annual enterprise software license renewal', 150000.00, 'NEGOTIATION', 90, '2024-03-15', 'EXISTING_CUSTOMER', 'Renewal with 20% increase', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Global Manufacturing Integration', 'System integration project for manufacturing processes', 250000.00, 'PROPOSAL', 75, '2024-04-30', 'INBOUND_LEAD', 'Large integration project', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('StartupXYZ Initial Setup', 'Initial CRM setup for growing startup', 25000.00, 'QUALIFICATION', 50, '2024-02-28', 'OUTBOUND_PROSPECTING', 'Small but growing company', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Healthcare Compliance Solution', 'HIPAA compliance software implementation', 80000.00, 'NEEDS_ANALYSIS', 60, '2024-05-15', 'REFERRAL', 'Compliance requirements', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Retail Analytics Platform', 'Customer analytics and reporting platform', 120000.00, 'PROSPECTING', 25, '2024-06-30', 'PARTNER', 'Early stage opportunity', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample activities
INSERT INTO activities (subject, description, type, status, priority, start_date, customer_id, contact_id, created_at, updated_at) VALUES
('Follow-up call with John Smith', 'Discuss contract renewal terms', 'CALL', 'COMPLETED', 'HIGH', '2024-01-15 10:00:00', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Product demo for Global Manufacturing', 'Demonstrate integration capabilities', 'DEMO', 'PLANNED', 'HIGH', '2024-02-01 14:00:00', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Send proposal to StartupXYZ', 'Email detailed proposal document', 'EMAIL', 'PLANNED', 'MEDIUM', '2024-01-25 09:00:00', 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Meeting with Healthcare Partners', 'Discuss compliance requirements', 'MEETING', 'PLANNED', 'HIGH', '2024-02-05 11:00:00', 4, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Retail Giants quarterly review', 'Review account status and opportunities', 'MEETING', 'COMPLETED', 'MEDIUM', '2024-01-10 15:00:00', 5, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
