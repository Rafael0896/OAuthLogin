package edu.oauthlogin.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Endpoints públicos - NO requieren autenticación
                        .requestMatchers("/", "/login", "/error", "/webjars/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/api/health", "/api/auth/login-url", "/api/system/info").permitAll()
                        .requestMatchers("/auth/google/select-account", "/auth/logout-complete", "/auth/switch-account").permitAll()

                        // Endpoints de API públicos para testing con Postman
                        .requestMatchers("/api/auth/status", "/api/user/profile", "/api/auth/logout").permitAll()

                        // Solo endpoints de vistas requieren autenticación
                        .requestMatchers("/dashboard").authenticated()

                        // El resto permite acceso
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                        // Configuración adicional para forzar selección de cuenta
                        .authorizationEndpoint(authorization ->
                                authorization.baseUri("/oauth2/authorization")
                        )
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        // Deshabilitar CSRF para endpoints de API (solo para desarrollo/pruebas)
                        .ignoringRequestMatchers("/api/**")
                );

        return http.build();
    }
}