CREATE TABLE IF NOT EXISTS orders (
    order_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    shopping_cart_id UUID NOT NULL,
    payment_id UUID,
    delivery_Id UUID,
    state VARCHAR NOT NULL,
    delivery_weight DECIMAL,
    delivery_volume DECIMAL,
    fragile BOOLEAN,
    total_price DECIMAL,
    delivery_price DECIMAL,
    product_price DECIMAL
);