package com.ezshopping.config.security.config;

import static com.ezshopping.api.EndpointsAPI.*;
import com.ezshopping.config.security.filters.CustomAuthenticationFilter;
import com.ezshopping.config.security.filters.CustomAuthorizationFilter;
import com.ezshopping.user.UserRole;
import com.ezshopping.user.service.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), userDetailsService, userService);
        customAuthenticationFilter.setFilterProcessesUrl(API + LOGIN);

        http.cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(API + LOGIN + ALL, API + USERS + TOKEN + REFRESH + ALL, API + USERS + REGISTRATION + ALL, "/iot").permitAll()
                .antMatchers(API + "/arduino/**").permitAll()
                .antMatchers(API + "/arduino").permitAll()
                .antMatchers("/web-socket/**").permitAll()
                .antMatchers("/web-socket").permitAll()
                .antMatchers(HttpMethod.GET,"/actuator/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/actuator/**").permitAll()
                .antMatchers("/instances").permitAll()
                .antMatchers("/instances/**").permitAll()
                .antMatchers("/applications/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers(HttpMethod.GET, API + USERS + "/user*").hasAnyAuthority(UserRole.ADMINISTRATOR.getValue(), UserRole.CLIENT.getValue())
                .antMatchers(HttpMethod.PATCH, API + USERS + "/user/password*").hasAnyAuthority(UserRole.ADMINISTRATOR.getValue(), UserRole.CLIENT.getValue())
                .antMatchers(HttpMethod.GET, API + ALL).hasAnyAuthority(UserRole.ADMINISTRATOR.getValue())
                .antMatchers(HttpMethod.POST, API + ALL).hasAnyAuthority(UserRole.ADMINISTRATOR.getValue())
                .antMatchers(HttpMethod.PATCH, API + ALL).hasAnyAuthority(UserRole.ADMINISTRATOR.getValue())
                .antMatchers(HttpMethod.DELETE, API + ALL).hasAnyAuthority(UserRole.ADMINISTRATOR.getValue())
                .antMatchers(HttpMethod.OPTIONS, API + ALL).hasAnyAuthority(UserRole.ADMINISTRATOR.getValue())
                .anyRequest().authenticated().and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}