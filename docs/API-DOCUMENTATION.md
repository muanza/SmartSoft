# SmartSoft API Documentation

Base URL: `http://localhost:8080/api`

All endpoints (except `/auth/login` and `/licencas/validar`) require a JWT bearer token:
```
Authorization: Bearer <seu-jwt-aqui>
```

---

## Authentication

### POST /auth/login
Login and receive a JWT token.

**Request:**
```json
{
  "tenantNif": "123456789",
  "email": "admin@empresa.ao",
  "senha": "senha123"
}
```

**Response 200:**
```json
{
  "sucesso": true,
  "mensagem": "Login realizado com sucesso",
  "dados": {
    "token": "<jwt-token-value>",
    "email": "admin@empresa.ao",
    "nomeUtilizador": "admin",
    "perfil": "administrador",
    "tenantNif": "123456789",
    "expiracaoMs": 86400000
  }
}
```

### POST /auth/logout
Logout (client should discard the token).

**Response 200:**
```json
{"sucesso": true, "mensagem": "Sessão terminada com sucesso"}
```

---

## Invoices (Facturas)

### POST /facturas
Create a new invoice.

**Request:**
```json
{
  "tipoFactura": "FT",
  "clienteNome": "Cliente Exemplo Lda",
  "clienteNif": "987654321",
  "clienteEmail": "cliente@exemplo.ao",
  "metodoPagamento": "numerario",
  "moeda": "AOA",
  "linhas": [
    {
      "descricaoLinha": "Produto A",
      "quantidade": 2,
      "precoUnitario": 5000.00,
      "taxaIva": "14%"
    }
  ],
  "montantePago": 12000.00
}
```

**Response 201:**
```json
{
  "sucesso": true,
  "mensagem": "Factura criada com sucesso",
  "dados": {
    "idFactura": "uuid",
    "numeroFactura": "00000001/2024",
    "tipoFactura": "FT",
    "totalLiquido": 11400.00,
    "status": "paga",
    ...
  }
}
```

### GET /facturas
List invoices (paginated). Query params: `page`, `size`, `sort`.

### GET /facturas/{id}
Get invoice by ID.

### PATCH /facturas/{id}/cancelar
Cancel an invoice.

**Request:**
```json
{"motivo": "Erro na factura"}
```

---

## Payments (Pagamentos)

### POST /facturas/{idFactura}/pagamentos
Record a payment.

**Request:**
```json
{
  "montante": 5000.00,
  "metodoPagamento": "transferencia",
  "referencia": "REF-2024-001"
}
```

### GET /facturas/{idFactura}/pagamentos
List payments for an invoice.

---

## Products (Produtos)

### POST /produtos
Create a product.

**Request:**
```json
{
  "codigoProduto": "PROD-001",
  "nomeProduto": "Produto Exemplo",
  "precoVenda": 5000.00,
  "taxaIva": "14%",
  "unidadeMedida": "UN",
  "controlaStock": true
}
```

### GET /produtos
List products (paginated).

### GET /produtos/{id}
Get product by ID.

### PUT /produtos/{id}
Update product.

### DELETE /produtos/{id}
Deactivate product.

---

## Cash Register (Caixa)

### POST /caixa/abrir
Open a cash register session.

**Request:**
```json
{
  "fundoManeio": 50000.00,
  "saldoInicial": 50000.00,
  "observacoes": "Abertura normal"
}
```

### PATCH /caixa/{id}/fechar
Close a cash register session.

**Request:**
```json
{
  "saldoFinal": 150000.00,
  "observacoes": "Fecho normal"
}
```

### POST /caixa/{id}/movimentos
Record a cash movement.

**Request:**
```json
{
  "tipoMovimento": "entrada",
  "montante": 10000.00,
  "descricao": "Depósito de numerário"
}
```

### GET /caixa/aberta
Get the currently open cash register session.

---

## Users (Utilizadores)

### POST /utilizadores
Create a user (admin only).

**Request:**
```json
{
  "nomeCompleto": "João Silva",
  "nomeUtilizador": "joaosilva",
  "email": "joao@empresa.ao",
  "senha": "Senha@123",
  "perfil": "operador",
  "telefone": "+244923456789"
}
```

### GET /utilizadores
List users.

### GET /utilizadores/{id}
Get user by ID.

### PUT /utilizadores/{id}
Update user.

### PATCH /utilizadores/{id}/alterar-senha
Change password.

**Request:**
```json
{
  "senhaAtual": "senha123",
  "novaSenha": "novaSenha456"
}
```

### PATCH /utilizadores/{id}/desbloquear
Unblock a user.

### DELETE /utilizadores/{id}
Deactivate a user.

---

## Reports (Relatórios)

### GET /relatorios/vendas/diario
Get daily sales report.

Query params:
- `data` (optional, default: today) — ISO date, e.g., `2024-01-15`

**Response:**
```json
{
  "sucesso": true,
  "dados": {
    "data": "2024-01-15",
    "totalFacturas": 45,
    "totalVendas": 2500000.00,
    "tenantNif": "123456789"
  }
}
```

### GET /relatorios/vendas/periodo
Get sales report for a period.

Query params:
- `inicio` — ISO date (default: first day of current month)
- `fim` — ISO date (default: today)

---

## Backup

### POST /backup/iniciar
Trigger a manual backup.

Query params:
- `tipo` (optional, default: `manual`) — `manual`, `incremental`, `completo`

### GET /backup
List backup history.

---

## Error Responses

All errors follow this format:

```json
{
  "sucesso": false,
  "mensagem": "Descrição do erro",
  "erro": "VALIDATION_ERROR",
  "dados": null
}
```

### HTTP Status Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 201 | Created |
| 400 | Bad request / validation error |
| 401 | Unauthorized (missing or invalid token) |
| 403 | Forbidden (insufficient permissions) |
| 404 | Resource not found |
| 409 | Conflict (duplicate resource) |
| 500 | Internal server error |
