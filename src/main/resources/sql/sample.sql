use timber;

DELETE FROM authority_division;

INSERT INTO authority_division (_ID, USE_YN, VISIBLE_YN, DIVISION_ORDER, NAME,  DESCRIPTION) VALUES
                                                                                                 (0,1,0,0,'PUBLIC','공용'),
                                                                                                 (1,1,1,1,'대시보드','실적조회 및 모니터링'),
                                                                                                 (2,1,1,2,'과제 관리','과제 신청, 심사 및 운영/개발 관리'),
                                                                                                 (3,1,1,3,'실적 관리','실적 관리'),
                                                                                                 (4,1,1,4,'RPA 운영','RPA 운영 및 관리'),
                                                                                                 (5,1,1,5,'모니터링','모니터링'),
                                                                                                 (6,1,1,6,'커뮤니티','공지사항 및 QnA'),
                                                                                                 (7,1,1,7,'관리자','관리자'),
                                                                                                 (8,1,1,8,'RPA 봇 전용','RPA 봇 전용'),
                                                                                                 (10000,1,0,10000,'ETC','ETC');

DELETE FROM authority_menu;
DELETE FROM authority_api;
DELETE FROM authority_api_role_map;

INSERT INTO authority_menu (_ID, DIVISION_PK, USE_YN, VISIBLE_YN, MENU_ORDER, CODE, NAME, DESCRIPTION) VALUES
                                                                                                           (0, 0, 1, 0, 1, '/auth/login', '로그인', '로그인 화면'),
                                                                                                           (101, 1, 1, 1, 1, '/dashboard', '운영 대시보드', '대시보드 및 실적조회'),
                                                                                                           (102, 1, 1, 1, 2, '/dashboard/teamEntity', '부서 대시보드', '대시보드 및 실적조회'),
                                                                                                           (103, 1, 1, 1, 3, '/dashboard/userEntity', '개인 대시보드', '대시보드 및 실적조회'),

                                                                                                           (201, 2, 1, 1, 1, '/task/petition', '과제 신청', '과제 신청'),
                                                                                                           (202, 2, 1, 1, 2, '/task/evaluation', '과제 심사', '과제 심사'),
                                                                                                           (203, 2, 1, 1, 3, '/task/management', '과제 운영/개발', '과제 운영/개발'),

                                                                                                           (302, 3, 1, 1, 2, '/statistics/task', '과제 처리현황 ', '과제 처리현황'),
                                                                                                           (303, 3, 1, 1, 3, '/statistics/daily', '일별 처리현황', '일별 처리현황'),

                                                                                                           (401, 4, 1, 1, 2, '/execution/history', '과제 실행 이력', '과제 실행 이력'),
                                                                                                           (402, 4, 1, 1, 1, '/rpa/schedule', '스케줄 관리', '스케줄 현황'),
                                                                                                           (403, 4, 1, 1, 4, '/rpa/failure', '장애 관리', '장애 관리'),
                                                                                                           (404, 4, 1, 1, 3, '/rpa/deploy', '과제 수행 관리', '과제 수행 관리'),

                                                                                                           (501, 5, 1, 1, 1, '/monitor/bot', 'BOT 모니터링', 'BOT 모니터링'),
                                                                                                           (502, 5, 1, 1, 2, '/monitor/task', '과제 모니터링', '과제 모니터링'),

                                                                                                           (601, 6, 1, 1, 1, '/board/notice', '공지사항', '공지사항'),
                                                                                                           (602, 6, 1, 1, 2, '/board/archives', '자료실', '자료실'),
                                                                                                           (603, 6, 1, 1, 3, '/board/faq', 'FAQ', 'FAQ'),
                                                                                                           (604, 6, 1, 1, 4, '/board/question', 'QnA', '질의응답'),
                                                                                                           (699, 6, 1, 0, 99, '/board-management', '게시판 관리', '게시판 관리'),

                                                                                                           (700, 7, 1, 1, 0, '/admin/tenant', '테넌트 관리', '테넌트 관리'),
                                                                                                           (701, 7, 1, 1, 1, '/admin/account', '계정 관리', '계정 관리'),
                                                                                                           (702, 7, 1, 1, 2, '/admin/role', '계정권한 관리', '계정권한 관리'),
                                                                                                           (703, 7, 1, 1, 3, '/admin/teamEntity', '부서 관리', '부서 관리'),
                                                                                                           (704, 7, 1, 1, 4, '/admin/agent', 'BOT 관리', 'BOT 관리'),
                                                                                                           (705, 7, 1, 1, 5, '/admin/workingday', '영업일 관리', '영업일 관리'),
                                                                                                           (706, 7, 1, 1, 6, '/admin/log', '감사 로그', '감사 로그'),
                                                                                                           (707, 7, 1, 1, 7, '/admin/setting', '기타 설정', '기타 설정'),

                                                                                                           (709, 7, 0, 1, 8, '/admin/dataset', '기준정보 관리', '기준정보 관리'),

                                                                                                           (801, 8, 1, 1, 1, '/agent/archive', 'RPA 자료실', 'RPA 자료실'),
                                                                                                           (802, 8, 1, 1, 2, '/agent/deploy', '과제 수행 요청', '과제 수행 요청'),

                                                                                                           (1000000, 10000, 1, 0, 1, '/admin/etc', 'ETC', 'ETC');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (999901, 0, 'AUTHENTICATION', 1, 0, 0, 1, 3, 1, '/api/v1/authentication/login', 'login', '로그인 (Token 발급)'),
                                                                                                                                                  (999902, 0, 'AUTHENTICATION', 1, 0, 0, 1, 1, 1, '/api/v1/authentication/logout', 'logout', '로그아웃 (Token 만료 처리)'),
                                                                                                                                                  (999903, 0, 'AUTHENTICATION', 1, 0, 0, 1, 1, 1, '/api/v1/authentication/check', 'validateToken', 'Token 유효확인'),
                                                                                                                                                  (999904, 0, 'AUTHENTICATION', 1, 0, 0, 1, 1, 1, '/api/v1/authentication/refresh', 'refreshToken', 'Token 재발급');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (1000011, 1000000, 'FILE', 1, 0, 0, 1, 3, 1, '/api/v1/files/upload', 'uploadFile', '파일 업로드'),
                                                                                                                                                  (1000012, 1000000, 'FILE', 1, 0, 0, 1, 1, 1, '/api/v1/files/download/*', 'downloadFile', '파일 다운로드'),
                                                                                                                                                  (1000013, 1000000, 'FILE', 1, 0, 0, 1, 3, 1, '/api/v1/files/upload/secure','uploadSecureFile', '보안 파일 업로드'),
                                                                                                                                                  (1000014, 1000000, 'FILE', 1, 0, 0, 1, 1, 1, '/api/v1/files/download/secure/*','downloadSecureFile', '보안 파일 다운로드'),
                                                                                                                                                  (1000015, 1000000, 'FILE', 1, 0, 0, 1, 3, 0, '/api/v1/files/upload/anonymous', 'uploadAnonymousFile', '익명 파일 업로드');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
    (1000021, 1000000, 'DB', 1, 0, 0, 0, 1, 0, '/api/v1/database/backup', 'backup', 'DB 백업');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
    (1000022, 1000000, 'CACHE', 1, 0, 0, 1, 6, 0, '/api/v1/cache', 'clearCache', 'cache 초기화');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES

                                                                                                                                                  (1000032, 1000000, 'RPA.AGENT.JOB', 1, 0, 0, 1, 3, 0, '/api/v1/rpa/job/status', 'updateDeviceStatus','RPA AGENT 장비의 모니터링 정보 등록'),
                                                                                                                                                  (1000033, 1000000, 'RPA.AGENT.JOB', 1, 0, 0, 1, 3, 0, '/api/v1/rpa/job', 'job', 'RPA JOB AUDIT 추가'),
                                                                                                                                                  (1000034, 1000000, 'RPA.AGENT.JOB', 1, 0, 0, 1, 3, 0, '/api/v1/rpa/job/bulk','bulkJob', 'RPA JOB AUDIT 추가');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (1000041, 1000000, 'GLOBAL_VARIABLE', 1, 0, 0, 0, 1, 1, '/api/v1/global-variable/list', 'selectGlobalVariableList',  '조회'),
                                                                                                                                                  (1000042, 1000000, 'GLOBAL_VARIABLE', 1, 0, 0, 0, 4, 1, '/api/v1/global-variable', 'upsertGlobalVariable',  '등록/수정'),
                                                                                                                                                  (1000043, 1000000, 'GLOBAL_VARIABLE', 1, 0, 0, 0, 6, 1, '/api/v1/global-variable', 'deleteGlobalVariableList',  '삭제');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (1000051, 1000000, 'ENCRYPTION', 1, 0, 0, 1, 1, 0, '/api/v1/encryption/enc', 'encryption', 'ENCRYPTION'),
                                                                                                                                                  (1000052, 1000000, 'ENCRYPTION', 1, 0, 0, 1, 1, 0, '/api/v1/encryption/dec', 'decryption', 'DECRYPTION');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (1000091, 1000000, 'TEST', 1, 0, 0, 1, 1, 0, '/api/v1/test', 'test', 'TEST'),
                                                                                                                                                  (1000092, 1000000, 'TEST', 1, 0, 0, 1, 1, 0, '/api/v1/test/**', 'test-get', 'TEST'),
                                                                                                                                                  (1000093, 1000000, 'TEST', 1, 0, 0, 1, 3, 0, '/api/v1/test/**', 'test-post', 'TEST'),
                                                                                                                                                  (1000094, 1000000, 'TEST', 1, 0, 0, 1, 4, 0, '/api/v1/test/**', 'test-put', 'TEST'),
                                                                                                                                                  (1000095, 1000000, 'TEST', 1, 0, 0, 1, 6, 0, '/api/v1/test/**', 'test=delete', 'TEST');

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (10101, 101, 'DASHBOARD', 1, 1, 1, 0, 1, 1, '/api/v1/dashboard/overview', 'selectDashboardOverview',  '대시보드 개요 조회 API'),
                                                                                                                                                  (10102, 101, 'DASHBOARD', 1, 1, 0, 0, 3, 1, '/api/v1/dashboard/stat', 'selectDashboardStatOfDateRange', '대시보드 기간 통계 조회 API');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (10101, 1),
                                                         (10102, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
    (10201, 102, 'DASHBOARD.TEAM', 1, 1, 1, 0, 3, 1, '/api/v1/dashboard/teamEntity', 'selectTeamDashboard', '대시보드 기간 통계 조회 API');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
    (10201, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
    (10301, 103, 'DASHBOARD.USER', 1, 1, 1, 0, 3, 1, '/api/v1/dashboard/userEntity', 'selectUserDashboard', '대시보드 기간 통계 조회 API');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
    (10301, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (20101, 201, 'TASK.PETITION', 1, 1, 1, 1, 3, 1, '/api/v1/task/petition/list', 'selectTaskPetitionList',  '과제 신청 리스트 조회'),
                                                                                                                                                  (20102, 201, 'TASK.PETITION', 1, 1, 0, 0, 1, 1, '/api/v1/task/petition/{^[\\d]$}', 'selectTaskPetition',  '과제 신청 조회'),
                                                                                                                                                  (20103, 201, 'TASK.PETITION', 1, 1, 0, 0, 3, 1, '/api/v1/task/petition', 'createTaskPetition',  '과제 신청 등록'),
                                                                                                                                                  (20104, 201, 'TASK.PETITION', 1, 1, 0, 0, 4, 1, '/api/v1/task/petition/{^[\\d]$}', 'updateTaskPetition',  '과제 신청 수정'),
                                                                                                                                                  (20105, 201, 'TASK.PETITION', 1, 1, 0, 0, 6, 1, '/api/v1/task/petition/{^[\\d]$}', 'deleteTaskPetition',  '과제 신청 삭제');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (20101, 1),
                                                         (20102, 1),
                                                         (20103, 1),
                                                         (20104, 1),
                                                         (20105, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (20201, 202, 'TASK.EVALUATION', 1, 1, 1, 0, 3, 1, '/api/v1/task/evaluation/list', 'selectTaskEvaluationList',  '과제 심사 리스트 조회'),
                                                                                                                                                  (20202, 202, 'TASK.EVALUATION', 1, 1, 0, 0, 1, 1, '/api/v1/task/evaluation/{^[\\d]$}', 'selectEvaluationPetition',  '과제 심사 조회'),
                                                                                                                                                  (20203, 202, 'TASK.EVALUATION', 1, 1, 0, 0, 4, 1, '/api/v1/task/evaluation/{^[\\d]$}', 'evaluatePetition',  '과제 심사');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (20201, 1),
                                                         (20202, 1),
                                                         (20203, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (20301, 203, 'TASK', 1, 0, 0, 0, 3, 1, '/api/v1/task/list', 'selectTaskList',  '과제 리스트 조회'),
                                                                                                                                                  (20302, 203, 'TASK', 1, 0, 0, 0, 1, 1, '/api/v1/task/{^[\\d]$}', 'selectTask',  '과제 조회'),
                                                                                                                                                  (20303, 203, 'TASK', 1, 0, 0, 0, 3, 1, '/api/v1/task/download', 'downloadTaskList',  '과제 리스트 다운로드'),
                                                                                                                                                  (20304, 203, 'TASK', 1, 0, 0, 1, 1, 1, '/api/v1/task/available', 'selectAvailableTaskList',  '사용 가능한 과제'),
                                                                                                                                                  (20305, 203, 'TASK', 1, 0, 0, 0, 3, 1, '/api/v1/task', 'createTask',  '과제 등록'),
                                                                                                                                                  (20306, 203, 'TASK', 1, 0, 0, 0, 4, 1, '/api/v1/task/{^[\\d]$}', 'updateTask',  '과제 수정'),
                                                                                                                                                  (20307, 203, 'TASK', 1, 0, 0, 0, 6, 1, '/api/v1/task/{^[\\d]$}', 'deleteTask',  '과제 삭제'),
                                                                                                                                                  (20308, 203, 'TASK', 1, 0, 0, 1, 1, 1, '/api/v1/task/script/{^[\\d]$}', 'selectScript',  '스크립트 조회'),
                                                                                                                                                  (20309, 203, 'TASK', 1, 0, 0, 1, 3, 1, '/api/v1/task/script/list', 'selectScriptList',  '스크립트 검색'),
                                                                                                                                                  (20310, 203, 'TASK', 1, 0, 0, 1, 1, 1, '/api/v1/task/script/{^[\\d]$}/history', 'selectScriptHistory',  '스크립트 수정 기록 조회'),

                                                                                                                                                  (20311, 203, 'TASK', 1, 1, 1, 0, 3, 1, '/api/v1/task/development/list', 'selectTaskDevelopmentList',  '과제 선정 리스트 조회'),
                                                                                                                                                  (20312, 203, 'TASK', 1, 1, 0, 0, 1, 1, '/api/v1/task/development/{^[\\d]$}', 'selectDevelopmentPetition',  '과제 선정 조회'),
                                                                                                                                                  (20313, 203, 'TASK', 1, 1, 0, 0, 3, 1, '/api/v1/task/development', 'createTaskPetitionWithOperate',  '과제 신규 등록 및 운영'),
                                                                                                                                                  (20314, 203, 'TASK', 1, 1, 0, 0, 4, 1, '/api/v1/task/development/{^[\\d]$}/operate', 'operateTaskPetition',  '과제 운영'),
                                                                                                                                                  (20315, 203, 'TASK', 1, 1, 0, 0, 4, 1, '/api/v1/task/development/{^[\\d]$}/scrap', 'scrapTaskPetition',  '과제 폐기'),
                                                                                                                                                  (20316, 203, 'TASK', 1, 1, 0, 0, 6, 1, '/api/v1/task/development/{^[\\d]$}', 'scrapAndDeleteTaskPetition',  '과제 폐기 및 삭제');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (20311, 1),
                                                         (20312, 1),
                                                         (20313, 1),
                                                         (20314, 1),
                                                         (20315, 1),
                                                         (20316, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (30201, 302, 'STATISTICS.TASK', 1, 1, 1, 0, 3, 1, '/api/v1/statistics/task', 'selectTaskSummary',  '과제 성공률 현황'),
                                                                                                                                                  (30202, 302, 'STATISTICS.TASK', 1, 1, 0, 0, 3, 1, '/api/v1/statistics/task/download', 'downloadTaskSummary',  '과제 성공률 현황 다운로드');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (30201, 1),
                                                         (30202, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (30301, 303, 'STATISTICS.DAILY', 1, 1, 1, 0, 3, 1, '/api/v1/statistics/daily', 'selectDailyTaskSummary',  '일별 과제 총합'),
                                                                                                                                                  (30302, 303, 'STATISTICS.DAILY', 1, 1, 0, 0, 3, 1, '/api/v1/statistics/daily/download', 'downloadDailyTaskSummary',  '일별 과제 총합 다운로드');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (30301, 1),
                                                         (30302, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (40101, 401, 'EXECUTION', 1, 1, 1, 0, 3, 1, '/api/v1/execution/list', 'selectExecutionList',  'RPA 실행 이력 조회'),
                                                                                                                                                  (40102, 401, 'EXECUTION', 1, 1, 0, 0, 3, 1, '/api/v1/execution/download', 'downloadExecutionList',  'RPA 실행 이력 다운로드'),
                                                                                                                                                  (40103, 401, 'EXECUTION', 1, 1, 0, 0, 1, 1, '/api/v1/execution/*', 'selectExecutionDetail',  'RPA 실행 이력 상세 조회'),
                                                                                                                                                  (40104, 401, 'EXECUTION', 1, 1, 0, 0, 1, 1, '/api/v1/execution/download/*', 'downloadExecutionDetail',  'RPA 실행 이력 상세 다운로드'),
                                                                                                                                                  (40105, 401, 'EXECUTION', 0, 0, 0, 0, 3, 1, '/api/v1/execution', 'createRpaExecutionList',  'RPA 실행이력 일괄 추가'),
                                                                                                                                                  (40106, 401, 'EXECUTION', 1, 1, 0, 0, 6, 1, '/api/v1/execution/*', 'deleteRpaExecution',  'RPA 실행 이력 삭제'),
                                                                                                                                                  (40107, 401, 'EXECUTION', 1, 1, 0, 0, 4, 1, '/api/v1/execution/*/result', 'updateRpaExecutionResult',  'RPA 실행 이력 처리건수 결과 수정');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (40101, 1),
                                                         (40102, 1),
                                                         (40104, 1),
                                                         (40106, 1),
                                                         (40107, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (40201, 402, 'RPA.SCHEDULE', 1, 1, 1, 0, 3, 1, '/api/v1/rpa/schedule/list', 'selectScheduleList',  'RPA 스케쥴 리스트 조회'),
                                                                                                                                                  (40202, 402, 'RPA.SCHEDULE', 1, 1, 0, 0, 1, 1, '/api/v1/rpa/schedule/{^[\\d]$}', 'selectSchedule',  'RPA 스케쥴 조회'),
                                                                                                                                                  (40203, 402, 'RPA.SCHEDULE', 1, 1, 0, 0, 3, 1, '/api/v1/rpa/schedule/download', 'downloadScheduleList',  'RPA 스케쥴 리스트 다운로드'),
                                                                                                                                                  (40204, 402, 'RPA.SCHEDULE', 1, 1, 0, 0, 3, 1, '/api/v1/rpa/schedule/timeline', 'selectScheduleTimeLineList',  '월별 스케쥴 타임라인'),
                                                                                                                                                  (40205, 402, 'RPA.SCHEDULE', 0, 0, 0, 0, 3, 1, '/api/v1/rpa/schedule/timeline/daily', 'selectDailyScheduleTimeLineList',  '일별 스케쥴 타임라인'),
                                                                                                                                                  (40206, 402, 'RPA.SCHEDULE', 1, 1, 0, 0, 3, 1, '/api/v1/rpa/schedule', 'createSchedule',  'RPA 스케쥴 등록'),
                                                                                                                                                  (40207, 402, 'RPA.SCHEDULE', 1, 1, 0, 0, 4, 1, '/api/v1/rpa/schedule/{^[\\d]$}', 'updateSchedule',  'RPA 스케쥴 수정'),
                                                                                                                                                  (40208, 402, 'RPA.SCHEDULE', 1, 1, 0, 0, 6, 1, '/api/v1/rpa/schedule/{^[\\d]$}', 'deleteSchedule',  'RPA 스케쥴 삭제'),
                                                                                                                                                  (40209, 402, 'RPA.SCHEDULE', 1, 0, 0, 1, 1, 1, '/api/v1/rpa/schedule/agent/list', 'selectScheduledAgentList',  'RPA 스케쥴링 가능 Agent 리스트'),
                                                                                                                                                  (40211, 402, 'RPA.SCHEDULE', 1, 1, 0, 0, 3, 1, '/api/v1/rpa/schedule/runnow/{^[\\d]$}', 'runScheduleNow',  'RPA 스케쥴 즉시 실행'),
                                                                                                                                                  (40212, 402, 'RPA.SCHEDULE', 1, 1, 0, 0, 1, 1, '/api/v1/rpa/schedule/sync', 'syncSchedule',  'RPA 스케쥴 동기화');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (40201, 1),
                                                         (40202, 1),
                                                         (40203, 1),
                                                         (40204, 1),
                                                         (40205, 1),
                                                         (40206, 1),
                                                         (40207, 1),
                                                         (40208, 1),
                                                         (40211, 1),
                                                         (40212, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (40301, 403, 'RPA.FAILURE', 1, 1, 1, 0, 3, 1, '/api/v1/rpa/failure/list', 'selectFailureManeuverList',  'RPA 장애 리스트 조회'),
                                                                                                                                                  (40302, 403, 'RPA.FAILURE', 1, 1, 0, 0, 1, 1, '/api/v1/rpa/failure/{^[\\d]$}', 'selectFailureManeuver',  'RPA 장애 조회'),
                                                                                                                                                  (40303, 403, 'RPA.FAILURE', 1, 1, 0, 0, 3, 1, '/api/v1/rpa/failure/download', 'downloadFailureManeuverList',  'RPA 장애 리스트 다운로드'),
                                                                                                                                                  (40304, 403, 'RPA.FAILURE', 1, 1, 0, 0, 6, 1, '/api/v1/rpa/failure/{^[\\d]$}', 'deleteFailureManeuver',  'RPA 장애 삭제'),
                                                                                                                                                  (40305, 403, 'RPA.FAILURE', 1, 1, 0, 0, 4, 1, '/api/v1/rpa/failure/{^[\\d]$}', 'upsertFailureManeuver',  'RPA 장애 대응 등록');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (40301, 1),
                                                         (40302, 1),
                                                         (40303, 1),
                                                         (40304, 1),
                                                         (40305, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (40401, 404, 'TASK.DEPLOY', 1, 1, 1, 0, 3, 1, '/api/v1/task/deploy/list', 'selectTaskDeployList',  '수행요청 리스트 조회'),
                                                                                                                                                  (40402, 404, 'TASK.DEPLOY', 1, 1, 0, 0, 1, 1, '/api/v1/task/deploy/{^[\\d]$}', 'selectTaskDeploy',  '수행요청 정보 조회'),
                                                                                                                                                  (40403, 404, 'TASK.DEPLOY', 1, 1, 0, 0, 3, 1, '/api/v1/task/deploy/list/download', 'downloadTaskDeployList',  '수행요청 리스트 다운로드'),
                                                                                                                                                  (40404, 404, 'TASK.DEPLOY', 1, 1, 0, 0, 3, 1, '/api/v1/task/deploy', 'createTaskDeploy',  '수행요청 등록'),
                                                                                                                                                  (40405, 404, 'TASK.DEPLOY', 1, 1, 0, 0, 4, 1, '/api/v1/task/deploy/{^[\\d]$}', 'updateTaskDeploy',  '수행요청 수정'),
                                                                                                                                                  (40406, 404, 'TASK.DEPLOY', 1, 1, 0, 0, 6, 1, '/api/v1/task/deploy/{^[\\d]$}', 'deleteTaskDeploy',  '수행요청 삭제'),
                                                                                                                                                  (40407, 404, 'TASK.DEPLOY', 1, 1, 0, 0, 3, 1, '/api/v1/task/deploy/history/list', 'selectTaskDeployHistoryList',  '수행요청이력 리스트 조회'),
                                                                                                                                                  (40408, 404, 'TASK.DEPLOY', 1, 1, 0, 0, 3, 1, '/api/v1/task/deploy/history/list/download', 'downloadTaskDeployHistoryList',  '수행요청이력 리스트 다운로드'),
                                                                                                                                                  (40409, 404, 'TASK.DEPLOY', 1, 0, 0, 1, 3, 1, '/api/v1/task/deploy/files/{^[\\d]$}', 'uploadTaskDeployFile',  '수행 파일 업로드'),
                                                                                                                                                  (40410, 404, 'TASK.DEPLOY', 1, 0, 0, 1, 6, 1, '/api/v1/task/deploy/files/{^[\\d]$}/*', 'deleteTaskDeployFile',  '수행 파일 삭제'),
                                                                                                                                                  (40411, 404, 'TASK.DEPLOY', 1, 0, 0, 1, 1, 1, '/api/v1/task/deploy/files/{^[\\d]$}/*', 'downloadTaskDeployFile',  '수행 파일 다운로드');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (40401, 1),
                                                         (40402, 1),
                                                         (40403, 1),
                                                         (40404, 1),
                                                         (40405, 1),
                                                         (40406, 1),
                                                         (40407, 1),
                                                         (40408, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (50101, 501, 'MONITOR.AGENT', 1, 1, 1, 0, 1, 1, '/api/v1/monitor/agent/status', 'selectAllAgentStatus',  'AGENT 실시간 수행 정보'),
                                                                                                                                                  (50102, 501, 'MONITOR.AGENT', 1, 1, 0, 0, 1, 1, '/api/v1/monitor/agent/status/{^[\\d]$}', 'selectAgentStatus',  'AGENT 장비의 모니터링 정보 조회'),
                                                                                                                                                  (50103, 501, 'MONITOR.AGENT', 1, 0, 0, 0, 4, 1, '/api/v1/monitor/agent/status/{^[\\d]$}/record', 'updateAgentStatusRecordYn',  'AGENT 장비의 모니터링 기록모드 설정'),
                                                                                                                                                  (50104, 501, 'MONITOR.AGENT', 1, 0, 0, 0, 3, 1, '/api/v1/monitor/agent/utilization', 'selectAgentUtilizationRatio',  'AGENT 가동률'),
                                                                                                                                                  (50105, 501, 'MONITOR.AGENT', 1, 0, 0, 0, 3, 1, '/api/v1/monitor/agent/utilization/download', 'downloadAgentUtilizationRatio',  'AGENT 가동률 다운로드'),
                                                                                                                                                  (50106, 501, 'MONITOR.AGENT', 0, 0, 0, 0, 3, 1, '/api/v1/monitor/agent/summary/date', 'selectSummaryByDate',  'AGENT 성공률 현황 (날짜 기준'),
                                                                                                                                                  (50107, 501, 'MONITOR.AGENT', 0, 0, 0, 0, 1, 1, '/api/v1/monitor/agent/schedule/{^[\\d]$}', 'selectAgentScheduleList',  'AGENT 별 스케쥴 리스트'),
                                                                                                                                                  (50108, 501, 'MONITOR.AGENT', 0, 0, 0, 0, 1, 1, '/api/v1/monitor/agent/usage', 'selectAgentUsageRateList',  'AGENT 사용률 (금일 0시 기준)'),
                                                                                                                                                  (50109, 501, 'MONITOR.AGENT', 1, 0, 0, 1, 1, 1, '/api/v1/monitor/agent/status/sse', 'selectAllAgentStatusSSE',  'AGENT 장비의 모니터링 정보 (SSE)');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (50101, 1),
                                                         (50102, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
    (50201, 502, 'MONITOR.TASK', 1, 1, 1, 0, 1, 1, '/api/v1/monitor/task', 'selectAllTaskMonitor',  '실시간 과제 실행 정보');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
    (50201, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (69901, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 1, 1, 1, '/api/v1/board-management/type', 'selectBoardTypeList',  '게시판 타입 리스트 조회'),
                                                                                                                                                  (69902, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 3, 1, '/api/v1/board-management/type', 'createBoardType',  '게시판 타입 생성'),
                                                                                                                                                  (69903, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 6, 1, '/api/v1/board-management/type', 'deleteBoardType',  '게시판 타입 삭제'),
                                                                                                                                                  (69904, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 1, 1, 1, '/api/v1/board-management/category', 'selectBoardCategoryList',  '게시판 카테고리 리스트 조회'),
                                                                                                                                                  (69905, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 3, 1, '/api/v1/board-management/category', 'createBoardCategory',  '게시판 카테고리 생성'),
                                                                                                                                                  (69906, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 6, 1, '/api/v1/board-management/category', 'deleteBoardCategory',  '게시판 카테고리 삭제'),
                                                                                                                                                  (69907, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 1, 1, 1, '/api/v1/board-management/reply/category', 'selectReplyCategoryList',  '댓글 타입 리스트 조회"'),
                                                                                                                                                  (69908, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 3, 1, '/api/v1/board-management/reply/category', 'createReplyCategory',  '댓글 타입 생성'),
                                                                                                                                                  (69909, 699, 'BOARD.MANAGEMENT', 1, 1, 0, 0, 6, 1, '/api/v1/board-management/reply/category', 'deleteReplyCategory',  '댓글 타입 삭제');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (69902, 1),
                                                         (69903, 1),
                                                         (69905, 1),
                                                         (69906, 1),
                                                         (69908, 1),
                                                         (69909, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (60101, 601, 'BOARD.NOTICE', 1, 1, 1, 0, 3, 1, '/api/v1/board/notice/list', 'selectBoardList-notice',  '게시판 리스트 조회'),
                                                                                                                                                  (60102, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 1, 1, '/api/v1/board/notice/{^[\\d]$}', 'selectBoard-notice',  '게시글 조회'),
                                                                                                                                                  (60103, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 3, 1, '/api/v1/board/notice', 'createBoard-notice',  '게시글 생성'),
                                                                                                                                                  (60104, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 4, 1, '/api/v1/board/notice/{^[\\d]$}', 'updateBoard-notice',  '게시글  업데이트'),
                                                                                                                                                  (60105, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 6, 1, '/api/v1/board/notice/{^[\\d]$}', 'deleteBoard-notice',  '게시글 삭제'),
                                                                                                                                                  (60106, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 4, 1, '/api/v1/board/notice/{^[\\d]$}/like', 'likeBoard-notice',  '게시글의 추천 & 취소'),
                                                                                                                                                  (60107, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 1, 1, '/api/v1/board/notice/{^[\\d]$}/reply', 'selectReplyList-notice',  '게시글 댓글 리스트"'),
                                                                                                                                                  (60108, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 3, 1, '/api/v1/board/notice/{^[\\d]$}/reply', 'createReply-notice',  '댓글 생성'),
                                                                                                                                                  (60109, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 4, 1, '/api/v1/board/notice/reply/{^[\\d]$}', 'updateReply-notice',  '댓글 업데이트'),
                                                                                                                                                  (60110, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 6, 1, '/api/v1/board/notice/reply/{^[\\d]$}', 'deleteReply-notice',  '댓글 삭제'),
                                                                                                                                                  (60111, 601, 'BOARD.NOTICE', 1, 1, 0, 0, 4, 1, '/api/v1/board/notice/reply/{^[\\d]$}/like', 'likeReply-notice',  '댓글의 추천/취소');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (60101, 1),
                                                         (60102, 1),
                                                         (60103, 1),
                                                         (60104, 1),
                                                         (60105, 1),
                                                         (60106, 1),
                                                         (60107, 1),
                                                         (60108, 1),
                                                         (60109, 1),
                                                         (60110, 1),
                                                         (60111, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (60201, 602, 'BOARD.ARCHIVES', 1, 1, 1, 0, 3, 1, '/api/v1/board/archives/list', 'selectBoardList-archives',  '게시판 리스트 조회'),
                                                                                                                                                  (60202, 602, 'BOARD.ARCHIVES', 1, 1, 0, 0, 1, 1, '/api/v1/board/archives/{^[\\d]$}', 'selectBoard-archives',  '게시글 조회'),
                                                                                                                                                  (60203, 602, 'BOARD.ARCHIVES', 1, 1, 0, 0, 3, 1, '/api/v1/board/archives', 'createBoard-archives',  '게시글 생성'),
                                                                                                                                                  (60204, 602, 'BOARD.ARCHIVES', 1, 1, 0, 0, 4, 1, '/api/v1/board/archives/{^[\\d]$}', 'updateBoard-archives',  '게시글  업데이트'),
                                                                                                                                                  (60205, 602, 'BOARD.ARCHIVES', 1, 1, 0, 0, 6, 1, '/api/v1/board/archives/{^[\\d]$}', 'deleteBoard-archives',  '게시글 삭제'),
                                                                                                                                                  (60206, 602, 'BOARD.ARCHIVES', 1, 1, 0, 0, 4, 1, '/api/v1/board/archives/{^[\\d]$}/like', 'likeBoard-archives',  '게시글의 추천 & 취소'),
                                                                                                                                                  (60207, 602, 'BOARD.ARCHIVES', 1, 1, 0, 0, 1, 1, '/api/v1/board/archives/{^[\\d]$}/reply', 'selectReplyList-archives',  '게시글 댓글 리스트"'),
                                                                                                                                                  (60208, 602, 'BOARD.ARCHIVES', 1, 1, 0, 0, 3, 1, '/api/v1/board/archives/{^[\\d]$}/reply', 'createReply-archives',  '댓글 생성'),
                                                                                                                                                  (60209, 602, 'BOARD.ARCHIVES', 1, 1, 0, 0, 4, 1, '/api/v1/board/archives/reply/{^[\\d]$}', 'updateReply-archives',  '댓글 업데이트'),
                                                                                                                                                  (60210, 602, 'BOARD.ARCHIVES', 1, 1, 0, 0, 6, 1, '/api/v1/board/archives/reply/{^[\\d]$}', 'deleteReply-archives',  '댓글 삭제'),
                                                                                                                                                  (60211, 602, 'BOARD.ARCHIVES', 1, 1, 0, 0, 4, 1, '/api/v1/board/archives/reply/{^[\\d]$}/like', 'likeReply-archives',  '댓글의 추천/취소');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (60201, 1),
                                                         (60202, 1),
                                                         (60203, 1),
                                                         (60204, 1),
                                                         (60205, 1),
                                                         (60206, 1),
                                                         (60207, 1),
                                                         (60208, 1),
                                                         (60209, 1),
                                                         (60210, 1),
                                                         (60211, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (60301, 603, 'BOARD.FAQ', 1, 1, 1, 0, 3, 1, '/api/v1/board/faq/list', 'selectBoardList-faq',  '게시판 리스트 조회'),
                                                                                                                                                  (60302, 603, 'BOARD.FAQ', 1, 1, 0, 0, 1, 1, '/api/v1/board/faq/{^[\\d]$}', 'selectBoard-faq',  '게시글 조회'),
                                                                                                                                                  (60303, 603, 'BOARD.FAQ', 1, 1, 0, 0, 3, 1, '/api/v1/board/faq', 'createBoard-faq',  '게시글 생성'),
                                                                                                                                                  (60304, 603, 'BOARD.FAQ', 1, 1, 0, 0, 4, 1, '/api/v1/board/faq/{^[\\d]$}', 'updateBoard-faq',  '게시글  업데이트'),
                                                                                                                                                  (60305, 603, 'BOARD.FAQ', 1, 1, 0, 0, 6, 1, '/api/v1/board/faq/{^[\\d]$}', 'deleteBoard-faq',  '게시글 삭제'),
                                                                                                                                                  (60306, 603, 'BOARD.FAQ', 1, 1, 0, 0, 4, 1, '/api/v1/board/faq/{^[\\d]$}/like', 'likeBoard-faq',  '게시글의 추천 & 취소'),
                                                                                                                                                  (60307, 603, 'BOARD.FAQ', 1, 1, 0, 0, 1, 1, '/api/v1/board/faq/{^[\\d]$}/reply', 'selectReplyList-faq',  '게시글 댓글 리스트"'),
                                                                                                                                                  (60308, 603, 'BOARD.FAQ', 1, 1, 0, 0, 3, 1, '/api/v1/board/faq/{^[\\d]$}/reply', 'createReply-faq',  '댓글 생성'),
                                                                                                                                                  (60309, 603, 'BOARD.FAQ', 1, 1, 0, 0, 4, 1, '/api/v1/board/faq/reply/{^[\\d]$}', 'updateReply-faq',  '댓글 업데이트'),
                                                                                                                                                  (60310, 603, 'BOARD.FAQ', 1, 1, 0, 0, 6, 1, '/api/v1/board/faq/reply/{^[\\d]$}', 'deleteReply-faq',  '댓글 삭제'),
                                                                                                                                                  (60311, 603, 'BOARD.FAQ', 1, 1, 0, 0, 4, 1, '/api/v1/board/faq/reply/{^[\\d]$}/like', 'likeReply-faq',  '댓글의 추천/취소');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (60301, 1),
                                                         (60302, 1),
                                                         (60303, 1),
                                                         (60304, 1),
                                                         (60305, 1),
                                                         (60306, 1),
                                                         (60307, 1),
                                                         (60308, 1),
                                                         (60309, 1),
                                                         (60310, 1),
                                                         (60311, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (60401, 604, 'BOARD.QUESTION', 1, 1, 1, 0, 3, 1, '/api/v1/board/question/list', 'selectBoardList-question',  '게시판 리스트 조회'),
                                                                                                                                                  (60402, 604, 'BOARD.QUESTION', 1, 1, 0, 0, 1, 1, '/api/v1/board/question/{^[\\d]$}', 'selectBoard-question',  '게시글 조회'),
                                                                                                                                                  (60403, 604, 'BOARD.QUESTION', 1, 1, 0, 0, 3, 1, '/api/v1/board/question', 'createBoard-question',  '게시글 생성'),
                                                                                                                                                  (60404, 604, 'BOARD.QUESTION', 1, 1, 0, 0, 4, 1, '/api/v1/board/question/{^[\\d]$}', 'updateBoard-question',  '게시글  업데이트'),
                                                                                                                                                  (60405, 604, 'BOARD.QUESTION', 1, 1, 0, 0, 6, 1, '/api/v1/board/question/{^[\\d]$}', 'deleteBoard-question',  '게시글 삭제'),
                                                                                                                                                  (60406, 604, 'BOARD.QUESTION', 1, 1, 0, 0, 4, 1, '/api/v1/board/question/{^[\\d]$}/like', 'likeBoard-question',  '게시글의 추천 & 취소'),
                                                                                                                                                  (60407, 604, 'BOARD.QUESTION', 1, 1, 0, 0, 1, 1, '/api/v1/board/question/{^[\\d]$}/reply', 'selectReplyList-question',  '게시글 댓글 리스트"'),
                                                                                                                                                  (60408, 604, 'BOARD.QUESTION', 1, 1, 0, 0, 3, 1, '/api/v1/board/question/{^[\\d]$}/reply', 'createReply-question',  '댓글 생성'),
                                                                                                                                                  (60409, 604, 'BOARD.QUESTION', 1, 1, 0, 0, 4, 1, '/api/v1/board/question/reply/{^[\\d]$}', 'updateReply-question',  '댓글 업데이트'),
                                                                                                                                                  (60410, 604, 'BOARD.QUESTION', 1, 1, 0, 0, 6, 1, '/api/v1/board/question/reply/{^[\\d]$}', 'deleteReply-question',  '댓글 삭제'),
                                                                                                                                                  (60411, 604, 'BOARD.QUESTION', 1, 1, 0, 0, 4, 1, '/api/v1/board/question/reply/{^[\\d]$}/like', 'likeReply-question',  '댓글의 추천/취소');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (60401, 1),
                                                         (60402, 1),
                                                         (60403, 1),
                                                         (60404, 1),
                                                         (60405, 1),
                                                         (60406, 1),
                                                         (60407, 1),
                                                         (60408, 1),
                                                         (60409, 1),
                                                         (60410, 1),
                                                         (60411, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (70001, 700, 'TENANT', 1, 0, 0, 1, 1, 0, '/api/v1/tenant/public', 'selectPublicTenantList',  '공개 Tenant 리스트 조회'),
                                                                                                                                                  (70002, 700, 'TENANT', 1, 0, 0, 0, 1, 1, '/api/v1/tenant/switchable', 'selectSwitchableTenantList',  '전환 가능한 Tenant 리스트 조회'),
                                                                                                                                                  (70003, 700, 'TENANT', 1, 0, 1, 0, 1, 1, '/api/v1/tenant/list', 'selectTenantList',  'Tenant 리스트 조회'),
                                                                                                                                                  (70004, 700, 'TENANT', 1, 0, 0, 0, 1, 1, '/api/v1/tenant/{^[\\d]$}', 'selectTenant',  'Tenant 조회'),
                                                                                                                                                  (70005, 700, 'TENANT', 1, 0, 0, 0, 3, 1, '/api/v1/tenant', 'createTenant',  'Tenant 등록'),
                                                                                                                                                  (70006, 700, 'TENANT', 1, 0, 0, 0, 4, 1, '/api/v1/tenant/{^[\\d]$}', 'updateTenant',  'Tenant 수정'),
                                                                                                                                                  (70007, 700, 'TENANT', 1, 0, 0, 0, 6, 1, '/api/v1/tenant/{^[\\d]$}', 'deleteTenant',  'Tenant 삭제'),
                                                                                                                                                  (70008, 700, 'TENANT', 1, 0, 0, 0, 4, 1, '/api/v1/tenant/{^[\\d]$}/switch', 'switchTenant',  'Tenant 전환');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (70003, 1),
                                                         (70004, 1),
                                                         (70005, 1),
                                                         (70006, 1),
                                                         (70007, 1),
                                                         (70008, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (70101, 701, 'USER', 1, 1, 1, 1, 3, 1, '/api/v1/userEntity/list', 'selectUserList',  '유저 리스트 조회'),
                                                                                                                                                  (70102, 701, 'USER', 1, 0, 0, 1, 1, 1, '/api/v1/userEntity/{^[\\d]$}', 'selectUser',  '특정 유저정보 조회'),
                                                                                                                                                  (70103, 701, 'USER', 1, 0, 0, 1, 1, 1, '/api/v1/userEntity/me', 'selectMe',  '내 정보 조회'),
                                                                                                                                                  (70104, 701, 'USER', 1, 0, 0, 1, 1, 1, '/api/v1/userEntity/exists/*', 'isUserIdExist',  '유저 ID  중복확인'),
                                                                                                                                                  (70105, 701, 'USER', 1, 1, 0, 0, 3, 1, '/api/v1/userEntity', 'createUser',  '유저 등록'),
                                                                                                                                                  (70106, 701, 'USER', 1, 1, 0, 0, 4, 1, '/api/v1/userEntity/{^[\\d]$}', 'updateUser',  '유저 정보 변경'),
                                                                                                                                                  (70107, 701, 'USER', 1, 1, 0, 0, 6, 1, '/api/v1/userEntity/{^[\\d]$}', 'deleteUser',  '유저 삭제'),
                                                                                                                                                  (70108, 701, 'USER', 0, 0, 0, 0, 3, 1, '/api/v1/userEntity/{^[\\d]$}/validation/', 'requestValidation',  '유저 이메일 인증 요청'),
                                                                                                                                                  (70109, 701, 'USER', 0, 0, 0, 0, 1, 1, '/api/v1/userEntity/validation/*', 'userValidation',  '유저 이메일 인증'),
                                                                                                                                                  (70110, 701, 'USER', 0, 0, 0, 0, 1, 1, '/api/v1/userEntity/{^[\\d]$}/requestPasswordReset', 'requestPasswordReset',  '패스워드 변경 요청'),
                                                                                                                                                  (70111, 701, 'USER', 0, 0, 0, 0, 4, 1, '/api/v1/userEntity/updatePassword', 'updatePassword',  '패스워드 변경 처리'),
                                                                                                                                                  (70112, 701, 'USER', 0, 0, 0, 0, 1, 1, '/api/v1/{^[\\d]$}/tempPassword', 'requestTempPasswordReset',  '임시 패스워드 발급'),
                                                                                                                                                  (70121, 701, 'USER.EMPLOYEE', 0, 1, 0, 1, 3, 1, '/api/v1/employee/list', 'selectEmployeeList',  '사원 리스트 조회'),
                                                                                                                                                  (70122, 701, 'USER.EMPLOYEE', 0, 1, 0, 1, 1, 1, '/api/v1/employee/{^[\\d]$}', 'selectEmployee',  '사원 조회'),
                                                                                                                                                  (70123, 701, 'USER.EMPLOYEE', 0, 1, 0, 0, 3, 1, '/api/v1/employee/list/download', 'downloadEmployeeList',  '사원 리스트 파일 다운로드'),
                                                                                                                                                  (70124, 701, 'USER.EMPLOYEE', 0, 1, 0, 1, 1, 1, '/api/v1/employee/exists/*', 'isEmployeeNumberExist',  '사원번호 중복확인'),
                                                                                                                                                  (70125, 701, 'USER.EMPLOYEE', 0, 1, 0, 0, 3, 1, '/api/v1/employee', 'createEmployee',  '사원 등록'),
                                                                                                                                                  (70126, 701, 'USER.EMPLOYEE', 0, 1, 0, 0, 4, 1, '/api/v1/employee/{^[\\d]$}', 'updateEmployee',  '사원 수정'),
                                                                                                                                                  (70127, 701, 'USER.EMPLOYEE', 0, 1, 0, 0, 6, 1, '/api/v1/employee/{^[\\d]$}', 'deleteEmployee',  '사원 삭제'),
                                                                                                                                                  (70128, 701, 'USER.EMPLOYEE', 0, 0, 0, 0, 3, 1, '/api/v1/employee/subscribe/list', 'selectSubscribeList',  '계정 신청 리스트 조회'),
                                                                                                                                                  (70129, 701, 'USER.EMPLOYEE', 0, 0, 0, 0, 1, 1, '/api/v1/employee/subscribe/{^[\\d]$}', 'selectSubscribe',  '계정 신청 조회'),
                                                                                                                                                  (70130, 701, 'USER.EMPLOYEE', 0, 0, 0, 0, 3, 1, '/api/v1/employee/subscribe', 'createSubscribe',  '계정 신청'),
                                                                                                                                                  (70131, 701, 'USER.EMPLOYEE', 0, 0, 0, 0, 4, 1, '/api/v1/employee/subscribe/{^[\\d]$', 'acceptSubscribe',  '계정 신청 처리');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (70101, 1),
                                                         (70102, 1),
                                                         (70104, 1),
                                                         (70105, 1),
                                                         (70106, 1),
                                                         (70107, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (70201, 702, 'ROLE', 1, 1, 1, 0, 3, 1, '/api/v1/role/list', 'selectRoleList',  '권한 리스트 조회'),
                                                                                                                                                  (70202, 702, 'ROLE', 1, 0, 0, 0, 1, 1, '/api/v1/role/all', 'selectAllRoleList',  '모든 권한 정보 조회 (관리자 전용)'),
                                                                                                                                                  (70203, 702, 'ROLE', 1, 1, 0, 0, 1, 1, '/api/v1/role/{^[\\d]$}', 'selectRole',  '권한 조회'),
                                                                                                                                                  (70204, 702, 'ROLE', 1, 1, 0, 0, 3, 1, '/api/v1/role/list/download', 'downloadRoleList',  '권한 리스트 다운로드'),
                                                                                                                                                  (70205, 702, 'ROLE', 1, 0, 0, 1, 1, 1, '/api/v1/role/me', 'selectMyRoles',  '내 권한 조회'),
                                                                                                                                                  (70206, 702, 'ROLE', 1, 1, 0, 0, 3, 1, '/api/v1/role', 'createRole',  '권한 등록'),
                                                                                                                                                  (70207, 702, 'ROLE', 1, 1, 0, 0, 4, 1, '/api/v1/role/{^[\\d]$}', 'updateRole',  '권한 수정'),
                                                                                                                                                  (70208, 702, 'ROLE', 1, 1, 0, 0, 6, 1, '/api/v1/role/{^[\\d]$}', 'deleteRole',  '권한 삭제'),
                                                                                                                                                  (70209, 702, 'ROLE', 1, 0, 0, 1, 1, 1, '/api/v1/role/api', 'selectAllApiList',  '전체 API 정보 조회');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (70201, 1),
                                                         (70203, 1),
                                                         (70204, 1),
                                                         (70206, 1),
                                                         (70207, 1),
                                                         (70208, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (70301, 703, 'TEAM', 1, 1, 1, 1, 3, 1, '/api/v1/teamEntity/list', 'selectTeamList',  '부서 전체 리스트 조회'),
                                                                                                                                                  (70302, 703, 'TEAM', 1, 1, 0, 1, 1, 1, '/api/v1/teamEntity/{^[\\d]$}', 'selectTeam',  '부서 조회'),
                                                                                                                                                  (70303, 703, 'TEAM', 1, 1, 0, 0, 3, 1, '/api/v1/teamEntity', 'createTeam',  '부서 등록'),
                                                                                                                                                  (70304, 703, 'TEAM', 1, 1, 0, 0, 4, 1, '/api/v1/teamEntity/{^[\\d]$}', 'updateTeam',  '부서 수정'),
                                                                                                                                                  (70305, 703, 'TEAM', 1, 1, 0, 0, 6, 1, '/api/v1/teamEntity/{^[\\d]$}', 'deleteTeam',  '부서 삭제'),
                                                                                                                                                  (70306, 703, 'TEAM', 0, 0, 0, 0, 3, 1, '/api/v1/teamEntity/bulk', 'createTeamBulk',  '부서 일괄 등록');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (70301, 1),
                                                         (70302, 1),
                                                         (70303, 1),
                                                         (70304, 1),
                                                         (70305, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (70401, 704, 'RPA.AGENT', 1, 1, 1, 0, 1, 1, '/api/v1/rpa/agent/list', 'selectAgentList',  'RPA AGENT 리스트 조회'),
                                                                                                                                                  (70402, 704, 'RPA.AGENT', 1, 1, 0, 0, 1, 1, '/api/v1/rpa/agent/{^[\\d]$}', 'selectAgent',  'RPA AGENT 조회'),
                                                                                                                                                  (70403, 704, 'RPA.AGENT', 1, 1, 0, 0, 1, 1, '/api/v1/rpa/agent/list/download', 'downloadAgentList',  'RPA AGENT 리스트 다운로드'),
                                                                                                                                                  (70404, 704, 'RPA.AGENT', 1, 1, 0, 0, 3, 1, '/api/v1/rpa/agent', 'createAgent',  'RPA AGENT 등록'),
                                                                                                                                                  (70405, 704, 'RPA.AGENT', 1, 1, 0, 0, 4, 1,  '/api/v1/rpa/agent/{^[\\d]$}', 'updateAgent',  'RPA AGENT 수정'),
                                                                                                                                                  (70406, 704, 'RPA.AGENT', 1, 1, 0, 0, 6, 1, '/api/v1/rpa/agent/{^[\\d]$}', 'deleteAgent',  'RPA AGENT 삭제'),
                                                                                                                                                  (70407, 704, 'RPA.AGENT', 1, 0, 0, 1, 1, 1, '/api/v1/rpa/agent/available', 'selectAvailableAgentList',  '사용 가능한 RPA AGENT 리스트 조회'),
                                                                                                                                                  (70408, 704, 'RPA.AGENT', 1, 0, 0, 1, 1, 1, '/api/v1/rpa/agent/runner/available', 'selectAvailableRunnerList',  '등록 가능한 RPA Runner 계정 리스트 조회');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (70401, 1),
                                                         (70402, 1),
                                                         (70403, 1),
                                                         (70404, 1),
                                                         (70405, 1),
                                                         (70406, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (70501, 705, 'WORKINGDAY', 1, 1, 1, 0, 1, 1, '/api/v1/workingday/today', 'selectThisWorkingDay',  '금일 영업일 조회'),
                                                                                                                                                  (70502, 705, 'WORKINGDAY', 1, 1, 0, 0, 1, 1, '/api/v1/workingday', 'selectHolidayList',  '휴일 리스트 조회'),
                                                                                                                                                  (70503, 705, 'WORKINGDAY', 1, 1, 0, 0, 3, 1, '/api/v1/workingday', 'createHoliday',  '휴일 등록'),
                                                                                                                                                  (70504, 705, 'WORKINGDAY', 1, 1, 0, 0, 4, 1, '/api/v1/workingday/{^[\\d]$}', 'updateHoliday',  '휴일 수정'),
                                                                                                                                                  (70505, 705, 'WORKINGDAY', 1, 1, 0, 0, 6, 1, '/api/v1/workingday/{^[\\d]$}', 'deleteHoliday',  '휴일 삭제'),
                                                                                                                                                  (70506, 705, 'WORKINGDAY', 1, 1, 0, 0, 1, 1, '/api/v1/workingday/download', 'downloadHolidayList',  '휴일 다운로드'),
                                                                                                                                                  (70507, 705, 'WORKINGDAY', 1, 1, 0, 0, 3, 1, '/api/v1/workingday/upload', 'uploadHolidayBulk',  '휴일 일괄 등록');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (70501, 1),
                                                         (70502, 1),
                                                         (70503, 1),
                                                         (70504, 1),
                                                         (70505, 1),
                                                         (70506, 1),
                                                         (70507, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (70601, 706, 'AUDIT', 1, 1, 1, 0, 3, 1, '/api/v1/audit/list', 'selectAuditLog',  '요청 기록 조회'),
                                                                                                                                                  (70602, 706, 'AUDIT', 1, 1, 0, 0, 3, 1, '/api/v1/audit/list/download', 'downloadAuditLog',  '요청 기록 다운로드');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (70601, 1),
                                                         (70602, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (70701, 707, 'SETTING', 1, 0, 0, 1, 1, 1, '/api/v1/setting/all', 'selectAllSetting',  '관리자 설정 값 전체조회'),
                                                                                                                                                  (70702, 707, 'SETTING', 1, 0, 0, 1, 1, 1, '/api/v1/setting/*', 'selectSetting',  '관리자 설정 값 조회'),
                                                                                                                                                  (70703, 707, 'SETTING', 1, 1, 1, 0, 4, 1, '/api/v1/setting/*', 'upsertSetting',  '관리자 설정 값 등록');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
    (70703, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (70901, 709, 'DATASET', 1, 1, 1, 1, 1, 1, '/api/v1/dataset/list', 'selectGlobalDataSet',  '전체 Global DataSet 리스트 조회'),
                                                                                                                                                  (70902, 709, 'DATASET', 1, 1, 0, 0, 4, 1, '/api/v1/dataset', 'upsertGlobalDataSet',  'Global DataSet 등록'),
                                                                                                                                                  (70903, 709, 'DATASET', 1, 1, 0, 1, 3, 1, '/api/v1/dataset/load', 'loadDataSet',  'DataSet 조회');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
    (70902, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (80101, 801, 'BOARD.RPA-ARCHIVES', 1, 1, 1, 0, 3, 1, '/api/v1/board/rpa-archives/list', 'selectBoardList-rpa-archives',  '게시판 리스트 조회'),
                                                                                                                                                  (80102, 801, 'BOARD.RPA-ARCHIVES', 1, 1, 0, 0, 1, 1, '/api/v1/board/rpa-archives/{^[\\d]$}', 'selectBoard-rpa-archives',  '게시글 조회'),
                                                                                                                                                  (80103, 801, 'BOARD.RPA-ARCHIVES', 1, 1, 0, 0, 3, 1, '/api/v1/board/rpa-archives', 'createBoard-rpa-archives',  '게시글 생성'),
                                                                                                                                                  (80104, 801, 'BOARD.RPA-ARCHIVES', 1, 1, 0, 0, 4, 1, '/api/v1/board/rpa-archives/{^[\\d]$}', 'updateBoard-rpa-archives',  '게시글  업데이트'),
                                                                                                                                                  (80105, 801, 'BOARD.RPA-ARCHIVES', 1, 1, 0, 0, 6, 1, '/api/v1/board/rpa-archives/{^[\\d]$}', 'deleteBoard-rpa-archives',  '게시글 삭제'),
                                                                                                                                                  (80106, 801, 'BOARD.RPA-ARCHIVES', 1, 1, 0, 0, 4, 1, '/api/v1/board/rpa-archives/{^[\\d]$}/like', 'likeBoard-rpa-archives',  '게시글의 추천 & 취소'),
                                                                                                                                                  (80107, 801, 'BOARD.RPA-ARCHIVES', 1, 1, 0, 0, 1, 1, '/api/v1/board/rpa-archives/{^[\\d]$}/reply', 'selectReplyList-rpa-archives',  '게시글 댓글 리스트"'),
                                                                                                                                                  (80108, 801, 'BOARD.RPA-ARCHIVES', 1, 1, 0, 0, 3, 1, '/api/v1/board/rpa-archives/{^[\\d]$}/reply', 'createReply-rpa-archives',  '댓글 생성'),
                                                                                                                                                  (80109, 801, 'BOARD.RPA-ARCHIVES', 1, 1, 0, 0, 4, 1, '/api/v1/board/rpa-archives/reply/{^[\\d]$}', 'updateReply-rpa-archives',  '댓글 업데이트'),
                                                                                                                                                  (80110, 801, 'BOARD.RPA-ARCHIVES', 1, 1, 0, 0, 6, 1, '/api/v1/board/rpa-archives/reply/{^[\\d]$}', 'deleteReply-rpa-archives',  '댓글 삭제'),
                                                                                                                                                  (80111, 801, 'BOARD.RPA-ARCHIVES', 1, 1, 0, 0, 4, 1, '/api/v1/board/rpa-archives/reply/{^[\\d]$}/like', 'likeReply-rpa-archives',  '댓글의 추천/취소');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (80101, 1),
                                                         (80102, 1),
                                                         (80103, 1),
                                                         (80104, 1),
                                                         (80105, 1),
                                                         (80106, 1),
                                                         (80107, 1),
                                                         (80108, 1),
                                                         (80109, 1),
                                                         (80110, 1),
                                                         (80111, 1);

INSERT INTO authority_api (_ID, MENU_PK, SERVICE, USE_YN, VISIBLE_YN, PRIMARY_YN, PUBLIC_YN, HTTP_METHOD, RECORD_YN,  URL, CODE, DESCRIPTION) VALUES
                                                                                                                                                  (80201, 802, 'TASK.DEPLOY', 1, 1, 1, 0, 3, 1, '/api/v1/task/deploy/list/agent', 'selectTaskDeployListForAgent',  '수행요청이력 리스트 조회 / RPA 용'),
                                                                                                                                                  (80203, 802, 'TASK.DEPLOY', 1, 1, 0, 0, 1, 1, '/api/v1/task/deploy/{^[\\d]$}/accept', 'acceptTaskDeploy',  '수행요청 처리'),
                                                                                                                                                  (80204, 802, 'TASK.DEPLOY', 1, 1, 0, 0, 1, 1, '/api/v1/task/deploy/rpa/{^[\\d]$}/*', 'downloadTaskDeployFileOfRpa',  'RPA 수행 파일 다운로드');
INSERT INTO authority_api_role_map (API_PK, ROLE_PK) VALUES
                                                         (80201, 1),
                                                         (80202, 1),
                                                         (80203, 1),
                                                         (80204, 1);
