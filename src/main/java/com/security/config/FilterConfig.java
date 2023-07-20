package com.security.config;

import com.security.filter.FirstFilter;
import com.security.filter.SecondFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* Filter Chain 적용 Config */
@Configuration
public class FilterConfig {

    // FilterRegistrationBean의 생성자로 Filter 인터페이스의 구현체를 넘겨주는 형태로 첫번째 필터 등록
    @Bean
    public FilterRegistrationBean<FirstFilter> registerFirstFilter() {
        FilterRegistrationBean<FirstFilter> registrationBean = new FilterRegistrationBean<>(new FirstFilter());
        registrationBean.setOrder(1); // 필터 우선순위 지정
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SecondFilter> registerSecondFilter() {
        FilterRegistrationBean<SecondFilter> registrationBean = new FilterRegistrationBean<>(new SecondFilter());
        registrationBean.setOrder(2); // 필터 우선순위 지정
        return registrationBean;
    }
}
