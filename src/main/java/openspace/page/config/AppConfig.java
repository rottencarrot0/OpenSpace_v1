package openspace.page.config;

import openspace.page.filter.LoginCheckFilter;
import openspace.page.filter.UrlCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<UrlCheckFilter> urlCheckFilter(RequestMappingHandlerMapping mappingHandlerMapping) {
        FilterRegistrationBean<UrlCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UrlCheckFilter(mappingHandlerMapping));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
    // 필터 등록하고
    // /user/login 에 redirect 설젗 추가하기
    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean loginFilter = new FilterRegistrationBean();
        loginFilter.setFilter(new LoginCheckFilter());
        loginFilter.addUrlPatterns("/*");
        loginFilter.setOrder(2);
        return loginFilter;
    }

}
