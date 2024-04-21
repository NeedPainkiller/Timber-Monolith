# TIMBER (Monolith) - Deprecated


<p align="center">
  <a href="https://blog.needpainkiller.xyz/" target="blank"><img src="./img/timber-logo.svg" width="200" alt="Timber Logo" /></a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-67493A?style=flat-square&logo=OpenJDK&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"/>
</p>
<p align="center">
  <img src="https://img.shields.io/badge/Apache Kafka-231F20?style=flat-square&logo=apachekafka&logoColor=white"/>
  <img src="https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=Hibernate&logoColor=white"/>
  <img src="https://img.shields.io/badge/Mysql-4479A1?style=flat-square&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Mariadb-003545?style=flat-square&logo=mariadb&logoColor=white"/>
  <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white"/>
</p>
<p align="center">
  <img src="https://img.shields.io/badge/Microsoft Azure-0078D4?style=flat-square&logo=microsoftazure&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white"/>
</p>
  <!--[![Backers on Open Collective](https://opencollective.com/nest/backers/badge.svg)](https://opencollective.com/nest#backer)
  [![Sponsors on Open Collective](https://opencollective.com/nest/sponsors/badge.svg)](https://opencollective.com/nest#sponsor)-->
This project uses Spring Boot with OpenJDK 21 and Gradle7.2.1

This project is a monolithic architecture that is deprecated. The new Timber Application project can be found in the following [this repositroy](https://github.com/NeedPainkiller/Timber).

If you want to learn more about [Timber](https://github.com/NeedPainkiller/Timber), please visit its Blog: https://blog.needpainkiller.xyz


## Description
Log processing backend service for [Timber](https://github.com/NeedPainkiller/Timber) Framework
## Requirements
- <img src="https://img.shields.io/badge/Java-67493A?style=flat-square&logo=OpenJDK&logoColor=white"/>
- <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"/>
- <img src="https://img.shields.io/badge/Apache Kafka-231F20?style=flat-square&logo=apachekafka&logoColor=white"/>
- <img src="https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=Hibernate&logoColor=white"/>
- <img src="https://img.shields.io/badge/Mysql-4479A1?style=flat-square&logo=mysql&logoColor=white"/>
- <img src="https://img.shields.io/badge/Mariadb-003545?style=flat-square&logo=mariadb&logoColor=white"/>
- <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white"/>
- <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white"/>
## Installation
```bash
https://github.com/NeedPainkiller/Timber-Monolith.git
```

## Project Structure
```bash
+---java
|   \---xyz
|       \---needpainkiller
|           |   Application.java
|           |
|           +---api - RestAPI 서비스
|           |   +---audit
|           |   |   |   AuditService.java
|           |   |   |   FileAuditService.java
|           |   |   |   KafkaAuditService.java
|           |   |   |
|           |   |   +---dao
|           |   |   |       AuditLogRepo.java
|           |   |   |       AuditLogSpecification.java
|           |   |   |
|           |   |   +---dto
|           |   |   |       AuditLogCsv.java
|           |   |   |       AuditRequests.java
|           |   |   |
|           |   |   \---model
|           |   |           AuditLog.java
|           |   |           AuditLogMessage.java
|           |   |
|           |   +---authentication
|           |   |   |   AuthenticationApi.java
|           |   |   |   AuthenticationController.java
|           |   |   |   AuthenticationService.java
|           |   |   |   AuthorizationChecker.java
|           |   |   |   AuthorizationService.java
|           |   |   |
|           |   |   +---dao
|           |   |   |       ApiRepo.java
|           |   |   |       ApiRoleMapRepo.java
|           |   |   |       DivisionRepo.java
|           |   |   |       MenuRepo.java
|           |   |   |
|           |   |   +---dto
|           |   |   |       AuthenticationRequests.java
|           |   |   |
|           |   |   +---error
|           |   |   |       ApiException.java
|           |   |   |       LoginException.java
|           |   |   |       PasswordException.java
|           |   |   |
|           |   |   \---model
|           |   |           Api.java
|           |   |           ApiRoleMap.java
|           |   |           ApiRoleMapId.java
|           |   |           Division.java
|           |   |           Menu.java
|           |   |
|           |   +---enc
|           |   |       EncryptionController.java
|           |   |
|           |   +---file
|           |   |   |   FileApi.java
|           |   |   |   FileController.java
|           |   |   |   FileService.java
|           |   |   |   KafkaFileDownloadListener.java
|           |   |   |   KafkaFileStoreListener.java
|           |   |   |
|           |   |   +---dao
|           |   |   |       FileRepo.java
|           |   |   |
|           |   |   +---error
|           |   |   |       FileErrorCode.java
|           |   |   |       FileException.java
|           |   |   |
|           |   |   \---model
|           |   |           FileAuthorityType.java
|           |   |           Files.java
|           |   |           FileServiceType.java
|           |   |
|           |   +---team
|           |   |   |   TeamApi.java
|           |   |   |   TeamController.java
|           |   |   |   TeamService.java
|           |   |   |
|           |   |   +---dao
|           |   |   |       TeamRepo.java
|           |   |   |
|           |   |   +---dto
|           |   |   |       TeamRequests.java
|           |   |   |
|           |   |   +---error
|           |   |   |       TeamErrorCode.java
|           |   |   |       TeamException.java
|           |   |   |
|           |   |   \---model
|           |   |           Team.java
|           |   |           TeamLevel.java
|           |   |
|           |   +---tenant
|           |   |   |   TenantApi.java
|           |   |   |   TenantController.java
|           |   |   |   TenantService.java
|           |   |   |
|           |   |   +---dao
|           |   |   |       TenantRepo.java
|           |   |   |
|           |   |   +---dto
|           |   |   |       TenantBootstrapRequests.java
|           |   |   |       TenantRequests.java
|           |   |   |
|           |   |   +---error
|           |   |   |       TenantErrorCode.java
|           |   |   |       TenantException.java
|           |   |   |
|           |   |   \---model
|           |   |           ServerStatus.java
|           |   |           Tenant.java
|           |   |           TenantBase.java
|           |   |
|           |   +---user
|           |   |   |   RoleApi.java
|           |   |   |   RoleController.java
|           |   |   |   RoleService.java
|           |   |   |   UserApi.java
|           |   |   |   UserController.java
|           |   |   |   UserService.java
|           |   |   |
|           |   |   +---dao
|           |   |   |       RoleRepo.java
|           |   |   |       RoleSpecification.java
|           |   |   |       UserRepo.java
|           |   |   |       UserRoleMapRepo.java
|           |   |   |       UserSpecification.java
|           |   |   |
|           |   |   +---dto
|           |   |   |       RoleCsv.java
|           |   |   |       RoleRequests.java
|           |   |   |       UserCsv.java
|           |   |   |       UserProfile.java
|           |   |   |       UserRequests.java
|           |   |   |
|           |   |   +---error
|           |   |   |       RoleException.java
|           |   |   |       UserException.java
|           |   |   |
|           |   |   \---model
|           |   |           Role.java
|           |   |           SecurityUser.java
|           |   |           User.java
|           |   |           UserRoleMap.java
|           |   |           UserRoleMapId.java
|           |   |           UserStatusType.java
|           |   |
|           |   \---workingday
|           |       |   WorkingDayApi.java
|           |       |   WorkingDayController.java
|           |       |   WorkingDayService.java
|           |       |
|           |       +---dao
|           |       |       HolidayRepo.java
|           |       |
|           |       +---dto
|           |       |       HolidayCsv.java
|           |       |       WorkingDay.java
|           |       |       WorkingDayRequests.java
|           |       |
|           |       +---error
|           |       |       WorkingDayErrorCode.java
|           |       |       WorkingDayException.java
|           |       |
|           |       \---model
|           |               Holiday.java
|           |
|           +---common
|           |   +---controller
|           |   |       CommonController.java
|           |   |       ResourceController.java
|           |   |
|           |   +---dto
|           |   |       CommonRequests.java
|           |   |       DateType.java
|           |   |       SearchCollectionResult.java
|           |   |
|           |   \---model
|           |           HttpMethod.java
|           |           RelationMap.java
|           |
|           +---config
|           |   |   ApiConfig.java
|           |   |   AspectConfig.java
|           |   |   AsyncTaskConfig.java
|           |   |   DatabaseConfig.java
|           |   |   EncryptConfig.java
|           |   |   EncryptVaultConfig.java
|           |   |   JacksonConfig.java
|           |   |   PasswordEncoderConfig.java
|           |   |   QuartzConfig.java
|           |   |   RetrofitConfig.java
|           |   |   SwaggerConfig.java
|           |   |   WebMvcConfig.java
|           |   |   WebSecurityConfig.java
|           |   |
|           |   +---cache
|           |   |       CustomJCacheManagerFactoryBean.java
|           |   |       EhCacheConfig.java
|           |   |       EhCacheEventLogger.java
|           |   |       RedisCacheConfig.java
|           |   |       RedisCacheErrorHandler.java
|           |   |       RedisConfig.java
|           |   |       RedisEventLogger.java
|           |   |
|           |   \---mq
|           |           AMQPConfig.java
|           |           KafkaConfig.java
|           |
|           +---helper
|           |       BrowserHelper.java
|           |       CalcHelper.java
|           |       CharHelper.java
|           |       CompareHelper.java
|           |       CompressHelper.java
|           |       CookieUtils.java
|           |       DateRange.java
|           |       FileHelper.java
|           |       HttpHelper.java
|           |       Inets.java
|           |       LocalDateComparator.java
|           |       MailHelper.java
|           |       PasswordHelper.java
|           |       Predicates.java
|           |       TimeHelper.java
|           |       ValidationHelper.java
|           |
|           +---lib
|           |   |   HtmlCharacterEscapes.java
|           |   |   JpaPaginationDirection.java
|           |   |
|           |   +---amqp
|           |   |       CustomMessageListener.java
|           |   |       MessageQueueService.java
|           |   |       Receiver.java
|           |   |
|           |   +---api
|           |   |   |   ApiDispatcherServlet.java
|           |   |   |   ApiInterceptor.java
|           |   |   |   CachedBodyHttpServletRequest.java
|           |   |   |   CachedBodyServletInputStream.java
|           |   |   |
|           |   |   \---filter
|           |   |           ApiDevFilter.java
|           |   |           ApiFilter.java
|           |   |           ApiProdFilter.java
|           |   |
|           |   +---azure
|           |   |       KeyVaultManager.java
|           |   |
|           |   +---exceptions
|           |   |       ApiErrorResponse.java
|           |   |       BusinessException.java
|           |   |       CommonErrorCode.java
|           |   |       ErrorCode.java
|           |   |       GlobalExceptionHandler.java
|           |   |       ServiceErrorCode.java
|           |   |       ServiceExceptionHandler.java
|           |   |       SystemErrorCode.java
|           |   |
|           |   +---jpa
|           |   |       BaseConverter.java
|           |   |       BooleanConverter.java
|           |   |       CodeEnumConverter.java
|           |   |       InetConverter.java
|           |   |       IntegerListConverter.java
|           |   |       JsonToMapConverter.java
|           |   |       ListConverter.java
|           |   |       LongListConverter.java
|           |   |
|           |   +---mail
|           |   |   |   MailService.java
|           |   |   |   MailServiceImpl.java
|           |   |   |   SmtpAuthenticator.java
|           |   |   |
|           |   |   \---error
|           |   |           MailException.java
|           |   |
|           |   +---mybatis
|           |   |   |   BooleanTypeHandler.java
|           |   |   |   CodeEnum.java
|           |   |   |   CodeEnumTypeHandler.java
|           |   |   |   InetTypeHandler.java
|           |   |   |   ListTypeHandler.java
|           |   |   |   MybatisJsonTypeHandler.java
|           |   |   |
|           |   |   \---translator
|           |   |           DacSQLErrorCodeSQLExceptionTranslator.java
|           |   |           MyBatisExceptionTranslator.java
|           |   |
|           |   +---security
|           |   |   |   CommonJwtDoubleChecker.java
|           |   |   |   JwtAccessDeniedHandler.java
|           |   |   |   JwtAuthenticationEntryPoint.java
|           |   |   |   JwtAuthenticationFilter.java
|           |   |   |   JwtDoubleChecker.java
|           |   |   |   RedisJwtDoubleChecker.java
|           |   |   |
|           |   |   +---error
|           |   |   |       RedisException.java
|           |   |   |       TokenValidFailedException.java
|           |   |   |
|           |   |   +---provider
|           |   |   |       HeaderJsonWebTokenProvider.java
|           |   |   |       HttpsCookieJsonWebTokenProvider.java
|           |   |   |       JsonWebTokenProvider.java
|           |   |   |
|           |   |   \---secret
|           |   |           CommonJsonWebTokenSecretKey.java
|           |   |           JsonWebTokenSecretKeyManager.java
|           |   |           KeyVaultJsonWebTokenSecretKey.java
|           |   |
|           |   +---sheet
|           |   |       CommaSeparatedValue.java
|           |   |       SpreadSheetService.java
|           |   |
|           |   +---sse
|           |   |       SseEmitters.java
|           |   |
|           |   +---storage
|           |   |       DefaultStorageService.java
|           |   |       LocalStorageService.java
|           |   |       SecureStorageService.java
|           |   |
|           |   \---validation
|           |           NonSpecialCharacter.java
|           |           SpecialCharValidator.java
|           |           VersionNumberCharacter.java
|           |           VersionNumberCharValidator.java
|           |
|           \---schedule
|               |   AutowiringSpringBeanJobFactory.java
|               |   QuartzScheduleService.java
|               |   SystemJobs.java
|               |
|               +---dto
|               |       JobRequest.java
|               |       JobResponse.java
|               |       JobStatusResponse.java
|               |
|               +---error
|               |       QuartzSchedulerException.java
|               |
|               +---job
|               |       FileStorageCleanJob.java
|               |       UpdateTeamPathJob.java
|               |
|               +---listener
|               |       JobsListener.java
|               |       TriggersListener.java
|               |
|               +---model
|               |       ScheduleTriggerType.java
|               |
|               \---util
|                       DateTimeUtil.java
|                       JobUtil.java
|
\---resources
    |   application-ehcache.yml
    |   application-kafka.yml
    |   application-local.yml
    |   application-mariadb.yml
    |   application-mssql.yml
    |   application-mysql.yml
    |   application-redis.yml
    |   application-sso.yml
    |   application-test.yml
    |   application.yml
    |   log4jdbc.log4j2.properties
    |   logback-spring.xml
    |
    +---cache
    |       ehcache.xml
    |
    +---graphql
    +---quartz
    |       quartz-ddl-mssql.sql
    |       quartz-ddl-mysql.sql
    |
    +---sample
    |       404.html
    |
    +---sql
    |   |   sample.sql
    |   |
    |   +---mariadb
    |   |       mariadb-api.sql
    |   |       mariadb-audit-schema.sql
    |   |       mariadb-data.sql
    |   |       mariadb-quartz.sql
    |   |       mariadb-schema.sql
    |   |       mariadb-test.sql
    |   |
    |   \---mysql
    |           mysql-data.sql
    |           mysql-quartz.sql
    |           mysql-schema.sql
    |
    +---static
    |   |   favicon.ico
    |   |   hansam_.ico
    |   |   han_favicon.ico
    |   |   index.html
    |   |   mockServiceWorker.js
    |   |
    |   \---assets
    |
    \---templates
        |   mail-education-answer.html
        |   mail-employee-subscribe-accepted.html
        |   mail-execution-failed.html
        |   mail-rpa-archives-trigger.html
        |   mail-task-petition-notification-sample.html
        |   mail-task-petition-notification.html
        |   mail-temp-password-updated.html
        |   mail-user-verification.html
        |
        \---images
                logo.png
                logo_hanssem_full.png
                mail-footer.png
                petition-content.png
```

## Running on IntelliJ IDEA

```bash
# VM Options
-server -Dspring.profiles.active=local,mariadb,jwt-header,ehcache,kafka -Djava.net.preferIPv4Stack=true -Dlog4j2.formatMsgNoLookups=true -Xms1024m -Xmx1024m -XX:MaxNewSize=384m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./logs/java_pid<pid>.hprof -XX:ParallelGCThreads=2 -Xlog:gc:./logs/gclog

# development
./.run/TIMBER-LOCAL-MARIADB.run.xml
```

## Build
```Bash
# Build 
gradle bootJar
```

## Run Jar
```Bash
java -jar build/libs/Timber-0.0.1-SNAPSHOT.jar
```

## Stay in touch
<p>
  <a href="https://home.needpainkiller.xyz/" target="_blank"><img src="https://img.shields.io/badge/Home-EF3346?style=flat-square&logo=googlehome&logoColor=white"/></a>
  <a href="https://blog.needpainkiller.xyz/" target="_blank"><img src="https://img.shields.io/badge/Blog-15171A?style=flat-square&logo=Ghost&logoColor=white"/></a>
  <a href="mailto:kam6512@gmail.com" target="_blank"><img src="https://img.shields.io/badge/kam6512@gmail.com-EA4335?style=flat-square&logo=Gmail&logoColor=white"/></a>
  <a href="mailto:needpainkiller6512@gmail.com" target="_blank"><img src="https://img.shields.io/badge/needpainkiller6512@gmail.com-EA4335?style=flat-square&logo=Gmail&logoColor=white"/></a>
</p>

- Author - [NeedPainkiller](https://home.needpainkiller.xyz/)
- Blog - [https://blog.needpainkiller.xyz](https://blog.needpainkiller.xyz/)
- Github - [@PainKiller](https://github.com/NeedPainkiller)

## License

Timber is [MIT licensed](LICENSE)
