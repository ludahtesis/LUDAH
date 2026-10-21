CREATE TABLE dataset_records (
    id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    object_path VARCHAR(1024),
    status VARCHAR(32) NOT NULL,
    message TEXT,
    file_hash VARCHAR(64),
    partial_match_percentage DOUBLE PRECISION,
    matched_with VARCHAR(1024),
    total_rows INTEGER,
    total_columns INTEGER,
    null_percentage DOUBLE PRECISION,
    duplicate_rows_percentage DOUBLE PRECISION,
    has_duplicate_columns BOOLEAN,
    has_data_rows BOOLEAN,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_dataset_records_object_path ON dataset_records (object_path);
CREATE INDEX idx_dataset_records_status ON dataset_records (status);
CREATE INDEX idx_dataset_records_created_at ON dataset_records (created_at);

CREATE TABLE audit_records (
    id BIGSERIAL PRIMARY KEY,
    audited_prefix VARCHAR(1024) NOT NULL,
    total_files INTEGER NOT NULL,
    raw_files INTEGER NOT NULL,
    review_files INTEGER NOT NULL,
    rejected_files INTEGER NOT NULL,
    ingestion_risk_percentage DOUBLE PRECISION NOT NULL,
    quality_risk_percentage DOUBLE PRECISION NOT NULL,
    exact_duplication_risk_percentage DOUBLE PRECISION NOT NULL,
    redundancy_risk_percentage DOUBLE PRECISION NOT NULL,
    data_swamp_index_percentage DOUBLE PRECISION NOT NULL,
    classification VARCHAR(64) NOT NULL,
    message TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_audit_records_created_at ON audit_records (created_at);
CREATE INDEX idx_audit_records_prefix ON audit_records (audited_prefix);

CREATE TABLE risk_threshold_config (
    id SMALLINT PRIMARY KEY,
    clean_max DOUBLE PRECISION NOT NULL,
    frontier_max DOUBLE PRECISION NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_risk_thresholds
        CHECK (clean_max >= 0 AND clean_max < frontier_max AND frontier_max < 100)
);

INSERT INTO risk_threshold_config (id, clean_max, frontier_max)
VALUES (1, 25.0, 50.0);

