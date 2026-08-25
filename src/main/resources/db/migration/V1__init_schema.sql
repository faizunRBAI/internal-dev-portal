-- V1: Initial schema for Internal Developer Portal

CREATE TABLE app_user (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(100) NOT NULL UNIQUE,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(50)  NOT NULL DEFAULT 'ROLE_USER',
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE team (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE project (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    repository  VARCHAR(500),
    team_id     BIGINT REFERENCES team(id) ON DELETE SET NULL,
    status      VARCHAR(50)  NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE environment (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    type        VARCHAR(50)  NOT NULL DEFAULT 'DEV',
    base_url    VARCHAR(500),
    project_id  BIGINT       REFERENCES project(id) ON DELETE CASCADE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    UNIQUE (name, project_id)
);

CREATE TABLE deployment (
    id              BIGSERIAL PRIMARY KEY,
    version         VARCHAR(100) NOT NULL,
    status          VARCHAR(50)  NOT NULL DEFAULT 'PENDING',
    commit_sha      VARCHAR(40),
    notes           TEXT,
    project_id      BIGINT REFERENCES project(id) ON DELETE CASCADE,
    environment_id  BIGINT REFERENCES environment(id) ON DELETE CASCADE,
    deployed_by     BIGINT REFERENCES app_user(id) ON DELETE SET NULL,
    deployed_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_at      TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_project_team   ON project(team_id);
CREATE INDEX idx_env_project    ON environment(project_id);
CREATE INDEX idx_deploy_project ON deployment(project_id);
CREATE INDEX idx_deploy_env     ON deployment(environment_id);
CREATE INDEX idx_deploy_status  ON deployment(status);
