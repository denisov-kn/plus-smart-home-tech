
CREATE TABLE IF NOT EXISTS payments (
     payment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     state VARCHAR NOT NULL,
     order_id UUID NOT NULL,
     total_payment DECIMAL,
     delivery_total DECIMAL,
     fee_total DECIMAL
);
