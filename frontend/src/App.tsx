import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Box } from '@mui/material';
import Layout from './components/Layout/Layout';
import Dashboard from './pages/Dashboard/Dashboard';
import Customers from './pages/Customers/Customers';
import CustomerDetail from './pages/Customers/CustomerDetail';
import Contacts from './pages/Contacts/Contacts';
import ContactDetail from './pages/Contacts/ContactDetail';
import Leads from './pages/Leads/Leads';
import LeadDetail from './pages/Leads/LeadDetail';
import Opportunities from './pages/Opportunities/Opportunities';
import OpportunityDetail from './pages/Opportunities/OpportunityDetail';
import Activities from './pages/Activities/Activities';

function App() {
  return (
    <Box sx={{ display: 'flex' }}>
      <Layout>
        <Routes>
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
          <Route path="/dashboard" element={<Dashboard />} />
          
          {/* Customer Routes */}
          <Route path="/customers" element={<Customers />} />
          <Route path="/customers/:id" element={<CustomerDetail />} />
          
          {/* Contact Routes */}
          <Route path="/contacts" element={<Contacts />} />
          <Route path="/contacts/:id" element={<ContactDetail />} />
          
          {/* Lead Routes */}
          <Route path="/leads" element={<Leads />} />
          <Route path="/leads/:id" element={<LeadDetail />} />
          
          {/* Opportunity Routes */}
          <Route path="/opportunities" element={<Opportunities />} />
          <Route path="/opportunities/:id" element={<OpportunityDetail />} />
          
          {/* Activity Routes */}
          <Route path="/activities" element={<Activities />} />
          
          {/* Catch all route */}
          <Route path="*" element={<Navigate to="/dashboard" replace />} />
        </Routes>
      </Layout>
    </Box>
  );
}

export default App;
