
CREATE TABLE account (
  id          INTEGER      NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  password    TEXT         NOT NULL,
  name        TEXT         NOT NULL,
  email       TEXT         NOT NULL UNIQUE,
  created_at  TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE task (
  id          INTEGER      NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  title       TEXT         NOT NULL,
  description TEXT         NOT NULL,
  inputContext  TEXT        NOT NULL,
  outputType  TEXT         NOT NULL,
  status      TEXT         NOT NULL CHECK (status IN ('PENDING', 'RUNNING', 'COMPLETED', 'FAILED')),
  created_by  INTEGER      NOT NULL REFERENCES account(id),
  created_at   TIMESTAMPTZ  NOT NULL,
  updated_at   TIMESTAMPTZ  NOT NULL
);

CREATE TABLE task_step (
  id          INTEGER      NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  task_id     INTEGER      NOT NULL REFERENCES task(id),
  order_index INTEGER      NOT NULL,
  title       TEXT         NOT NULL,
  detail      TEXT         NOT NULL ,
  created_at  TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (task_id, order_index)
);

CREATE TABLE task_execution (
  id               INTEGER      NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  task_id          INTEGER      NOT NULL REFERENCES task(id),
  attempt_no       INTEGER      NOT NULL,
  worker_id        INTEGER,
  status           TEXT         NOT NULL CHECK (status IN ('RUNNING', 'SUCCESS', 'FAILED')),
  started_at       TIMESTAMPTZ  NOT NULL,
  finished_at      TIMESTAMPTZ,
  duration_ms      INTEGER,
  error_code       TEXT         CHECK (error_code IN ('AI_TIMEOUT', 'AI_RATE_LIMIT', 'VALIDATION_ERROR', 'SYSTEM_ERROR')),
  error_message    TEXT,
  input_snapshot   JSONB,
  result_snapshot  JSONB,
  created_at       TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE task_execution_log (
  id           INTEGER      NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  execution_id INTEGER      NOT NULL REFERENCES task_execution(id),
  sequence_no  INTEGER      NOT NULL,
  log_level    TEXT         NOT NULL CHECK (log_level IN ('INFO', 'WARN', 'ERROR', 'DEBUG')),
  message      TEXT         NOT NULL,
  meta         JSONB,
  logged_at    TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP
);
