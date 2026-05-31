DROP TABLE IF EXISTS sincronizacoes_cloud CASCADE;
DROP TABLE IF EXISTS backups_locais CASCADE;
DROP TABLE IF EXISTS movimentos_caixa CASCADE;
DROP TABLE IF EXISTS sessoes_caixa CASCADE;
DROP TABLE IF EXISTS linhas_factura CASCADE;
DROP TABLE IF EXISTS facturas CASCADE;
DROP TABLE IF EXISTS series_factura CASCADE;
DROP TABLE IF EXISTS utilizadores CASCADE;
DROP TABLE IF EXISTS clientes CASCADE;
DROP TABLE IF EXISTS produtos CASCADE;

CREATE TABLE clientes (
    id VARCHAR(36) PRIMARY KEY,
    tenant_nif VARCHAR(20) NOT NULL,
    nif VARCHAR(20),
    nome VARCHAR(180) NOT NULL,
    email VARCHAR(180),
    telefone VARCHAR(30),
    idioma_preferido VARCHAR(5) NOT NULL DEFAULT 'pt'
);

CREATE TABLE produtos (
    id VARCHAR(36) PRIMARY KEY,
    tenant_nif VARCHAR(20) NOT NULL,
    codigo VARCHAR(50) NOT NULL,
    nome VARCHAR(180) NOT NULL,
    descricao VARCHAR(255),
    preco_venda NUMERIC(18,2) NOT NULL,
    imposto_percentual NUMERIC(8,2) NOT NULL,
    stock_actual NUMERIC(18,2) NOT NULL,
    activo BOOLEAN NOT NULL
);

CREATE TABLE utilizadores (
    id VARCHAR(36) PRIMARY KEY,
    tenant_nif VARCHAR(20) NOT NULL,
    nome VARCHAR(150) NOT NULL,
    username VARCHAR(80) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    perfil VARCHAR(30) NOT NULL,
    activo BOOLEAN NOT NULL
);

CREATE TABLE series_factura (
    id VARCHAR(36) PRIMARY KEY,
    tenant_nif VARCHAR(20) NOT NULL,
    codigo VARCHAR(30) NOT NULL,
    tipo_documento VARCHAR(30) NOT NULL,
    proximo_numero INTEGER NOT NULL,
    activo BOOLEAN NOT NULL
);

CREATE TABLE facturas (
    id VARCHAR(36) PRIMARY KEY,
    tenant_nif VARCHAR(20) NOT NULL,
    numero VARCHAR(60) NOT NULL UNIQUE,
    tipo_documento VARCHAR(30) NOT NULL,
    hash_fiscal VARCHAR(255) NOT NULL,
    qr_code_texto TEXT NOT NULL,
    cliente_nome VARCHAR(180),
    idioma VARCHAR(5) NOT NULL,
    subtotal NUMERIC(18,2) NOT NULL,
    imposto_total NUMERIC(18,2) NOT NULL,
    total NUMERIC(18,2) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    data_emissao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE linhas_factura (
    id VARCHAR(36) PRIMARY KEY,
    factura_id VARCHAR(36) NOT NULL REFERENCES facturas(id) ON DELETE CASCADE,
    descricao VARCHAR(180) NOT NULL,
    quantidade NUMERIC(18,2) NOT NULL,
    preco_unitario NUMERIC(18,2) NOT NULL,
    taxa_imposto NUMERIC(8,2) NOT NULL,
    subtotal NUMERIC(18,2) NOT NULL
);

CREATE TABLE sessoes_caixa (
    id VARCHAR(36) PRIMARY KEY,
    tenant_nif VARCHAR(20) NOT NULL,
    caixa_codigo VARCHAR(40) NOT NULL,
    operador VARCHAR(150) NOT NULL,
    valor_abertura NUMERIC(18,2) NOT NULL,
    valor_fecho NUMERIC(18,2),
    estado VARCHAR(30) NOT NULL,
    aberta_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fechada_em TIMESTAMP
);

CREATE TABLE movimentos_caixa (
    id VARCHAR(36) PRIMARY KEY,
    sessao_id VARCHAR(36) NOT NULL REFERENCES sessoes_caixa(id) ON DELETE CASCADE,
    tipo VARCHAR(30) NOT NULL,
    descricao VARCHAR(180) NOT NULL,
    valor NUMERIC(18,2) NOT NULL,
    data_movimento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE backups_locais (
    id BIGSERIAL PRIMARY KEY,
    tenant_nif VARCHAR(20) NOT NULL,
    caminho_arquivo VARCHAR(255) NOT NULL,
    incremental BOOLEAN NOT NULL,
    data_backup TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sincronizacoes_cloud (
    id BIGSERIAL PRIMARY KEY,
    tenant_nif VARCHAR(20) NOT NULL,
    referencia_documento VARCHAR(60) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    detalhe TEXT,
    data_sincronizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_produtos_tenant_codigo ON produtos(tenant_nif, codigo);
CREATE INDEX idx_facturas_tenant_data ON facturas(tenant_nif, data_emissao);
CREATE INDEX idx_facturas_tenant_estado ON facturas(tenant_nif, estado);
CREATE INDEX idx_sessoes_caixa_tenant_estado ON sessoes_caixa(tenant_nif, estado);
