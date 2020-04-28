CREATE TABLE IF NOT EXISTS `filehub`.`oauth_client_details`
(
    `client_id`               VARCHAR(256)  NOT NULL
        PRIMARY KEY,
    `resource_ids`            VARCHAR(256)  NULL,
    `client_secret`           VARCHAR(256)  NULL,
    `scope`                   VARCHAR(256)  NULL,
    `authorized_grant_types`  VARCHAR(256)  NULL,
    `web_server_redirect_uri` VARCHAR(256)  NULL,
    `authorities`             VARCHAR(256)  NULL,
    `access_token_validity`   INT           NULL,
    `refresh_token_validity`  INT           NULL,
    `additional_information`  VARCHAR(4096) NULL,
    `autoapprove`             VARCHAR(256)  NULL
)
    CHARSET = `utf8`;

INSERT INTO `filehub`.`oauth_client_details` (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES ('client', 'resource1', '$2a$10$kKy.SFlyOQcf0xzQ7/sy/OFiHUxcQ9H8QL8dZxafP64tERSwXctuO', 'role_admin0,role_admin,role_vip', 'authorization_code,password,refresh_token,implicit', 'http://localhost:7000/login', null, null, null, null, '');

CREATE TABLE IF NOT EXISTS `filehub`.`oauth_access_token`
(
    `token_id`          VARCHAR(256) NULL,
    `token`             BLOB         NULL,
    `authentication_id` VARCHAR(128) NOT NULL
        PRIMARY KEY,
    `user_name`         VARCHAR(256) NULL,
    `client_id`         VARCHAR(256) NULL,
    `authentication`    BLOB         NULL,
    `refresh_token`     VARCHAR(256) NULL
)
    CHARSET = `utf8`;

CREATE TABLE IF NOT EXISTS `filehub`.`oauth_code`
(
    `creation_time`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `code`           VARCHAR(256)                        NULL,
    `authentication` BLOB                                NULL
)
    CHARSET = `utf8`;

CREATE TABLE IF NOT EXISTS `filehub`.`oauth_refresh_token`
(
    `token_id`       VARCHAR(256) NULL,
    `token`          BLOB         NULL,
    `authentication` BLOB         NULL
)
    CHARSET = `utf8`;

CREATE TABLE IF NOT EXISTS `filehub`.`oauth_approvals`
(
    `userid`         VARCHAR(256) NULL,
    `clientid`       VARCHAR(256) NULL,
    `scope`          VARCHAR(256) NULL,
    `status`         VARCHAR(10)  NULL,
    `expiresat`      TIMESTAMP    NULL,
    `lastmodifiedat` TIMESTAMP    NULL
)
    CHARSET = `utf8`;

CREATE TABLE IF NOT EXISTS `filehub`.`oauth_client_token`
(
    `token_id`          VARCHAR(256)   NULL,
    `token`             VARBINARY(512) NULL,
    `authentication_id` VARCHAR(128)   NOT NULL
        PRIMARY KEY,
    `user_name`         VARCHAR(256)   NULL,
    `client_id`         VARCHAR(256)   NULL
)
    CHARSET = `utf8`;



