## Remind Spring Security
- Spring Security 6.1.2 (Spring Security 5 에서 사용하던 각종 함수들이 많이 Deprecated 됨)
- Spring Boot 3.1.1
- JDK 17

---

## Dependencies
- Spring Web
- Spring Security
- Lombok
- Spring Data JPA
- H2

---

## Remind 완료 리스트
> Security 기본 설정 (로그인, 로그아웃, 회원가입, Http Security 설정)
- Local Test를 위한 Inmemory User 권한 생성 방식
- DelegatingPasswordEncoder
- Custom한 UserDetails, Authority 부여
- Multi Factor Authentication & Custom한 직접 인증 처리를 위한 AuthenticationProvider
  - AuthenticationProvider에서 AuthenticationException 외에 Exception 발생 시 AuthenticationExcpetion으로 Re-Throw
  - 이유 : 등록된 회원이 없을 시 Business Logic에서 다른 Exception을 던지는데 Provider를 거쳐 그대로 Spring Security 내부로 이동하기 때문에 Re-Throw 처리를 해줘야 함

<br>

>JWT
- 

<br>

> OAuth2
-

