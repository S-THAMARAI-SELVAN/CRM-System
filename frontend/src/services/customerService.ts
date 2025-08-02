import api from './api';
import { Customer, PageResponse } from '../types';

export interface CustomerFilters {
  page?: number;
  size?: number;
  sortBy?: string;
  sortDir?: 'asc' | 'desc';
  search?: string;
  status?: string;
  industry?: string;
}

export const customerService = {
  // Get all customers with pagination and filters
  getCustomers: async (filters: CustomerFilters = {}): Promise<PageResponse<Customer>> => {
    const params = new URLSearchParams();
    
    if (filters.page !== undefined) params.append('page', filters.page.toString());
    if (filters.size !== undefined) params.append('size', filters.size.toString());
    if (filters.sortBy) params.append('sortBy', filters.sortBy);
    if (filters.sortDir) params.append('sortDir', filters.sortDir);
    if (filters.search) params.append('search', filters.search);
    
    const response = await api.get(`/customers?${params.toString()}`);
    return response.data;
  },

  // Get customer by ID
  getCustomer: async (id: number): Promise<Customer> => {
    const response = await api.get(`/customers/${id}`);
    return response.data;
  },

  // Get customer with contacts
  getCustomerWithContacts: async (id: number): Promise<Customer> => {
    const response = await api.get(`/customers/${id}/with-contacts`);
    return response.data;
  },

  // Get customer with opportunities
  getCustomerWithOpportunities: async (id: number): Promise<Customer> => {
    const response = await api.get(`/customers/${id}/with-opportunities`);
    return response.data;
  },

  // Create new customer
  createCustomer: async (customer: Omit<Customer, 'id' | 'createdAt' | 'updatedAt'>): Promise<Customer> => {
    const response = await api.post('/customers', customer);
    return response.data;
  },

  // Update customer
  updateCustomer: async (id: number, customer: Partial<Customer>): Promise<Customer> => {
    const response = await api.put(`/customers/${id}`, customer);
    return response.data;
  },

  // Delete customer
  deleteCustomer: async (id: number): Promise<void> => {
    await api.delete(`/customers/${id}`);
  },

  // Get customers by status
  getCustomersByStatus: async (status: string): Promise<Customer[]> => {
    const response = await api.get(`/customers/by-status/${status}`);
    return response.data;
  },

  // Get distinct industries
  getIndustries: async (): Promise<string[]> => {
    const response = await api.get('/customers/industries');
    return response.data;
  },

  // Get customer statistics
  getCustomerStats: async () => {
    const response = await api.get('/customers/stats');
    return response.data;
  },
};
