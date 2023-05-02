package app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan({"app.configs", "app.service", "app.dao"})
@EnableWebSecurity
public class WebSecurityConfig {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsService userDetailsService;


    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailsService userDetailsService) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**", "/update_user/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers("/registration/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form.loginPage("/login").successHandler(successUserHandler)
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);

        return authenticationProvider;
    }
}