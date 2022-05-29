package com.example.werehouse.configuration;


import com.example.werehouse.component.JwtAuthorizationFilter;
import com.example.werehouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                    .httpBasic()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(
                            new JwtAuthorizationFilter(authenticationManager(), userService),
                            UsernamePasswordAuthenticationFilter.class
                    );
    }
}
