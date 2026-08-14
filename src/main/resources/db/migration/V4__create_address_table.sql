CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    postal_code VARCHAR(255) NOT NULL,
    prefecture_id INTEGER NOT NULL,
    city VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    building VARCHAR(255),
    phone_number VARCHAR(255) NOT NULL,
    order_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_address_orders FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);