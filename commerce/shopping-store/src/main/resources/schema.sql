CREATE TABLE IF NOT EXISTS products(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    image_src VARCHAR NOT NULL,
    quantity_state VARCHAR NOT NULL,
    product_state VARCHAR NOT NULL,
    product_category VARCHAR NOT NULL,
    price DECIMAL NOT NULL
);
