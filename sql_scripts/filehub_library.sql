CREATE DATABASE IF NOT EXISTS `filehub`;

CREATE TABLE IF NOT EXISTS `filehub`.`library`
(
    `library_id`               INT AUTO_INCREMENT
        PRIMARY KEY,
    `library_name`             VARCHAR(32)          NOT NULL COMMENT '可重复',
    `library_desc`             VARCHAR(256)         NULL,
    `owner_uid`                INT                  NOT NULL,
    `followers_count`          INT        DEFAULT 0 NOT NULL,
    `library_url`              VARCHAR(128)         NOT NULL,
    `privacy_type`             TINYINT(1) DEFAULT 0 NOT NULL COMMENT 'is_private',
    `library_creation_time`    DATETIME             NOT NULL,
    `library_creation_uid`     INT                  NOT NULL,
    `library_last_update_time` DATETIME             NOT NULL,
    `library_last_update_uid`  INT                  NOT NULL
)
    COMMENT '文档库';

INSERT INTO `filehub`.`library` (`library_id`, `library_name`, `library_desc`, `owner_uid`, `followers_count`,
                                 `library_url`, `privacy_type`, `library_creation_time`, `library_creation_uid`,
                                 `library_last_update_time`, `library_last_update_uid`)
VALUES (1, 'tiny_spring', 'Tiny Spring', 2, 10, '/sammy/tiny_spring', 0, '2018-09-15 16:47:33', 2,
        '2019-04-15 16:47:37', 2);
INSERT INTO `filehub`.`library` (`library_id`, `library_name`, `library_desc`, `owner_uid`, `followers_count`,
                                 `library_url`, `privacy_type`, `library_creation_time`, `library_creation_uid`,
                                 `library_last_update_time`, `library_last_update_uid`)
VALUES (2, 'leetcode-animations', 'Leetcode in Animations', 3, 899, '/123456/leetcode_animations', 0,
        '2016-10-15 16:49:13', 3, '2020-02-15 05:49:19', 3);
INSERT INTO `filehub`.`library` (`library_id`, `library_name`, `library_desc`, `owner_uid`, `followers_count`,
                                 `library_url`, `privacy_type`, `library_creation_time`, `library_creation_uid`,
                                 `library_last_update_time`, `library_last_update_uid`)
VALUES (3, 'test123', 'Test123', 2, 0, '/sammy/test123', 1, '2020-02-21 16:51:15', 2, '2020-03-23 11:51:31', 2);

CREATE TABLE IF NOT EXISTS `filehub`.`file`
(
    `file_id`               INT AUTO_INCREMENT
        PRIMARY KEY,
    `file_real_name`        VARCHAR(128)         NOT NULL,
    `file_display_name`     VARCHAR(32)          NOT NULL,
    `file_desc`             VARCHAR(256)         NULL,
    `download_count`        INT        DEFAULT 0 NOT NULL,
    `file_url`              VARCHAR(128)         NOT NULL,
    `privacy_type`          TINYINT(1) DEFAULT 0 NOT NULL COMMENT 'is_private',
    `file_type`             VARCHAR(16)          NOT NULL,
    `storage_type`          TINYINT(1)           NOT NULL,
    `sync_type`             TINYINT(1)           NOT NULL,
    `file_size`             INT                  NULL COMMENT '文件大小（字节）',
    `file_creation_time`    DATETIME             NOT NULL,
    `file_creation_uid`     INT                  NOT NULL,
    `file_last_update_time` DATETIME             NOT NULL,
    `file_last_update_uid`  INT                  NOT NULL
)
    COMMENT '文件';

INSERT INTO `filehub`.`file` (`file_id`, `file_real_name`, `file_display_name`, `file_desc`, `download_count`,
                              `file_url`, `privacy_type`, `file_type`, `storage_type`, `sync_type`, `file_size`,
                              `file_creation_time`, `file_creation_uid`, `file_last_update_time`,
                              `file_last_update_uid`)
VALUES (13, 'c7c0fa77-f569-4b9c-a1be-417e38fe99d4_gone with the wind.txt', 'Gone with the Wind.txt', '', 6,
        '/file/c7c0fa77-f569-4b9c-a1be-417e38fe99d4_gone with the wind.txt', 0, 'text', 1, 1, NULL,
        '2020-04-16 08:14:32', 3, '2020-04-16 08:14:32', 3);
INSERT INTO `filehub`.`file` (`file_id`, `file_real_name`, `file_display_name`, `file_desc`, `download_count`,
                              `file_url`, `privacy_type`, `file_type`, `storage_type`, `sync_type`, `file_size`,
                              `file_creation_time`, `file_creation_uid`, `file_last_update_time`,
                              `file_last_update_uid`)
VALUES (14, '7ac806e5-b730-4ed2-9cf8-a820df41559c_pom.xml', 'pom.xml', '', 6,
        '/file/7ac806e5-b730-4ed2-9cf8-a820df41559c_pom.xml', 0, 'xml', 1, 1, NULL, '2020-04-16 08:15:23', 2,
        '2020-04-16 08:15:23', 2);
INSERT INTO `filehub`.`file` (`file_id`, `file_real_name`, `file_display_name`, `file_desc`, `download_count`,
                              `file_url`, `privacy_type`, `file_type`, `storage_type`, `sync_type`, `file_size`,
                              `file_creation_time`, `file_creation_uid`, `file_last_update_time`,
                              `file_last_update_uid`)
VALUES (15, '0fa5c91c-1bcf-4e91-9bc5-20d82257bb59_init.lua', 'init.lua', '', 6,
        '/file/0fa5c91c-1bcf-4e91-9bc5-20d82257bb59_init.lua', 0, 'lua', 1, 1, NULL, '2020-04-16 08:15:31', 2,
        '2020-04-16 08:15:31', 2);
INSERT INTO `filehub`.`file` (`file_id`, `file_real_name`, `file_display_name`, `file_desc`, `download_count`,
                              `file_url`, `privacy_type`, `file_type`, `storage_type`, `sync_type`, `file_size`,
                              `file_creation_time`, `file_creation_uid`, `file_last_update_time`,
                              `file_last_update_uid`)
VALUES (16, 'a35ec799-ccf9-41ff-9bed-47c0796344f5_gone with the wind.txt', 'application.yml', '', 9,
        '/file/a35ec799-ccf9-41ff-9bed-47c0796344f5_gone with the wind.txt', 0, 'yaml', 1, 1, NULL,
        '2020-04-30 09:28:42', 3, '2020-04-30 09:28:42', 3);
INSERT INTO `filehub`.`file` (`file_id`, `file_real_name`, `file_display_name`, `file_desc`, `download_count`,
                              `file_url`, `privacy_type`, `file_type`, `storage_type`, `sync_type`, `file_size`,
                              `file_creation_time`, `file_creation_uid`, `file_last_update_time`,
                              `file_last_update_uid`)
VALUES (17, 'f007d1ea-fd05-40b8-92f7-d0e49f42e0c7_pom.xml', '__init__.py', '', 8,
        '/file/f007d1ea-fd05-40b8-92f7-d0e49f42e0c7_pom.xml', 0, 'python', 1, 1, NULL, '2020-04-30 09:28:42', 2,
        '2020-04-30 09:28:42', 2);
INSERT INTO `filehub`.`file` (`file_id`, `file_real_name`, `file_display_name`, `file_desc`, `download_count`,
                              `file_url`, `privacy_type`, `file_type`, `storage_type`, `sync_type`, `file_size`,
                              `file_creation_time`, `file_creation_uid`, `file_last_update_time`,
                              `file_last_update_uid`)
VALUES (18, 'e4714001-661d-4da8-9fd4-809435851b53_init.lua', 'main.js', '', 2,
        '/file/e4714001-661d-4da8-9fd4-809435851b53_init.lua', 0, 'javascript', 1, 1, NULL, '2020-04-30 09:28:42', 2,
        '2020-04-30 09:28:42', 2);

CREATE TABLE IF NOT EXISTS `filehub`.`library_collaborator`
(
    `id`         INT AUTO_INCREMENT
        PRIMARY KEY,
    `library_id` INT NOT NULL,
    `uid`        INT NOT NULL,
    CONSTRAINT `library_collaborator_library_id_uindex`
        UNIQUE (`library_id`)
)
    COMMENT '文档库协作者关联表';

CREATE INDEX `library_collaborator_uid_index`
    ON `library_collaborator` (`uid`);

CREATE TABLE IF NOT EXISTS `filehub`.`library_file`
(
    `id`         INT AUTO_INCREMENT
        PRIMARY KEY,
    `library_id` INT NOT NULL,
    `file_id`    INT NOT NULL,
    CONSTRAINT `library_file_file_id_uindex`
        UNIQUE (`file_id`)
)
    COMMENT '文档库文件关联表';

INSERT INTO `filehub`.`library_file` (`id`, `library_id`, `file_id`)
VALUES (1, 3, 13);
INSERT INTO `filehub`.`library_file` (`id`, `library_id`, `file_id`)
VALUES (2, 2, 14);
INSERT INTO `filehub`.`library_file` (id, library_id, file_id) VALUES (3, 3, 15);
INSERT INTO filehub.library_file (id, library_id, file_id) VALUES (4, 3, 16);
INSERT INTO filehub.library_file (id, library_id, file_id) VALUES (5, 2, 17);
INSERT INTO filehub.library_file (id, library_id, file_id) VALUES (6, 3, 18);