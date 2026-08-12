package in.tech_camp.furima.config;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                       .requestMatchers("/css/**", "/images/**", "/", "/users/sign_up", "/users/sign_in", "/error").permitAll()
                       .requestMatchers(HttpMethod.POST, "/users").permitAll()
                       .anyRequest().authenticated())

                .formLogin(login -> login
                        .loginProcessingUrl("/users/sign_in")
                        .loginPage("/users/sign_in")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/users/sign_in?error")
                        .usernameParameter("email")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/"));
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}