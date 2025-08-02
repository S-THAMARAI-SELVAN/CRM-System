import React from 'react';
import {
  Box,
  Grid,
  Card,
  CardContent,
  Typography,
  Paper,
  useTheme,
} from '@mui/material';
import {
  People as PeopleIcon,
  TrendingUp as LeadsIcon,
  BusinessCenter as OpportunitiesIcon,
  AttachMoney as RevenueIcon,
} from '@mui/icons-material';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  PieChart,
  Pie,
  Cell,
  ResponsiveContainer,
  LineChart,
  Line,
} from 'recharts';

// Mock data for demonstration
const statsData = {
  totalCustomers: 1247,
  totalLeads: 89,
  totalOpportunities: 156,
  totalRevenue: 2450000,
};

const leadSourceData = [
  { name: 'Website', value: 35, color: '#8884d8' },
  { name: 'Referral', value: 25, color: '#82ca9d' },
  { name: 'Social Media', value: 20, color: '#ffc658' },
  { name: 'Email Campaign', value: 15, color: '#ff7300' },
  { name: 'Other', value: 5, color: '#00ff00' },
];

const salesData = [
  { month: 'Jan', revenue: 180000, opportunities: 12 },
  { month: 'Feb', revenue: 220000, opportunities: 15 },
  { month: 'Mar', revenue: 280000, opportunities: 18 },
  { month: 'Apr', revenue: 320000, opportunities: 22 },
  { month: 'May', revenue: 290000, opportunities: 19 },
  { month: 'Jun', revenue: 350000, opportunities: 25 },
];

const opportunityStageData = [
  { stage: 'Prospecting', count: 25 },
  { stage: 'Qualification', count: 18 },
  { stage: 'Proposal', count: 12 },
  { stage: 'Negotiation', count: 8 },
  { stage: 'Closed Won', count: 15 },
];

interface StatCardProps {
  title: string;
  value: string | number;
  icon: React.ReactNode;
  color: string;
}

const StatCard: React.FC<StatCardProps> = ({ title, value, icon, color }) => {
  return (
    <Card sx={{ height: '100%' }}>
      <CardContent>
        <Box display="flex" alignItems="center" justifyContent="space-between">
          <Box>
            <Typography color="textSecondary" gutterBottom variant="body2">
              {title}
            </Typography>
            <Typography variant="h4" component="div" sx={{ fontWeight: 'bold' }}>
              {value}
            </Typography>
          </Box>
          <Box
            sx={{
              backgroundColor: color,
              borderRadius: '50%',
              p: 2,
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
            }}
          >
            {React.cloneElement(icon as React.ReactElement, {
              sx: { color: 'white', fontSize: 32 },
            })}
          </Box>
        </Box>
      </CardContent>
    </Card>
  );
};

const Dashboard: React.FC = () => {
  const theme = useTheme();

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 0,
    }).format(value);
  };

  return (
    <Box>
      <Typography variant="h4" gutterBottom sx={{ fontWeight: 'bold', mb: 3 }}>
        Dashboard
      </Typography>

      {/* Stats Cards */}
      <Grid container spacing={3} sx={{ mb: 4 }}>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Total Customers"
            value={statsData.totalCustomers.toLocaleString()}
            icon={<PeopleIcon />}
            color={theme.palette.primary.main}
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Active Leads"
            value={statsData.totalLeads}
            icon={<LeadsIcon />}
            color={theme.palette.success.main}
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Opportunities"
            value={statsData.totalOpportunities}
            icon={<OpportunitiesIcon />}
            color={theme.palette.warning.main}
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Total Revenue"
            value={formatCurrency(statsData.totalRevenue)}
            icon={<RevenueIcon />}
            color={theme.palette.error.main}
          />
        </Grid>
      </Grid>

      {/* Charts */}
      <Grid container spacing={3}>
        {/* Sales Trend */}
        <Grid item xs={12} lg={8}>
          <Paper sx={{ p: 3, height: 400 }}>
            <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold' }}>
              Sales Trend
            </Typography>
            <ResponsiveContainer width="100%" height="90%">
              <LineChart data={salesData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis />
                <Tooltip formatter={(value, name) => [
                  name === 'revenue' ? formatCurrency(value as number) : value,
                  name === 'revenue' ? 'Revenue' : 'Opportunities'
                ]} />
                <Legend />
                <Line
                  type="monotone"
                  dataKey="revenue"
                  stroke={theme.palette.primary.main}
                  strokeWidth={3}
                  name="Revenue"
                />
                <Line
                  type="monotone"
                  dataKey="opportunities"
                  stroke={theme.palette.secondary.main}
                  strokeWidth={3}
                  name="Opportunities"
                />
              </LineChart>
            </ResponsiveContainer>
          </Paper>
        </Grid>

        {/* Lead Sources */}
        <Grid item xs={12} lg={4}>
          <Paper sx={{ p: 3, height: 400 }}>
            <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold' }}>
              Lead Sources
            </Typography>
            <ResponsiveContainer width="100%" height="90%">
              <PieChart>
                <Pie
                  data={leadSourceData}
                  cx="50%"
                  cy="50%"
                  labelLine={false}
                  label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                  outerRadius={80}
                  fill="#8884d8"
                  dataKey="value"
                >
                  {leadSourceData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={entry.color} />
                  ))}
                </Pie>
                <Tooltip />
              </PieChart>
            </ResponsiveContainer>
          </Paper>
        </Grid>

        {/* Opportunity Pipeline */}
        <Grid item xs={12}>
          <Paper sx={{ p: 3, height: 400 }}>
            <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold' }}>
              Opportunity Pipeline
            </Typography>
            <ResponsiveContainer width="100%" height="90%">
              <BarChart data={opportunityStageData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="stage" />
                <YAxis />
                <Tooltip />
                <Bar dataKey="count" fill={theme.palette.primary.main} />
              </BarChart>
            </ResponsiveContainer>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
};

export default Dashboard;
