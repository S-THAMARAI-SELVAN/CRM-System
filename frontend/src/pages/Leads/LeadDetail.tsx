import React from 'react';
import { Box, Typography, Card, CardContent } from '@mui/material';

const LeadDetail: React.FC = () => {
  return (
    <Box>
      <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 3 }}>
        Lead Details
      </Typography>

      <Card>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Lead Detail View
          </Typography>
          <Typography color="text.secondary">
            This page will show detailed lead information, scoring, and conversion tracking.
          </Typography>
        </CardContent>
      </Card>
    </Box>
  );
};

export default LeadDetail;
