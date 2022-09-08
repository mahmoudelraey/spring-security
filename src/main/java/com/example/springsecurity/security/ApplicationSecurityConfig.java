package com.example.springsecurity.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.springsecurity.security.ApplicationUserPermission.STUDENT_READ;
import static com.example.springsecurity.security.ApplicationUserPermission.STUDENT_WRITE;
import static com.example.springsecurity.security.ApplicationUserPermission.COURSE_READ;
import static com.example.springsecurity.security.ApplicationUserPermission.COURSE_WRITE;
import static com.example.springsecurity.security.ApplicationUserRule.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/", "/html", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/v1/**").hasAnyRole(STUDENT.name(), COURSE.name())
//                .antMatchers(HttpMethod.DELETE, "/api/v1/students/**").hasAuthority(STUDENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/api/v1/students/**").hasAuthority(STUDENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/api/v1/students/**").hasAuthority(STUDENT_READ.getPermission())
//                .antMatchers(HttpMethod.PUT, "/api/v1/students/**").hasAuthority(STUDENT_WRITE.getPermission())

//                .antMatchers(HttpMethod.DELETE, "/api/v1/courses/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/api/v1/courses/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/api/v1/courses/**").hasAuthority(STUDENT_READ.getPermission())
//                .antMatchers(HttpMethod.PUT, "/api/v1/courses/**").hasAuthority(COURSE_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails studentUser = User
                .builder()
                .username("student")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMIN.name())
                .authorities(STUDENT.getAuthorities())
                .build();

        UserDetails courseUser = User
                .builder()
                .username("course")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMIN.name())
                .authorities(COURSE.getAuthorities())
                .build();

        return new InMemoryUserDetailsManager(studentUser, courseUser);
    }
}
