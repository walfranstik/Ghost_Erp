package com.ghost.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Activa la seguridad por métodos (@PreAuthorize)
public class SecurityConfig {

    // --- BEANS DE SEGURIDAD ---

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    // --- CONFIGURACIÓN DE FILTROS ---
    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        )

        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/css/**", "/js/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        )

        .formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .successHandler((req, res, auth) -> {
                // 1. Obtenemos los roles del usuario autenticado
                var roles = auth.getAuthorities().stream()
                        .map(r -> r.getAuthority())
                        .toList();

                // 2. Definimos la ruta destino según el rol
                String targetUrl = "/dashboard"; // Ruta por defecto

                if (roles.contains("ROLE_ADMIN")) {
                    targetUrl = "/admin/panel";
                } else if (roles.contains("ROLE_SALES_PERSON")) {
                    targetUrl = "/sales/pos"; // Ejemplo: Punto de venta para vendedores
                }

                // 3. Enviamos la cabecera especial para que HTMX haga la redirección completa
                res.setHeader("HX-Redirect", targetUrl);
            })
            .failureHandler((req, res, ex) -> {
                res.setStatus(200); 
                res.setContentType("text/html;charset=UTF-8");

                String message = "Credenciales inválidas";

                if (ex instanceof org.springframework.security.authentication.LockedException || 
                (ex.getCause() instanceof org.springframework.security.authentication.LockedException)) {
                    message = "Cuenta bloqueada temporalmente por exceso de intentos";
                } else if (ex instanceof org.springframework.security.authentication.DisabledException) {
                    message = "Su cuenta no está activa";
                }

                // 2. Devolvemos un fragmento de HTML con clases de Tailwind/DaisyUI
                String htmlResponse = String.format(
                "<div role='alert' class='alert alert-error shadow-lg mb-6 flex items-center gap-3 animate-pulse'>" +
                "  <svg xmlns='http://www.w3.org' class='stroke-current shrink-0 h-6 w-6' fill='none' viewBox='0 0 24 24'>" +
                "    <path stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='X' />" + // Icono de error simplificado
                "    <path stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z' />" +
                "  </svg>" +
                "  <span class='font-medium'>%s</span>" +
                "</div>", message);

                res.getWriter().write(htmlResponse);
            })
        )

        .logout(logout -> logout
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        )

        .sessionManagement(session -> session
            .sessionFixation().migrateSession()
            .maximumSessions(1) // 🔐 solo una sesión por usuario
        );

    return http.build();
}


}
