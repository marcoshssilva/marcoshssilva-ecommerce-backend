package br.com.marcoshssilva.ecommerce.core.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.marcoshssilva.ecommerce.core.security.JWTAuthenticationFilter;
import br.com.marcoshssilva.ecommerce.core.security.JWTAuthorizationFilter;
import br.com.marcoshssilva.ecommerce.rest.utils.JwtUtils;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@EnableGlobalAuthentication
public class SecurityConfig {

    private static final String[] PUBLIC_MATCHERS = {
        "/h2-console/**",};

    private static final String[] PUBLIC_MATCHERS_GET = {
        "/produtos/**",
        "/categorias/**",
        "/estados/**"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
        "/clientes"
    };

    @Autowired
    private Environment env;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain configure(HttpSecurity http, AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        AuthenticationManager authenticationManager = authenticationManagerBuilder.getOrBuild();

        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.cors().and().csrf().disable();

        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET)
                    .permitAll()
                .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST)
                    .permitAll()
                .requestMatchers(PUBLIC_MATCHERS)
                    .permitAll()
                .anyRequest()
                    .authenticated();

        http.addFilter(new JWTAuthenticationFilter(
                authenticationManager,
                jwtUtils));

        http.addFilter(new JWTAuthorizationFilter(
                authenticationManager,
                jwtUtils,
                userDetailsService));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration conf = new CorsConfiguration().applyPermitDefaultValues();
        conf.setAllowedMethods(Arrays.asList("POST", "GET", "DELETE", "PUT", "OPTIONS"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", conf);

        return source;
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
