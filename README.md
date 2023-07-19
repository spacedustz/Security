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

**요약**

- Spring Security에서 지원하는 InMemory User는 말 그대로 메모리에 등록되어 사용되는 User이므로 애플리케이션 실행이 종료되면 InMember User 역시 메모리에서 사라진다.

- InMemory User를 사용하는 방식은 테스트 환경이나 데모 환경에서 사용할 수 있는 방법이다.

- Spring Security는 사용자의 크리덴셜(Credential, 자격증명을 위한 구체적인 수단)을 암호화하기 위한 PasswordEncoder를 제공하며, PasswordEncoder는 다양한 암호화 방식을 제공하며, Spring Security에서 지원하는 PasswordEncoder의 디폴트 암호화 알고리즘은 bcrypt이다.

- 패스워드 같은 민감한(sensitive) 정보는 반드시 암호화되어 저장되어야 합니다.
패스워드는 복호화할 이유가 없으므로 단방향 암호화 방식으로 암호화되어야 한다.

- Spring Security에서 SimpleGrantedAuthority를 사용해 Role 베이스 형태의 권한을 지정할 때 ‘ROLE_’ + 권한명 형태로 지정해 주어야 한다.

- Spring Security에서는 Spring Security에서 관리하는 User 정보를 UserDetails로 관리한다.

- UserDetails는 UserDetailsService에 의해 로드(load)되는 핵심 User 정보를 표현하는 인터페이스입니다.

- UserDetailsService는 User 정보를 로드(load)하는 핵심 인터페이스이다.

- 일반적으로 Spring Security에서는 인증을 시도하는 주체를 User(비슷한 의미로 Principal도 있음)라고 부른다. Principal은 User의 더 구체적인 정보를 의미하며, 일반적으로 Username을 의미한다.

- Custom UserDetailsService를 사용해 로그인 인증을 처리하는 방식은 Spring Security가 내부적으로 인증을 대신 처리해 주는 방식이다.

- AuthenticationProvider는 Spring Security에서 클라이언트로부터 전달받은 인증 정보를 바탕으로 인증된 사용자인지를 처리하는 Spring Security의 컴포넌트이다.

<br>

>JWT
- 

<br>

> OAuth2
-

