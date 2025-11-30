-- ===========================
-- TABLA: items
-- ===========================
CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    reference_id INT NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('PRODUCT', 'MACHINE')),
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- ===========================
-- TABLA: inventory_balances
-- ===========================
CREATE TABLE inventory_balances (
    id SERIAL PRIMARY KEY,
    item_id INT NOT NULL,
    current_quantity NUMERIC(19,4) NOT NULL DEFAULT 0,
    last_updated TIMESTAMP NOT NULL,
    CONSTRAINT fk_balance_item FOREIGN KEY (item_id) REFERENCES items(id)
);

-- ===========================
-- TABLA: inventory_movements
-- ===========================
CREATE TABLE inventory_movements (
    id SERIAL PRIMARY KEY,
    item_id INT NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('IN', 'OUT')),
    quantity NUMERIC(19,4) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_movement_item FOREIGN KEY (item_id) REFERENCES items(id)
);
