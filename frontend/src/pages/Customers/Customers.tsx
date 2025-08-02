import React, { useState } from 'react';
import {
  Box,
  Typography,
  Button,
  TextField,
  Grid,
  Card,
  CardContent,
  CardActions,
  Chip,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Pagination,
} from '@mui/material';
import {
  Add as AddIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  Visibility as ViewIcon,
  Search as SearchIcon,
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { customerService } from '../../services/customerService';
import { Customer, CustomerStatus } from '../../types';
import toast from 'react-hot-toast';

const Customers: React.FC = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState('');
  const [statusFilter, setStatusFilter] = useState('');
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [customerToDelete, setCustomerToDelete] = useState<Customer | null>(null);

  const { data: customersData, isLoading, error } = useQuery(
    ['customers', page, search, statusFilter],
    () => customerService.getCustomers({
      page,
      size: 12,
      search: search || undefined,
      status: statusFilter || undefined,
    }),
    {
      keepPreviousData: true,
    }
  );

  const deleteMutation = useMutation(customerService.deleteCustomer, {
    onSuccess: () => {
      queryClient.invalidateQueries(['customers']);
      toast.success('Customer deleted successfully');
      setDeleteDialogOpen(false);
      setCustomerToDelete(null);
    },
    onError: () => {
      toast.error('Failed to delete customer');
    },
  });

  const handleSearch = (event: React.FormEvent) => {
    event.preventDefault();
    setPage(0);
  };

  const handleDeleteClick = (customer: Customer) => {
    setCustomerToDelete(customer);
    setDeleteDialogOpen(true);
  };

  const handleDeleteConfirm = () => {
    if (customerToDelete?.id) {
      deleteMutation.mutate(customerToDelete.id);
    }
  };

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

  if (error) {
    return (
      <Box>
        <Typography color="error">Error loading customers</Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" sx={{ fontWeight: 'bold' }}>
          Customers
        </Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => navigate('/customers/new')}
        >
          Add Customer
        </Button>
      </Box>

      {/* Filters */}
      <Card sx={{ mb: 3 }}>
        <CardContent>
          <form onSubmit={handleSearch}>
            <Grid container spacing={2} alignItems="center">
              <Grid item xs={12} md={6}>
                <TextField
                  fullWidth
                  label="Search customers..."
                  value={search}
                  onChange={(e) => setSearch(e.target.value)}
                  InputProps={{
                    endAdornment: (
                      <IconButton type="submit">
                        <SearchIcon />
                      </IconButton>
                    ),
                  }}
                />
              </Grid>
              <Grid item xs={12} md={3}>
                <FormControl fullWidth>
                  <InputLabel>Status</InputLabel>
                  <Select
                    value={statusFilter}
                    label="Status"
                    onChange={(e) => setStatusFilter(e.target.value)}
                  >
                    <MenuItem value="">All</MenuItem>
                    <MenuItem value="ACTIVE">Active</MenuItem>
                    <MenuItem value="PROSPECT">Prospect</MenuItem>
                    <MenuItem value="INACTIVE">Inactive</MenuItem>
                  </Select>
                </FormControl>
              </Grid>
              <Grid item xs={12} md={3}>
                <Button
                  fullWidth
                  variant="outlined"
                  onClick={() => {
                    setSearch('');
                    setStatusFilter('');
                    setPage(0);
                  }}
                >
                  Clear Filters
                </Button>
              </Grid>
            </Grid>
          </form>
        </CardContent>
      </Card>

      {/* Customer Grid */}
      {isLoading ? (
        <Typography>Loading customers...</Typography>
      ) : (
        <>
          <Grid container spacing={3}>
            {customersData?.content.map((customer) => (
              <Grid item xs={12} sm={6} md={4} key={customer.id}>
                <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
                  <CardContent sx={{ flexGrow: 1 }}>
                    <Box display="flex" justifyContent="space-between" alignItems="flex-start" mb={2}>
                      <Typography variant="h6" component="div" sx={{ fontWeight: 'bold' }}>
                        {customer.companyName}
                      </Typography>
                      <Chip
                        label={customer.status}
                        color={getStatusColor(customer.status)}
                        size="small"
                      />
                    </Box>
                    
                    {customer.industry && (
                      <Typography color="text.secondary" gutterBottom>
                        {customer.industry}
                      </Typography>
                    )}
                    
                    {customer.email && (
                      <Typography variant="body2" color="text.secondary">
                        {customer.email}
                      </Typography>
                    )}
                    
                    {customer.phone && (
                      <Typography variant="body2" color="text.secondary">
                        {customer.phone}
                      </Typography>
                    )}
                    
                    {customer.city && customer.state && (
                      <Typography variant="body2" color="text.secondary">
                        {customer.city}, {customer.state}
                      </Typography>
                    )}
                  </CardContent>
                  
                  <CardActions>
                    <IconButton
                      size="small"
                      onClick={() => navigate(`/customers/${customer.id}`)}
                      title="View Details"
                    >
                      <ViewIcon />
                    </IconButton>
                    <IconButton
                      size="small"
                      onClick={() => navigate(`/customers/${customer.id}/edit`)}
                      title="Edit"
                    >
                      <EditIcon />
                    </IconButton>
                    <IconButton
                      size="small"
                      onClick={() => handleDeleteClick(customer)}
                      title="Delete"
                      color="error"
                    >
                      <DeleteIcon />
                    </IconButton>
                  </CardActions>
                </Card>
              </Grid>
            ))}
          </Grid>

          {/* Pagination */}
          {customersData && customersData.totalPages > 1 && (
            <Box display="flex" justifyContent="center" mt={4}>
              <Pagination
                count={customersData.totalPages}
                page={page + 1}
                onChange={(_, newPage) => setPage(newPage - 1)}
                color="primary"
              />
            </Box>
          )}
        </>
      )}

      {/* Delete Confirmation Dialog */}
      <Dialog open={deleteDialogOpen} onClose={() => setDeleteDialogOpen(false)}>
        <DialogTitle>Confirm Delete</DialogTitle>
        <DialogContent>
          <Typography>
            Are you sure you want to delete {customerToDelete?.companyName}? This action cannot be undone.
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteDialogOpen(false)}>Cancel</Button>
          <Button
            onClick={handleDeleteConfirm}
            color="error"
            variant="contained"
            disabled={deleteMutation.isLoading}
          >
            {deleteMutation.isLoading ? 'Deleting...' : 'Delete'}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Customers;
