# Deploy e execução passo a passo

## 1. Pré-requisitos

1. Instalar Java 11.
2. Instalar Maven 3.9 ou superior.
3. Instalar PostgreSQL 15 ou superior.
4. Instalar WildFly 26 ou superior.

## 2. Criar as bases centrais e template

1. Executar `/tmp/workspace/muanza/SmartSoft/database/01_create_database.sql`.
2. Ligar à base `crm_faturacao` e executar `/tmp/workspace/muanza/SmartSoft/database/02_crm_schema.sql`.
3. Ligar à base `template_tenant_faturacao` e executar `/tmp/workspace/muanza/SmartSoft/database/03_tenant_template.sql`.
4. Executar `/tmp/workspace/muanza/SmartSoft/database/04_seed_data.sql` em `crm_faturacao`.

## 3. Provisionar um tenant novo

1. Criar a base dedicada do tenant com base no template.
2. Usar o NIF como identificador da base e do tenant.
3. Registar o tenant no CRM.
4. Criar licença, API key e máquina autorizada.

### Exemplo

```sql
CREATE DATABASE tenant_500000001 TEMPLATE template_tenant_faturacao;
```

## 4. Configurar datasources no WildFly

Criar os seguintes datasources:

- `java:/jdbc/FaturacaoCRMDS` apontando para `crm_faturacao`
- `java:/jdbc/FaturacaoTenantDS` apontando para a base dedicada do tenant em uso

Configurar o driver PostgreSQL no WildFly e usar o utilizador com permissões de leitura/escrita.

## 5. Compilar os módulos

```bash
cd /tmp/workspace/muanza/SmartSoft
mvn test
mvn -DskipTests package
```

Os ficheiros gerados serão:

- `/tmp/workspace/muanza/SmartSoft/faturacao-crm/target/faturacao-crm.war`
- `/tmp/workspace/muanza/SmartSoft/faturacao-pos/target/faturacao-pos.war`

## 6. Deploy no WildFly

1. Copiar ambos os WARs para `standalone/deployments/`.
2. Confirmar os datasources ativos.
3. Reiniciar o servidor se necessário.

## 7. URLs base

- CRM: `http://localhost:8080/faturacao-crm/login.xhtml`
- POS: `http://localhost:8080/faturacao-pos/login.xhtml`
- API CRM: `http://localhost:8080/faturacao-crm/api/licencas?tenant=500000001`

## 8. Operação

1. Criar parceiro e tenant no CRM.
2. Criar plano e licença.
3. Gerar API key.
4. Configurar o POS com URL do CRM, API key e NIF do tenant.
5. Abrir caixa e iniciar vendas.
6. Validar sincronização e backup local.

## 9. Testes mínimos recomendados

1. Login CRM e POS.
2. Criação de tenant.
3. Validação de licença pela API.
4. Abertura/fecho de caixa.
5. Emissão de factura.
6. Geração de hash e QR textual.
7. Tradução da factura para os quatro idiomas.
8. Simulação de backup e sincronização.

## 10. Pontos a fechar antes de produção

1. Validar requisitos AGT oficiais.
2. Validar formato legal do QR code e hash fiscal.
3. Confirmar séries, motivos de isenção e layouts homologados.
4. Validar a matriz funcional que pode ou não replicar o Cegid Vendus.
