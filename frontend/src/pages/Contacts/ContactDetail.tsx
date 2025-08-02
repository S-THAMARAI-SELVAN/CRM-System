import React from 'react';
import { Box, Typography, Card, CardContent } from '@mui/material';

const ContactDetail: React.FC = () => {
  return (
    <Box>
      <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 3 }}>
        Contact Details
      </Typography>

      <Card>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Contact Detail View
          </Typography>
          <Typography color="text.secondary">
            This page will show detailed contact information and related activities.
          </Typography>
        </CardContent>
      </Card>
    </Box>
  );
};

export default ContactDetail;
