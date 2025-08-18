CREATE TABLE IF NOT EXISTS warehouse_product (
product_id UUID PRIMARY KEY,
fragile BOOLEAN NOT NULL,
width DOUBLE PRECISION NOT NULL,
height DOUBLE PRECISION NOT NULL,
depth DOUBLE PRECISION NOT NULL,
weight DOUBLE PRECISION NOT NULL
);


CREATE TABLE IF NOT EXISTS warehouse_inventory (
   product_id UUID PRIMARY KEY,
   quantity INTEGER NOT NULL,
   FOREIGN KEY (product_id) REFERENCES warehouse_product(product_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_booking (
    order_id UUID PRIMARY KEY,
    delivery_id UUID
);