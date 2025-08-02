import React from 'react';
import { Box, Typography, Button, Card, CardContent } from '@mui/material';
import { Add as AddIcon } from '@mui/icons-material';

const Leads: React.FC = () => {
  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" sx={{ fontWeight: 'bold' }}>
          Leads
        </Typography>
        <Button variant="contained" startIcon={<AddIcon />}>
          Add Lead
        </Button>
      </Box>

      <Card>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Lead Management
          </Typography>
          <Typography color="text.secondary">
            This page will contain lead management functionality including:
          </Typography>
          <ul>
            <li>Lead pipeline visualization</li>
            <li>Lead scoring and qualification</li>
            <li>Lead conversion tracking</li>
            <li>Source attribution</li>
            <li>Lead nurturing workflows</li>
          </ul>
        </CardContent>
      </Card>
    </Box>
  );
};

export default Leads;
