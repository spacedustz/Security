//package com.security.filter;
//
//import jakarta.servlet.*;
//
//import java.io.IOException;
//
//public class SecondFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//        System.out.println("Second Filter 생성");
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("===== Second Filter 시작 =====");
//        chain.doFilter(request, response);
//        System.out.println("===== Second Filter 종료 =====");
//    }
//
//    @Override
//    public void destroy() {
//        System.out.println("Second Filter Destroy");
//        Filter.super.destroy();
//    }
//}
