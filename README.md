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
This project uses Spring Boot with Java21 and Gradle7.2.1

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
git clone 
```

## Running the app

```bash
```

## Test

```bash
# unit tests
npm run test

# e2e tests
npm run test:e2e

# test coverage
npm run test:cov
```

## Build
```Bash
# Build 
npm run build
```
## Docker
```Bash
# create ".env" file

## Database (MariaDB)
DB_DIALECT=mariadb
DB_HOST=x.y.z
DB_PORT=3306
DB_NAME=timber-audit
DB_USERNAME=timber
DB_PASSWORD=passwd

## Message Queue (Kafka)
KAFKA_BROKERS=x.x.x.x:9092,y.y.y.y:2909,z.z.z.z:9092
KAFKA_ID=timber-sawmill
KAFKA_GROUP_ID=timber-sawmill-group
```


```Bash
# Build Dockerfile
docker build . -t timber-sawmill

# Run Dockerfile
docker container run -d -p 3000:3000 -v ./.env:/var/app/.env nest-docker 
```

## Support

Timber-Sawmill is an MIT-licensed open source project. It can grow thanks to the sponsors and support by the amazing backers. If you'd like to join them, please [read more here](https://docs.nestjs.com/support).

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

Timber-Sawmill is [MIT licensed](LICENSE).
