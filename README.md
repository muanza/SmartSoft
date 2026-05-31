# faturacao-agt-completa

Plataforma multi-tenant de faturação e CRM preparada para WildFly + PostgreSQL, organizada em dois módulos:

- `faturacao-crm`: CRM online para parceiros, tenants, licenças, banners, comunicação e API de licenciamento.
- `faturacao-pos`: software de faturação/POS com gestão de vendas, produtos, stock, utilizadores, caixa, emissão, backup e sincronização.

## Estrutura

```text
faturacao-agt-completa/
├── pom.xml
├── faturacao-crm/
├── faturacao-pos/
├── database/
├── docs/
├── Dockerfile
└── docker-compose.yml
```

## Requisitos técnicos

- Java 11
- Maven 3.9+
- PostgreSQL 15+
- WildFly 26+

## Build rápido

```bash
mvn test
mvn -DskipTests package
```

## Deploy e configuração

Siga `/tmp/workspace/muanza/SmartSoft/docs/DEPLOY-STEP-BY-STEP.md` para:

1. criar as bases CRM e template tenant;
2. provisionar tenants por NIF;
3. configurar datasources no WildFly;
4. gerar e publicar os WARs;
5. testar a API de licenciamento e o POS.

## Assunções implementadas

- `tenantId` corresponde ao NIF da empresa.
- o CRM usa uma base central;
- cada tenant usa uma base dedicada clonada do template SQL;
- o CRM comunica com o POS por API key;
- os módulos incluem i18n em Português, Inglês, Francês e Mandarim.

## Observação funcional

Os fluxos fiscais e comerciais foram estruturados para suportar faturação, hash, QR textual, séries, caixa, backup e sincronização. A validação legal específica AGT e a replicação exata do comportamento do Cegid Vendus continuam dependentes de requisitos oficiais adicionais antes de certificação final.
