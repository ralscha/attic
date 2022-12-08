CREATE TABLE app_user (
    id                BIGINT NOT NULL AUTO_INCREMENT,
    identity_key      CHAR(43) NOT NULL,    
    server_unlock_key CHAR(43) NOT NULL,
	verify_unlock_key CHAR(43) NOT NULL,
    enabled           BOOLEAN NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(identity_key)
);

CREATE TABLE previous_identity_key (
    prev_identity_key  CHAR(43) NOT NULL,
    app_user_id        BIGINT NOT NULL,    
    PRIMARY KEY(app_user_id, prev_identity_key),
    FOREIGN KEY (app_user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

