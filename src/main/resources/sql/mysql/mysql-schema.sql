
CREATE TABLE IF NOT EXISTS TENANT
(
    `_ID`        bigint AUTO_INCREMENT NOT NULL,
    USE_YN       tinyint unsigned DEFAULT 0 NOT NULL,
    DEFAULT_YN   tinyint unsigned DEFAULT 0 NOT NULL,
    VISIBLE_YN   tinyint unsigned DEFAULT 0 NOT NULL,
    TITLE        nvarchar(256) NOT NULL,
    LABEL        nvarchar(256) NOT NULL,
    URL_PATH     nvarchar(256) NOT NULL,
    CREATED_BY   bigint DEFAULT 0 NOT NULL,
    CREATED_DATE DATETIME         NOT NULL,
    UPDATED_BY   bigint DEFAULT 0 NOT NULL,
    UPDATED_DATE DATETIME         NOT NULL,
    CONSTRAINT PK_tenant__ID PRIMARY KEY (`_ID`)
    );

CREATE TABLE IF NOT EXISTS ACCOUNT_USER
(
    `_ID`            bigint AUTO_INCREMENT NOT NULL,
    TENANT_PK        bigint   DEFAULT NULL NULL,
    USE_YN           tinyint unsigned DEFAULT 0 NOT NULL,
    USER_ID          nvarchar(256) NOT NULL,
    USER_EMAIL       nvarchar(256) DEFAULT NULL NULL,
    USER_NAME        nvarchar(256) NOT NULL,
    USER_PWD         nvarchar(1024) NOT NULL,
    USER_STATUS      int unsigned DEFAULT 0 NOT NULL,
    TEAM_PK          bigint   DEFAULT 0 NOT NULL,
    CREATED_BY       bigint   DEFAULT 0 NOT NULL,
    CREATED_DATE     DATETIME           NOT NULL,
    UPDATED_BY       bigint   DEFAULT NULL NULL,
    UPDATED_DATE     DATETIME DEFAULT NULL NULL,
    LOGIN_FAILED_CNT int unsigned  DEFAULT 0 NOT NULL,
    LAST_LOGIN_DATE  DATETIME DEFAULT NULL NULL,
    EXTRA_DATA       longtext DEFAULT NULL NULL,
    CONSTRAINT PK_account___ID PRIMARY KEY (`_ID`),
    CONSTRAINT account_user_UN UNIQUE (TENANT_PK, USER_ID)
    );


CREATE TABLE IF NOT EXISTS ACCOUNT_ROLE
(
    `_ID`            bigint AUTO_INCREMENT NOT NULL,
    TENANT_PK        bigint DEFAULT NULL NULL,
    USE_YN           tinyint unsigned DEFAULT 0 NOT NULL,
    IS_SYSTEM_ADMIN  tinyint unsigned DEFAULT 0 NOT NULL,
    IS_ADMIN         tinyint unsigned DEFAULT 0 NOT NULL,
    IS_EDITABLE      tinyint unsigned DEFAULT 1 NOT NULL,
    ROLE_NAME        nvarchar(256) NOT NULL,
    ROLE_DESCRIPTION nvarchar(1024) DEFAULT NULL NULL,
    CREATED_BY       int      NOT NULL,
    CREATED_DATE     DATETIME NOT NULL,
    UPDATED_BY       int      NOT NULL,
    UPDATED_DATE     DATETIME NOT NULL,
    CONSTRAINT PK_account_role__ID PRIMARY KEY (`_ID`)
    );

CREATE TABLE IF NOT EXISTS ACCOUNT_USER_ROLE_MAP
(
    USER_PK bigint DEFAULT 0 NOT NULL,
    ROLE_PK bigint DEFAULT 0 NOT NULL,
    CONSTRAINT PK_account_user_role_map_USER_PK PRIMARY KEY (USER_PK, ROLE_PK)
    );

CREATE TABLE IF NOT EXISTS TEAM
(
    `_ID`          bigint AUTO_INCREMENT NOT NULL,
    TENANT_PK      bigint DEFAULT 0 NOT NULL,
    USE_YN         tinyint unsigned DEFAULT 0 NOT NULL,
    VISIBLE_YN     tinyint unsigned NOT NULL,
    TEAM_NAME      nvarchar(256) NOT NULL,
    TEAM_ORDER     int unsigned DEFAULT 9999 NOT NULL,
    CREATED_BY     bigint           NOT NULL,
    CREATED_DATE   DATETIME         NOT NULL,
    UPDATED_BY     bigint           NOT NULL,
    UPDATED_DATE   DATETIME         NOT NULL,
    TEAM_LEVEL     int unsigned DEFAULT 0 NOT NULL,
    TEAM_PATH      nvarchar(2048) NULL,
    PARENT_TEAM_PK bigint NULL,
    CONSTRAINT PK_team_new__ID PRIMARY KEY (`_ID`)
    );

CREATE TABLE IF NOT EXISTS AUTHORITY_DIVISION
(
    `_ID`          bigint            NOT NULL,
    USE_YN         tinyint unsigned DEFAULT 0 NOT NULL,
    VISIBLE_YN     tinyint unsigned DEFAULT 0 NOT NULL,
    DIVISION_ORDER int unsigned DEFAULT 9999 NOT NULL,
    NAME           nvarchar(256) NOT NULL,
    DESCRIPTION    nvarchar(1024) NULL,
    CONSTRAINT `_authority_division_PK` PRIMARY KEY (`_ID`)
    );


CREATE TABLE IF NOT EXISTS AUTHORITY_MENU
(
    `_ID`       bigint            NOT NULL,
    DIVISION_PK bigint            NOT NULL,
    USE_YN      tinyint unsigned DEFAULT 0 NOT NULL,
    VISIBLE_YN  tinyint unsigned DEFAULT 0 NOT NULL,
    MENU_ORDER  int unsigned DEFAULT 9999 NOT NULL,
    CODE        nvarchar(256) NOT NULL,
    NAME        nvarchar(256) NOT NULL,
    DESCRIPTION nvarchar(1024) NULL,
    CONSTRAINT `_authority_menu_PK` PRIMARY KEY (`_ID`)
    );

CREATE TABLE IF NOT EXISTS AUTHORITY_API
(
    `_ID`       bigint           NOT NULL,
    CODE        nvarchar(256) NOT NULL,
    MENU_PK     bigint           NOT NULL,
    SERVICE     nvarchar(32) NOT NULL,
    USE_YN      tinyint unsigned DEFAULT 0 NOT NULL,
    VISIBLE_YN  tinyint unsigned DEFAULT 0 NOT NULL,
    PRIMARY_YN  tinyint unsigned DEFAULT 0 NOT NULL,
    PUBLIC_YN   tinyint unsigned DEFAULT 0 NOT NULL,
    RECORD_YN   tinyint unsigned DEFAULT 0 NOT NULL,
    HTTP_METHOD int unsigned DEFAULT 0 NOT NULL,
    URL         nvarchar(256) NOT NULL,
    DESCRIPTION nvarchar(256) DEFAULT NULL NULL,
    CONSTRAINT `_PK_authority_api_PK` PRIMARY KEY (`_ID`),
    CONSTRAINT `_authority_api$API_KEY` UNIQUE (CODE),
    CONSTRAINT `_authority_api$URL_METHOD` UNIQUE (URL, HTTP_METHOD)
    );


CREATE TABLE IF NOT EXISTS AUTHORITY_API_ROLE_MAP
(
    API_PK  bigint DEFAULT 0 NOT NULL,
    ROLE_PK bigint DEFAULT 0 NOT NULL,
    CONSTRAINT `_PK_authority_api_role_map_API_ROLE_PK` PRIMARY KEY (API_PK, ROLE_PK)
    );



CREATE TABLE IF NOT EXISTS FILES
(
    `_ID`             bigint AUTO_INCREMENT NOT NULL,
    UUID              nchar(36)                NOT NULL,
    USE_YN            tinyint unsigned DEFAULT 0 NOT NULL,
    FILE_EXISTS       tinyint unsigned DEFAULT 1 NOT NULL,
    FILE_TYPE         nvarchar(256) NOT NULL,
    FILE_SIZE         numeric(20, 0) DEFAULT NULL NULL,
    FILE_NM_ORIGINAL  nvarchar(256) NOT NULL,
    FILE_NM_CHANGE    nvarchar(64) NOT NULL,
    DOWNLOAD_CNT      bigint         DEFAULT 0 NOT NULL,
    FILE_SERVICE      nvarchar(32) DEFAULT NULL NULL,
    FILE_SERVICE_ID   bigint         DEFAULT NULL NULL,
    FILE_SERVICE_TYPE int unsigned  DEFAULT NULL NULL,
    ACCESS_AUTHORITY  bigint         DEFAULT 0 NOT NULL,
    CREATED_BY        bigint         DEFAULT 0 NOT NULL,
    CREATED_DATE      DATETIME                 NOT NULL,
    CONSTRAINT PK_file__ID PRIMARY KEY (`_ID`),
    CONSTRAINT file$UUID UNIQUE (UUID)
    );

CREATE TABLE IF NOT EXISTS HOLIDAY
(
    `_ID`      bigint AUTO_INCREMENT PRIMARY KEY NOT NULL,
    TENANT_PK  bigint   DEFAULT 0 NOT NULL,
    UUID       nvarchar(36) NOT NULL,
    TITLE      nvarchar(36) NOT NULL,
    DATE_START DATETIME           NOT NULL,
    DATE_END   DATETIME           NOT NULL,
    EXTRA_DATA longtext DEFAULT NULL NULL
    );

