CREATE SCHEMA `db_keycloak_certificates` DEFAULT CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS `db_keycloak_certificates`.`gift_certificate`
(
    `id`               BIGINT        NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(255)  NOT NULL,
    `description`      VARCHAR(255)  NOT NULL,
    `price`            DECIMAL(6, 2) NOT NULL,
    `duration`         INT           NOT NULL,
    `is_available`     INT           not null,
    `create_date`      timestamp,
    `last_update_date` timestamp,
    primary key (id)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `db_keycloak_certificates`.`tag`
(
    `id`   BIGINT              NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) unique NOT NULL,
    primary key (id)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `db_keycloak_certificates`.`gift_certificate_tag`
(
    `gift_certificate_id` BIGINT,
    `tag_id`              BIGINT,
    primary key (`gift_certificate_id`, `tag_id`),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
) ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `db_keycloak_certificates`.`account`
(
    `id`   BIGINT              NOT NULL AUTO_INCREMENT,
    `uuid` VARCHAR(255) unique not null,
    primary key (id)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `db_keycloak_certificates`.`orders`
(
    `id`             BIGINT        NOT NULL AUTO_INCREMENT,
    `date`           timestamp     NOT NULL,
    `order_cost`     DECIMAL(6, 2) NOT NULL,
    `account_id`     BIGINT,
    `certificate_id` BIGINT,
    primary key (id),
    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (certificate_id) references gift_certificate (id)
)
    ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `db_keycloak_certificates`.`audit`
(
    `id`        BIGINT    NOT NULL auto_increment,
    `operation` varchar(50),
    `date`      timestamp NOT NULL,
    `entity`    varchar(500),
    primary key (id)
)
    ENGINE = InnoDB;