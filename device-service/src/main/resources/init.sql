CREATE TABLE IF NOT EXISTS heating_systems (
    id BIGSERIAL PRIMARY KEY,
    is_on BOOLEAN NOT NULL,
    target_temperature DOUBLE PRECISION NOT NULL,
    current_temperature DOUBLE PRECISION NOT NULL,
    device_module_id bigint
);

CREATE TABLE IF NOT EXISTS device_modules (
    id BIGSERIAL PRIMARY KEY,
    name text NOT NULL,
    description text,
    serial_number text
);
