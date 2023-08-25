package com.kezlo.security.config

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import java.util.stream.Collectors

class KeycloakRoleConverter: Converter<Jwt, Collection<GrantedAuthority>>{
    override fun convert(jwt: Jwt): Collection<GrantedAuthority>? {
        val realmAccess :Map<String, Any> = jwt.claims.get("realm_access") as Map<String, Any>

        if(realmAccess.isEmpty()){
            return ArrayList()
        }

        return (realmAccess.get("roles") as List<String>)
            .stream().map { "ROLE_${it}" }
            .map { SimpleGrantedAuthority(it) }
            .collect(Collectors.toList())
    }
}