import React from 'react';
import { Box, Typography, Button, Card, CardContent } from '@mui/material';
import { Add as AddIcon } from '@mui/icons-material';

const Opportunities: React.FC = () => {
  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" sx={{ fontWeight: 'bold' }}>
          Opportunities
        </Typography>
        <Button variant="contained" startIcon={<AddIcon />}>
          Add Opportunity
        </Button>
      </Box>

      <Card>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Opportunity Management
          </Typography>
          <Typography color="text.secondary">
            This page will contain opportunity management functionality including:
          </Typography>
          <ul>
            <li>Sales pipeline visualization</li>
            <li>Deal tracking and forecasting</li>
            <li>Revenue projections</li>
            <li>Stage progression tracking</li>
            <li>Win/loss analysis</li>
          </ul>
        </CardContent>
      </Card>
    </Box>
  );
};

export default Opportunities;
