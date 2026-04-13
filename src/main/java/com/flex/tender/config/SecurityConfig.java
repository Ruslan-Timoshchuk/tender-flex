package com.flex.tender.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.flex.tender.controller.filter.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Value("${allowed.origin.hosts}")
    private String allowedOriginHosts;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, 
                                                   final JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   final RequestMatcher publicEndpointsMatcher) throws Exception {
        http
          .csrf(AbstractHttpConfigurer::disable)
          .cors(httpSecurityCorsConfigurer -> 
                  httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
          .sessionManagement(session -> 
                               session
                                 .sessionCreationPolicy(STATELESS))
          .authorizeHttpRequests(requests -> 
                                   requests
                                     .requestMatchers(publicEndpointsMatcher).permitAll()
                                     .anyRequest().authenticated())
          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowedOrigins(List.of(allowedOriginHosts));
        configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public RequestMatcher publicEndpointsMatcher() {
        return new OrRequestMatcher(
                PathPatternRequestMatcher
                  .withDefaults()
                  .matcher(HttpMethod.POST, "/api/v1/authentication/login"));
    }
    
}