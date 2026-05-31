# SmartSoft — Deployment Guide

## Prerequisites

| Component | Version |
|-----------|---------|
| Java | 11+ (OpenJDK 11 or 17 recommended) |
| Maven | 3.8+ |
| PostgreSQL | 14+ |
| WildFly (optional) | 27+ |
| Docker (optional) | 20+ |

---

## 1. PostgreSQL Installation & Configuration

### 1.1 Install PostgreSQL (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install -y postgresql postgresql-contrib
sudo systemctl enable postgresql
sudo systemctl start postgresql
```

### 1.2 Install PostgreSQL (RHEL/CentOS)
```bash
sudo dnf install -y postgresql-server postgresql-contrib
sudo postgresql-setup --initdb
sudo systemctl enable postgresql
sudo systemctl start postgresql
```

### 1.3 Create Database and User
```bash
sudo -u postgres psql << 'SQL'
-- Create application user
CREATE USER smartsoft WITH PASSWORD 'SmartSoft2024!';

-- Create main database
CREATE DATABASE smartsoft_db
    OWNER smartsoft
    ENCODING 'UTF8'
    LC_COLLATE 'pt_AO.UTF-8'
    LC_CTYPE 'pt_AO.UTF-8'
    TEMPLATE template0;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE smartsoft_db TO smartsoft;

-- Enable UUID extension (required for UUID primary keys)
\c smartsoft_db
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
SQL
```

### 1.4 Initialize the Database Schema
```bash
psql -U smartsoft -d smartsoft_db -f database/schema_smartsoft.sql
```

---

## 2. Maven Build

### 2.1 Build the Application
```bash
cd /path/to/SmartSoft
mvn clean package -DskipTests
```

### 2.2 Run Tests
```bash
mvn test
```

### 2.3 Build without tests (production)
```bash
mvn clean package -DskipTests -Pprod
```

The JAR will be at: `target/smartsoft-1.0.0.jar`

---

## 3. Environment Variables

Set the following environment variables before starting the application:

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=smartsoft_db
export DB_USER=smartsoft
export DB_PASSWORD=SmartSoft2024!

# Generate a strong 512-bit key: openssl rand -base64 64
export JWT_SECRET=<your-base64-encoded-secret>
export JWT_EXPIRATION_MS=86400000

# Optional: custom log directory
export LOG_DIR=/var/log/smartsoft
```

---

## 4. Running the Application

### 4.1 Standalone JAR (recommended for development)
```bash
java -jar target/smartsoft-1.0.0.jar
```

### 4.2 With custom properties
```bash
java -jar target/smartsoft-1.0.0.jar \
  --spring.datasource.url=jdbc:postgresql://localhost:5432/smartsoft_db \
  --server.port=8080
```

### 4.3 As a systemd service (production)
Create `/etc/systemd/system/smartsoft.service`:

```ini
[Unit]
Description=SmartSoft Invoicing System
After=network.target postgresql.service

[Service]
Type=simple
User=smartsoft
WorkingDirectory=/opt/smartsoft
ExecStart=/usr/bin/java -jar /opt/smartsoft/smartsoft-1.0.0.jar
EnvironmentFile=/etc/smartsoft/environment
Restart=always
RestartSec=10
StandardOutput=syslog
StandardError=syslog
SyslogIdentifier=smartsoft

[Install]
WantedBy=multi-user.target
```

Create `/etc/smartsoft/environment`:
```
DB_HOST=localhost
DB_PORT=5432
DB_NAME=smartsoft_db
DB_USER=smartsoft
DB_PASSWORD=your-secure-password
JWT_SECRET=your-base64-secret
```

Enable and start:
```bash
sudo systemctl daemon-reload
sudo systemctl enable smartsoft
sudo systemctl start smartsoft
sudo systemctl status smartsoft
```

---

## 5. WildFly Deployment (Optional)

### 5.1 Download WildFly
```bash
wget https://github.com/wildfly/wildfly/releases/download/27.0.1.Final/wildfly-27.0.1.Final.zip
unzip wildfly-27.0.1.Final.zip -d /opt
```

### 5.2 Add PostgreSQL DataSource to standalone.xml
In `/opt/wildfly-27.0.1.Final/standalone/configuration/standalone.xml`, add within `<datasources>`:

```xml
<datasource jndi-name="java:/jdbc/smartsoft" pool-name="SmartSoftDS" enabled="true">
    <connection-url>jdbc:postgresql://localhost:5432/smartsoft_db</connection-url>
    <driver>postgresql</driver>
    <security>
        <user-name>smartsoft</user-name>
        <password>SmartSoft2024!</password>
    </security>
    <pool>
        <min-pool-size>5</min-pool-size>
        <max-pool-size>20</max-pool-size>
    </pool>
    <validation>
        <check-valid-connection-sql>SELECT 1</check-valid-connection-sql>
    </validation>
</datasource>

<drivers>
    <driver name="postgresql" module="org.postgresql">
        <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
    </driver>
</drivers>
```

### 5.3 Deploy the WAR
```bash
cp target/smartsoft-1.0.0.war /opt/wildfly-27.0.1.Final/standalone/deployments/
/opt/wildfly-27.0.1.Final/bin/standalone.sh
```

---

## 6. Docker Setup (Optional)

### 6.1 Build Docker image
```bash
docker build -t smartsoft:1.0.0 .
```

### 6.2 Run with Docker Compose
```bash
docker-compose up -d
```

See `docker-compose.yml` for full configuration.

---

## 7. Validation Steps

After deployment, verify the application is running:

```bash
# Health check
curl http://localhost:8080/api/actuator/health

# Expected response:
# {"status":"UP"}
```

Test login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"tenantNif":"123456789","email":"admin@empresa.com","senha":"admin123"}'
```

---

## 8. Troubleshooting

### Database connection issues
```bash
# Test PostgreSQL connection
psql -U smartsoft -d smartsoft_db -c "SELECT 1;"

# Check PostgreSQL is running
sudo systemctl status postgresql

# View PostgreSQL logs
sudo journalctl -u postgresql -n 50
```

### Application fails to start
```bash
# View application logs
journalctl -u smartsoft -n 100

# Check port in use
netstat -tlnp | grep 8080
```

### JWT authentication errors
- Ensure `JWT_SECRET` is set and is a valid Base64 string of at least 32 bytes
- Generate a new secret: `openssl rand -base64 64 | tr -d '\n'`

### UUID extension missing
```sql
-- Run as postgres superuser
\c smartsoft_db
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
```
