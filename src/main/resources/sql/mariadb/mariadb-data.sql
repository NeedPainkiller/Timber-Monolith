use `timber-application`;


DELETE FROM tenant;
INSERT INTO tenant (_ID, USE_YN, VISIBLE_YN, TITLE, LABEL, URL_PATH, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE) VALUES
(1, 1, 1, 'ROOT', 'ROOT', '/', 1, now(), 1, now());


DELETE FROM team;
INSERT INTO team (_ID, USE_YN, VISIBLE_YN, TEAM_NAME, TEAM_ORDER, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, TEAM_LEVEL, TEAM_PATH, PARENT_TEAM_PK) VALUES
        (1, 1, 1, 'ROOT', 0, 1, now(), 1, now(), 0, '/', null);


DELETE FROM account_role;
INSERT INTO account_role (_ID, TENANT_PK, USE_YN, IS_SYSTEM_ADMIN, IS_ADMIN, IS_EDITABLE, ROLE_NAME, ROLE_DESCRIPTION, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE) VALUES
    (1, 1, 1, 1, 1, 1, 'ROLE_ADMIN', 'ROLE_ADMIN', 1, now(), 1, now()),
    (2, 1, 1, 0, 1, 1, 'ROLE_USER', 'ROLE_USER', 1, now(), 1, now());

DELETE FROM account_user;
INSERT INTO account_user (_ID, TENANT_PK, USE_YN, USER_ID, USER_EMAIL, USER_NAME, USER_PWD, USER_STATUS, TEAM_PK, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, LOGIN_FAILED_CNT,LAST_LOGIN_DATE,EXTRA_DATA) VALUES
    (1, 1, 1, 'admin', 'admin@xxx.com', 'admin', '', 1, 1, 1, now(), 1, now(), 0, null, ''),
    (2, 1, 1, 'user', 'user@xxx.com', 'user', '', 1, 1, 1, now(), 1, now(), 0, null, '');

DELETE FROM account_user_role_map;
INSERT INTO account_user_role_map (USER_PK, ROLE_PK) VALUES
    (1, 1),
    (2, 2);
