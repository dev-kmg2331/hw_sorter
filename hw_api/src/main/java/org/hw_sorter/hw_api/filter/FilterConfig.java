package org.hw_sorter.hw_api.filter;

import org.hw_sorter.hw_api.filter.logging.RequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

//    @Bean
//    public FilterRegistrationBean<Filter> loggingFilter() {
//        FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<>();

//        filterBean.setFilter(new RequestLoggingFilter());

//        return filterBean;
//    }

    @Bean
    public RequestLoggingFilter loggingFilter() {
        RequestLoggingFilter filter = new RequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeHeaders(false);
        filter.setIncludePayload(true);
        filter.setIncludeQueryString(true);
        filter.setMaxPayloadLength(9999);
        return filter;
    }
}
