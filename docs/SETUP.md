# SmartSoft Setup

1. Install Java 11+ and Maven 3.8+.
2. Install PostgreSQL 13+.
3. Create database/user:
   - `createuser smartsoft --pwprompt`
   - `createdb smartsoft -O smartsoft`
4. Run schema script:
   - `psql -U smartsoft -d postgres -f database/schema_smartsoft.sql`
5. Configure env vars:
   - `SMARTSOFT_DB_URL`
   - `SMARTSOFT_DB_USERNAME`
   - `SMARTSOFT_DB_PASSWORD`
6. Build and test:
   - `mvn clean test`
7. Run app:
   - `mvn spring-boot:run`
