package pl.com.tenderflex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.com.tenderflex.model.Role;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
       
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/**/auth/**")
              .permitAll()
            .antMatchers("/**/tender/**")
              .hasAuthority(String.valueOf(Role.CONTRACTOR))
            .antMatchers("/**/global/countries")
              .hasAuthority(String.valueOf(Role.CONTRACTOR))
            .antMatchers("/**/global/tender-types")
              .hasAuthority(String.valueOf(Role.CONTRACTOR))
            .antMatchers("/**/global/currencies")
              .hasAuthority(String.valueOf(Role.CONTRACTOR))
            .antMatchers("/**/document/**")
              .hasAuthority(String.valueOf(Role.CONTRACTOR)) 
            .anyRequest()
              .authenticated()
            .and()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); 
        return http.build();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}