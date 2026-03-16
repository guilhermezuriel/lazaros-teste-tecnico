CREATE TABLE IF NOT EXISTS profiles
(
    id        UUID         NOT NULL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    CONSTRAINT chk_descricao_min_length CHECK (LENGTH(TRIM(descricao)) >= 5)
);

CREATE TABLE IF NOT EXISTS users
(
    id   UUID         NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    CONSTRAINT chk_nome_min_length CHECK (LENGTH(TRIM(nome)) >= 10)
);

CREATE TABLE IF NOT EXISTS user_profiles
(
    user_id    UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    profile_id UUID NOT NULL REFERENCES profiles (id) ON DELETE RESTRICT,
    PRIMARY KEY (user_id, profile_id)
);
