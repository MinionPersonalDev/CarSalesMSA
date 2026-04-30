CREATE TABLE payments
(
    id             BIGINT      NOT NULL AUTO_INCREMENT,
    contract_id    BIGINT      NOT NULL,
    amount         INT         NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    fail_reason    VARCHAR(255) NULL,
    status         VARCHAR(20) NOT NULL,
    created_at     DATETIME    NOT NULL,
    updated_at     DATETIME    NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_payments_contract_id ON payments (contract_id);