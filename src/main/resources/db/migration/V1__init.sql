-- ===========================
-- TABLA: images
-- ===========================
CREATE TABLE images (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) UNIQUE NOT NULL,
    s3_key VARCHAR(255) UNIQUE NOT NULL
);

-- ===========================
-- TABLA: banners
-- ===========================
CREATE TABLE banners (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    image_id INT UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_banner_image FOREIGN KEY (image_id) REFERENCES images (id) ON DELETE CASCADE
);

-- ===========================
-- TABLA: machine_types
-- ===========================
CREATE TABLE machine_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- ===========================
-- TABLA: machines
-- ===========================
CREATE TABLE machines (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    type_id INT NOT NULL,
    description TEXT NOT NULL,
    image_id INT UNIQUE NOT NULL,
    is_featured BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_machine_type FOREIGN KEY (type_id) REFERENCES machine_types (id) ON DELETE CASCADE,
    CONSTRAINT fk_machine_image FOREIGN KEY (image_id) REFERENCES images (id) ON DELETE CASCADE
);

-- ===========================
-- TABLA: product_types
-- ===========================
CREATE TABLE product_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- ===========================
-- TABLA: products
-- ===========================
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    type_id INT NOT NULL,
    capacity DOUBLE PRECISION NOT NULL,
    description TEXT NOT NULL,
    image_id INT UNIQUE NOT NULL,
    is_featured BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_product_type FOREIGN KEY (type_id) REFERENCES product_types (id) ON DELETE CASCADE,
    CONSTRAINT fk_product_image FOREIGN KEY (image_id) REFERENCES images (id) ON DELETE CASCADE
);

-- ===========================
-- TABLA: service_item_types
-- ===========================
CREATE TABLE service_item_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- ===========================
-- TABLA: service_items
-- ===========================
CREATE TABLE service_items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    type_id INT NOT NULL,
    description TEXT NOT NULL,
    image_id INT UNIQUE NOT NULL,
    is_featured BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_service_type FOREIGN KEY (type_id) REFERENCES service_item_types (id) ON DELETE CASCADE,
    CONSTRAINT fk_service_image FOREIGN KEY (image_id) REFERENCES images (id) ON DELETE CASCADE
);

-- ===========================
-- TABLA: social_media
-- ===========================
CREATE TABLE social_media (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255),
    type VARCHAR(50) UNIQUE NOT NULL CHECK (type IN ('FACEBOOK', 'TIKTOK', 'EMAIL', 'WHATSAPP'))
);
