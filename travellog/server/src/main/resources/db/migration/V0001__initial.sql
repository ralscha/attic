CREATE TABLE app_user (
    id               BIGINT NOT NULL AUTO_INCREMENT,
    email            VARCHAR(255) NOT NULL,
    email_new        VARCHAR(255) NULL,
    password_hash    VARCHAR(255) NULL,
    authority        VARCHAR(10) NOT NULL,
    enabled          BOOLEAN NOT NULL,
    expired          TIMESTAMP NULL,
    last_access      TIMESTAMP NULL,
    confirmation_token           CHAR(35),
    confirmation_token_created   TIMESTAMP NULL,
    password_reset_token         CHAR(35),
    password_reset_token_created TIMESTAMP NULL,
    CHECK (authority IN ('USER', 'ADMIN')),
    PRIMARY KEY(id),
    UNIQUE(email)
);

CREATE TABLE app_session (
    id            CHAR(35)  NOT NULL,
    app_user_id   BIGINT    NOT NULL,
    last_access   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ip            VARCHAR(39),
    user_agent    VARCHAR(255),
    PRIMARY KEY(id),
    FOREIGN KEY (app_user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE TABLE travel (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NOT NULL, 
    app_user_id   BIGINT       NOT NULL,
    updated       TIMESTAMP    NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (app_user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE TABLE log (
    id            BIGINT         NOT NULL AUTO_INCREMENT,
    created       TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    lat           DECIMAL(10, 8) NOT NULL, 
    lng           DECIMAL(11, 8) NOT NULL,
    location      VARCHAR(255), 
    report        MEDIUMTEXT,
    travel_id     BIGINT         NOT NULL,
    updated       TIMESTAMP      NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (travel_id) REFERENCES travel(id) ON DELETE CASCADE
);

CREATE TABLE log_photo (
    id            BIGINT         NOT NULL AUTO_INCREMENT,
    subject       VARCHAR(255), 
    name          VARCHAR(255)   NOT NULL,
    mime_type     VARCHAR(255)   NOT NULL,
    size          INTEGER        NOT NULL,
    log_id        BIGINT         NOT NULL,
    updated       TIMESTAMP      NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (log_id) REFERENCES log(id) ON DELETE CASCADE
);