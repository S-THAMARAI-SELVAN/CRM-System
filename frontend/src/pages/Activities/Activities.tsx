import React from 'react';
import { Box, Typography, Button, Card, CardContent } from '@mui/material';
import { Add as AddIcon } from '@mui/icons-material';

const Activities: React.FC = () => {
  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" sx={{ fontWeight: 'bold' }}>
          Activities
        </Typography>
        <Button variant="contained" startIcon={<AddIcon />}>
          Add Activity
        </Button>
      </Box>

      <Card>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Activity Management
          </Typography>
          <Typography color="text.secondary">
            This page will contain activity management functionality including:
          </Typography>
          <ul>
            <li>Activity calendar view</li>
            <li>Task and meeting scheduling</li>
            <li>Call and email logging</li>
            <li>Activity tracking and reporting</li>
            <li>Follow-up reminders</li>
          </ul>
        </CardContent>
      </Card>
    </Box>
  );
};

export default Activities;
