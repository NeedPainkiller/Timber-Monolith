use `timber-sawmill`;

CREATE TABLE audit_log
(
    `_ID`                 bigint AUTO_INCREMENT                                    NOT NULL,
    TENANT_PK             bigint                                      DEFAULT NULL NULL,
    VISIBLE_YN            int                                         DEFAULT 1    NOT NULL,
    HTTP_STATUS           int                                         DEFAULT 0    NULL,
    HTTP_METHOD           tinyint unsigned                                     DEFAULT 0    NULL,
    AGENT_OS              nvarchar(64)   DEFAULT NULL NULL,
    AGENT_OS_VERSION      nvarchar(16)   DEFAULT NULL NULL,
    AGENT_BROWSER         nvarchar(64)   DEFAULT NULL NULL,
    AGENT_BROWSER_VERSION nvarchar(16)   DEFAULT NULL NULL,
    AGENT_DEVICE          nvarchar(128)  DEFAULT NULL NULL,
    REQUEST_URI           nvarchar(256)  DEFAULT NULL NULL,
    REQUEST_IP            char(45)                              DEFAULT NULL    NULL,
--     REQUEST_IP            numeric(20, 0)                              DEFAULT 0    NULL,
    REQUEST_CONTENT_TYPE  nvarchar(128) DEFAULT NULL NULL,
    REQUEST_PAYLOAD       longtext  DEFAULT NULL NULL,
    RESPONSE_CONTENT_TYPE nvarchar(128)   DEFAULT NULL NULL,
    RESPONSE_PAYLOAD      longtext  DEFAULT NULL NULL,
    CREATED_DATE          DATETIME                                             NOT NULL,
    USER_PK               bigint                                      DEFAULT NULL NULL,
    USER_ID               nvarchar(128)  DEFAULT NULL NULL,
    USER_EMAIL            nvarchar(128)  DEFAULT NULL NULL,
    USER_NAME             nvarchar(64)   DEFAULT NULL NULL,
    TEAM_PK               bigint                                      DEFAULT NULL NULL,
    TEAM_NAME             nvarchar(128)               NULL,
    MENU_UID              bigint                                      DEFAULT NULL NULL,
    MENU_NAME             nvarchar(128)               NULL,
    API_UID               bigint                                      DEFAULT NULL NULL,
    API_NAME              nvarchar(256)  DEFAULT NULL NULL,
    ERROR_DATA            longtext  DEFAULT NULL NULL,
    CONSTRAINT PK_audit_log__ID PRIMARY KEY (`_ID`)
) ENGINE=Archive DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- `timber-audit`.audit_log definition

CREATE TABLE `audit_log` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `TENANT_PK` bigint(20) DEFAULT 0,
                             `VISIBLE_YN` tinyint(4) DEFAULT 1,
                             `HTTP_STATUS` int(11) DEFAULT 0,
                             `HTTP_METHOD` tinyint(4) DEFAULT 0,
                             `AGENT_OS` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `AGENT_OS_VERSION` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `AGENT_BROWSER` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `AGENT_BROWSER_VERSION` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `AGENT_DEVICE` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `REQUEST_URI` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0',
                             `REQUEST_IP` char(45) DEFAULT '0',
                             `REQUEST_CONTENT_TYPE` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0',
                             `REQUEST_PAYLOAD` longtext DEFAULT NULL,
                             `RESPONSE_CONTENT_TYPE` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `RESPONSE_PAYLOAD` longtext DEFAULT NULL,
                             `CREATED_DATE` datetime DEFAULT NULL,
                             `USER_PK` bigint(20) DEFAULT NULL,
                             `USER_ID` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `USER_EMAIL` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `USER_NAME` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `TEAM_PK` bigint(20) DEFAULT NULL,
                             `TEAM_NAME` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `MENU_UID` bigint(20) DEFAULT NULL,
                             `MENU_NAME` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `API_UID` bigint(20) DEFAULT NULL,
                             `API_NAME` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `ERROR_DATA` longtext DEFAULT NULL,
                             `createdAt` datetime NOT NULL,
                             `updatedAt` datetime NOT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=ARCHIVE DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;