CREATE TABLE IF NOT EXISTS carts(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR UNIQUE NOT NULL,
    state VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS cart_product(
    cart_id UUID REFERENCES carts(id) ON DELETE CASCADE,
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY (cart_id, product_id)
);
