## Remind Spring Security
<details>
<summary>Spec</summary>

- Spring Security 복습
- Spring Security 6.1.2 (Spring Security 5 에서 사용하던 각종 함수들이 많이 Deprecated 됨)
- Spring Boot 3.1.1
- JDK 17
</details>

<br>

<details>
<summary>Dependencies</summary>

- Spring Web
- Spring Security
- Lombok
- Spring Data JPA
- H2
</details>

---

## Remind 완료

<details>
<summary>Security 기본 설정 (로그인, 로그아웃, 회원가입, Http Security 설정)</summary>

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
</details>

<br>

<details>
<summary>Servlet Filter Chain</summary>

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
</details>

<br>

<details>
<summary>JWT</summary>

- JWT는 Header, Payload Signature의 구조로 되어있다.
- JwtTokenizer (토큰 생성 클래스)
- JwtAuthenticationFilter (Authentication 객체를 생성하고 검증 후 토큰 발급을 해주는 Security Filter)
  - 이 인증 필터를 구현할때 UsernamePasswordAuthenticationFilter를 구현하는 방법,
  - OncePerRequestFilter를 구현하는 방법 등 인증 방식에 따라 다양한 필터가 있다
- JwtVerifycationFilter (발급된 토큰을 검증하는 Verifycation FIlter)

<br>

> Header

Header는 어떤 종류의 토큰인지, 어떤 알고리즘으로 Sign할지 정의한다. (Json Format)
이걸 Base64로 Encoding 하면 JWT의 첫번째 부분이 완성된다.
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```
<br>

|요소|요소의 key 값|요소의 value 값|
|---|---|---|
|토큰의 타입|typ|JWT|
|토큰의 서명에서 사용할 암호화 알고리즘 종류|alg|HMAC, SHA256, RSA, HS256, RS256|


---

> Payload

Payload에는 서버에서 활용할 수 있는 사용자의 정보가 담겨있다.
어떤 정보에 접근 가능한지에 대한 권한을 담을 수도, 이름 등 필요한 데이터를 담을 수 있다.
Payload는 다음으로 설명할 Signature를 통해 유효성이 검증될 정보이지만, 민감한 정보는 안담는게 좋다.
```json
{
  "sub": "someInformation",
  "name": "phillip",
  "iat": 151623391
}
```

---

> Signature

Base64로 인코딩된 1,2번째 부분이 완성되었다면 여기서는<br>
원하는 Secret Key, Header에서 지정한 알고리즘을 사용하여 Header/Payload에 대한 단방향 암호화를 수행한다.
이렇게 암호회된 메시지는 토큰의 위/변조 유무를 검증하는데 사용함
예를 들어, HMAC SHA256을 사용한다면 Signature는 다음과 같은 방식으로 생성된다.
```java
HMACSHA256(base64UrlEncode(header) + '.' + base64UrlEncode(payload), secret);
```

<br>

> 요약

UsernamePasswordAuthenticationFilter를 이용해서 JWT 발급 전의 로그인 인증 기능을 구현할 수 있다.<br>
Spring Security에서는 개발자가 직접 Custom Configurer를 구성해 Spring Security의 Configuration을 커스터마이징(customizations) 할 수 있다.<br>
Username/Password 기반의 로그인 인증은 OncePerRequestFilter 같은 Spring Security에서 지원하는 다른 Filter를 이용해서 구현할 수 있으며,<br>
Controller에서 REST API 엔드포인트로 구현하는 것도 가능하다.<br>
Spring Security에서는 Username/Password 기반의 로그인 인증에 성공했을 때,<br>
로그를 기록하거나 로그인에 성공한 사용자 정보를 response로 전송하는 등의 추가 처리를 할 수 있는<br>
AuthenticationSuccessHandler를 지원하며, 로그인 인증 실패 시에도 마찬가지로 인증 실패에 대해 추가 처리를 할 수 있는 AuthenticationFailureHandler를 지원한다.

</details>

<br>

<details>
<summary>OAuth2</summary>

## Google API

[Google API 만들기](https://console.cloud.google.com/projectselector2/apis/dashboard?pli=1&supportedpurview=project)

> 프로젝트 만들기

- 프로젝트 이름 설정
- 만들기

<br>

> OAuth 동의 화면 만들기

 - User Type = '외부' 선택
 - 앱 정보, 개발자 연락처 정보 지정

<br>

> 사용자 인증 정보 생성

- 사용자 인정 정보 만들기 유형 = OAuth 클라이언트 ID
- 앱이름, 리디렉션 URI 지정 (http://localhost:8080/login/oauth2/code/google)

---
 
## CSR 방식의 Application에서의 OAuth2 Flow

- Resource Owner가 Google Login 버튼 클릭
- Frontend 어플리케이션에서 Backend 어플리케이션의 OAuth2 로그인 API로 로그인 Request 전성
- 이때의 Request는 `OAuth2LoginAuthenticationFilter`가 처리한다.
- Google 로그인 화면을 요청하는 URI로 리다이렉션 한다.
- 이때 Authorization Code를 전송한 Redirect URI(http://localhost:8080/login/oauth2/google)를 쿼리 파라미터로 전달한다.
- 예시 : https://account.google.com/o/oauth2/v2/auth?redirect_uri=http://localhost:8080/login/oauth2/code/google
- 로그인에 성공하면 전달한 Backend Redirect URL로 Authorization Code를 요청한다.
- `(Spring Security가 내부적으로 자동 처리)` Authorization Server가 Backend 어플리케이션에게 Authorization Code를 응답으로 전송한다.
- `(Spring Security가 내부적으로 자동 처리)` Backend Application이 Authorization Code를 가지고 Authorization Server에 Access Token을 요청한다.
- `(Spring Security가 내부적으로 자동 처리)` Authorization Server가 Backend Application에게 Access Token을 응답으로 전송한다.
- `(Spring Security가 내부적으로 자동 처리)` Backend Application이 Resource Server에 Resource(User Info)를 요청한다.
- `(Spring Security가 내부적으로 자동 처리)` Resource Server가 Backend Application에게 Resource(User Info)를 응답으로 전송한다.
- Backend Application은 JWT로 구성된 토큰을 생성 후, Frontend Application에게 JWT를 전달하기 위해 http://localhost?access_token={jwt-access-token}&refresu_token={jwt-refresh-token} 로 Redirect 한다.
</details>

