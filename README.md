# setting-test
setting-test(Spring boot)

# Mybatis + JPA + Thymeleaf 프로젝트 초기 셋팅 방법

MySQL 안정화 버전(현재 기준 8.0.32) 설치한다.

mysql-schema.sql 파일을 참조하여 스키마 이름를 만들고 member, user 테이블 생성한다(어드민 계정과 유저 계정).

웹 접속은 URL : localhost:8070

로그인 또는 회원 가입 후, 권한에 따라 상단 메뉴가 바뀌는 것 확인한다.

# Redis(lettuce) 적용

gradle.build 에서 새로고침 후,

brew로 redis 로컬 설치한다(설치 참고 페이지 : https://redis.io/docs/getting-started/installation/install-redis-on-mac-os/).

test -> java.com.teamflace.redis 패키지에서 RedisConfigTest를 실행하여 연동되는 것을 확인한다.

# Spring Security 및 JWT 추가 

JWT token provider에서 토큰 생성 후, Redis 캐시에 저장

순간적으로 다수의 로그인 요청이 발생하여도 서버의 안정성을 높임

# QueryDsl 적용

gradle.build 에서 새로고침 후,

상단 우측 Gradle 탭을 클릭 후, other -> compileQuerydsl 로 엔티티들의 Qclass 생성 후 test case를 작성한다.

# thymeleaf template engine 세팅 완료

html 기반으로 화면을 간략하게 그릴 수 있도록 설정이 되어있음
