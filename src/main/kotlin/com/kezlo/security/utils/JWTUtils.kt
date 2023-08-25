package com.kezlo.security.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.kezlo.security.constant.SecurityConstant
import com.kezlo.security.model.AuthenticationDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.impl.DefaultClaims
import io.jsonwebtoken.security.Keys
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

class JWTUtils {

    companion object {
        fun generateJWT(authDto: AuthenticationDto): String {
            var key = Keys.hmacShaKeyFor(SecurityConstant.JWT_KEY.toByteArray(StandardCharsets.UTF_8))
            var jwt = Jwts.builder()
                .setIssuer("OlzeK")
                .setSubject("JWT Token")
                .claim("authDto", authDto)
                .setExpiration(Date(Date().time + 30000000))
                .signWith(key).compact()

            return jwt;
        }

        fun getClaimFromJWT(jwt: String, objectMapper: ObjectMapper): Claims{
            val jwtPart = jwt.split(".")

            return objectMapper.readValue(String(Base64.getDecoder().decode(jwtPart[1]), Charset.defaultCharset()), DefaultClaims::class.java)
        }
    }
}