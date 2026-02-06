package io.github.ngthduongg623.enterprise_manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
        http.authorizeHttpRequests(configurer->
                    configurer
                            .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                            .requestMatchers("/login", "/access-denied").permitAll()
                            .requestMatchers("/register/**") .permitAll()
                            // Feature specific roles
                            .requestMatchers("/accounts/**").hasAnyRole("ADMIN", "HR")
                            .requestMatchers("/employees/profile").authenticated()
                            .requestMatchers("/employees/**").hasAnyRole("ADMIN", "HR")
                            .requestMatchers("/payroll/my-history").authenticated()
                            .requestMatchers("/payroll/**").hasAnyRole("ADMIN", "HR")
                            .anyRequest().authenticated()
            ).formLogin(form->
                    form
                            .loginPage("/login")
                            .loginProcessingUrl("/login")
                            .successHandler(customAuthenticationSuccessHandler)
                            .permitAll()
            ).logout(logout->logout.permitAll()
            ).exceptionHandling(configurer->
                                        configurer.accessDeniedPage("/access-denied"));
        http.csrf(csrf->csrf.disable());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider provider(AccountAuthenticationService accountAuthService){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(accountAuthService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}