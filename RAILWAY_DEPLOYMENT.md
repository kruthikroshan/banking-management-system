# Railway Deployment Guide for Banking Management System

## Prerequisites
1. A [Railway](https://railway.app) account (sign up with GitHub)
2. Git installed on your computer
3. Your project code

## Deployment Steps

### 1. Initialize Git Repository (if not already done)
```bash
cd "c:\Users\kruth\Downloads\Banking Management System"
git init
git add .
git commit -m "Initial commit - Banking Management System"
```

### 2. Push to GitHub
```bash
# Create a new repository on GitHub first, then:
git remote add origin https://github.com/YOUR_USERNAME/banking-management-system.git
git branch -M main
git push -u origin main
```

### 3. Deploy to Railway

#### Option A: Using Railway CLI
```bash
# Install Railway CLI
npm i -g @railway/cli

# Login to Railway
railway login

# Initialize and deploy
railway init
railway up
```

#### Option B: Using Railway Dashboard (Easier)
1. Go to [railway.app](https://railway.app)
2. Click "Start a New Project"
3. Select "Deploy from GitHub repo"
4. Authorize Railway to access your GitHub
5. Select your repository
6. Railway will automatically detect it's a Java/Maven project
7. Click "Deploy"

### 4. Configure Environment Variables (Optional)
In Railway Dashboard > Your Project > Variables:
- `PORT` - Auto-set by Railway
- `ADMIN_USERNAME` - Custom admin username (default: admin)
- `ADMIN_PASSWORD` - Custom admin password (default: admin123)
- `LOG_LEVEL` - Set to INFO for production (default: INFO)

### 5. Access Your Application
Railway will provide a public URL like:
`https://banking-management-system-production.up.railway.app`

## What's Configured

✅ **Railway Configuration** (`railway.json`, `nixpacks.toml`, `Procfile`)
✅ **Dynamic Port Binding** - App uses Railway's `$PORT` environment variable
✅ **Environment Variables** - Admin credentials configurable
✅ **Production Logging** - Reduced verbosity for production
✅ **Build Optimization** - Skip tests during deployment

## Important Notes

⚠️ **Database**: Currently using H2 in-memory database
- Data is lost on app restart
- For production, consider upgrading to PostgreSQL (Railway offers this)

⚠️ **Security**: Change default admin credentials in production
```bash
railway variables --set ADMIN_USERNAME=yourusername
railway variables --set ADMIN_PASSWORD=yourSecurePassword123!
```

## Upgrade to PostgreSQL (Recommended for Production)

1. In Railway Dashboard, add PostgreSQL service
2. Update `application.properties`:
```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.driverClassName=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```
3. Add PostgreSQL dependency to `pom.xml`
4. Redeploy

## Troubleshooting

**Build fails?**
- Check Railway build logs
- Ensure Java 21 is being used
- Verify all dependencies in pom.xml

**App crashes on startup?**
- Check Railway deployment logs
- Verify PORT environment variable is set
- Check memory limits (Railway free tier: 512MB)

**Can't access the app?**
- Ensure Railway generated a public URL
- Check if app is running in Railway dashboard
- Verify security settings allow public access

## Cost
- Railway offers $5 free credit per month
- Typically enough for small applications
- Upgrade for more resources if needed

## Support
- Railway Docs: https://docs.railway.app
- Railway Discord: https://discord.gg/railway
