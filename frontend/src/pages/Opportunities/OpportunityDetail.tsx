import React from 'react';
import { Box, Typography, Card, CardContent } from '@mui/material';

const OpportunityDetail: React.FC = () => {
  return (
    <Box>
      <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 3 }}>
        Opportunity Details
      </Typography>

      <Card>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Opportunity Detail View
          </Typography>
          <Typography color="text.secondary">
            This page will show detailed opportunity information, stage progression, and revenue forecasting.
          </Typography>
        </CardContent>
      </Card>
    </Box>
  );
};

export default OpportunityDetail;
