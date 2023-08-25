package com.kezlo.security.properties

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(OAuth2Properties::class)
class ConfigurationProperties {
}