package com.kezlo.security.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfiguration {

    @Bean
    fun restTemplate(): RestTemplate{
        return RestTemplateBuilder()
            .build()
    }
}