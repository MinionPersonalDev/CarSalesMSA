CREATE TABLE inventories
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    car_id      BIGINT       NOT NULL,
    car_name    VARCHAR(100) NOT NULL,
    model_year  INT          NOT NULL,
    price       INT          NOT NULL,
    stock       INT          NOT NULL,
    status      VARCHAR(20)  NOT NULL,
    updated_at  DATETIME     NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_inventories_car_id (car_id)
);