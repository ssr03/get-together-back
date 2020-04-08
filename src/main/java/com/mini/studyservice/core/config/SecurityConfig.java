package com.mini.studyservice.core.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
@EnableWebSecurity
@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = 31536000)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.mini.studyservice.core.security")
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final DataSource dataSource;

    @Autowired
    public SecurityConfig(DataSource dataSource) {
        super();
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        " SELECT loginid, replace(password, '$2y', '$2a') as password, enable_flag" +
                        " FROM USER " +
                        " WHERE loginid = ?  AND enable_flag = 1")     
                .authoritiesByUsernameQuery(
                        " SELECT USER.loginid, USER_ROLE.role " +
                        " FROM USER_ROLE " +
                        " INNER JOIN USER " +
                        " ON (USER.user_id = USER_ROLE.user_id) " +
                        " WHERE USER.loginid = ? "
                ).passwordEncoder(passwordEncoder());                
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/actuator/**").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/study/user/**").permitAll()
//                    .antMatchers("/logout").permitAll()
//                    .antMatchers("/api/*").hasAnyAuthority("ADMIN","SUPER_USER","LAB","SALES")
//                    .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/study/recruit").permitAll()
                    .antMatchers("/study/img/**").permitAll()
                    .antMatchers("/study/thumbnail/**").permitAll()
                    //.antMatchers("/study/upload/file").permitAll()
                    .antMatchers("/study/recruit/start/**").permitAll()
                    .antMatchers("/study/recruit/status/**").permitAll()
                    .antMatchers("/study/recruit/{board_id}").permitAll()
                    .antMatchers(HttpMethod.GET,"/study/recruit/{board_id}/comment/**").permitAll()
                    .antMatchers("/study/recruit/tag/**").permitAll()
                    .antMatchers("/mail/**").permitAll()
                    .antMatchers("/study/search/**").permitAll()
//                    .antMatchers("/study/recruit/{board_id}/join").authenticated()
//                    .antMatchers("/study/**").permitAll()
                    .anyRequest().authenticated()
            .and()
		        .httpBasic();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

