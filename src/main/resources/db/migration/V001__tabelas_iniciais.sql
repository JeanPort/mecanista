
-- Migration: 001_create_schema_chamados
-- Descricao: Estrutura inicial para controle de chamados de manutencao
-- Banco: MySQL 8+

START TRANSACTION;

-- =========================================================
-- Tabela: usuario
-- =========================================================
CREATE TABLE usuario (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(150)        NOT NULL,
    email           VARCHAR(150)        NOT NULL,
    senha_hash      VARCHAR(255)        NOT NULL,
    ativo           TINYINT(1)          NOT NULL DEFAULT 1,
    criado_em       DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP
                                         ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_usuario_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- Tabela: papel
-- Catalogo fixo de papeis do sistema (mecanico, gerente, solicitante)
-- =========================================================
CREATE TABLE papel (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    codigo          VARCHAR(30)         NOT NULL,
    descricao       VARCHAR(100)        NOT NULL,
    CONSTRAINT uq_papel_codigo UNIQUE (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO papel (codigo, descricao) VALUES
    ('mecanico',    'Responsavel por executar o reparo'),
    ('gerente',     'Responsavel por analisar, atribuir e aprovar chamados'),
    ('solicitante', 'Responsavel por abrir chamados de reparo');

-- =========================================================
-- Tabela: usuario_papel
-- Relacionamento N:N entre usuario e papel
-- =========================================================
CREATE TABLE usuario_papel (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    usuario_id      BIGINT UNSIGNED     NOT NULL,
    papel_id        BIGINT UNSIGNED     NOT NULL,
    criado_em       DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_usuario_papel UNIQUE (usuario_id, papel_id),
    CONSTRAINT fk_usuario_papel_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_usuario_papel_papel
        FOREIGN KEY (papel_id) REFERENCES papel (id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- Tabela: maquina
-- =========================================================
CREATE TABLE maquina (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    codigo          VARCHAR(50)         NOT NULL,
    nome            VARCHAR(150)        NOT NULL,
    localizacao     VARCHAR(150)        NULL,
    setor           VARCHAR(100)        NULL,
    ativa           TINYINT(1)          NOT NULL DEFAULT 1,
    criado_em       DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP
                                         ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_maquina_codigo UNIQUE (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- Tabela: chamado
-- =========================================================
CREATE TABLE chamado (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    maquina_id      BIGINT UNSIGNED     NOT NULL,
    solicitante_id  BIGINT UNSIGNED     NOT NULL,
    mecanico_id     BIGINT UNSIGNED     NULL,
    status          ENUM (
                        'ABERTO',
                        'EM_ANALISE',
                        'EM_EXECUCAO',
                        'CONCLUIDO',
                        'CANCELADO'
                    )                   NOT NULL DEFAULT 'ABERTO',
    prioridade      ENUM ('BAIXA', 'MEDIA', 'ALTA', 'URGENTE')
                                        NOT NULL DEFAULT 'MEDIA',
    descricao       TEXT                NOT NULL,
    criado_em       DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP
                                         ON UPDATE CURRENT_TIMESTAMP,
    concluido_em    DATETIME            NULL,
    CONSTRAINT fk_chamado_maquina
        FOREIGN KEY (maquina_id) REFERENCES maquina (id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_chamado_solicitante
        FOREIGN KEY (solicitante_id) REFERENCES usuario (id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_chamado_mecanico
        FOREIGN KEY (mecanico_id) REFERENCES usuario (id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_chamado_status (status),
    INDEX idx_chamado_maquina (maquina_id),
    INDEX idx_chamado_mecanico (mecanico_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- Tabela: chamado_historico
-- Auditoria de cada transicao de status do chamado
-- =========================================================
CREATE TABLE chamado_historico (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    chamado_id      BIGINT UNSIGNED     NOT NULL,
    usuario_id      BIGINT UNSIGNED     NOT NULL,
    status_anterior ENUM (
                        'ABERTO',
                        'EM_ANALISE',
                        'EM_EXECUCAO',
                        'CONCLUIDO',
                        'CANCELADO'
                    )                   NULL,
    status_novo     ENUM (
                        'ABERTO',
                        'EM_ANALISE',
                        'EM_EXECUCAO',
                        'CONCLUIDO',
                        'CANCELADO'
                    )                   NOT NULL,
    observacao      TEXT                NULL,
    criado_em       DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_chamado_historico_chamado
        FOREIGN KEY (chamado_id) REFERENCES chamado (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_chamado_historico_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario (id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_chamado_historico_chamado (chamado_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

COMMIT;