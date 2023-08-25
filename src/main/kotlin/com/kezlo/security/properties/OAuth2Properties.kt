package com.kezlo.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.oauth2")
class OAuth2Properties(
    var google: ThirdPartyProperties
)