package com.testing.course.springboottesting.mainApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class MainAppSecurityConfig {

    @Bean
    SecurityFilterChain rolesSecurity(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(HttpMethod.GET,"/api/employees").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/employees/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/employees/secured/employees").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET,"/api/employees/secured/employee/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.PUT,"/api/employees/secured/employee").hasAnyRole("EMPLOYEE","MANAGER")
                .requestMatchers(HttpMethod.POST,"/api/employees/secured/employee").hasAnyRole("EMPLOYEE","MANAGER")
                .requestMatchers(HttpMethod.DELETE,"/api/employees/secured/employee/**").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
                .anyRequest().permitAll()
        );

        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf().disable();
        return httpSecurity.build();
        }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("EMPLOYEE")
                .build();
        UserDetails user2 = User.builder()
                .username("userx")
                .password("$2a$12$j57/yHJ1MM8KhE1HRja3fuJAfqh26viXRmhHQO.Lk3oGqAxlYnpnK")
                .roles("EMPLOYEE")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, user2, admin);
    }


//    @Bean
//    public UserDetailsManager useDbUsers(DataSource dataSource){
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id, pw, active from members where user_id=?");
//
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id, role from roles where user_id=?");
//
//        return jdbcUserDetailsManager;
//    }
}
