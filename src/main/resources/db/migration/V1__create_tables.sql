CREATE TABLE IF NOT EXISTS payments (
    id             INTEGER          NOT NULL,
    source         VARCHAR(3),
    target         VARCHAR(3),
    source_amount  DECIMAL(20, 2),
    target_amount  DECIMAL(20, 2),
    rate           DECIMAL(20, 2),
    fee            DECIMAL(20, 2),

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id              UUID            NOT NULL,
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    payout_currency VARCHAR(3),

    PRIMARY KEY (id)
);
