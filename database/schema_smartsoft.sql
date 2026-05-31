-- ========================================================================
-- SmartSoft: Multi-Tenant Invoicing & CRM System
-- Database Schema - PostgreSQL
-- Version: 1.0
-- Language: Portuguese table/column names with English comments
-- ========================================================================

-- Drop existing database if it exists
DROP DATABASE IF EXISTS smartsoft;

-- Create database
CREATE DATABASE smartsoft
    WITH 
    ENCODING = 'UTF8'
    LC_COLLATE = 'pt_PT.UTF-8'
    LC_CTYPE = 'pt_PT.UTF-8';

-- Connect to the database
\c smartsoft;

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Enable pgcrypto for password hashing
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ========================================================================
-- TENANT MANAGEMENT TABLES
-- ========================================================================

-- Tenants table: Each company has a dedicated database schema
CREATE TABLE tenants (
    id_tenant UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nif VARCHAR(20) UNIQUE NOT NULL COMMENT 'Company NIF (National ID Number)',
    nome_empresa VARCHAR(255) NOT NULL,
    email_empresa VARCHAR(255) NOT NULL,
    telefone_empresa VARCHAR(20),
    morada VARCHAR(500),
    codigo_postal VARCHAR(10),
    cidade VARCHAR(100),
    pais VARCHAR(100),
    logotipo BYTEA,
    idioma_padrao VARCHAR(10) DEFAULT 'pt' CHECK (idioma_padrao IN ('pt', 'en', 'fr', 'zh')),
    fuso_horario VARCHAR(50) DEFAULT 'Europe/Lisbon',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    ult_backup TIMESTAMP
);

CREATE INDEX idx_tenants_nif ON tenants(nif);
CREATE INDEX idx_tenants_ativo ON tenants(ativo);

-- ========================================================================
-- CRM ADMINISTRATION TABLES
-- ========================================================================

-- Partners: CRM Partners who manage multiple tenants
CREATE TABLE parceiros (
    id_parceiro UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome_parceiro VARCHAR(255) NOT NULL,
    email_parceiro VARCHAR(255) NOT NULL UNIQUE,
    telefone_parceiro VARCHAR(20),
    morada_parceiro VARCHAR(500),
    nif_parceiro VARCHAR(20) UNIQUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE INDEX idx_parceiros_email ON parceiros(email_parceiro);
CREATE INDEX idx_parceiros_ativo ON parceiros(ativo);

-- Relationship between Partners and Tenants
CREATE TABLE parceiro_tenant (
    id_parceiro_tenant UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_parceiro UUID NOT NULL REFERENCES parceiros(id_parceiro) ON DELETE CASCADE,
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    data_associacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    UNIQUE(id_parceiro, id_tenant)
);

CREATE INDEX idx_parceiro_tenant_parceiro ON parceiro_tenant(id_parceiro);
CREATE INDEX idx_parceiro_tenant_tenant ON parceiro_tenant(id_tenant);

-- Licenses: Software licenses for tenants
CREATE TABLE licencas (
    id_licenca UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    id_parceiro UUID REFERENCES parceiros(id_parceiro) ON DELETE SET NULL,
    chave_licenca VARCHAR(255) UNIQUE NOT NULL,
    tipo_licenca VARCHAR(50) NOT NULL CHECK (tipo_licenca IN ('basico', 'profissional', 'empresa')),
    versao_software VARCHAR(20),
    data_inicio DATE NOT NULL,
    data_expiracao DATE NOT NULL,
    num_utilizadores_permitidos INT DEFAULT 5,
    limite_facturas_diarias INT DEFAULT 1000,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    bloqueada BOOLEAN DEFAULT FALSE,
    motivo_bloqueio TEXT
);

CREATE INDEX idx_licencas_tenant ON licencas(id_tenant);
CREATE INDEX idx_licencas_chave ON licencas(chave_licenca);
CREATE INDEX idx_licencas_ativo ON licencas(ativo);
CREATE INDEX idx_licencas_expiracao ON licencas(data_expiracao);

-- License Validation Log
CREATE TABLE log_validacao_licenca (
    id_log UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_licenca UUID NOT NULL REFERENCES licencas(id_licenca) ON DELETE CASCADE,
    endereco_ip VARCHAR(45),
    data_validacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valida BOOLEAN,
    motivo_rejeicao TEXT
);

CREATE INDEX idx_log_validacao_licenca ON log_validacao_licenca(id_licenca);

-- Advertising Banners for Partners and Tenants
CREATE TABLE banners (
    id_banner UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    imagem BYTEA,
    url_destino VARCHAR(500),
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    tipo_banner VARCHAR(50) DEFAULT 'geral' CHECK (tipo_banner IN ('geral', 'parceiro', 'tenant')),
    alvo_parceiro UUID REFERENCES parceiros(id_parceiro) ON DELETE CASCADE,
    alvo_tenant UUID REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    ativo BOOLEAN DEFAULT TRUE,
    ordem_exibicao INT DEFAULT 0,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_banners_ativo ON banners(ativo);
CREATE INDEX idx_banners_datas ON banners(data_inicio, data_fim);

-- ========================================================================
-- USER MANAGEMENT TABLES
-- ========================================================================

-- Users: CRM Admin and Partner users
CREATE TABLE utilizadores_crm (
    id_utilizador_crm UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_parceiro UUID REFERENCES parceiros(id_parceiro) ON DELETE CASCADE,
    email VARCHAR(255) NOT NULL UNIQUE,
    nome_completo VARCHAR(255) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    perfil VARCHAR(50) NOT NULL CHECK (perfil IN ('administrador', 'parceiro')),
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_ultimo_acesso TIMESTAMP,
    ultimo_ip_acesso VARCHAR(45)
);

CREATE INDEX idx_utilizadores_crm_email ON utilizadores_crm(email);
CREATE INDEX idx_utilizadores_crm_perfil ON utilizadores_crm(perfil);
CREATE INDEX idx_utilizadores_crm_ativo ON utilizadores_crm(ativo);

-- Mass Communication: Communications sent to all companies
CREATE TABLE comunicacoes_massa (
    id_comunicacao UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_utilizador_crm UUID NOT NULL REFERENCES utilizadores_crm(id_utilizador_crm) ON DELETE CASCADE,
    titulo VARCHAR(255) NOT NULL,
    conteudo TEXT NOT NULL,
    tipo_comunicacao VARCHAR(50) DEFAULT 'email' CHECK (tipo_comunicacao IN ('email', 'notificacao', 'sms')),
    data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enviado BOOLEAN DEFAULT FALSE
);

CREATE INDEX idx_comunicacoes_massa_data ON comunicacoes_massa(data_envio);
CREATE INDEX idx_comunicacoes_massa_enviado ON comunicacoes_massa(enviado);

-- Communication Recipients Tracking
CREATE TABLE destinatarios_comunicacao (
    id_destinatario UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_comunicacao UUID NOT NULL REFERENCES comunicacoes_massa(id_comunicacao) ON DELETE CASCADE,
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    data_entrega TIMESTAMP,
    lido BOOLEAN DEFAULT FALSE,
    data_leitura TIMESTAMP
);

CREATE INDEX idx_destinatarios_comunicacao ON destinatarios_comunicacao(id_comunicacao);

-- ========================================================================
-- INVOICING SYSTEM TABLES (Multi-Tenant)
-- ========================================================================

-- Tenant Users: Users in each company
CREATE TABLE utilizadores (
    id_utilizador UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    nome_utilizador VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    perfil VARCHAR(50) NOT NULL CHECK (perfil IN ('administrador', 'operador')),
    bloqueado BOOLEAN DEFAULT FALSE,
    numero_tentativas_falhas INT DEFAULT 0,
    bloqueio_ecrã BOOLEAN DEFAULT FALSE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acesso TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    UNIQUE(id_tenant, email)
);

CREATE INDEX idx_utilizadores_tenant ON utilizadores(id_tenant);
CREATE INDEX idx_utilizadores_email ON utilizadores(email);
CREATE INDEX idx_utilizadores_perfil ON utilizadores(perfil);

-- Audit Log for user actions
CREATE TABLE log_auditoria (
    id_log_auditoria UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    id_utilizador UUID REFERENCES utilizadores(id_utilizador) ON DELETE SET NULL,
    tipo_acao VARCHAR(50),
    descricao_acao TEXT,
    tabela_afetada VARCHAR(100),
    id_registro_afetado UUID,
    dados_anteriores JSONB,
    dados_novos JSONB,
    data_acao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    endereco_ip VARCHAR(45)
);

CREATE INDEX idx_log_auditoria_tenant ON log_auditoria(id_tenant);
CREATE INDEX idx_log_auditoria_utilizador ON log_auditoria(id_utilizador);
CREATE INDEX idx_log_auditoria_data ON log_auditoria(data_acao);

-- ========================================================================
-- POS (POINT OF SALE) TABLES
-- ========================================================================

-- Cash Register Sessions
CREATE TABLE caixas (
    id_caixa UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    id_utilizador UUID NOT NULL REFERENCES utilizadores(id_utilizador) ON DELETE CASCADE,
    numero_caixa VARCHAR(20) NOT NULL,
    data_abertura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_fecho TIMESTAMP,
    saldo_inicial DECIMAL(12, 2) DEFAULT 0.00,
    saldo_final DECIMAL(12, 2),
    num_facturas INT DEFAULT 0,
    total_vendas DECIMAL(12, 2) DEFAULT 0.00,
    moeda VARCHAR(3) DEFAULT 'AOA',
    status VARCHAR(20) CHECK (status IN ('aberta', 'fechada', 'suspensa')),
    observacoes TEXT,
    UNIQUE(id_tenant, numero_caixa, data_abertura)
);

CREATE INDEX idx_caixas_tenant ON caixas(id_tenant);
CREATE INDEX idx_caixas_utilizador ON caixas(id_utilizador);
CREATE INDEX idx_caixas_status ON caixas(status);

-- Cash Register Movements
CREATE TABLE movimentos_caixa (
    id_movimento UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_caixa UUID NOT NULL REFERENCES caixas(id_caixa) ON DELETE CASCADE,
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    tipo_movimento VARCHAR(50) NOT NULL CHECK (tipo_movimento IN ('entrada', 'saida', 'devolucao')),
    descricao VARCHAR(255),
    montante DECIMAL(12, 2) NOT NULL,
    metodo_pagamento VARCHAR(50) CHECK (metodo_pagamento IN ('dinheiro', 'cartao', 'cheque', 'transferencia', 'outro')),
    data_movimento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    referencia_factura UUID REFERENCES facturas(id_factura) ON DELETE SET NULL
);

CREATE INDEX idx_movimentos_caixa ON movimentos_caixa(id_caixa);
CREATE INDEX idx_movimentos_tenant ON movimentos_caixa(id_tenant);

-- ========================================================================
-- PRODUCTS & STOCK MANAGEMENT
-- ========================================================================

-- Product Categories
CREATE TABLE categorias_produtos (
    id_categoria UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    nome_categoria VARCHAR(100) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(id_tenant, nome_categoria)
);

CREATE INDEX idx_categorias_tenant ON categorias_produtos(id_tenant);

-- Products
CREATE TABLE produtos (
    id_produto UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    id_categoria UUID NOT NULL REFERENCES categorias_produtos(id_categoria) ON DELETE CASCADE,
    codigo_produto VARCHAR(50) NOT NULL,
    nome_produto VARCHAR(255) NOT NULL,
    descricao TEXT,
    preco_custo DECIMAL(12, 2),
    preco_venda DECIMAL(12, 2) NOT NULL,
    taxa_iva VARCHAR(10) DEFAULT '17%' CHECK (taxa_iva IN ('0%', '7%', '14%', '17%')),
    unidade_medida VARCHAR(20) DEFAULT 'unidade',
    imagem BYTEA,
    sku VARCHAR(50),
    codigo_barras VARCHAR(100),
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(id_tenant, codigo_produto)
);

CREATE INDEX idx_produtos_tenant ON produtos(id_tenant);
CREATE INDEX idx_produtos_categoria ON produtos(id_categoria);
CREATE INDEX idx_produtos_codigo ON produtos(codigo_produto);
CREATE INDEX idx_produtos_nome ON produtos(nome_produto);

-- Stock Management
CREATE TABLE stock (
    id_stock UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_produto UUID NOT NULL REFERENCES produtos(id_produto) ON DELETE CASCADE,
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    quantidade_atual INT NOT NULL DEFAULT 0,
    quantidade_minima INT DEFAULT 10,
    quantidade_maxima INT DEFAULT 1000,
    data_ultima_contagem TIMESTAMP,
    localizacao_armazem VARCHAR(100),
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(id_tenant, id_produto)
);

CREATE INDEX idx_stock_tenant ON stock(id_tenant);
CREATE INDEX idx_stock_produto ON stock(id_produto);

-- Stock Movements Log
CREATE TABLE movimentos_stock (
    id_movimento_stock UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_stock UUID NOT NULL REFERENCES stock(id_stock) ON DELETE CASCADE,
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    id_utilizador UUID REFERENCES utilizadores(id_utilizador) ON DELETE SET NULL,
    tipo_movimento VARCHAR(50) NOT NULL CHECK (tipo_movimento IN ('entrada', 'saida', 'ajuste', 'devolucao', 'transferencia')),
    quantidade_movimento INT NOT NULL,
    motivo TEXT,
    referencia_documento VARCHAR(100),
    data_movimento TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_movimentos_stock_stock ON movimentos_stock(id_stock);
CREATE INDEX idx_movimentos_stock_tenant ON movimentos_stock(id_tenant);
CREATE INDEX idx_movimentos_stock_data ON movimentos_stock(data_movimento);

-- ========================================================================
-- INVOICE TABLES
-- ========================================================================

-- Invoices
CREATE TABLE facturas (
    id_factura UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    id_caixa UUID REFERENCES caixas(id_caixa) ON DELETE SET NULL,
    id_utilizador UUID NOT NULL REFERENCES utilizadores(id_utilizador) ON DELETE CASCADE,
    numero_factura VARCHAR(50) NOT NULL,
    numero_serie VARCHAR(20),
    data_factura DATE NOT NULL DEFAULT CURRENT_DATE,
    hora_factura TIME DEFAULT CURRENT_TIME,
    tipo_factura VARCHAR(50) NOT NULL CHECK (tipo_factura IN ('venda', 'devolucao', 'orcamento', 'proforma')),
    cliente_nome VARCHAR(255),
    cliente_nif VARCHAR(20),
    cliente_email VARCHAR(255),
    cliente_telefone VARCHAR(20),
    cliente_morada VARCHAR(500),
    subtotal DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    total_iva DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    desconto_percentual DECIMAL(5, 2) DEFAULT 0.00,
    desconto_montante DECIMAL(12, 2) DEFAULT 0.00,
    total_liquido DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    moeda VARCHAR(3) DEFAULT 'AOA',
    metodo_pagamento VARCHAR(50) CHECK (metodo_pagamento IN ('dinheiro', 'cartao', 'cheque', 'transferencia', 'misto', 'outro')),
    montante_pago DECIMAL(12, 2) DEFAULT 0.00,
    troco DECIMAL(12, 2) DEFAULT 0.00,
    status VARCHAR(50) DEFAULT 'finalizada' CHECK (status IN ('rascunho', 'finalizada', 'paga', 'cancelada', 'devolvida')),
    observacoes TEXT,
    assinatura_digital VARCHAR(500),
    hash_integridade VARCHAR(500),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_cancelamento TIMESTAMP,
    motivo_cancelamento TEXT,
    UNIQUE(id_tenant, numero_factura)
);

CREATE INDEX idx_facturas_tenant ON facturas(id_tenant);
CREATE INDEX idx_facturas_numero ON facturas(numero_factura);
CREATE INDEX idx_facturas_data ON facturas(data_factura);
CREATE INDEX idx_facturas_status ON facturas(status);
CREATE INDEX idx_facturas_utilizador ON facturas(id_utilizador);

-- Invoice Line Items
CREATE TABLE linhas_factura (
    id_linha UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_factura UUID NOT NULL REFERENCES facturas(id_factura) ON DELETE CASCADE,
    id_produto UUID REFERENCES produtos(id_produto) ON DELETE SET NULL,
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    descricao_linha VARCHAR(255) NOT NULL,
    quantidade DECIMAL(10, 2) NOT NULL,
    preco_unitario DECIMAL(12, 2) NOT NULL,
    taxa_iva VARCHAR(10) DEFAULT '17%',
    montante_iva DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    total_linha DECIMAL(12, 2) NOT NULL,
    numero_serie_linha INT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_linhas_factura ON linhas_factura(id_factura);
CREATE INDEX idx_linhas_produto ON linhas_factura(id_produto);

-- Invoice Payments
CREATE TABLE pagamentos_factura (
    id_pagamento UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_factura UUID NOT NULL REFERENCES facturas(id_factura) ON DELETE CASCADE,
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    montante_pagamento DECIMAL(12, 2) NOT NULL,
    metodo_pagamento VARCHAR(50) NOT NULL,
    data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    referencia_pagamento VARCHAR(100),
    status_pagamento VARCHAR(50) DEFAULT 'processado' CHECK (status_pagamento IN ('pendente', 'processado', 'falhou', 'cancelado')),
    observacoes TEXT
);

CREATE INDEX idx_pagamentos_factura ON pagamentos_factura(id_factura);
CREATE INDEX idx_pagamentos_tenant ON pagamentos_factura(id_tenant);

-- ========================================================================
-- SALES MANAGEMENT TABLES
-- ========================================================================

-- Sales Targets
CREATE TABLE metas_vendas (
    id_meta UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    id_utilizador UUID REFERENCES utilizadores(id_utilizador) ON DELETE CASCADE,
    ano INT NOT NULL,
    mes INT NOT NULL CHECK (mes BETWEEN 1 AND 12),
    valor_meta DECIMAL(12, 2) NOT NULL,
    valor_realizado DECIMAL(12, 2) DEFAULT 0.00,
    percentagem_atingimento DECIMAL(5, 2) DEFAULT 0.00,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(id_tenant, id_utilizador, ano, mes)
);

CREATE INDEX idx_metas_vendas_tenant ON metas_vendas(id_tenant);
CREATE INDEX idx_metas_vendas_utilizador ON metas_vendas(id_utilizador);

-- Sales Reports
CREATE TABLE relatorios_vendas (
    id_relatorio UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    id_utilizador UUID REFERENCES utilizadores(id_utilizador) ON DELETE SET NULL,
    titulo_relatorio VARCHAR(255) NOT NULL,
    tipo_relatorio VARCHAR(50),
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    dados_relatorio JSONB,
    total_vendas DECIMAL(12, 2),
    numero_transacoes INT,
    data_geracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_relatorios_vendas_tenant ON relatorios_vendas(id_tenant);

-- ========================================================================
-- BACKUP & SYNCHRONIZATION TABLES
-- ========================================================================

-- Backup Log
CREATE TABLE log_backup (
    id_log_backup UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    tipo_backup VARCHAR(50) DEFAULT 'incremental' CHECK (tipo_backup IN ('completo', 'incremental', 'diferencial')),
    local_backup VARCHAR(500) NOT NULL,
    cloud_backup VARCHAR(500),
    tamanho_backup BIGINT,
    data_inicio_backup TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_fim_backup TIMESTAMP,
    status_backup VARCHAR(50) DEFAULT 'em_progresso' CHECK (status_backup IN ('em_progresso', 'concluido', 'falhou')),
    motivo_falha TEXT,
    sincronizado_cloud BOOLEAN DEFAULT FALSE,
    data_sincronizacao_cloud TIMESTAMP,
    numero_registos_backup INT,
    checksum VARCHAR(64)
);

CREATE INDEX idx_log_backup_tenant ON log_backup(id_tenant);
CREATE INDEX idx_log_backup_data ON log_backup(data_inicio_backup);

-- Cloud Synchronization Log
CREATE TABLE log_sincronizacao_cloud (
    id_log_sincronizacao UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    tipo_sincronizacao VARCHAR(50) CHECK (tipo_sincronizacao IN ('upload', 'download', 'bidireccional')),
    dados_sincronizados JSONB,
    data_inicio_sincronizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_fim_sincronizacao TIMESTAMP,
    status_sincronizacao VARCHAR(50) DEFAULT 'em_progresso' CHECK (status_sincronizacao IN ('em_progresso', 'concluida', 'falhou')),
    motivo_falha TEXT,
    numero_registos_sincronizados INT,
    checksum_verificacao VARCHAR(64)
);

CREATE INDEX idx_log_sincronizacao_cloud_tenant ON log_sincronizacao_cloud(id_tenant);
CREATE INDEX idx_log_sincronizacao_cloud_data ON log_sincronizacao_cloud(data_inicio_sincronizacao);

-- ========================================================================
-- CONFIGURATION & SETTINGS TABLES
-- ========================================================================

-- Company Configuration
CREATE TABLE configuracoes_empresa (
    id_configuracao UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID NOT NULL REFERENCES tenants(id_tenant) ON DELETE CASCADE UNIQUE,
    numero_serie_inicial_factura VARCHAR(20),
    proximo_numero_factura INT DEFAULT 1,
    formato_numero_factura VARCHAR(100) DEFAULT 'NNNNNNNN/YYYY',
    separador_milhares VARCHAR(5) DEFAULT ',',
    separador_decimal VARCHAR(5) DEFAULT '.',
    casas_decimais INT DEFAULT 2,
    margem_lucro_padrao DECIMAL(5, 2) DEFAULT 0.00,
    taxa_iva_padrao VARCHAR(10) DEFAULT '17%',
    moeda_padrao VARCHAR(3) DEFAULT 'AOA',
    url_api_crm VARCHAR(500),
    chave_api_crm VARCHAR(255),
    backup_automatico_ativo BOOLEAN DEFAULT TRUE,
    intervalo_backup INT DEFAULT 24 COMMENT 'em horas',
    caminho_backup_local VARCHAR(500),
    caminho_backup_cloud VARCHAR(500),
    url_cloud_storage VARCHAR(500),
    credencial_cloud_storage VARCHAR(500),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_configuracoes_empresa_tenant ON configuracoes_empresa(id_tenant);

-- Translations/Multilingual Support
CREATE TABLE traducoes (
    id_traducao UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_tenant UUID REFERENCES tenants(id_tenant) ON DELETE CASCADE,
    chave_traducao VARCHAR(255) NOT NULL,
    idioma VARCHAR(10) NOT NULL CHECK (idioma IN ('pt', 'en', 'fr', 'zh')),
    valor_traducao TEXT NOT NULL,
    tipo_elemento VARCHAR(50) CHECK (tipo_elemento IN ('label', 'mensagem', 'titulo', 'campo', 'outro')),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(id_tenant, chave_traducao, idioma)
);

CREATE INDEX idx_traducoes_idioma ON traducoes(idioma);
CREATE INDEX idx_traducoes_chave ON traducoes(chave_traducao);

-- ========================================================================
-- VIEWS FOR REPORTING
-- ========================================================================

-- View: Total Sales by Day
CREATE VIEW vw_vendas_por_dia AS
SELECT 
    id_tenant,
    data_factura,
    COUNT(*) as total_facturas,
    SUM(total_liquido) as total_vendas,
    SUM(total_iva) as total_iva,
    COUNT(DISTINCT id_utilizador) as num_operadores
FROM facturas
WHERE status IN ('finalizada', 'paga')
GROUP BY id_tenant, data_factura
ORDER BY data_factura DESC;

-- View: Stock Status
CREATE VIEW vw_status_stock AS
SELECT 
    s.id_tenant,
    p.nome_produto,
    p.codigo_produto,
    s.quantidade_atual,
    s.quantidade_minima,
    s.quantidade_maxima,
    CASE 
        WHEN s.quantidade_atual <= s.quantidade_minima THEN 'CRITICO'
        WHEN s.quantidade_atual <= (s.quantidade_minima * 1.5) THEN 'BAIXO'
        WHEN s.quantidade_atual >= s.quantidade_maxima THEN 'CHEIO'
        ELSE 'OK'
    END as status_stock,
    s.data_atualizacao
FROM stock s
JOIN produtos p ON s.id_produto = p.id_produto
WHERE p.ativo = TRUE;

-- View: License Status
CREATE VIEW vw_status_licencas AS
SELECT 
    l.id_licenca,
    t.nif,
    t.nome_empresa,
    l.chave_licenca,
    l.tipo_licenca,
    l.data_inicio,
    l.data_expiracao,
    CASE 
        WHEN l.bloqueada THEN 'BLOQUEADA'
        WHEN l.data_expiracao < CURRENT_DATE THEN 'EXPIRADA'
        WHEN l.data_expiracao <= (CURRENT_DATE + INTERVAL '30 days') THEN 'PROXIMA_EXPIRACAO'
        ELSE 'ATIVA'
    END as status_licenca,
    (l.data_expiracao - CURRENT_DATE) as dias_restantes
FROM licencas l
JOIN tenants t ON l.id_tenant = t.id_tenant;

-- View: Daily Cash Register Summary
CREATE VIEW vw_resumo_caixa_dia AS
SELECT 
    id_tenant,
    DATE(data_abertura) as data_caixa,
    COUNT(*) as total_caixas_abertas,
    SUM(total_vendas) as vendas_dia,
    SUM(saldo_final - saldo_inicial) as diferenca_caixa,
    COUNT(CASE WHEN status = 'aberta' THEN 1 END) as caixas_abertas
FROM caixas
GROUP BY id_tenant, DATE(data_abertura);

-- ========================================================================
-- FUNCTIONS
-- ========================================================================

-- Function to update tenants last modification timestamp
CREATE OR REPLACE FUNCTION atualizar_timestamp_tenant()
RETURNS TRIGGER AS $$
BEGIN
    NEW.data_atualizacao = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_atualizar_timestamp_tenant
BEFORE UPDATE ON tenants
FOR EACH ROW
EXECUTE FUNCTION atualizar_timestamp_tenant();

-- Function to validate invoice number format
CREATE OR REPLACE FUNCTION validar_numero_factura()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.numero_factura IS NULL OR NEW.numero_factura = '' THEN
        RAISE EXCEPTION 'Número da factura não pode ser nulo';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_validar_numero_factura
BEFORE INSERT ON facturas
FOR EACH ROW
EXECUTE FUNCTION validar_numero_factura();

-- Function to update stock on invoice creation
CREATE OR REPLACE FUNCTION atualizar_stock_apos_factura()
RETURNS TRIGGER AS $$
DECLARE
    v_id_stock UUID;
    v_quantidade INT;
BEGIN
    IF NEW.status = 'finalizada' THEN
        FOR v_id_stock, v_quantidade IN 
            SELECT s.id_stock, lf.quantidade
            FROM linhas_factura lf
            JOIN stock s ON lf.id_produto = s.id_produto
            WHERE lf.id_factura = NEW.id_factura
        LOOP
            UPDATE stock SET quantidade_atual = quantidade_atual - v_quantidade
            WHERE id_stock = v_id_stock;
        END LOOP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_atualizar_stock_apos_factura
AFTER INSERT ON facturas
FOR EACH ROW
EXECUTE FUNCTION atualizar_stock_apos_factura();

-- ========================================================================
-- DEFAULT DATA
-- ========================================================================

-- Insert default translations (Portuguese, English, French, Mandarin)
INSERT INTO traducoes (chave_traducao, idioma, valor_traducao, tipo_elemento) VALUES
-- Portuguese
('factura', 'pt', 'Factura', 'label'),
('numero', 'pt', 'Número', 'label'),
('data', 'pt', 'Data', 'label'),
('cliente', 'pt', 'Cliente', 'label'),
('produto', 'pt', 'Produto', 'label'),
('quantidade', 'pt', 'Quantidade', 'label'),
('preco', 'pt', 'Preço', 'label'),
('total', 'pt', 'Total', 'label'),
('iva', 'pt', 'IVA', 'label'),

-- English
('factura', 'en', 'Invoice', 'label'),
('numero', 'en', 'Number', 'label'),
('data', 'en', 'Date', 'label'),
('cliente', 'en', 'Customer', 'label'),
('produto', 'en', 'Product', 'label'),
('quantidade', 'en', 'Quantity', 'label'),
('preco', 'en', 'Price', 'label'),
('total', 'en', 'Total', 'label'),
('iva', 'en', 'VAT', 'label'),

-- French
('factura', 'fr', 'Facture', 'label'),
('numero', 'fr', 'Numéro', 'label'),
('data', 'fr', 'Date', 'label'),
('cliente', 'fr', 'Client', 'label'),
('produto', 'fr', 'Produit', 'label'),
('quantidade', 'fr', 'Quantité', 'label'),
('preco', 'fr', 'Prix', 'label'),
('total', 'fr', 'Total', 'label'),
('iva', 'fr', 'TVA', 'label'),

-- Mandarin Chinese
('factura', 'zh', '发票', 'label'),
('numero', 'zh', '编号', 'label'),
('data', 'zh', '日期', 'label'),
('cliente', 'zh', '客户', 'label'),
('produto', 'zh', '产品', 'label'),
('quantidade', 'zh', '数量', 'label'),
('preco', 'zh', '价格', 'label'),
('total', 'zh', '总计', 'label'),
('iva', 'zh', '增值税', 'label');

-- ========================================================================
-- END OF SCHEMA
-- ========================================================================

COMMIT;
