package com.kezlo.security.model

import org.springframework.security.core.GrantedAuthority

data class AuthenticationDto (
    val username: String,
    val pwd: String,
    val authorities: Collection<GrantedAuthority>,
    val loginType: String,
)