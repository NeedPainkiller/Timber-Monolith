use `timber-application`;


DELETE FROM authority_division;
INSERT INTO authority_division (_ID, USE_YN, VISIBLE_YN, DIVISION_ORDER, NAME, DESCRIPTION) VALUES
                                                                                                (0,1,0,0,'PUBLIC','공용'),
                                                                                                (9999,1,1,9999,'관리자','관리자'),
                                                                                                (10000,1,0,10000,'ETC','ETC');



DELETE FROM authority_menu;
INSERT INTO authority_menu (_ID, DIVISION_PK, USE_YN, VISIBLE_YN, MENU_ORDER, CODE, NAME, DESCRIPTION) VALUES
                                                                                                           (0, 0, 1, 0, 1, '/auth/login', '로그인', '로그인 화면'),

                                                                                                           (9999000, 9999, 1, 1, 0, '/admin/tenant', '테넌트 관리', '테넌트 관리'),
                                                                                                           (9999001, 9999, 1, 1, 1, '/admin/user', '계정 관리', '계정 관리'),
                                                                                                           (9999002, 9999, 1, 1, 2, '/admin/role', '계정권한 관리', '계정권한 관리'),
                                                                                                           (9999003, 9999, 1, 1, 3, '/admin/team', '부서 관리', '부서 관리'),
                                                                                                           (9999004, 9999, 1, 1, 5, '/admin/workingday', '영업일 관리', '영업일 관리'),
                                                                                                           (9999005, 9999, 1, 1, 6, '/admin/audit', '감사 로그', '감사 로그'),
                                                                                                           (9999006, 9999, 1, 1, 7, '/admin/setting', '기타 설정', '기타 설정'),

                                                                                                           (10000000, 10000, 1, 0, 1, '/etc', 'ETC', 'ETC');


DELETE FROM authority_api;
DELETE FROM authority_api_role_map;

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (1, 0, 'AUTHENTICATION', 1, 0, 0, 1, 3, 1, '/api/v1/authentication/login', 'login', '로그인 (Token 발급)'),
                                                                                                                                                  (2, 0, 'AUTHENTICATION', 1, 0, 0, 1, 1, 1, '/api/v1/authentication/logout', 'logout', '로그아웃 (Token 만료 처리)'),
                                                                                                                                                  (3, 0, 'AUTHENTICATION', 1, 0, 0, 1, 1, 1, '/api/v1/authentication/check', 'validateToken', 'Token 유효확인'),
                                                                                                                                                  (4, 0, 'AUTHENTICATION', 1, 0, 0, 1, 1, 1, '/api/v1/authentication/refresh', 'refreshToken', 'Token 재발급');


INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (999900001, 9999000, 'TENANT', 1, 0, 0, 1, 1, 0, '/api/v1/tenant/public', 'selectPublicTenantList',  '공개 Tenant 리스트 조회'),
                                                                                                                                                  (999900002, 9999000, 'TENANT', 1, 0, 0, 0, 1, 1, '/api/v1/tenant/switchable', 'selectSwitchableTenantList',  '전환 가능한 Tenant 리스트 조회'),
                                                                                                                                                  (999900003, 9999000, 'TENANT', 1, 0, 1, 0, 1, 1, '/api/v1/tenant/list', 'selectTenantList',  'Tenant 리스트 조회'),
                                                                                                                                                  (999900004, 9999000, 'TENANT', 1, 0, 0, 0, 1, 1, '/api/v1/tenant/{^[\\d]$}', 'selectTenant',  'Tenant 조회'),
                                                                                                                                                  (999900005, 9999000, 'TENANT', 1, 0, 0, 0, 3, 1, '/api/v1/tenant', 'createTenant',  'Tenant 등록'),
                                                                                                                                                  (999900006, 9999000, 'TENANT', 1, 0, 0, 0, 4, 1, '/api/v1/tenant/{^[\\d]$}', 'updateTenant',  'Tenant 수정'),
                                                                                                                                                  (999900007, 9999000, 'TENANT', 1, 0, 0, 0, 6, 1, '/api/v1/tenant/{^[\\d]$}', 'deleteTenant',  'Tenant 삭제'),
                                                                                                                                                  (999900008, 9999000, 'TENANT', 1, 0, 0, 0, 4, 1, '/api/v1/tenant/{^[\\d]$}/switch', 'switchTenant',  'Tenant 전환');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (999900003, 1),
                                                         (999900004, 1),
                                                         (999900005, 1),
                                                         (999900006, 1),
                                                         (999900007, 1),
                                                         (999900008, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (999900101, 9999001, 'USER', 1, 1, 1, 1, 3, 1, '/api/v1/user/list', 'selectUserList',  '유저 리스트 조회'),
                                                                                                                                                  (999900102, 9999001, 'USER', 1, 0, 0, 1, 1, 1, '/api/v1/user/{^[\\d]$}', 'selectUser',  '특정 유저정보 조회'),
                                                                                                                                                  (999900103, 9999001, 'USER', 1, 0, 0, 1, 1, 1, '/api/v1/user/me', 'selectMe',  '내 정보 조회'),
                                                                                                                                                  (999900104, 9999001, 'USER', 1, 0, 0, 1, 1, 1, '/api/v1/user/exists/*', 'isUserIdExist',  '유저 ID  중복확인'),
                                                                                                                                                  (999900105, 9999001, 'USER', 1, 1, 0, 0, 3, 1, '/api/v1/user', 'createUser',  '유저 등록'),
                                                                                                                                                  (999900106, 9999001, 'USER', 1, 1, 0, 0, 4, 1, '/api/v1/user/{^[\\d]$}', 'updateUser',  '유저 정보 변경'),
                                                                                                                                                  (999900107, 9999001, 'USER', 1, 1, 0, 0, 6, 1, '/api/v1/user/{^[\\d]$}', 'deleteUser',  '유저 삭제'),
                                                                                                                                                  (999900108, 9999001, 'USER', 0, 0, 0, 0, 3, 1, '/api/v1/user/{^[\\d]$}/validation/', 'requestValidation',  '유저 이메일 인증 요청'),
                                                                                                                                                  (999900109, 9999001, 'USER', 0, 0, 0, 0, 1, 1, '/api/v1/user/validation/*', 'userValidation',  '유저 이메일 인증'),
                                                                                                                                                  (999900110, 9999001, 'USER', 0, 0, 0, 0, 1, 1, '/api/v1/user/{^[\\d]$}/requestPasswordReset', 'requestPasswordReset',  '패스워드 변경 요청'),
                                                                                                                                                  (999900111, 9999001, 'USER', 0, 0, 0, 0, 4, 1, '/api/v1/user/updatePassword', 'updatePassword',  '패스워드 변경 처리'),
                                                                                                                                                  (999900112, 9999001, 'USER', 0, 0, 0, 0, 1, 1, '/api/v1/{^[\\d]$}/tempPassword', 'requestTempPasswordReset',  '임시 패스워드 발급');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (999900101, 1),
                                                         (999900102, 1),
                                                         (999900104, 1),
                                                         (999900105, 1),
                                                         (999900106, 1),
                                                         (999900107, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (999900201, 9999002, 'ROLE', 1, 1, 1, 0, 3, 1, '/api/v1/role/list', 'selectRoleList',  '권한 리스트 조회'),
                                                                                                                                                  (999900202, 9999002, 'ROLE', 1, 0, 0, 0, 1, 1, '/api/v1/role/all', 'selectAllRoleList',  '모든 권한 정보 조회 (관리자 전용)'),
                                                                                                                                                  (999900203, 9999002, 'ROLE', 1, 1, 0, 0, 1, 1, '/api/v1/role/{^[\\d]$}', 'selectRole',  '권한 조회'),
                                                                                                                                                  (999900204, 9999002, 'ROLE', 1, 1, 0, 0, 3, 1, '/api/v1/role/list/download', 'downloadRoleList',  '권한 리스트 다운로드'),
                                                                                                                                                  (999900205, 9999002, 'ROLE', 1, 0, 0, 1, 1, 1, '/api/v1/role/me', 'selectMyRoles',  '내 권한 조회'),
                                                                                                                                                  (999900206, 9999002, 'ROLE', 1, 1, 0, 0, 3, 1, '/api/v1/role', 'createRole',  '권한 등록'),
                                                                                                                                                  (999900207, 9999002, 'ROLE', 1, 1, 0, 0, 4, 1, '/api/v1/role/{^[\\d]$}', 'updateRole',  '권한 수정'),
                                                                                                                                                  (999900208, 9999002, 'ROLE', 1, 1, 0, 0, 6, 1, '/api/v1/role/{^[\\d]$}', 'deleteRole',  '권한 삭제'),
                                                                                                                                                  (999900209, 9999002, 'ROLE', 1, 0, 0, 1, 1, 1, '/api/v1/role/api', 'selectAllApiList',  '전체 API 정보 조회');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (999900201, 1),
                                                         (999900203, 1),
                                                         (999900204, 1),
                                                         (999900206, 1),
                                                         (999900207, 1),
                                                         (999900208, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (999900301, 9999003, 'TEAM', 1, 1, 1, 1, 3, 1, '/api/v1/team/list', 'selectTeamList',  '부서 전체 리스트 조회'),
                                                                                                                                                  (999900302, 9999003, 'TEAM', 1, 1, 0, 1, 1, 1, '/api/v1/team/{^[\\d]$}', 'selectTeam',  '부서 조회'),
                                                                                                                                                  (999900303, 9999003, 'TEAM', 1, 1, 0, 0, 3, 1, '/api/v1/team', 'createTeam',  '부서 등록'),
                                                                                                                                                  (999900304, 9999003, 'TEAM', 1, 1, 0, 0, 4, 1, '/api/v1/team/{^[\\d]$}', 'updateTeam',  '부서 수정'),
                                                                                                                                                  (999900305, 9999003, 'TEAM', 1, 1, 0, 0, 6, 1, '/api/v1/team/{^[\\d]$}', 'deleteTeam',  '부서 삭제'),
                                                                                                                                                  (999900306, 9999003, 'TEAM', 0, 0, 0, 0, 3, 1, '/api/v1/team/bulk', 'createTeamBulk',  '부서 일괄 등록');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (999900301, 1),
                                                         (999900302, 1),
                                                         (999900303, 1),
                                                         (999900304, 1),
                                                         (999900305, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (999900401, 9999004, 'WORKINGDAY', 1, 1, 1, 0, 1, 1, '/api/v1/workingday/today', 'selectThisWorkingDay',  '금일 영업일 조회'),
                                                                                                                                                  (999900402, 9999004, 'WORKINGDAY', 1, 1, 0, 0, 1, 1, '/api/v1/workingday', 'selectHolidayList',  '휴일 리스트 조회'),
                                                                                                                                                  (999900403, 9999004, 'WORKINGDAY', 1, 1, 0, 0, 3, 1, '/api/v1/workingday', 'createHoliday',  '휴일 등록'),
                                                                                                                                                  (999900404, 9999004, 'WORKINGDAY', 1, 1, 0, 0, 4, 1, '/api/v1/workingday/{^[\\d]$}', 'updateHoliday',  '휴일 수정'),
                                                                                                                                                  (999900405, 9999004, 'WORKINGDAY', 1, 1, 0, 0, 6, 1, '/api/v1/workingday/{^[\\d]$}', 'deleteHoliday',  '휴일 삭제'),
                                                                                                                                                  (999900406, 9999004, 'WORKINGDAY', 1, 1, 0, 0, 1, 1, '/api/v1/workingday/download', 'downloadHolidayList',  '휴일 다운로드'),
                                                                                                                                                  (999900407, 9999004, 'WORKINGDAY', 1, 1, 0, 0, 3, 1, '/api/v1/workingday/upload', 'uploadHolidayBulk',  '휴일 일괄 등록');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (999900401, 1),
                                                         (999900402, 1),
                                                         (999900403, 1),
                                                         (999900404, 1),
                                                         (999900405, 1),
                                                         (999900406, 1),
                                                         (999900407, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (999900501, 9999005, 'AUDIT', 1, 1, 1, 0, 3, 1, '/api/v1/audit/list', 'selectAuditLog',  '요청 기록 조회'),
                                                                                                                                                  (999900502, 9999005, 'AUDIT', 1, 1, 0, 0, 3, 1, '/api/v1/audit/list/download', 'downloadAuditLog',  '요청 기록 다운로드');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (999900501, 1),
                                                         (999900502, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (999900601, 9999006, 'SETTING', 1, 0, 0, 1, 1, 1, '/api/v1/setting/all', 'selectAllSetting',  '관리자 설정 값 전체조회'),
                                                                                                                                                  (999900602, 9999006, 'SETTING', 1, 0, 0, 1, 1, 1, '/api/v1/setting/*', 'selectSetting',  '관리자 설정 값 조회'),
                                                                                                                                                  (999900603, 9999006, 'SETTING', 1, 1, 1, 0, 4, 1, '/api/v1/setting/*', 'upsertSetting',  '관리자 설정 값 등록');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
    (999900603, 1);




INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (1000000011, 10000000, 'FILE', 1, 0, 0, 1, 3, 1, '/api/v1/files/upload', 'uploadFile', '파일 업로드'),
                                                                                                                                                  (1000000012, 10000000, 'FILE', 1, 0, 0, 1, 1, 1, '/api/v1/files/download/*', 'downloadFile', '파일 다운로드'),
                                                                                                                                                  (1000000013, 10000000, 'FILE', 1, 0, 0, 1, 3, 1, '/api/v1/files/upload/secure','uploadSecureFile', '보안 파일 업로드'),
                                                                                                                                                  (1000000014, 10000000, 'FILE', 1, 0, 0, 1, 1, 1, '/api/v1/files/download/secure/*','downloadSecureFile', '보안 파일 다운로드'),
                                                                                                                                                  (1000000015, 10000000, 'FILE', 1, 0, 0, 1, 3, 0, '/api/v1/files/upload/anonymous', 'uploadAnonymousFile', '익명 파일 업로드');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
    (1000000021, 10000000, 'DB', 1, 0, 0, 0, 1, 0, '/api/v1/database/backup', 'backup', 'DB 백업');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
    (1000000022, 10000000, 'CACHE', 1, 0, 0, 1, 6, 0, '/api/v1/cache', 'clearCache', 'cache 초기화');


INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (1000000051, 10000000, 'ENCRYPTION', 1, 0, 0, 1, 1, 0, '/api/v1/encryption/enc', 'encryption', 'ENCRYPTION'),
                                                                                                                                                  (1000000052, 10000000, 'ENCRYPTION', 1, 0, 0, 1, 1, 0, '/api/v1/encryption/dec', 'decryption', 'DECRYPTION'),
                                                                                                                                                  (1000000053, 10000000, 'ENCRYPTION', 1, 0, 0, 1, 1, 0, '/api/v1/encryption/passwd', 'passwd', 'PASSWORD');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (1000000091, 10000000, 'TEST', 1, 0, 0, 1, 1, 0, '/api/v1/test', 'test', 'TEST'),
                                                                                                                                                  (1000000092, 10000000, 'TEST', 1, 0, 0, 1, 1, 0, '/api/v1/test/**', 'test-get', 'TEST'),
                                                                                                                                                  (1000000093, 10000000, 'TEST', 1, 0, 0, 1, 3, 0, '/api/v1/test/**', 'test-post', 'TEST'),
                                                                                                                                                  (1000000094, 10000000, 'TEST', 1, 0, 0, 1, 4, 0, '/api/v1/test/**', 'test-put', 'TEST'),
                                                                                                                                                  (1000000095, 10000000, 'TEST', 1, 0, 0, 1, 6, 0, '/api/v1/test/**', 'test-delete', 'TEST');

-- INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
--                                                                                                                                                   (69901, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 1, 1, 1, '/api/v1/board-management/type', 'selectBoardTypeList',  '게시판 타입 리스트 조회'),
--                                                                                                                                                   (69902, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 3, 1, '/api/v1/board-management/type', 'createBoardType',  '게시판 타입 생성'),
--                                                                                                                                                   (69903, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 6, 1, '/api/v1/board-management/type', 'deleteBoardType',  '게시판 타입 삭제'),
--                                                                                                                                                   (69904, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 1, 1, 1, '/api/v1/board-management/category', 'selectBoardCategoryList',  '게시판 카테고리 리스트 조회'),
--                                                                                                                                                   (69905, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 3, 1, '/api/v1/board-management/category', 'createBoardCategory',  '게시판 카테고리 생성'),
--                                                                                                                                                   (69906, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 6, 1, '/api/v1/board-management/category', 'deleteBoardCategory',  '게시판 카테고리 삭제'),
--                                                                                                                                                   (69907, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 1, 1, 1, '/api/v1/board-management/reply/category', 'selectReplyCategoryList',  '댓글 타입 리스트 조회"'),
--                                                                                                                                                   (69908, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 3, 1, '/api/v1/board-management/reply/category', 'createReplyCategory',  '댓글 타입 생성'),
--                                                                                                                                                   (69909, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 6, 1, '/api/v1/board-management/reply/category', 'deleteReplyCategory',  '댓글 타입 삭제');
-- INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
--                                                          (69902, 1),
--                                                          (69903, 1),
--                                                          (69905, 1),
--                                                          (69906, 1),
--                                                          (69908, 1),
--                                                          (69909, 1);
--
-- INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
--                                                                                                                                                   (60101, 601, 'BOARD.NOTICE', 1, 1, 1, 0, 3, 1, '/api/v1/board/notice/list', 'selectBoardList-notice',  '게시판 리스트 조회'),
--                                                                                                                                                   (60102, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 1, 1, '/api/v1/board/notice/{^[\\d]$}', 'selectBoard-notice',  '게시글 조회'),
--                                                                                                                                                   (60103, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 3, 1, '/api/v1/board/notice', 'createBoard-notice',  '게시글 생성'),
--                                                                                                                                                   (60104, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 4, 1, '/api/v1/board/notice/{^[\\d]$}', 'updateBoard-notice',  '게시글  업데이트'),
--                                                                                                                                                   (60105, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 6, 1, '/api/v1/board/notice/{^[\\d]$}', 'deleteBoard-notice',  '게시글 삭제'),
--                                                                                                                                                   (60106, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 4, 1, '/api/v1/board/notice/{^[\\d]$}/like', 'likeBoard-notice',  '게시글의 추천 & 취소'),
--                                                                                                                                                   (60107, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 1, 1, '/api/v1/board/notice/{^[\\d]$}/reply', 'selectReplyList-notice',  '게시글 댓글 리스트"'),
--                                                                                                                                                   (60108, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 3, 1, '/api/v1/board/notice/{^[\\d]$}/reply', 'createReply-notice',  '댓글 생성'),
--                                                                                                                                                   (60109, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 4, 1, '/api/v1/board/notice/reply/{^[\\d]$}', 'updateReply-notice',  '댓글 업데이트'),
--                                                                                                                                                   (60110, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 6, 1, '/api/v1/board/notice/reply/{^[\\d]$}', 'deleteReply-notice',  '댓글 삭제'),
--                                                                                                                                                   (60111, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 4, 1, '/api/v1/board/notice/reply/{^[\\d]$}/like', 'likeReply-notice',  '댓글의 추천/취소');
-- INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
--                                                          (60101, 1),
--                                                          (60102, 1),
--                                                          (60103, 1),
--                                                          (60104, 1),
--                                                          (60105, 1),
--                                                          (60106, 1),
--                                                          (60107, 1),
--                                                          (60108, 1),
--                                                          (60109, 1),
--                                                          (60110, 1),
--                                                          (60111, 1);



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
