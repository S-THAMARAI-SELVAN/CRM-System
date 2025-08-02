import React from 'react';
import { Box, Typography, Button, Card, CardContent } from '@mui/material';
import { Add as AddIcon } from '@mui/icons-material';

const Contacts: React.FC = () => {
  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" sx={{ fontWeight: 'bold' }}>
          Contacts
        </Typography>
        <Button variant="contained" startIcon={<AddIcon />}>
          Add Contact
        </Button>
      </Box>

      <Card>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Contact Management
          </Typography>
          <Typography color="text.secondary">
            This page will contain contact management functionality including:
          </Typography>
          <ul>
            <li>Contact listing with search and filters</li>
            <li>Contact creation and editing forms</li>
            <li>Contact details view</li>
            <li>Relationship mapping to customers</li>
            <li>Activity tracking per contact</li>
          </ul>
        </CardContent>
      </Card>
    </Box>
  );
};

export default Contacts;
