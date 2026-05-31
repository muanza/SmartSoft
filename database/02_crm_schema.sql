DROP TABLE IF EXISTS api_keys CASCADE;
DROP TABLE IF EXISTS utilizadores_crm CASCADE;
DROP TABLE IF EXISTS comunicados CASCADE;
DROP TABLE IF EXISTS banners CASCADE;
DROP TABLE IF EXISTS maquinas_licenciadas CASCADE;
DROP TABLE IF EXISTS licencas CASCADE;
DROP TABLE IF EXISTS tenants CASCADE;
DROP TABLE IF EXISTS parceiros CASCADE;
DROP TABLE IF EXISTS planos_licenca CASCADE;
DROP TABLE IF EXISTS auditoria_crm CASCADE;

CREATE TABLE parceiros (
    id VARCHAR(36) PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL,
    telefone VARCHAR(30),
    estado VARCHAR(30) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tenants (
    nif VARCHAR(20) PRIMARY KEY,
    nome_empresa VARCHAR(180) NOT NULL,
    email_empresa VARCHAR(180) NOT NULL,
    telefone_empresa VARCHAR(30),
    idioma_preferido VARCHAR(5) NOT NULL,
    nome_base_dados VARCHAR(120) NOT NULL UNIQUE,
    estado VARCHAR(30) NOT NULL,
    parceiro_id VARCHAR(36) REFERENCES parceiros(id),
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE planos_licenca (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    limite_utilizadores INTEGER NOT NULL,
    limite_facturas_dia INTEGER NOT NULL,
    permite_multi_caixa BOOLEAN NOT NULL,
    sincronizacao_cloud BOOLEAN NOT NULL
);

CREATE TABLE licencas (
    id VARCHAR(36) PRIMARY KEY,
    tenant_nif VARCHAR(20) NOT NULL REFERENCES tenants(nif),
    parceiro_id VARCHAR(36) REFERENCES parceiros(id),
    plano_id VARCHAR(36) NOT NULL REFERENCES planos_licenca(id),
    chave_activacao VARCHAR(120) NOT NULL UNIQUE,
    estado VARCHAR(30) NOT NULL,
    limite_facturas_dia INTEGER NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    ultimo_ping TIMESTAMP
);

CREATE TABLE maquinas_licenciadas (
    id VARCHAR(36) PRIMARY KEY,
    tenant_nif VARCHAR(20) NOT NULL REFERENCES tenants(nif),
    serial_hardware VARCHAR(120) NOT NULL,
    nome_maquina VARCHAR(120) NOT NULL,
    ip_local VARCHAR(45),
    activa BOOLEAN NOT NULL,
    ultima_sincronizacao TIMESTAMP
);

CREATE TABLE banners (
    id VARCHAR(36) PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    conteudo VARCHAR(500) NOT NULL,
    url_destino VARCHAR(255),
    publico_alvo VARCHAR(30) NOT NULL,
    activo BOOLEAN NOT NULL,
    data_inicio TIMESTAMP,
    data_fim TIMESTAMP
);

CREATE TABLE comunicados (
    id VARCHAR(36) PRIMARY KEY,
    assunto VARCHAR(180) NOT NULL,
    mensagem TEXT NOT NULL,
    idioma VARCHAR(5) NOT NULL,
    publico_alvo VARCHAR(30) NOT NULL,
    data_envio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE utilizadores_crm (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    palavra_passe_hash VARCHAR(255) NOT NULL,
    perfil VARCHAR(30) NOT NULL,
    activo BOOLEAN NOT NULL,
    parceiro_id VARCHAR(36) REFERENCES parceiros(id)
);

CREATE TABLE api_keys (
    id VARCHAR(36) PRIMARY KEY,
    tenant_nif VARCHAR(20) NOT NULL REFERENCES tenants(nif),
    chave_valor VARCHAR(120) NOT NULL UNIQUE,
    descricao VARCHAR(180),
    activa BOOLEAN NOT NULL,
    ultima_utilizacao TIMESTAMP
);

CREATE TABLE auditoria_crm (
    id BIGSERIAL PRIMARY KEY,
    modulo VARCHAR(60) NOT NULL,
    acao VARCHAR(60) NOT NULL,
    actor VARCHAR(150) NOT NULL,
    tenant_nif VARCHAR(20),
    data_evento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    detalhe TEXT
);

CREATE INDEX idx_tenants_parceiro ON tenants(parceiro_id);
CREATE INDEX idx_licencas_tenant_estado ON licencas(tenant_nif, estado);
CREATE INDEX idx_maquinas_tenant_activa ON maquinas_licenciadas(tenant_nif, activa);
CREATE INDEX idx_api_keys_tenant ON api_keys(tenant_nif);
