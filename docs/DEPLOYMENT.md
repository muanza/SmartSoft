# Deployment Guide

## Build
- `mvn clean package`

## WildFly (standalone)
1. Add PostgreSQL driver module.
2. Add datasource in `standalone.xml` named `java:/jdbc/SmartSoftDS`.
3. Set JVM env vars for DB credentials and tenant defaults.
4. Deploy `target/smartsoft-1.0.0-SNAPSHOT.jar` via WildFly bootable jar or as WAR adaptation.

## Docker (optional)
- Build image: `docker build -t smartsoft:latest .`
- Compose should define `app` + `postgres` services and pass DB env vars.

## Validation
- Health endpoint: `GET /actuator/health`
- Test auth endpoint: `POST /api/auth/login`

## Troubleshooting
- Connection errors: verify DB URL/credentials.
- Tenant schema errors: ensure schema exists and header `X-Tenant-ID` is set.
- Validation errors: check payload against DTO constraints.
