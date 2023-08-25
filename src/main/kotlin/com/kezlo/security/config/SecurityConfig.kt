package com.kezlo.security.config

import com.kezlo.security.filter.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    val jwtTokenValidatorFilter: JWTTokenValidatorFilter
) {

    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val requestHandler = CsrfTokenRequestAttributeHandler()
        requestHandler.setCsrfRequestAttributeName("_csrf")

        val jwtAuthenticationConverter: JwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter ( KeycloakRoleConverter() )

        http
            // Create JSESSIONID every time when login then return to client
            .securityContext { it.requireExplicitSave(false) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.ALWAYS) }

            // Tell spring not create any JSESSIONID, we will create our own
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .csrf {
                it.csrfTokenRequestHandler(requestHandler)
                    .ignoringRequestMatchers("/contact", "/register")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

            }
//            .csrf { it.disable() }
            .addFilterAfter(CsrfCookieFilter(), BasicAuthenticationFilter::class.java)
            .addFilterBefore(jwtTokenValidatorFilter, BasicAuthenticationFilter::class.java)
            .authorizeHttpRequests {
                it
                    .requestMatchers("/myAccount").hasRole("USER")
                    .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/myLoans").authenticated()
                    .requestMatchers("/myCards").hasRole("USER")
                    .requestMatchers("/user").authenticated()

                    .requestMatchers("/notices", "/contact", "/register", "/login/**").permitAll()
            }
//            .oauth2ResourceServer { it ->
//                it.jwt {
//                    it.
//                }
//            }
//            .formLogin { }
//            .httpBasic { }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {

        val config = CorsConfiguration()
        config.allowedOrigins = arrayListOf("http://localhost:4200")
        config.allowedMethods = arrayListOf("*")
        config.allowCredentials = true
        config.allowedHeaders = arrayListOf("*")
        config.exposedHeaders = arrayListOf("Authorization")
        config.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }
}