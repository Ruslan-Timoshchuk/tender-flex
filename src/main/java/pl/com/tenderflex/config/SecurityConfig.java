package pl.com.tenderflex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.com.tenderflex.model.Role;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/**/auth/**").permitAll()
            .antMatchers("/**/tender/create").hasAuthority(String.valueOf(Role.CONTRACTOR))
            .antMatchers("/**/tender/tenders_by_contractor").hasAuthority(String.valueOf(Role.CONTRACTOR))
            .antMatchers("/**/offer/received_offers").hasAuthority(String.valueOf(Role.CONTRACTOR))
            .antMatchers("/**/tender/all_tenders").hasAuthority(String.valueOf(Role.BIDDER))
            .antMatchers("/**/offer/create").hasAuthority(String.valueOf(Role.BIDDER))
            .anyRequest()
            .authenticated();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}