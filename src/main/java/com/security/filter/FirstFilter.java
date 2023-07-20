//package com.security.filter;
//
//import jakarta.servlet.*;
//
//import java.io.IOException;
//
//public class FirstFilter implements Filter {
//
//    // Init Filter
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//        System.out.println("First Filter 생성");
//    }
//
//    // 실직적인 로직 처리 부분
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("===== First Filter 시작 (doFilter()의 전처리 부분) =====");
//        chain.doFilter(request, response);
//        System.out.println("===== First Filter 종료 (doFilter()의 후처리 부분) =====");
//    }
//
//    // 자원 반납
//    @Override
//    public void destroy() {
//        System.out.println("First Filter Destroy");
//        Filter.super.destroy();
//    }
//}
