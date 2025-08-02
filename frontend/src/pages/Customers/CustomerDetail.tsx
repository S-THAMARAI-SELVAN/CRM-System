import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Box,
  Typography,
  Card,
  CardContent,
  Button,
  Grid,
  Chip,
  Divider,
} from '@mui/material';
import { ArrowBack as ArrowBackIcon, Edit as EditIcon } from '@mui/icons-material';
import { useQuery } from 'react-query';
import { customerService } from '../../services/customerService';
import { CustomerStatus } from '../../types';

const CustomerDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { data: customer, isLoading, error } = useQuery(
    ['customer', id],
    () => customerService.getCustomer(Number(id)),
    {
      enabled: !!id,
    }
  );

  const getStatusColor = (status: CustomerStatus) => {
    switch (status) {
      case CustomerStatus.ACTIVE:
        return 'success';
      case CustomerStatus.PROSPECT:
        return 'warning';
      case CustomerStatus.INACTIVE:
        return 'error';
      default:
        return 'default';
    }
  };

  if (isLoading) {
    return <Typography>Loading customer details...</Typography>;
  }

  if (error || !customer) {
    return <Typography color="error">Error loading customer details</Typography>;
  }

  return (
    <Box>
      <Box display="flex" alignItems="center" mb={3}>
        <Button
          startIcon={<ArrowBackIcon />}
          onClick={() => navigate('/customers')}
          sx={{ mr: 2 }}
        >
          Back to Customers
        </Button>
        <Typography variant="h4" sx={{ fontWeight: 'bold', flexGrow: 1 }}>
          {customer.companyName}
        </Typography>
        <Button
          variant="contained"
          startIcon={<EditIcon />}
          onClick={() => navigate(`/customers/${id}/edit`)}
        >
          Edit Customer
        </Button>
      </Box>

      <Grid container spacing={3}>
        <Grid item xs={12} md={8}>
          <Card>
            <CardContent>
              <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                  Company Information
                </Typography>
                <Chip
                  label={customer.status}
                  color={getStatusColor(customer.status)}
                />
              </Box>
              
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <Typography variant="subtitle2" color="text.secondary">
                    Company Name
                  </Typography>
                  <Typography variant="body1" gutterBottom>
                    {customer.companyName}
                  </Typography>
                </Grid>
                
                {customer.industry && (
                  <Grid item xs={12} sm={6}>
                    <Typography variant="subtitle2" color="text.secondary">
                      Industry
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                      {customer.industry}
                    </Typography>
                  </Grid>
                )}
                
                {customer.companySize && (
                  <Grid item xs={12} sm={6}>
                    <Typography variant="subtitle2" color="text.secondary">
                      Company Size
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                      {customer.companySize}
                    </Typography>
                  </Grid>
                )}
                
                {customer.website && (
                  <Grid item xs={12} sm={6}>
                    <Typography variant="subtitle2" color="text.secondary">
                      Website
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                      <a href={customer.website} target="_blank" rel="noopener noreferrer">
                        {customer.website}
                      </a>
                    </Typography>
                  </Grid>
                )}
              </Grid>

              <Divider sx={{ my: 3 }} />

              <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 2 }}>
                Contact Information
              </Typography>
              
              <Grid container spacing={2}>
                {customer.email && (
                  <Grid item xs={12} sm={6}>
                    <Typography variant="subtitle2" color="text.secondary">
                      Email
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                      {customer.email}
                    </Typography>
                  </Grid>
                )}
                
                {customer.phone && (
                  <Grid item xs={12} sm={6}>
                    <Typography variant="subtitle2" color="text.secondary">
                      Phone
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                      {customer.phone}
                    </Typography>
                  </Grid>
                )}
              </Grid>

              {(customer.address || customer.city || customer.state || customer.postalCode || customer.country) && (
                <>
                  <Divider sx={{ my: 3 }} />
                  
                  <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 2 }}>
                    Address
                  </Typography>
                  
                  <Grid container spacing={2}>
                    {customer.address && (
                      <Grid item xs={12}>
                        <Typography variant="subtitle2" color="text.secondary">
                          Street Address
                        </Typography>
                        <Typography variant="body1" gutterBottom>
                          {customer.address}
                        </Typography>
                      </Grid>
                    )}
                    
                    {customer.city && (
                      <Grid item xs={12} sm={6}>
                        <Typography variant="subtitle2" color="text.secondary">
                          City
                        </Typography>
                        <Typography variant="body1" gutterBottom>
                          {customer.city}
                        </Typography>
                      </Grid>
                    )}
                    
                    {customer.state && (
                      <Grid item xs={12} sm={6}>
                        <Typography variant="subtitle2" color="text.secondary">
                          State
                        </Typography>
                        <Typography variant="body1" gutterBottom>
                          {customer.state}
                        </Typography>
                      </Grid>
                    )}
                    
                    {customer.postalCode && (
                      <Grid item xs={12} sm={6}>
                        <Typography variant="subtitle2" color="text.secondary">
                          Postal Code
                        </Typography>
                        <Typography variant="body1" gutterBottom>
                          {customer.postalCode}
                        </Typography>
                      </Grid>
                    )}
                    
                    {customer.country && (
                      <Grid item xs={12} sm={6}>
                        <Typography variant="subtitle2" color="text.secondary">
                          Country
                        </Typography>
                        <Typography variant="body1" gutterBottom>
                          {customer.country}
                        </Typography>
                      </Grid>
                    )}
                  </Grid>
                </>
              )}
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 2 }}>
                Quick Actions
              </Typography>
              
              <Box display="flex" flexDirection="column" gap={1}>
                <Button
                  variant="outlined"
                  fullWidth
                  onClick={() => navigate(`/contacts?customer=${id}`)}
                >
                  View Contacts
                </Button>
                <Button
                  variant="outlined"
                  fullWidth
                  onClick={() => navigate(`/opportunities?customer=${id}`)}
                >
                  View Opportunities
                </Button>
                <Button
                  variant="outlined"
                  fullWidth
                  onClick={() => navigate(`/activities?customer=${id}`)}
                >
                  View Activities
                </Button>
              </Box>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
};

export default CustomerDetail;
