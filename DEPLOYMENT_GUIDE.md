# 🚀 CRM System Deployment Guide

## 📋 **Deployment Architecture**

```
Frontend (React) → Vercel
Backend (Spring Boot) → Heroku
Database → Heroku Postgres / PlanetScale MySQL
```

## 🌐 **Frontend Deployment to Vercel**

### Prerequisites
- Vercel account (free): https://vercel.com
- GitHub repository pushed
- Node.js installed locally

### Step 1: Install Vercel CLI
```bash
npm install -g vercel
```

### Step 2: Deploy Frontend
```bash
# Option A: Use deployment script
deploy-frontend.bat  # Windows
./deploy-frontend.sh # Mac/Linux

# Option B: Manual deployment
cd frontend
vercel --prod
```

### Step 3: Configure Environment Variables in Vercel
1. Go to Vercel Dashboard → Your Project → Settings → Environment Variables
2. Add these variables:
   ```
   REACT_APP_API_URL = https://your-backend-url.herokuapp.com/api
   REACT_APP_ENVIRONMENT = production
   GENERATE_SOURCEMAP = false
   ```

### Step 4: Connect to GitHub (Automatic Deployment)
1. Import your GitHub repository in Vercel
2. Set build command: `npm run build`
3. Set output directory: `build`
4. Enable automatic deployments

## 🔧 **Backend Deployment to Heroku**

### Prerequisites
- Heroku account (free): https://heroku.com
- Heroku CLI installed
- Git repository

### Step 1: Install Heroku CLI
Download from: https://devcenter.heroku.com/articles/heroku-cli

### Step 2: Login and Create App
```bash
heroku login
heroku create your-crm-backend
```

### Step 3: Add Database
```bash
# Option A: PostgreSQL (Recommended)
heroku addons:create heroku-postgresql:hobby-dev

# Option B: Use external MySQL (PlanetScale)
# Set DATABASE_URL manually
```

### Step 4: Configure Environment Variables
```bash
heroku config:set FRONTEND_URL=https://your-frontend.vercel.app
heroku config:set SPRING_PROFILES_ACTIVE=heroku
```

### Step 5: Deploy Backend
```bash
# From project root
git subtree push --prefix=backend heroku main

# Or create separate backend repository
cd backend
git init
git add .
git commit -m "Initial backend commit"
heroku git:remote -a your-crm-backend
git push heroku main
```

## 🗄️ **Database Options**

### Option 1: Heroku PostgreSQL (Recommended)
- Automatic with Heroku
- Update `application-heroku.properties` for PostgreSQL

### Option 2: PlanetScale MySQL (Free Tier)
1. Create account: https://planetscale.com
2. Create database
3. Get connection string
4. Set as `DATABASE_URL` in Heroku

### Option 3: Railway MySQL
1. Create account: https://railway.app
2. Deploy MySQL
3. Get connection details
4. Configure in Heroku

## 🔄 **Complete Deployment Process**

### Step 1: Prepare Repository
```bash
# Commit all changes
git add .
git commit -m "Prepare for deployment"
git push origin main
```

### Step 2: Deploy Backend First
```bash
# Deploy to Heroku
cd backend
heroku create your-crm-backend
heroku addons:create heroku-postgresql:hobby-dev
git init
git add .
git commit -m "Backend deployment"
heroku git:remote -a your-crm-backend
git push heroku main
```

### Step 3: Get Backend URL
```bash
heroku open  # Opens your backend URL
# Copy the URL (e.g., https://your-crm-backend.herokuapp.com)
```

### Step 4: Deploy Frontend
```bash
# Update frontend environment
cd ../frontend
# Update .env.production with your backend URL
vercel --prod
```

### Step 5: Update CORS
```bash
# Update backend CORS with your Vercel URL
heroku config:set FRONTEND_URL=https://your-frontend.vercel.app
```

## 🎯 **Expected URLs**

After successful deployment:
- **Frontend**: https://your-crm-frontend.vercel.app
- **Backend API**: https://your-crm-backend.herokuapp.com/api
- **Database**: Managed by Heroku/PlanetScale

## 🔧 **Troubleshooting**

### Common Issues:

1. **CORS Errors**
   ```bash
   # Update backend CORS configuration
   heroku config:set FRONTEND_URL=https://your-exact-vercel-url.vercel.app
   ```

2. **Database Connection Issues**
   ```bash
   # Check database URL
   heroku config:get DATABASE_URL
   ```

3. **Build Failures**
   ```bash
   # Check Heroku logs
   heroku logs --tail
   ```

4. **Environment Variables**
   ```bash
   # List all config vars
   heroku config
   ```

## 📊 **Monitoring & Maintenance**

### Heroku Dashboard
- Monitor backend performance
- View logs and metrics
- Manage database

### Vercel Dashboard
- Monitor frontend deployments
- View build logs
- Manage domains

## 🎉 **Success Checklist**

✅ Frontend deployed to Vercel
✅ Backend deployed to Heroku
✅ Database connected and accessible
✅ CORS configured correctly
✅ Environment variables set
✅ API endpoints responding
✅ Frontend can fetch data from backend
✅ All CRM features working in production

## 🔗 **Useful Commands**

```bash
# Heroku
heroku logs --tail                    # View backend logs
heroku config                         # View environment variables
heroku restart                        # Restart backend
heroku run bash                       # Access backend shell

# Vercel
vercel logs                          # View frontend logs
vercel env ls                        # List environment variables
vercel --prod                        # Deploy to production
vercel domains                       # Manage custom domains
```

Your CRM system will be fully deployed and accessible worldwide! 🌍
