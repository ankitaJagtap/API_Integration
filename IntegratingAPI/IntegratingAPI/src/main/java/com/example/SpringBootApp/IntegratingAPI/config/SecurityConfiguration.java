package com.example.SpringBootApp.IntegratingAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(httpSecurityCsrfConfigurer -> {httpSecurityCsrfConfigurer.disable();});

        http.authorizeHttpRequests(requests -> {
            requests.requestMatchers("weather/getForecastSummary/").permitAll();
            requests.requestMatchers("getHourlyForecast/").authenticated();
        });

        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
