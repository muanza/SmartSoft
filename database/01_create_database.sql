DROP DATABASE IF EXISTS crm_faturacao;
DROP DATABASE IF EXISTS template_tenant_faturacao;

CREATE DATABASE crm_faturacao
    WITH ENCODING 'UTF8'
         TEMPLATE template0;

CREATE DATABASE template_tenant_faturacao
    WITH ENCODING 'UTF8'
         TEMPLATE template0;
