## Remind Spring Security
- Spring Security 복습
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

# Remind 완료 리스트

## Security 기본 설정 (로그인, 로그아웃, 회원가입, Http Security 설정)
- Local Test를 위한 Inmemory User 권한 생성 방식
- DelegatingPasswordEncoder
- Custom한 UserDetails, Authority 부여
- Multi Factor Authentication & Custom한 직접 인증 처리를 위한 AuthenticationProvider
  - AuthenticationProvider에서 AuthenticationException 외에 Exception 발생 시 AuthenticationExcpetion으로 Re-Throw
  - 이유 : 등록된 회원이 없을 시 Business Logic에서 다른 Exception을 던지는데 Provider를 거쳐 그대로 Spring Security 내부로 이동하기 때문에 Re-Throw 처리를 해줘야 함

<br>

> 요약
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

---

## Servlet Filter Chain
- Servlet 기반 어플리케이션에서는 javax.servlet 패키지의 인터페이스를 정의하여 doFilter() 함수 호출을 통해 필터 체인을 구성할 수 있다.
- 이러한 필터체인은 웹 요청,응답 전/후 처리를 할 수 있다.
- 흐름은 웹요청 -> ServletFilterChain -> HttpServlet -> DispatcherServlet / 응답은 그 반대 이다.
- Spring Security에서의 Filter는 Servlet Filter Chain에 DelegatingFilterProxy, FilterChainProxy 이다.

<br>

> **Delegating Filter Proxy란?**

Spring Security 역시 Spring ApplicationContext를 이용한다.<br>
서블릿 필터와 연결되는 Spring Security만의 필터를 ApplicationContext에 Bean으로 등록한 후, <br>
이 Bean들을 이용해서 보안과 관련된 작업을 처리하는 시작점이 DelegatingFilterProxy이다.<br>
즉, 보안과 관련된 직접적인 처리를 하는것이 아닌, Servlet Container 영역의 필터와 ApplicationContext에 등록된 필터들을 연결해주는 Bridge 역할이다.

<br>

> **FilterChainProxy란?**

Spring Security만의 보안을 위한 작업을 처리하는 Filter들의 모음이자 Spring Security의 필터를 사용하기 위한 진입점이다.<br>
Spring Security의 Filter Chain은 URL별로 여러개 등록할 수 있고, Filter Chain이 있을떄 어떤 Filter Chain을 사용할지는<br>
FilterChainProxy가 결정하며, 가장 먼저 매칭된 Filter Chain을 실행한다.
즉, Filter들 사이에 우선순위를 잘 적용해 수행 우선순위를 잘 정하는것도 중요하다.

<br>

> **요약**

Servlet FIlterChain은 요청 URI Path를 기반으로 HttpServlet Request를 처리한다.<br>
따라서 요청 URI 경로를 기반으로 어떤 Filter와 어떤 Servlet을 매핑할지 결정하기 때문에,<br>
Spring Security의 FilterChain을 구성할 때 FilterChainProxy의 필터 실행 우선순위를 생각하며 URI Path를 잘 설정하기

<br>

Filter의 우선순위를 정하는 방법
- `@Order` 어노테이션 사용
- `Ordered` 인터페이스를 구현하는 방법
- `FilterRegistrationBean`을 이용해 순서를 명시적으로 정할수도 있다.


---

## JWT
- 

---

## OAuth2
-

