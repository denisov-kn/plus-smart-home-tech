CREATE TABLE IF NOT EXISTS addresses (
    address_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    country VARCHAR,
    city VARCHAR,
    street VARCHAR,
    house VARCHAR,
    flat VARCHAR
);

CREATE TABLE IF NOT EXISTS deliveries (
    delivery_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    from_address_id UUID NOT NULL,
    to_address_id UUID NOT NULL,
    delivery_state VARCHAR,

    CONSTRAINT fk_delivery_address_from
    FOREIGN KEY (from_address_id)
           REFERENCES addresses(address_id)
           ON DELETE CASCADE,

    CONSTRAINT fk_delivery_address_to
        FOREIGN KEY (to_address_id)
        REFERENCES addresses(address_id)
        ON DELETE CASCADE
);


