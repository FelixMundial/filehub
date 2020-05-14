CREATE DATABASE IF NOT EXISTS `filehub`;

CREATE TABLE `filehub`.`user`
(
    `uid`              INT AUTO_INCREMENT
        PRIMARY KEY,
    `login_name`       VARCHAR(32)          NOT NULL COMMENT '不可重复',
    `login_password`   VARCHAR(128)         NOT NULL,
    `nickname`         VARCHAR(32)          NULL,
    `creation_time`    DATETIME             NOT NULL,
    `last_update_time` DATETIME             NOT NULL,
    `active_state`     TINYINT(1) DEFAULT 1 NOT NULL COMMENT '1-可用；0-注销',
    CONSTRAINT `user_login_name_uindex`
        UNIQUE (`login_name`)
)
    COMMENT '用户账户信息';

INSERT INTO `filehub`.`user` (`uid`, `login_name`, `login_password`, `nickname`, `creation_time`, `last_update_time`,
                              `active_state`)
VALUES (1, 'root', '$2a$10$2wI5l21ByPwN0rP3V05IhOOqnvyTQE5p4FD9/QY.i0.MehC10DHDm', 'Admin', '2020-04-14 18:30:54',
        '2020-04-14 18:30:56', 1);
INSERT INTO `filehub`.`user` (`uid`, `login_name`, `login_password`, `nickname`, `creation_time`, `last_update_time`,
                              `active_state`)
VALUES (2, 'admin001', '$2a$10$kPfrfEQTAIG1VpuFJ75.1uUqpSIXFoTQodu/CoIq0On7FQZxMZSoO', 'Admin001',
        '2020-04-29 10:24:46', '2020-04-29 10:24:46', 1);
INSERT INTO `filehub`.`user` (`uid`, `login_name`, `login_password`, `nickname`, `creation_time`, `last_update_time`,
                              `active_state`)
VALUES (3, 'guest', '$2a$10$sd.cL4PoWMPvka4HuTozc.FrgQmjQffsX3qqUY4LMjenkTnpmIbyC', 'Guest', '2020-04-14 13:28:27',
        '2020-04-14 13:28:27', 1);
INSERT INTO `filehub`.`user` (`uid`, `login_name`, `login_password`, `nickname`, `creation_time`, `last_update_time`,
                              `active_state`)
VALUES (50, 'sammy@example.com', '$2a$10$Rqba81gOvtLYcWm/z5nIquR3z.ZCMGHdFL.D8WeByMvJ2qY0JS0/q', 'Sammy',
        '2021-10-14 18:31:07', '2020-11-14 18:31:16', 1);
INSERT INTO `filehub`.`user` (`uid`, `login_name`, `login_password`, `nickname`, `creation_time`, `last_update_time`,
                              `active_state`)
VALUES (100, 'test', '$2a$10$4OVlg9vwQ1SFyp24ebZSGOLXAwalScv9XmlAqoy3PpoWc/RdGGumi', NULL, '2020-04-29 09:54:28',
        '2020-04-29 09:54:28', 1);
INSERT INTO `filehub`.`user` (`uid`, `login_name`, `login_password`, `nickname`, `creation_time`, `last_update_time`,
                              `active_state`)
VALUES (101, '123456789@qq.com', '$2a$10$3EBvqcfyHBaBh9NEJXbo6eGocWY0KQh128mq.IAd2Y4z/195RQNhC', NULL,
        '2020-04-29 10:53:36', '2020-04-29 10:53:36', 1);
INSERT INTO `filehub`.`user` (`uid`, `login_name`, `login_password`, `nickname`, `creation_time`, `last_update_time`,
                              `active_state`)
VALUES (105, 'chris_avalon', '$2a$10$6r7c3exjtFT/ULxUU04Qo.HC1RkGOreZvDldddlEYPywDyREbK7Iq', NULL,
        '2020-05-13 16:06:05', '2020-05-13 16:06:05', 1);

CREATE TABLE IF NOT EXISTS `filehub`.`role`
(
    `id`               BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    `name`             VARCHAR(64)  NOT NULL COMMENT '角色名称',
    `code`             VARCHAR(64)  NULL COMMENT '角色编码',
    `notes`            VARCHAR(255) NULL COMMENT '备注',
    `creation_time`    DATETIME     NOT NULL,
    `last_update_time` DATETIME     NULL
)
    COMMENT '角色';

INSERT INTO `filehub`.`role` (`id`, `name`, `code`, `notes`, `creation_time`, `last_update_time`)
VALUES (1, '系统管理员', 'role_admin', '', '2019-11-29 01:11:16', '2020-04-23 19:40:32');
INSERT INTO `filehub`.`role` (`id`, `name`, `code`, `notes`, `creation_time`, `last_update_time`)
VALUES (2, '普通用户', 'role_user', NULL, '2019-12-05 23:31:12', '2020-04-29 12:23:40');
INSERT INTO `filehub`.`role` (`id`, `name`, `code`, `notes`, `creation_time`, `last_update_time`)
VALUES (3, '游客', 'role_guest', '', '2020-01-29 12:21:54', '2020-02-25 05:26:49');
INSERT INTO `filehub`.`role` (`id`, `name`, `code`, `notes`, `creation_time`, `last_update_time`)
VALUES (5, '文库所有者', 'role_library_owner', NULL, '2020-04-29 12:26:31', '2020-04-29 12:26:32');
INSERT INTO `filehub`.`role` (`id`, `name`, `code`, `notes`, `creation_time`, `last_update_time`)
VALUES (6, '文库协作者', 'role_library_collaborator', NULL, '2020-04-29 12:26:53', '2020-04-29 12:26:54');
INSERT INTO `filehub`.`role` (`id`, `name`, `code`, `notes`, `creation_time`, `last_update_time`)
VALUES (7, '文库参与者', 'role_library_follower', NULL, '2020-04-29 12:27:47', '2020-04-29 12:27:48');
INSERT INTO `filehub`.`role` (`id`, `name`, `code`, `notes`, `creation_time`, `last_update_time`)
VALUES (8, '[测试]', 'role_library_test', NULL, '2020-05-01 09:54:28', NULL);

CREATE TABLE IF NOT EXISTS `filehub`.`permission`
(
    `id`               BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    `name`             VARCHAR(64)  NOT NULL COMMENT '权限名称',
    `code`             VARCHAR(64)  NULL COMMENT '权限编码',
    `resource_url`     VARCHAR(255) NOT NULL COMMENT '资源路径',
    `resource_name`    VARCHAR(64)  NULL COMMENT '资源名称',
    `notes`            VARCHAR(255) NULL COMMENT '备注',
    `creation_time`    DATETIME     NOT NULL,
    `last_update_time` DATETIME     NULL
)
    COMMENT '权限';

INSERT INTO `filehub`.`permission` (`id`, `name`, `code`, `resource_url`, `resource_name`, `notes`, `creation_time`,
                                    `last_update_time`)
VALUES (1, '删除评论', 'disable_comments', '/library/comments/disable', NULL, NULL, '2020-04-30 09:49:57', NULL);

CREATE TABLE IF NOT EXISTS `filehub`.`user_role`
(
    `id`      BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户id',
    `role_id` BIGINT NOT NULL COMMENT '角色id'
)
    COMMENT '用户对应的角色';

CREATE TABLE IF NOT EXISTS `filehub`.`user_role`
(
    `id`      BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户id',
    `role_id` BIGINT NOT NULL COMMENT '角色id'
)
    COMMENT '用户对应的角色（约定：一个用户只能拥有一个角色）';

INSERT INTO `filehub`.`user_role` (`id`, `user_id`, `role_id`)
VALUES (1, 1, 1);
INSERT INTO `filehub`.`user_role` (`id`, `user_id`, `role_id`)
VALUES (2, 50, 2);
INSERT INTO `filehub`.`user_role` (`id`, `user_id`, `role_id`)
VALUES (3, 3, 3);
INSERT INTO `filehub`.`user_role` (`id`, `user_id`, `role_id`)
VALUES (4, 100, 8);
INSERT INTO `filehub`.`user_role` (`id`, `user_id`, `role_id`)
VALUES (5, 2, 1);
INSERT INTO `filehub`.`user_role` (`id`, `user_id`, `role_id`)
VALUES (6, 101, 2);
INSERT INTO `filehub`.`user_role` (`id`, `user_id`, `role_id`)
VALUES (7, 105, 2);

CREATE TABLE IF NOT EXISTS `filehub`.`role_permission`
(
    `id`            BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    `role_id`       BIGINT NOT NULL COMMENT '角色id',
    `permission_id` BIGINT NOT NULL COMMENT '权限id'
)
    COMMENT '角色对应的权限（约定：一个角色可拥有多个权限）';

INSERT INTO `filehub`.`role_permission` (`id`, `role_id`, `permission_id`)
VALUES (1, 5, 1);
INSERT INTO `filehub`.`role_permission` (`id`, `role_id`, `permission_id`)
VALUES (2, 6, 1);
INSERT INTO `filehub`.`role_permission` (`id`, `role_id`, `permission_id`)
VALUES (3, 1, 1);