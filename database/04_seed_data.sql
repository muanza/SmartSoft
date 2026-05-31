INSERT INTO parceiros (id, codigo, nome, email, telefone, estado)
VALUES ('PARC-0001', 'PARC-0001', 'Parceiro Principal', 'parceiro@smartsoft.local', '+244900000001', 'ACTIVO');

INSERT INTO planos_licenca (id, nome, descricao, limite_utilizadores, limite_facturas_dia, permite_multi_caixa, sincronizacao_cloud)
VALUES
    ('PLANO-BASICO', 'Básico', 'Plano para operação inicial', 5, 1000, FALSE, TRUE),
    ('PLANO-PRO', 'Profissional', 'Plano com multi-caixa e sincronização total', 20, 5000, TRUE, TRUE);

INSERT INTO tenants (nif, nome_empresa, email_empresa, telefone_empresa, idioma_preferido, nome_base_dados, estado, parceiro_id)
VALUES ('500000001', 'Empresa Exemplo', 'empresa@exemplo.local', '+244900000002', 'pt', 'tenant_500000001', 'ACTIVO', 'PARC-0001');

INSERT INTO licencas (id, tenant_nif, parceiro_id, plano_id, chave_activacao, estado, limite_facturas_dia, data_inicio, data_fim)
VALUES ('LIC-0001', '500000001', 'PARC-0001', 'PLANO-PRO', 'CRM-500000001-ATIVA', 'ACTIVA', 5000, CURRENT_DATE, CURRENT_DATE + INTERVAL '365 day');

INSERT INTO utilizadores_crm (id, nome, email, palavra_passe_hash, perfil, activo, parceiro_id)
VALUES
    ('USR-CRM-ADMIN', 'Administrador CRM', 'admin@crm.local', 'admin', 'ADMINISTRADOR', TRUE, NULL),
    ('USR-CRM-PARC', 'Operador Parceiro', 'parceiro@crm.local', 'parceiro', 'PARCEIRO', TRUE, 'PARC-0001');

INSERT INTO api_keys (id, tenant_nif, chave_valor, descricao, activa)
VALUES ('API-0001', '500000001', 'APIKEY-500000001', 'Chave inicial do tenant', TRUE);

INSERT INTO maquinas_licenciadas (id, tenant_nif, serial_hardware, nome_maquina, ip_local, activa)
VALUES ('MAC-0001', '500000001', 'HW-EXEMPLO-001', 'POS-LOJA-1', '192.168.1.10', TRUE);

INSERT INTO banners (id, titulo, conteudo, url_destino, publico_alvo, activo)
VALUES ('BAN-0001', 'Promoção', 'Conheça o plano profissional com multi-caixa.', 'https://example.local/oferta', 'TODOS', TRUE);

INSERT INTO comunicados (id, assunto, mensagem, idioma, publico_alvo)
VALUES ('COM-0001', 'Boas-vindas', 'Bem-vindo à plataforma de faturação multi-tenant.', 'pt', 'TODOS');
