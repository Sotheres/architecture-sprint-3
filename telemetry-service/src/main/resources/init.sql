CREATE TABLE IF NOT EXISTS telemetry (
    id BIGSERIAL PRIMARY KEY,
    device_id INTEGER NOT NULL,
    current_temperature DOUBLE PRECISION NOT NULL
);
