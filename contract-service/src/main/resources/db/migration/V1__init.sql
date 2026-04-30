CREATE TABLE contracts
(
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    car_id      BIGINT      NOT NULL,
    customer_id BIGINT      NOT NULL,
    dealer_id   BIGINT      NOT NULL,
    price       INT         NOT NULL,
    status      VARCHAR(20) NOT NULL,
    created_at  DATETIME    NOT NULL,
    updated_at  DATETIME    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE outbox_events
(
    id             BIGINT      NOT NULL AUTO_INCREMENT,
    aggregate_type VARCHAR(50) NOT NULL,
    aggregate_id   VARCHAR(50) NOT NULL,
    event_type     VARCHAR(50) NOT NULL,
    payload        TEXT        NOT NULL,
    status         VARCHAR(20) NOT NULL,
    created_at     DATETIME    NOT NULL,
    published_at   DATETIME    NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_outbox_events_status ON outbox_events (status);