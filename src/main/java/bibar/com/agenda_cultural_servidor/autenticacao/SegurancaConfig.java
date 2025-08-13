package bibar.com.agenda_cultural_servidor.autenticacao;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import jakarta.servlet.Filter;


@Configuration
@EnableWebSecurity
public class SegurancaConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        // CORS
        Customizer<CorsConfigurer<HttpSecurity>> corsConfig;
        corsConfig = cors -> cors.configurationSource(
            request -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                return configuration;
            }
        );

        // CSRF
        Customizer<CsrfConfigurer<HttpSecurity>> csrfConfig;
        csrfConfig = csrf -> csrf.disable();

        // build
        SecurityFilterChain filterChain = httpSecurity
            .cors(corsConfig)
            .csrf(csrfConfig)
            .build();

        System.out.println("SegurancaConfig:    filtros da filterchain    <");
        for (Filter ff : filterChain.getFilters())
            System.out.println(ff.toString() + " - " + ff.getClass().getName());

        return filterChain;
    }
}
