# API Documentation

Base path: `/api`

- `POST /auth/login`, `POST /auth/logout`, `GET /auth/permissions`
- `POST|GET|PUT|DELETE /tenants`
- `POST|GET /products/categories`, `POST|GET|PUT|DELETE /products`
- `GET /stock/levels`, `POST|GET /stock/movements`
- `POST|GET|PUT /invoices`, `POST /invoices/{id}/cancel`, `POST /invoices/{id}/validate`
- `POST /payments`, `GET /payments/history`
- `POST /cash-register/open`, `POST /cash-register/{id}/close`, `GET /cash-register/movements`
- `GET /reports/sales/daily`, `GET /reports/sales/products`, `GET /reports/sales/users`
- `POST|GET|PUT /users`, `POST /users/{id}/change-password`
- `POST /system/backup`, `GET /system/sync-status`
- `GET /license/status?key=...`

All mutable endpoints accept JSON and validate fields using Bean Validation annotations.
