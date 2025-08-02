# 🚀 Deploy Backend to Railway

## Quick Deployment Steps:

### 1. **Go to Railway**
Visit: https://railway.app

### 2. **Sign up with GitHub**
- Click "Login with GitHub"
- Authorize Railway to access your repositories

### 3. **Create New Project**
- Click "New Project"
- Select "Deploy from GitHub repo"
- Choose your repository: `S-THAMARAI-SELVAN/CRM-System`
- Select the `backend` folder as root directory

### 4. **Configure Environment Variables**
Add these environment variables in Railway dashboard:
```
SPRING_PROFILES_ACTIVE=railway
PORT=8080
FRONTEND_URL=https://crm-system-frontend-bsx89x7g0-spartans1.vercel.app
```

### 5. **Deploy**
- Railway will automatically detect it's a Java Spring Boot app
- It will build and deploy automatically
- You'll get a live URL like: `https://your-app.railway.app`

### 6. **Update Frontend**
Update your Vercel environment variables:
```
REACT_APP_API_URL=https://your-backend-url.railway.app/api
```

## Alternative: Manual Deployment

If you prefer manual deployment, follow the detailed guide in DEPLOYMENT_GUIDE.md

## Expected Result:
- ✅ Backend API running on Railway
- ✅ Database with sample data
- ✅ CORS configured for your Vercel frontend
- ✅ Full CRM system working end-to-end
