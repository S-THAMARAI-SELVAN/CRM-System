// Customer Types
export interface Customer {
  id?: number;
  companyName: string;
  industry?: string;
  companySize?: string;
  website?: string;
  phone?: string;
  email?: string;
  address?: string;
  city?: string;
  state?: string;
  postalCode?: string;
  country?: string;
  status: CustomerStatus;
  createdAt?: string;
  updatedAt?: string;
  contacts?: Contact[];
  opportunities?: Opportunity[];
}

export enum CustomerStatus {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE',
  PROSPECT = 'PROSPECT'
}

// Contact Types
export interface Contact {
  id?: number;
  firstName: string;
  lastName: string;
  jobTitle?: string;
  email?: string;
  phone?: string;
  mobile?: string;
  linkedinUrl?: string;
  status: ContactStatus;
  isPrimary?: boolean;
  createdAt?: string;
  updatedAt?: string;
  customer?: Customer;
  activities?: Activity[];
}

export enum ContactStatus {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE',
  DO_NOT_CONTACT = 'DO_NOT_CONTACT'
}

// Lead Types
export interface Lead {
  id?: number;
  firstName: string;
  lastName: string;
  company?: string;
  jobTitle?: string;
  email?: string;
  phone?: string;
  status: LeadStatus;
  source?: LeadSource;
  score?: number;
  estimatedValue?: number;
  notes?: string;
  convertedAt?: string;
  createdAt?: string;
  updatedAt?: string;
  convertedCustomer?: Customer;
}

export enum LeadStatus {
  NEW = 'NEW',
  CONTACTED = 'CONTACTED',
  QUALIFIED = 'QUALIFIED',
  UNQUALIFIED = 'UNQUALIFIED',
  CONVERTED = 'CONVERTED',
  LOST = 'LOST'
}

export enum LeadSource {
  WEBSITE = 'WEBSITE',
  SOCIAL_MEDIA = 'SOCIAL_MEDIA',
  EMAIL_CAMPAIGN = 'EMAIL_CAMPAIGN',
  REFERRAL = 'REFERRAL',
  COLD_CALL = 'COLD_CALL',
  TRADE_SHOW = 'TRADE_SHOW',
  ADVERTISEMENT = 'ADVERTISEMENT',
  OTHER = 'OTHER'
}

// Opportunity Types
export interface Opportunity {
  id?: number;
  name: string;
  description?: string;
  amount: number;
  stage: OpportunityStage;
  probability?: number;
  expectedCloseDate?: string;
  actualCloseDate?: string;
  source?: OpportunitySource;
  notes?: string;
  createdAt?: string;
  updatedAt?: string;
  customer: Customer;
  primaryContact?: Contact;
}

export enum OpportunityStage {
  PROSPECTING = 'PROSPECTING',
  QUALIFICATION = 'QUALIFICATION',
  NEEDS_ANALYSIS = 'NEEDS_ANALYSIS',
  PROPOSAL = 'PROPOSAL',
  NEGOTIATION = 'NEGOTIATION',
  CLOSED_WON = 'CLOSED_WON',
  CLOSED_LOST = 'CLOSED_LOST'
}

export enum OpportunitySource {
  INBOUND_LEAD = 'INBOUND_LEAD',
  OUTBOUND_PROSPECTING = 'OUTBOUND_PROSPECTING',
  REFERRAL = 'REFERRAL',
  EXISTING_CUSTOMER = 'EXISTING_CUSTOMER',
  PARTNER = 'PARTNER',
  TRADE_SHOW = 'TRADE_SHOW',
  OTHER = 'OTHER'
}

// Activity Types
export interface Activity {
  id?: number;
  subject: string;
  description?: string;
  type: ActivityType;
  status: ActivityStatus;
  priority: ActivityPriority;
  startDate?: string;
  endDate?: string;
  completedAt?: string;
  outcome?: string;
  createdAt?: string;
  updatedAt?: string;
  customer?: Customer;
  contact?: Contact;
  opportunity?: Opportunity;
  lead?: Lead;
}

export enum ActivityType {
  CALL = 'CALL',
  EMAIL = 'EMAIL',
  MEETING = 'MEETING',
  TASK = 'TASK',
  NOTE = 'NOTE',
  DEMO = 'DEMO',
  PROPOSAL = 'PROPOSAL',
  FOLLOW_UP = 'FOLLOW_UP'
}

export enum ActivityStatus {
  PLANNED = 'PLANNED',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED',
  OVERDUE = 'OVERDUE'
}

export enum ActivityPriority {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH',
  URGENT = 'URGENT'
}

// API Response Types
export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

export interface ApiError {
  message: string;
  status: number;
  timestamp: string;
}

// Dashboard Stats Types
export interface DashboardStats {
  totalCustomers: number;
  totalLeads: number;
  totalOpportunities: number;
  totalRevenue: number;
  conversionRate: number;
  avgDealSize: number;
}

export interface ChartData {
  name: string;
  value: number;
  color?: string;
}
