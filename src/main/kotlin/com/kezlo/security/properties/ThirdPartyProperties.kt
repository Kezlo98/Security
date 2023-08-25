package com.kezlo.security.properties

data class ThirdPartyProperties (
    val client:  ClientProperties,
    val link: LinkProperties,
    val redirectUrl: String
)