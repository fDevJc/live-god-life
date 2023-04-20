# Refactoring
## 모듈화
### api-modules
> api 서비스에 필요한 기능 구현.  
- xxx-api
  - dto: request, response
  - presentation: controller
  - application: service
    - 서비스 비즈니스
- api-core
 
### domain-modules
> domain 비즈니스 처리 영역.
- xxx-domain
  - service:
    - 도메인 비즈니스
  - dto:
  - domain:
  - repository:

### cloud-modules
> cloud 서버와 관련된 외부 기능.
 
## 기타
### Feign Client
### Logging
### Docker Compose
 