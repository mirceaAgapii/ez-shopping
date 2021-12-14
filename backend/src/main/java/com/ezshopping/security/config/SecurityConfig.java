package com.ezshopping.security.config;

import static com.ezshopping.api.EndpointsAPI.*;
import com.ezshopping.security.filter.CustomAuthenticationFilter;
import com.ezshopping.security.filter.CustomAuthorizationFilter;
import com.ezshopping.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl(API + LOGIN);
        http.cors().and().csrf().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers(API + LOGIN + ALL, API + USERS + TOKEN + REFRESH + ALL, API + USERS + REGISTRATION, "/actuator/**", "/applications/**", "/error/**", "/instances/**").permitAll();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, API + ALL).hasAnyAuthority(UserRole.ADMINISTRATOR.getValue());
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, API + ALL).hasAnyAuthority(UserRole.ADMINISTRATOR.getValue());
        http.authorizeRequests()
                .antMatchers(HttpMethod.PATCH, API + ALL).hasAnyAuthority(UserRole.ADMINISTRATOR.getValue());
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, API + ALL).hasAnyAuthority(UserRole.ADMINISTRATOR.getValue());
        http.authorizeRequests()
                .anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}