CREATE TABLE IF NOT EXISTS addresses (
                                         address_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                         country VARCHAR,
                                         city VARCHAR,
                                         street VARCHAR,
                                         house VARCHAR,
                                         flat VARCHAR
);


CREATE TABLE IF NOT EXISTS orders (
    order_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    shopping_cart_id UUID NOT NULL,
    payment_id UUID,
    delivery_Id UUID,
    address_id UUID,
    username VARCHAR NOT NULL,
    state VARCHAR NOT NULL,
    delivery_weight DECIMAL,
    delivery_volume DECIMAL,
    fragile BOOLEAN,
    total_price DECIMAL,
    delivery_price DECIMAL,
    product_price DECIMAL,
    FOREIGN KEY (address_id) REFERENCES addresses(address_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS order_products (
  order_id   UUID NOT NULL,
  product_id UUID NOT NULL,
  quantity   INT NOT NULL,
  PRIMARY KEY (order_id, product_id),
  FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
);