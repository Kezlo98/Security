package com.kezlo.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.kezlo.security.client.impl.OAuth2ClientFactory
import com.kezlo.security.constant.OAuth2TypeEnum
import com.kezlo.security.constant.SecurityConstant
import com.kezlo.security.model.AuthenticationDto
import com.kezlo.security.model.OAuth2DTO
import com.kezlo.security.utils.JWTUtils
import com.kezlo.security.utils.JWTUtils.Companion.getClaimFromJWT
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets

@Component
class JWTTokenValidatorFilter(
    var oAuth2ClientFactory: OAuth2ClientFactory,
    val objectMapper: ObjectMapper
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getHeader(SecurityConstant.JWT_HEADER)
        if(jwt == null){
            filterChain.doFilter(request,response)
            return
        }

        val auth : Authentication? = if(StringUtils.startsWithIgnoreCase(jwt,SecurityConstant.BEARER)){
            validatorForOAuth2Token(request)
        } else {
            validatorForJWTToken(request, response, filterChain)
        }

        if(auth == null){
            throw BadCredentialsException("Invalid Token received")
        }

        SecurityContextHolder.getContext().authentication = auth
        filterChain.doFilter(request,response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath.equals("/user") ||
                request.servletPath.contains("/login/oauth2/code/")
    }

    private fun validatorForJWTToken(request: HttpServletRequest,
                                     response: HttpServletResponse,
                                     filterChain: FilterChain): UsernamePasswordAuthenticationToken?{
        val jwt = request.getHeader(SecurityConstant.JWT_HEADER)
        if(jwt == null){
            filterChain.doFilter(request,response)
            return null
        }

        try {
            val claims = getClaimFromJWT(jwt,objectMapper)

            val authDto = claims["authDto"] as AuthenticationDto

            return UsernamePasswordAuthenticationToken(
                authDto,null,authDto.authorities)
        } catch (ex: Exception){
            throw BadCredentialsException("Invalid Token received")
        }
    }

    private fun validatorForOAuth2Token(request: HttpServletRequest): UsernamePasswordAuthenticationToken {
        val token = request.getHeader(SecurityConstant.JWT_HEADER).substring(7)

        try {
            val claims = getClaimFromJWT(token,objectMapper)
            val oauthType = OAuth2TypeEnum.getTypeByIss(claims.issuer)
            val oAuth2Client = oAuth2ClientFactory.getOAuth2Client(oauthType)
            if(!oAuth2Client.verifyToken(token)){
                throw BadCredentialsException("Invalid Token received")
            }
            return UsernamePasswordAuthenticationToken(
                AuthenticationDto(claims["email"] as String, "", arrayListOf(SimpleGrantedAuthority("USER")),oauthType.name),
                null,
                arrayListOf(SimpleGrantedAuthority("USER")
            )
//            return OAuth2AuthenticationToken(
//                DefaultOAuth2User(arrayListOf(SimpleGrantedAuthority("USER")),attributes, "email"),
//                arrayListOf(SimpleGrantedAuthority("USER")),
//                claims["email"] as String
            )
        } catch (e: Exception){
            throw BadCredentialsException("Error: ${e.stackTraceToString()}")
        }

    }
}